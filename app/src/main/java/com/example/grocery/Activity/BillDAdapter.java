package com.example.grocery.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.databinding.ItemBillDBinding;
import com.example.grocery.models.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDAdapter extends RecyclerView.Adapter<BillDAdapter.ViewHolder> {
    private Context context;
    private List<Bill> listBill=new ArrayList<>();
    public BillDAdapter.IBill iBill;

    public BillDAdapter(Context context) {
        this.context = context;
    }


    public void setListBill(List<Bill> listBill) {
        this.listBill = listBill;
        notifyDataSetChanged();
    }

    public interface IBill{
        public void onCLick(int position);
    }
    @NonNull
    @Override
    public BillDAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBillDBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_bill_d,parent,false);

        return new BillDAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDAdapter.ViewHolder holder, int position) {
        Bill bill=listBill.get(position);
        holder.binding.txtPrice.setText(bill.getPrice()+"");
        holder.binding.txtDate.setText("Date: "+bill.getDate());
        holder.binding.txtBillId.setText("Bill id: "+bill.getId());
    }

    @Override
    public int getItemCount() {
        return listBill.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ItemBillDBinding binding;
        public ViewHolder(@NonNull ItemBillDBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
