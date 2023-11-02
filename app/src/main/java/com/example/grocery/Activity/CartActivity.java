package com.example.grocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.grocery.INTENTKEY;
import com.example.grocery.databinding.ActivityCartBinding;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private ViewModelFactory viewModelFactory;
    private CartViewModel cartViewModel;
    private int cID;
    private CartAdapter cartAdapter;
    private final String TAG="CARTACTIVITY";
    private double sumSelect=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }
    private void init(){
        binding.txtPrice.setText(sumSelect+"");
        Intent intent=getIntent();
        cID=intent.getIntExtra(INTENTKEY.CID,0);
        viewModelFactory=new ViewModelFactory(getApplication(),cID);
        cartViewModel= new ViewModelProvider(this,viewModelFactory).get(CartViewModel.class);
        cartViewModel.getListSelect().observe(this, new Observer<ArrayList<Cart>>() {
            @Override
            public void onChanged(ArrayList<Cart> carts) {
                sumSelect=0;
                for (Cart c:carts
                     ) {
                    Product p=cartViewModel.getProductById(c.getpID());
                    sumSelect+= c.sl*p.price;
                }
                if(carts.size()>0){
                    binding.btnDelete.setVisibility(View.VISIBLE);
                    binding.btnCheckOut.setEnabled(true);
                }else {
                    binding.btnDelete.setVisibility(View.INVISIBLE);
                    binding.btnCheckOut.setEnabled(false);
                }
                binding.txtPrice.setText("$"+sumSelect);
                Log.d(TAG,"Select list"+carts.size()+"");
            }
        });
        cartAdapter=new CartAdapter(this, cartViewModel, new CartAdapter.ICart() {
            @Override
            public void onCLick(int position,boolean status) {
                Cart cart=cartViewModel.getAllCart().getValue().get(position);
                ArrayList<Cart> listCartS=cartViewModel.getListSelect().getValue();
                Cart sC=cartViewModel.getCartEqual(cart);
                Log.d(TAG,"on click"+listCartS.size());
                if(status){
                    listCartS.add(cart);
                }else {
                    listCartS.remove(sC);
                }
                Log.d(TAG,"on click"+listCartS.size());
                cartViewModel.addListSelect(listCartS);
            }

            @Override
            public void onCLickSL(Boolean status,int position,boolean sStatus) {
                ArrayList<Cart> listCartS=cartViewModel.getListSelect().getValue();
                Cart c=cartViewModel.getAllCart().getValue().get(position);
                Cart sC=cartViewModel.getCartEqual(c);
                if(sStatus){
                    if(status){
                        listCartS.remove(sC);
                        Log.d(TAG,"add "+listCartS.size());
                        c.setSl(c.getSl()+1);
                        sC.setSl(sC.getSl()+1);
                        cartViewModel.updateCart(c);
                        listCartS.add(sC);
                        cartViewModel.addListSelect(listCartS);
                    }else {
                        listCartS.remove(sC);
                        c.setSl(c.getSl()-1);
                        sC.setSl(sC.getSl()-1);
                        if(c.getSl()>0){
                            cartViewModel.updateCart(c);
                            listCartS.add(sC);
                            cartViewModel.addListSelect(listCartS);
                        }else {
                            cartViewModel.deleteCart(c);
                            listCartS.remove(sC);
                            cartViewModel.addListSelect(listCartS);
                        }
                    }
                }else {
                    if(status){
                        c.setSl(c.getSl()+1);
                        cartViewModel.updateCart(c);
                    }else {
                        c.setSl(c.getSl()-1);
                        if(c.getSl()>0){
                            cartViewModel.updateCart(c);
                        }else {
                            cartViewModel.deleteCart(c);
                        }
                    }
                }

            }
        });
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        binding.rcvCart.setAdapter(cartAdapter);
        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setHasFixedSize(true);
        cartViewModel.getAllCart().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                cartAdapter.setListCart(carts);
                cartViewModel.addListSelect(new ArrayList<>());
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.deleteListCart(cartViewModel.getListSelect().getValue());
                cartViewModel.addListSelect(new ArrayList<>());
                cartAdapter.setListCart(cartViewModel.getListSelect().getValue());
                cartAdapter.setUnCheckAll(true);
                cartAdapter.notifyDataSetChanged();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(CartActivity.this, BillActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList(INTENTKEY.TRANSFER_LISTCART,cartViewModel.getListSelect().getValue());
                bundle.putInt(INTENTKEY.CID,cID);
                bundle.putDouble(INTENTKEY.TRANSFER_PRICE,sumSelect);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
    }
}