package com.example.grocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.grocery.INTENTKEY;
import com.example.grocery.R;
import com.example.grocery.databinding.ActivityMainBinding;
import com.example.grocery.models.Customer;
import com.example.grocery.models.Product;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private MainAdapter mainAdapter;
    private final String TAG = "MainActivity";
    private int notifiID=1;
    private int cID;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
            init();
    }

    private void init() {
        Intent intent = getIntent();
        cID = intent.getIntExtra(INTENTKEY.CID, 0);
        customer=mainViewModel.getRepository().getCustomerById(cID);
        binding.txtCInfor.setText(customer.getName());
        binding.txtBalance.setText("Account Balance: "+customer.getaBalace());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rcvMain.setLayoutManager(layoutManager);
        binding.rcvMain.setHasFixedSize(true);
        mainAdapter = new MainAdapter(this, new MainAdapter.IProduct() {
            @Override
            public void onCLick(int position) {
                Product p = mainViewModel.getListP().getValue().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(INTENTKEY.TRANSFER_PRODUCT, p);
                intent.putExtra(INTENTKEY.CID, cID);
                startActivity(intent);
            }
        });
        binding.rcvMain.setAdapter(mainAdapter);
        mainViewModel.getListP().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mainAdapter.setListProduct(products);
                Log.d(TAG, products.size() + "");

            }
        });
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MainActivity.this, CartActivity.class);
                intent1.putExtra(INTENTKEY.CID, cID);
                startActivity(intent1);
            }
        });
        binding.btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, BillDActivity.class);
                intent1.putExtra(INTENTKEY.CID, cID);
                startActivity(intent1);
            }
        });
        binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float destinationLatitude=10.8469938f;
                float destinationLongitude=106.6360576f;
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", destinationLatitude, destinationLongitude, "Tap hoa Vo Nguyen");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent1.setPackage("com.google.android.apps.maps");
                startActivity(intent1);
            }
        });
        mainViewModel.setCountCart(cID);
        int countCart = mainViewModel.getCountCart();

        if (countCart > 0) {
            Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification.Builder builder = new Notification.Builder(this,MyApplication.CHANNEL_ID);
            builder.setContentTitle("Fahasha")
                    .setContentText("Check your cart")
                    .setSound(uri)
                    .setSmallIcon(R.mipmap.ic_launcher);

            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null){
                notificationManager.notify(notifiID,notification);
                Log.d(TAG,countCart+"Count cart");
            }


        }
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        customer=mainViewModel.getRepository().getCustomerById(cID);
        binding.txtCInfor.setText("Hello "+customer.getName());
        binding.txtBalance.setText("Account Balance: "+customer.getaBalace());
    }
}