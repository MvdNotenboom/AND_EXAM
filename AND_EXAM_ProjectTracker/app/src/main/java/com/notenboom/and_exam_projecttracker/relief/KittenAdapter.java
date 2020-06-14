package com.notenboom.and_exam_projecttracker.relief;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.notenboom.and_exam_projecttracker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*************************************************************************
 * Adapter to connect to the recycler view of the relief section which will be populated
 * with data comming from a json object through a GET request
 * Did not work see comment in KittenRelief.java
 ************************************************************************/

public class KittenAdapter extends RecyclerView.Adapter<KittenAdapter.KittenViewHolder> {
    private Context context;
    private ArrayList<KittenItems> kittenList;

    public KittenAdapter(Context context, ArrayList<KittenItems> kittenList) {
        this.context = context;
        this.kittenList = kittenList;
    }

    @NonNull
    @Override
    public KittenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.kitten_item, parent, false);
        return new KittenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KittenViewHolder holder, int position) {
        KittenItems currentItem = kittenList.get(position);
        String imgUrl = currentItem.getImgUrl();

        Picasso.with(context).load(imgUrl).fit().centerInside().into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return kittenList.size();
    }

    public class KittenViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;

        public KittenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView.findViewById(R.id.kitten_image);
        }
    }
}
