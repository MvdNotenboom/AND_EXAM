package com.notenboom.and_exam_projecttracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notenboom.and_exam_projecttracker.database.ItemAdapter;
import com.notenboom.and_exam_projecttracker.fragment.FragmentStart;
import com.notenboom.and_exam_projecttracker.relief.KittenRelief;

import java.util.List;

/*************************************************************************
 * First Activity to be launched this is used for all the navigation and holding of the
 * main recycler view which holds all the items that are added from the database
 ************************************************************************/

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddMemo = findViewById(R.id.button_float_add);
        buttonAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpInItem.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAll().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                adapter.submitList(items);
            }
        });

/*************************************************************************
 * The following section enables swipe functionality to delete items in the recycler view
 * Has been uncommented because it can be confusing without instruction and accidental deletions are easy
************************************************************************/
/*
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;   //Unused but needed for the functionality so we simply return false
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                itemViewModel.delete(adapter.getMemoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, R.string.delete_item, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
*/

        adapter.setOnDeleteClickListener(new ItemAdapter.OnDeleteClickListener() {
            @Override
            public void onItemClick(Item item) {
                itemViewModel.delete(item);
                Toast.makeText(MainActivity.this, R.string.delete_item, Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Intent intent = new Intent(MainActivity.this, UpInItem.class);
                intent.putExtra(UpInItem.EXTRA_ID, item.getId());
                intent.putExtra(UpInItem.EXTRA_TITLE, item.getItemTitle());
                intent.putExtra(UpInItem.EXTRA_DESCRIPTION, item.getDescription());
                intent.putExtra(UpInItem.EXTRA_TYPE, item.getType());
                intent.putExtra(UpInItem.EXTRA_PRIORITY, item.getPriority());
                startActivityForResult(intent, EDIT_REQUEST);
            }
        });
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Intent intent = new Intent(MainActivity.this, UpInItem.class);
                intent.putExtra(UpInItem.EXTRA_ID, item.getId());
                intent.putExtra(UpInItem.EXTRA_TITLE, item.getItemTitle());
                intent.putExtra(UpInItem.EXTRA_DESCRIPTION, item.getDescription());
                intent.putExtra(UpInItem.EXTRA_TYPE, item.getType());
                intent.putExtra(UpInItem.EXTRA_PRIORITY, item.getPriority());
                startActivityForResult(intent, EDIT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(UpInItem.EXTRA_TITLE);
            String description = data.getStringExtra(UpInItem.EXTRA_DESCRIPTION);
            String type = data.getStringExtra(UpInItem.EXTRA_TYPE);
            int priority = data.getIntExtra(UpInItem.EXTRA_PRIORITY, 1);
            Item item = new Item(title, description, type, priority);
            itemViewModel.insert(item);
            Toast.makeText(this, R.string.item_saved, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(UpInItem.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, R.string.update_fail, Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(UpInItem.EXTRA_TITLE);
            String description = data.getStringExtra(UpInItem.EXTRA_DESCRIPTION);
            String type = data.getStringExtra(UpInItem.EXTRA_TYPE);
            int priority = data.getIntExtra(UpInItem.EXTRA_PRIORITY, 1);
            Item item = new Item(title, description, type, priority);
            item.setId(id);
            itemViewModel.update(item);
            Toast.makeText(this, R.string.update_succes, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.item_saved_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void toFragments() {
        Intent intentF = new Intent(MainActivity.this, FragmentStart.class);
        startActivity(intentF);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_items:
                itemViewModel.deleteAll();
                Toast.makeText(this, R.string.delete_all, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.relief:
                Intent intentR = new Intent(MainActivity.this, KittenRelief.class);
                startActivity(intentR);
                return true;
            case R.id.fragment:
                toFragments();
                return true;
            case R.id.maps:
                Intent intentM = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intentM);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
