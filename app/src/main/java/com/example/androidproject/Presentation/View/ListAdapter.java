package com.example.androidproject.Presentation.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Presentation.Model.Pokemon;
import com.example.androidproject.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Pokemon> values;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Pokemon item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        TextView txtFooter;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Pokemon pokemon) {
        values.add(position, pokemon);
        notifyItemInserted(position);
    }

    private void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public ListAdapter(List<Pokemon> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Pokemon currentPokemon = values.get(position);
        holder.txtHeader.setText(currentPokemon.getName());
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentPokemon);
            }
        });
        holder.txtFooter.setText(currentPokemon.getUrl());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
