package com.example.grocery.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.grocery.models.Bill;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Comment;
import com.example.grocery.models.Customer;
import com.example.grocery.models.Product;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Repository {
    String TAG="REPOSITORY";
    private DAO dao;
    private Application application;
    private LiveData<List<Product>> allProduct;
    private LiveData<List<Comment>> allComment;

    private LiveData<List<Cart>> allCart;
    private LiveData<Integer> getCountCart;
    public Repository(Application application){
        MyDatabase myDatabase=MyDatabase.getInstance(application);
        dao=myDatabase.dao();
        this.application=application;
        allProduct=dao.allProduct();

    }
    public Repository(Application application,int cID){
        MyDatabase myDatabase=MyDatabase.getInstance(application);
        dao=myDatabase.dao();
        this.application=application;
        allCart=dao.allCart(cID);
    }
    public boolean register(Customer customer){
        try {
            new RegisterCustomer(dao).execute(customer);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void updateCart(Cart cart){
        new UpdateCartAsy(dao).execute(cart);
    }
    public void deleteCart(Cart cart){
        new DeleteCartAsy(dao).execute(cart);
    }
    public void deleteListCart(List<Cart> lists){
        new DeleteListCartAsy(dao).execute(lists);
    }
    public void insertBill(Bill bill){
        new InsertBillAsy(dao).execute(bill);
    }
    public void updateCustomer(Customer customer){
        new UpdateCustomer(dao).execute(customer);
    }
    public LiveData<List<Bill>> getListBill(int cID){
        return dao.listBill(cID);
    }
    public LiveData<List<Comment>> getAllComment(int id){
        allComment=dao.allComment(id);
        return allComment;
    }
    public int login(String username,String pass){
        Callable<Integer> callable=new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                if(!username.isEmpty()&&!pass.isEmpty()){
                    result =dao.login(username,pass);
                }

                return result;
            }
        };
        Future<Integer> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            Log.d(TAG,future.get()+"");
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount(int cID){
        Callable<Integer> callable=new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                result=dao.getCountBill(cID);

                return result;
            }
        };
        Future<Integer> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            Log.d(TAG,future.get()+"");
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public String getCusNameById(int id){
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                String result = null;
                if(id!=0){
                    result =dao.getCusNameById(id);
                }
                return result;
            }
        };
        Future<String> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public Customer getCustomerById(int id){
        Callable<Customer> callable=new Callable<Customer>() {
            @Override
            public Customer call() throws Exception {
                Customer result = null;
                if(id!=0){
                    result =dao.getCustomerById(id);
                }
                return result;
            }
        };
        Future<Customer> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public Product getProductById(int pID){
        Callable<Product> callable=new Callable<Product>() {
            @Override
            public Product call() throws Exception {
                Product result = null;
                if(pID!=0){
                    result =dao.getProductById(pID);
                }
                return result;
            }
        };
        Future<Product> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<List<Cart>> getAllCart() {
        return allCart;
    }

    public LiveData<List<Product>> getAllProduct() {
        return allProduct;
    }
    public void Comment(Comment comment){
        Log.d(TAG,comment.getpID()+"");
        new CommentAsy(dao).execute(comment);
    }
    public LiveData<Integer> getCountCart(int cID){
        return dao.getCountCart(cID);
    }

    public boolean inserCart(Cart cart){
        try{
            new InsertCartAsy(dao).execute(cart);
            return true;
        }catch (Exception exception){
            return false;
        }
    }
    public String getAuthorName(int id){
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                String result = null;
                if(id!=0){
                    result =dao.getAuthorName(id);
                }
                return result;
            }
        };
        Future<String> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static class RegisterCustomer extends AsyncTask<Customer,Void,Void>{
        private DAO dao;
        private RegisterCustomer(DAO dao){
            this.dao=dao;
        }
        @Override
        protected Void doInBackground(Customer... customers) {
            dao.register(customers[0]);
            return null;
        }
    }

    private static class CommentAsy extends AsyncTask<Comment,Void,Void>{
        private DAO dao;
        private CommentAsy(DAO dao){
            this.dao=dao;
        }
        @Override
        protected Void doInBackground(Comment... comments) {
            dao.insertComment(comments[0]);
            return null;
        }
    }

    private static class InsertCartAsy extends AsyncTask<Cart,Void,Void>{
        private DAO dao;
        private InsertCartAsy(DAO dao){
            this.dao=dao;
        }
        @Override
        protected Void doInBackground(Cart... carts) {

            try{
                dao.insertCart(carts[0]);
            }catch (Exception exception){

            }
            return null;
        }
    }

    private static class UpdateCartAsy extends AsyncTask<Cart,Void,Void>{
        private DAO dao;
        private UpdateCartAsy(DAO dao){
            this.dao=dao;
        }
        @Override
        protected Void doInBackground(Cart... carts) {
            dao.updateCart(carts[0]);
            return null;
        }
    }
    private static class DeleteCartAsy extends AsyncTask<Cart,Void,Void>{
        private DAO dao;
        private DeleteCartAsy(DAO dao){
            this.dao=dao;
        }
        @Override
        protected Void doInBackground(Cart... carts) {
            dao.deleteCart(carts[0]);
            return null;
        }
    }
    private static class DeleteListCartAsy extends AsyncTask<List<Cart>,Void,Void>{
        private DAO dao;
        private DeleteListCartAsy(DAO dao){
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(List<Cart>... lists) {
            dao.deleteListCart(lists[0]);
            return null;
        }
    }
    private static class InsertBillAsy extends AsyncTask<Bill,Void,Void>{
        private DAO dao;
        private InsertBillAsy(DAO dao){
            this.dao=dao;
        }


        @Override
        protected Void doInBackground(Bill... bills) {
            dao.insertBill(bills[0]);
            return null;
        }
    }
    private static class UpdateCustomer extends AsyncTask<Customer,Void,Void>{
        private DAO dao;
        private UpdateCustomer(DAO dao){
            this.dao=dao;
        }
        @Override
        protected Void doInBackground(Customer... customers) {
            dao.updateCustomer(customers[0]);
            return null;
        }
    }


}
