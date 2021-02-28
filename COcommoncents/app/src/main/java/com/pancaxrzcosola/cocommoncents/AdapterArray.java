package com.pancaxrzcosola.cocommoncents;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class AdapterArray extends RecyclerView.Adapter<AdapterArray.ViewHolder> {
    ArrayList<PTransfer> obj;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getTextView(){
            return textView;
        }
    }

    public AdapterArray(ArrayList<PTransfer> pt){
        Log.i("constructer","It do");
        obj = pt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        Log.i("create?","poggers");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("TagString","Element "+position);
        try {
            holder.getTextView().setText("ID: "+obj.get(position).purchase.getString("_id")+" Amount: "+obj.get(position).purchase.getInt("amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        Log.i("size?",obj.size()+"");
        return obj.size();
    }


}
