package com.notenboom.and_exam_projecttracker.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.notenboom.and_exam_projecttracker.Item;
import com.notenboom.and_exam_projecttracker.R;

/*************************************************************************
 * Adapter built for the inclusion of the room database, This holds the main logic
 * between the database and the higher tiers in the app
 ************************************************************************/

public class ItemAdapter extends ListAdapter<Item, ItemAdapter.ItemHolder> {
    private OnItemClickListener listener;
    private OnDeleteClickListener listenerD;

    public ItemAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(Item oldItem, Item newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(Item oldItem, Item newItem) {
            return oldItem.getItemTitle().equals(newItem.getItemTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getType().equals(newItem.getType()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item currentItem = getItem(position);
        holder.textViewTitle.setText(currentItem.getItemTitle());
        holder.textViewDescription.setText(currentItem.getDescription());
        holder.textViewType.setText(currentItem.getType());
        holder.textViewPriority.setText(String.valueOf(currentItem.getPriority()));
    }

    public Item getItemAt(int position){
        return getItem(position);
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewType;
        private TextView textViewPriority;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
            ImageButton deleteProject = itemView.findViewById(R.id.button_project_delete);
            deleteProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listenerD != null && position != RecyclerView.NO_POSITION) {
                        listenerD.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnDeleteClickListener {
        void onItemClick(Item item);
    }
    public void setOnDeleteClickListener(OnDeleteClickListener listenerD) {
        this.listenerD = listenerD;
    }
}
