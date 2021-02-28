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
        private final TextView transferAmtBox;
        private final TextView transferIDBox;
        private final TextView merchantBox;
        private final TextView dateBox;

        public ViewHolder(View view) {
            super(view);
            this.transferAmtBox = (TextView) view.findViewById(R.id.transferAmtBox);
            this.transferIDBox = (TextView) view.findViewById(R.id.transferIDBox);
            this.merchantBox = (TextView) view.findViewById(R.id.merchantBox);
            this.dateBox = (TextView) view.findViewById(R.id.dateBox);
        }

        public TextView getTransferAmtBox(){
            return transferAmtBox;
        }

        public TextView getTransferIDBox() {
            return transferIDBox;
        }

        public TextView getMerchantBox() {
            return merchantBox;
        }

        public TextView getDateBox() {
            return dateBox;
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
            if(obj.get(position).purchase!=null) {
                holder.getTransferAmtBox().setText(obj.get(position).transfer.getInt("amount"));
                holder.getTransferIDBox().setText(obj.get(position).transfer.getString("_id"));
                holder.getMerchantBox().setText(obj.get(position).purchase.getString("merchant_id"));
                holder.getDateBox().setText(obj.get(position).transfer.getString("transaction_date"));
            }
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
