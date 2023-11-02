package com.example.grocery.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.grocery.models.Producer;
import com.example.grocery.models.Bill;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Comment;
import com.example.grocery.models.Customer;
import com.example.grocery.models.Product;

import java.util.List;
@Dao
public interface DAO {
    @Query("Select * from Product")
    public LiveData<List<Product>> allProduct();

    @Query("Select * from Product where id=:pID")
    public Product getProductById(int pID);

    @Query("Select id from Customer where username= :username and password=:pass")
    public int login(String username, String pass);

    @Insert
    void insertProduct(Product p);
    @Update
    void updateCustomer(Customer customer);

    @Insert
    void insertAuthor(Producer producer);

    @Insert
    public void register(Customer customer);

    @Query("Select name from Producer where id =:id")
    public String getAuthorName(int id);

    @Query("Select * from Comment where pID=:id")
    public LiveData<List<Comment>> allComment(int id);

    @Insert
    void insertComment(Comment comment);

    @Query("Select name from Customer where id=:id")
    String getCusNameById(int id);

    @Insert
    void insertCart(Cart cart);

    @Query("Select * from Cart where cID=:cID")
    LiveData<List<Cart>> allCart(int cID);

    @Query("Select Count(pID) from Cart where cID=:cID")
    LiveData<Integer> getCountCart(int cID);
    @Update
    void updateCart(Cart cart);
    @Delete
    void deleteCart(Cart cart);
    @Delete
    void deleteListCart(List<Cart> carts);


    @Query("Select * from Bill where cID=:cID")
    LiveData<List<Bill>> listBill(int cID);

    @Query("Select Count(cID) from Cart where cID=:cID")
    int getCountBill(int cID);

    @Query("Select * from Customer where id=:cID")
    Customer getCustomerById(int cID);

    @Insert
    void insertBill(Bill bill);






























//    private Context context;
//    private Database fDB;
//    private SQLiteDatabase db;
//    private String TAG="DAO";
//    public DAO(Context context){
//        this.context=context;
//        fDB=new Database(context);
//        db=fDB.getWritableDatabase();
//    }
//    public String login(String username,String pass){
//        String result=null;
//        String sql="Select id from Customer where username=? and password=? Limit 1";
//        Cursor c=db.rawQuery(sql,new String[]{username,pass});
//        c.moveToFirst();
//        if(c.getCount()>0){
//            result=c.getString(0);
//        }
//        return result;
//    }
//    public Boolean register(Customer customer){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("username",customer.getUsername());
//        contentValues.put("password",customer.getPassword());
//        contentValues.put("name",customer.getName());
//        contentValues.put("sdt",customer.getSdt());
//        long kq=db.insert("Customer",null,contentValues);
//        if(kq<=0){
//            return false;
//        }
//        return true;
//    }
//    public LiveData<List<Product>> allProduct(){
//        LiveData<List<Product>> resultList=new MutableLiveData<>();
//        Cursor c=db.query("Product",null,null,null,null,null,null);
//        c.moveToFirst();
//        ArrayList<Product> listP=new ArrayList<>();
//        while (!c.isAfterLast()){
//            Product p=new Product();
//            p.setId(c.getInt(0));
//            p.setName(c.getString(1));
//            p.setImage(c.getString(2));
//            p.setaID(c.getInt(3));
//            p.setDescription(c.getString(4));
//            p.setPrice(c.getDouble(5));
//            p.setPriceD(c.getDouble(6));
//            p.setRate(c.getInt(7));
//            p.setDate(c.getString(8));
//            listP.add(p);
//            Log.d(TAG,"allProduct");
//            c.moveToNext();
//        }
//        for (int i=0;i<listP.size();i++){
//            Log.d(TAG,listP.get(i).getId()+"");
//        }
//        c.close();
//        ((MutableLiveData<List<Product>>) resultList).postValue((List<Product>) listP);
//
//
//        return resultList;
//    }
//    public String getAuthorName(int id){
//        String result=null;
//        String sql="Select name from Author where id=? Limit 1";
//        Cursor c=db.rawQuery(sql,new String[]{id+""});
//        c.moveToFirst();
//        if(c.getCount()>0){
//            result=c.getString(0);
//        }
//        Log.d(TAG,result);
//        return result;
//    }
//    public LiveData<List<Comment>> allComment(){
//        LiveData<List<Comment>> resultList=new MutableLiveData<>();
//        Cursor c=db.query("Comment",null,null,null,null,null,null);
//        c.moveToFirst();
//        ArrayList<Comment> listP=new ArrayList<>();
//        while (!c.isAfterLast()){
//            Comment p=new Comment();
//            p.setpID(c.getInt(0));
//            p.setcID(c.getInt(1));
//            p.setDate(c.getString(2));
//            p.setContent(c.getString(3));
//            listP.add(p);
//            Log.d(TAG,"allProduct");
//            c.moveToNext();
//        }
//
//        c.close();
//        ((MutableLiveData<List<Comment>>) resultList).postValue((List<Comment>) listP);
//        return resultList;
//    }
//    public boolean insertComment(Comment p){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("pID",p.getpID());
//        contentValues.put("cID",p.getcID());
//        contentValues.put("date",p.getDate());
//        contentValues.put("content",p.getContent());
//        long kq=db.insert("Comment",null,contentValues);
//        if(kq<=0){
//            return false;
//        }
//        return true;
//    }

}
