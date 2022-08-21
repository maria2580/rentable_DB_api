package com.primitive.rentable_DB_api.Srvieces;

import com.primitive.rentable_DB_api.Cotrolers.DB_Connection_Data;
import com.primitive.rentable_DB_api.Data_object.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item_DB_service {
    DB_Connection_Data key = DB_Connection_Data.getInstance();
    public Item get_item(String related_item_index) {
        Item item = new Item();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from items where index = %d",related_item_index));
            if (rs.next()){
                item.setMy_index(rs.getInt("index"));
                item.setOwners_user_id(rs.getString("owners_id"));
                item.setTitle(rs.getString("title"));
                item.setContent(rs.getString("content"));
                item.setUploaded_date_time(rs.getString("uploaded_date_time"));
                item.setDeleted(rs.getBoolean("deleted"));
                item.setReservation_table_name(rs.getString("reservation_table_name"));
                item.setComments_table_name(rs.getString("comments_table_name"));
                item.setReview_table_name(rs.getString("review_table_name"));
            }else{
                item.setMy_index(0);
                item.setOwners_user_id("");
                item.setTitle("");
                item.setContent("");
                item.setUploaded_date_time("");
                item.setDeleted(true);
                item.setReservation_table_name("");
                item.setComments_table_name("");
                item.setReview_table_name("");
            }
            con.close();
            //item.setEncodedImages();

        }catch (Exception e){
            e.printStackTrace();
        }

        return item;
    }

    public void post_item(Item item) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());


            ResultSet rs =con.createStatement().executeQuery("SELECT index FROM item ORDER BY index DESC LIMIT 1;");
            rs.next();
            int last_idx;
            try{
                last_idx= rs.getInt("index");}catch (Exception e){last_idx=0;}
            System.out.println(last_idx+"to check arrived last idx value");
            //index int, owners_user_id varchar(20),title varchar(80),content text, uploaded_date_time varchar(20),
            //is_deleted boolean, reservation_table_name varchar(50), comment_table_name varchar(50), reviews_table_name varchar(50)
            String query = String.format("insert into items(index," +
                    " owners_user_id," +
                    " title," +
                    " content," +
                    " uploaded_date_time," +
                    " is_deleted," +
                    " reservation_table_name," +
                    " comment_table_name," +
                    " reviews_table_name ) values(%d,'%s','%s','%s', '%s', '%b', '%s', '%s', '%s');",
                    item.getMy_index(),
                    item.getOwners_user_id(),
                    item.getTitle(),
                    item.getContent(),
                    item.getUploaded_date_time(),
                    item.isDeleted(),
                    item.getReservation_table_name(),
                    item.getComments_table_name(),
                    item.getReview_table_name());

            con.createStatement().executeUpdate(query);
            con.close();

        }
        catch (Exception e ){e.printStackTrace();}
    }






}
