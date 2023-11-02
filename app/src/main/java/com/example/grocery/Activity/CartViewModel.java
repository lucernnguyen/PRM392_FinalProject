package com.example.grocery.Activity;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocery.database.Repository;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Cart>> allCart;
    private int cID;

    private MutableLiveData<ArrayList<Cart>> _listSelect=new MutableLiveData<>();



    public CartViewModel(Application application,int cID) {
        repository=new Repository(application,cID);
        this.cID=cID;
        allCart= repository.getAllCart();
        _listSelect.postValue(new ArrayList<>());

    }

    public LiveData<ArrayList<Cart>> getListSelect(){
        return _listSelect;
    }
    public void addListSelect(ArrayList<Cart> listCart){

        _listSelect.setValue(listCart);
    }
    public Cart getCartEqual(Cart cart){
        for (Cart c:_listSelect.getValue()
             ) {
            if(c.getpID()==cart.getpID()&&c.getcID()==cart.getcID()){
                return c;
            }
        }
        return cart;
    }

    public void updateCart(Cart cart){
        repository.updateCart(cart);
    }
    public void deleteCart(Cart cart){
        repository.deleteCart(cart);
    }
    public void deleteListCart(List<Cart> lists){
        repository.deleteListCart(lists);
    }
    public Product getProductById(int pID){
        return repository.getProductById(pID);
    }

    public LiveData<List<Cart>> getAllCart() {
        return allCart;
    }
    public int getcID(){
        return cID;
    }
}
