package com.example.grocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocery.INTENTKEY;
import com.example.grocery.database.Repository;
import com.example.grocery.databinding.ActivityBillDactivityBinding;
import com.example.grocery.models.Bill;

import java.util.List;

public class BillDActivity extends AppCompatActivity {
    private Repository repository;
    private LiveData<List<Bill>> listBill;
    private BillDAdapter billDAdapter;
    private ActivityBillDactivityBinding binding;
    private int cID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityBillDactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        cID=intent.getIntExtra(INTENTKEY.CID,0);
        repository=new Repository(getApplication());
        billDAdapter=new BillDAdapter(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        listBill= repository.getListBill(cID);
        binding.rcvBill.setAdapter(billDAdapter);
        binding.rcvBill.setLayoutManager(layoutManager);
        listBill.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                billDAdapter.setListBill(bills);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}