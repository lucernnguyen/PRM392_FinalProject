package com.example.grocery.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.grocery.models.Producer;
import com.example.grocery.models.Bill;
import com.example.grocery.models.Cart;
import com.example.grocery.models.Comment;
import com.example.grocery.models.Customer;
import com.example.grocery.models.Product;

@androidx.room.Database(entities = {Producer.class, Product.class, Customer.class, Comment.class, Cart.class, Bill.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
        private static MyDatabase instance;
        public abstract DAO dao();
        private final String TAG="MyDatabase";
        public static synchronized MyDatabase getInstance(Context context){
                if (instance==null){
                        instance= Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class,"my_database")
                                .fallbackToDestructiveMigration()
                                .addCallback(roomcalback)
                                .build();
                }
                return instance;

        }
        private static RoomDatabase.Callback roomcalback=new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        new PopulateDB(instance.dao()).execute();
                }
        };

        private static class PopulateDB extends AsyncTask<Void,Void,Void>{
                private DAO dao;
                public PopulateDB(DAO dao){
                        this.dao=dao;
                }
                @Override
                protected Void doInBackground(Void... voids) {
                        initData();
                        Log.d("mPopulateDB","true");
                        return null;
                }


                private void initData(){
                        dao.insertAuthor(new Producer(0,"Orion"));
                        dao.insertAuthor(new Producer(0,"Long Hai"));
                        dao.insertAuthor(new Producer(0,"Hoang ly"));
                        dao.insertProduct(new Product(0,"Thạch sữa chua natty","https://www.thachlonghai.com.vn/FileManager/Thachlonghai/Sanpham/Thach_sua_Chua_Natty/Natty-Collagen-500g/Thach-sua-chua-Natty-bo-sung-Collagen-01.jpg",2,"Qua quá trình nghiên cứu ứng dụng kết hợp với công nghệ chế biến và dây chuyền sản xuất hiện đại, Công ty TNHH Long hải đã chora đời dòng sản phẩm thạch sữa chua Natty bổ sung collagen hoàn toàn mới. Sữa chua vốn đã được đông đảo người tiêu dùng biết đến không chỉ vì vị ngon, nhiều giá trị dinh dưỡng nay được bổ sung thêm thực phẩm bổ sung collagen giúp cho làn da khỏe đẹp, tươi sáng và đặc biệt tốt cho đường ruột."
                                ,600,455.5,4,"27/11/2021"));
                        dao.insertProduct(new Product(0,"Nước rau câu Long Hai","https://www.thachlonghai.com.vn/FileManager/Thachlonghai/Sanpham/Nuoc_Rau_Cau/Vi_Dua_Thom/Nuoc_rau_cau_Long_Hai_vi_dua_01.jpg",2,"Nước rau câu Long Hải - Khoáng chất tự nhiên từ rong biển. Đây là những sản phẩm vừa bổ dưỡng, mát lành, thơm ngon, thích hợp dùng cho mọi đối tượng.\n" +
                                "\n" +
                                "Sản phẩm đạt các chứng nhận an toàn thực phẩm",455,355,3,"12/08/2022"));

                        dao.insertProduct(new Product(0,"NƯỚC RAU CÂU LONG HẢI 95 G VỊ CAM","https://www.thachlonghai.com.vn/FileManager/Thachlonghai/Sanpham/Nuoc_Rau_Cau/Vi_Cam/nuoc_rau_cau_long_hai_vi_cam_01.jpg",2,"Nước rau câu Long Hải - Khoáng chất tự nhiên từ rong biển. Đây là những sản phẩm vừa bổ dưỡng, mát lành, thơm ngon, thích hợp dùng cho mọi đối tượng.\n" +
                                "\n" +
                                "Sản phẩm đạt các chứng nhận an toàn thực phẩm",600,555,4,"27/11/2021"));

                        dao.insertProduct(new Product(0,"Bánh orion opera N1","https://orion.vn/media/awlehsm3/opera-case.png",1,"Chiếc bánh Opéra - Một sự kết hợp giữa lớp bánh bông lan mềm mịn và lớp kem béo mềm, đi kèm là lớp sô cô la phủ bên ngoài mang đến cảm giác sang trọng, thanh lịch và hiện đại, một cảm giác thật \"Âu\". Với nhiều tầng hương vị hòa quyện, Opéra mong muốn khách hàng được thưởng thức một chiếc bánh tiệm trong tầm tay, thăng hạng cảm xúc cùng trải nghiệm cao cấp.",444,400,3,"13/03/2023"));

                        dao.insertProduct(new Product(0,"Bánh orion opera N2","https://orion.vn/media/afih3og3/opera-film.png",1,"Chiếc bánh Opéra - Một sự kết hợp giữa lớp bánh bông lan mềm mịn và lớp kem béo mềm, đi kèm là lớp sô cô la phủ bên ngoài mang đến cảm giác sang trọng, thanh lịch và hiện đại, một cảm giác thật \"Âu\". Với nhiều tầng hương vị hòa quyện, Opéra mong muốn khách hàng được thưởng thức một chiếc bánh tiệm trong tầm tay, thăng hạng cảm xúc cùng trải nghiệm cao cấp.",666.6,625,3,"11/03/2021"));

                        dao.insertProduct(new Product(0,"Bánh ChocoPie Vị Mâm Xôi Việt Quất ","https://orion.vn/media/femlbozg/cp-berry-case-12p-mk-1.png",1,"ChocoPie vị mâm xôi việt quất với lớp mashmallow dai, tạo cảm giác vui miệng khi ăn. Kết hợp với lớp mứt chảy nhẹ là hỗn hợp của các loại trái mọng cao cấp: Mâm xôi, việt quất và vỏ bánh của CPIE Đậm kết hợp vị mứt chua chua ngọt ngọt tạo nên vị ngọt dịu, hài hòa.",666.6,625,4,"11/03/2021"));

                        dao.insertProduct(new Product(0,"Bánh Chocopie Molle Kem Mềm Vị Hạnh Nhân","https://orion.vn/media/cj3i3oro/molle-case-1.png",1,"Chocopie Molle với sự đổi mới đột phá cùng lớp kem mềm vị hạnh nhân và thiết kế bao bì vui nhộn, tinh nghịch hơn hẳn sẽ là một sự lựa chọn tuyệt vời cho các bạn trẻ theo đuổi sự mới mẻ và phá cách. Với mục tiêu mang lại sự tiện lợi cho Gen Z, chiếc bánh ChocoPie Molle có kích thước nhỏ hơn các dòng bánh ChocoPie truyền thống cùng các đường nét Socola trắng được vẽ một cách phá cách và vui nhộn để mang lại trải nghiệm độc đáo hơn.",500,480,4,"11/03/2021"));

                        dao.insertProduct(new Product(0,"NƯỚC GIẢI KHÁT CÓ THẠCH DỪA","http://www.thachduaminhchau.com.vn/static/img/products/pet.png",3,"THẠCH DỪA, NƯỚC TINH KHIẾT, ĐƯỜNG CÁT, ACID CITRIC, CHẤT TẠO NGỌT (951), CHẤT ỔN ĐỊNH (415), CHẤT BẢO QUẢN (202), HƯƠNG VẢI.",448,420,4,"11/03/2021"));
                }
        }




}
