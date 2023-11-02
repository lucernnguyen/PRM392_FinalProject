package com.example.grocery.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.databinding.ItemProductBinding;
import com.example.grocery.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context context;
    private List<Product> listProduct=new ArrayList<>();
    public IProduct iProduct;

    public MainAdapter(Context context,IProduct iProduct) {
        this.context = context;
        this.iProduct=iProduct;
    }


    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
        notifyDataSetChanged();
    }

    public interface IProduct{
        public void onCLick(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_product,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=listProduct.get(position);
        holder.binding.txtBookName.setText(product.getName());
        holder.binding.txtPrice.setText(product.getPrice()+"");
        holder.binding.txtPriceD.setText(product.getPriceD() + "");
        holder.binding.txtDate.setText(product.getDate());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.ic_error)
                .into(holder.binding.imgProduct);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iProduct.onCLick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ItemProductBinding binding;

        public ViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}

