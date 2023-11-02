package com.example.grocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.TypeConverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.grocery.INTENTKEY;
import com.example.grocery.database.Repository;
import com.example.grocery.databinding.ActivityBillBinding;

import com.example.grocery.models.Bill;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Customer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    private ArrayList<Cart> listCart;
    private Customer customer;
    private double price;
    private Repository repository;
    private BillAdapter billAdapter;

    private int cID;
    private double sPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        repository=new Repository(getApplication());
        sPrice=intent.getDoubleExtra(INTENTKEY.TRANSFER_PRICE,0);
        cID=intent.getIntExtra(INTENTKEY.CID,0);
        listCart= (ArrayList<Cart>) intent.getSerializableExtra(INTENTKEY.TRANSFER_LISTCART);
        customer=repository.getCustomerById(intent.getIntExtra(INTENTKEY.CID,0));
        binding.txtCName.setText(customer.getName());
        binding.txtCPhone.setText(customer.getSdt());
        billAdapter=new BillAdapter(this,repository);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setAdapter(billAdapter);
        billAdapter.setListBill(listCart);
        binding.txtPrice.setText("$"+sPrice);
        binding.txtBalance.setText("Balance: $"+customer.getaBalace());
        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location=binding.txtLocation.getText().toString();
                String name=binding.txtCName.getText().toString();
                String std=binding.txtCPhone.getText().toString();
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                JSONArray jsonArray;
                try {
                    jsonArray=new JSONArray(listCart.toArray());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String mlistCart=listToJsonString(jsonArray);
                int status=1;
                Bill bill=new Bill(0,cID,location,name,std,sPrice,timeStamp,mlistCart,status);
               if(location.isEmpty()||name.isEmpty()||std.isEmpty()){
                   Toast.makeText(BillActivity.this, "Enter enough fields", Toast.LENGTH_SHORT).show();
               }else {
                   if(sPrice>customer.getaBalace()){
                       Toast.makeText(BillActivity.this,"Customer balance don't enough",Toast.LENGTH_SHORT).show();
                   }else {
                       repository.insertBill(bill);
                       repository.deleteListCart(listCart);
                       customer.setaBalace((float) (customer.getaBalace()-sPrice));
                       repository.updateCustomer(customer);
                       Toast.makeText(BillActivity.this,"Check out success",Toast.LENGTH_SHORT).show();
                       finish();
                   }
               }
            }
        });
    }
    @TypeConverter
    private String listToJsonString(JSONArray values){
        return new Gson().toJson(values);
    }
}