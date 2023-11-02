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
import android.widget.Toast;

import com.example.grocery.INTENTKEY;
import com.example.grocery.R;
import com.example.grocery.database.Repository;
import com.example.grocery.databinding.ActivityDetailBinding;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Comment;
import com.example.grocery.models.Product;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private Product p;
    private Repository repository;
    private int cID;
    private CommentAdapter commentAdapter;
    private DetailViewModel detailViewModel;
    private final String TAG="DETAILACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        detailViewModel= new ViewModelProvider(this).get(DetailViewModel.class);
        init();
    }
    private void init(){
        Intent intent=getIntent();
        p= (Product) intent.getSerializableExtra(INTENTKEY.TRANSFER_PRODUCT);
        cID=intent.getIntExtra(INTENTKEY.CID,0);
        repository=new Repository(getApplication());
        Picasso.with(this).load(p.getImage())
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.icon_loading)
                .into(binding.image);
        binding.txtBookName.setText(p.getName());
        binding.bookRating.setRating((float) p.getRate());
        binding.txtPrice.setText(p.getPrice()+"");
        binding.txtPriceD.setText("Price: "+p.getPriceD()+"");
        binding.txtDescription.setText(p.getDescription());
        binding.authorName.setText(repository.getAuthorName(p.getaID()));
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Comment();
        InserCart();
    }
    private void Comment(){
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        commentAdapter=new CommentAdapter(this,detailViewModel);
        binding.rcvComment.setHasFixedSize(true);
        binding.rcvComment.setLayoutManager(layoutManager);
        binding.rcvComment.setAdapter(commentAdapter);
        detailViewModel.getAllComment(p.getaID()).observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                commentAdapter.setListComment(comments);
                Log.d(TAG,comments.size()+"");
            }
        });


        binding.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=binding.txtComment.getText().toString();
                if(!content.isEmpty()){
                    String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
                    Comment comment=new Comment(0,p.getId(),cID,date,content);
                    detailViewModel.Comment(comment);
                    Toast.makeText(DetailActivity.this,"Comment success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailActivity.this,"Enter comment",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void InserCart(){
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart=new Cart(cID,p.getId(),1,p.getPrice());
                try {
                    if(detailViewModel.insertCart(cart)){
                        Toast.makeText(DetailActivity.this,"Add to cart success",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DetailActivity.this,"Add to cart fail",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(DetailActivity.this,"Add to cart success",Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(DetailActivity.this,"Add to cart fail",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}