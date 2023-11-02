package com.example.grocery.Activity;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.grocery.database.Repository;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Comment;
import com.example.grocery.models.Product;

import java.util.List;
import java.util.Objects;

public class DetailViewModel extends AndroidViewModel {
    private MutableLiveData<Product> p=new MutableLiveData<>();
    private LiveData<List<Comment>> listComment=new MutableLiveData<>();
    private Repository repository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);

    }
    public LiveData<List<Comment>> getAllComment(int id){
        listComment=repository.getAllComment(id);
        return listComment;
    }
    public void Comment(Comment comment){
        repository.Comment(comment);
        Log.d("COmmmnet","comment");
    }
    public boolean insertCart(Cart cart){
        boolean status=repository.inserCart(cart);
        return status;
    }
    public String getCusNameById(int id){
        return repository.getCusNameById(id);
    }
    public String getName(){
        return Objects.requireNonNull(p.getValue()).getName();
    }
    public String getDate(){
        return Objects.requireNonNull(p.getValue()).getDate();
    }
    public String getPrice(){
        return Objects.requireNonNull(p.getValue()).getPrice()+"";
    }
    public String getPriceD(){
        return Objects.requireNonNull(p.getValue()).getPriceD()+"";
    }
    public float getRate(){
        return (float) p.getValue().getRate();
    }
    public String getAuthorName(){
       return repository.getAuthorName(p.getValue().getaID());
    }

}
