package com.example.mapproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myviewholder>  {
    public interface OnNoteListener{
        void onItemClick(int position);
    }

    ArrayList<Item>items ;
    ArrayList<Item> backup;
    public OnNoteListener onNoteListener;
    MyAdapter(ArrayList<Item> items, OnNoteListener onNoteListener)
    {
        this.items = items;
        backup = new ArrayList<>(items);
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v =layoutInflater.inflate(R.layout.activity_item_layout,parent,false);
        Myviewholder myviewholder = new Myviewholder(v, onNoteListener);
        return myviewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        Item item = items.get(position);
        String imageTitle = item.getImgTitle();
        int imgRef = item.getImgRef();
        String location = item.getLoc();
        int r = item.getRate();
        holder.textView.setText(imageTitle);
        holder.tv2.setText("Location: "+location);
        holder.tv3.setText("Rs."+r+"/Quintal");
        holder.imageView.setImageResource(imgRef);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Item> filterData = new ArrayList<>() ;
            if(keyword.toString().isEmpty())
            {
                filterData.addAll(backup);
            }
            else {
                for(Item obj : backup){
                    if(obj.getImgTitle().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filterData.add(obj);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((ArrayList<Item>)results.values);
            notifyDataSetChanged();

        }
    };


    public class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView;
        TextView textView, tv2, tv3;
        RelativeLayout relativeLayout;
        Myviewholder(View view, OnNoteListener onNoteListener){
            super(view);
            imageView = view.findViewById(R.id.im1);
            textView = view.findViewById(R.id.tv1);
            tv2 = view.findViewById(R.id.tv2);
            tv3 = view.findViewById(R.id.tv3);
            relativeLayout = view.findViewById(R.id.relativeL);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            onNoteListener.onItemClick(getAdapterPosition());
        }
    }


}