package com.example.grocery.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.databinding.ItemCartBinding;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<Cart> listCart=new ArrayList<>();
    public CartAdapter.ICart iCart;
    public CartViewModel cartViewModel;
    public boolean unCheckAll=false;

    public CartAdapter(Context context,CartViewModel cartViewModel, CartAdapter.ICart iCart) {
        this.context = context;
        this.iCart=iCart;
        this.cartViewModel=cartViewModel;
    }

    public void setUnCheckAll(boolean status){
        unCheckAll=status;
        notifyDataSetChanged();
    }
    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
        notifyDataSetChanged();
    }

    public interface ICart{
        public void onCLick(int position,boolean status);
        void onCLickSL(Boolean status,int position,boolean sStatus);
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cart,parent,false);

        return new CartAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart=listCart.get(position);
        Product product=cartViewModel.getProductById(cart.getpID());
        holder.binding.txtBookName.setText(product.getName());
        holder.binding.txtPrice.setText(product.getPrice()+"");
        holder.binding.txtPriceD.setText(product.getPriceD() + "");
        holder.binding.txtSL.setText(cart.getSl()+"");
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.ic_error)
                .into(holder.binding.imgProduct);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.btnCheck.setChecked(!holder.binding.btnCheck.isChecked());
                int status=(holder.binding.btnCheck.isChecked()==true) ?View.VISIBLE:View.GONE;
                holder.binding.btnCheck.setVisibility(status);
                iCart.onCLick(position,holder.binding.btnCheck.isChecked());
                notifyDataSetChanged();

            }
        });
        if(unCheckAll){
            holder.binding.btnCheck.setChecked(false);
            int status=(holder.binding.btnCheck.isChecked()==true) ?View.VISIBLE:View.GONE;
            holder.binding.btnCheck.setVisibility(status);

        }
        if(position==listCart.size()-1){
            unCheckAll=false;
        }
        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCart.onCLickSL(true,position,holder.binding.btnCheck.isChecked());
                notifyDataSetChanged();
            }
        });
        holder.binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCart.onCLickSL(false,position,holder.binding.btnCheck.isChecked());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ItemCartBinding binding;
        public ViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
