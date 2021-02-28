package com.pancaxrzcosola.cocommoncents;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class AdapterArray extends RecyclerView.Adapter<AdapterArray.ViewHolder> {
    PTransfer obj;

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

    public AdapterArray(PTransfer pt){
        obj = pt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("TagString","Element "+position);
        try {
            holder.getTextView().setText("ID: "+obj.purchase.getString("_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 25;
    }


}
