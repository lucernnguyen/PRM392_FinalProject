package com.example.grocery.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.database.Repository;
import com.example.grocery.databinding.ItemBillBinding;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Context context;
    private List<Cart> listCart=new ArrayList<>();
    public BillAdapter.IBill iBill;
    private Repository repository;
    private double Sprice;

    public BillAdapter(Context context, Repository repository) {
        this.context = context;
        this.repository=repository;
    }
    public double getSprice(){
        return Sprice;
    }


    public void setListBill(List<Cart> listCart) {
        this.listCart = listCart;
        notifyDataSetChanged();
    }

    public interface IBill{
        public void onCLick(int position);
    }
    @NonNull
    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBillBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_bill,parent,false);

        return new BillAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.ViewHolder holder, int position) {
        Cart cart=listCart.get(position);
        Product p=repository.getProductById(cart.getpID());
        double price=p.getPrice()*cart.getSl();
        Sprice+=price;
        String sl="Price: "+p.getPrice()+"x"+cart.getSl();
        holder.binding.txtBookName.setText(p.getName());
        holder.binding.txtPrice.setText("$"+price);
        holder.binding.txtSL.setText(sl);
        Picasso.with(context).load(p.getImage())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.ic_error)
                .into(holder.binding.imgProduct);

    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ItemBillBinding binding;
        public ViewHolder(@NonNull ItemBillBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}

