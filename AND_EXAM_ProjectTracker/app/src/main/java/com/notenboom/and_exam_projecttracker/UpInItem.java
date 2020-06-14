package com.notenboom.and_exam_projecttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*************************************************************************
 * A class reached by pressing the add button or by pressing an existing item
 * All functionality is here to add new or edit existing items
 ************************************************************************/

public class UpInItem extends AppCompatActivity {
    public final static String EXTRA_ID = "com.notenboom.project_tracker.EXTRA_ID";
    public final static String EXTRA_TITLE = "com.notenboom.project_tracker.EXTRA_TITLE";
    public final static String EXTRA_DESCRIPTION = "com.notenboom.project_tracker.EXTRA_DESCRIPTION";
    public final static String EXTRA_TYPE = "com.notenboom.project_tracker.EXTRA_TYPE";
    public final static String EXTRA_PRIORITY = "com.notenboom.project_tracker.EXTRA_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextdescription;
    private EditText editType;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upin_item);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextdescription = findViewById(R.id.edit_text_description);
        editType = findViewById(R.id.edit_text_type);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(0);
        numberPickerPriority.setMaxValue(9);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Item");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextdescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editType.setText(intent.getStringExtra(EXTRA_TYPE));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 0));
        } else {
            setTitle("Add Item");
        }
    }

    private void saveItem() {
        String title = editTextTitle.getText().toString();
        String description = editTextdescription.getText().toString();
        String type = editType.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, R.string.missing_info, Toast.LENGTH_LONG).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveItem();
                return true;
            case R.id.return_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}