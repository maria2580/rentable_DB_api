package com.primitive.rentable_DB_api.Cotrolers;

import com.primitive.rentable_DB_api.DAO.Items_DAO;
import com.primitive.rentable_DB_api.DAO.Users_DAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;

@RestController
@RequestMapping("")
public class Test_Controller {
    Items_DAO items;
    Users_DAO users;

    private final DB_Connection_Data key = DB_Connection_Data.getInstance();

    @GetMapping
    public String testController(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use "+key.getDBname());

            con.createStatement().execute("create table if not exists items_uploaded (index int, uoload_user_id varchar(20), uploaded_date_time varchar(20), is_deleted boolean, reservation_table_name varchar(50), comment_table_name varchar(50), reviews_table_name varchar(50));");
            con.createStatement().execute("create table if not exists users (index int, user_id varchar(20), name varchar(20), nickname varchar(20), Address varchar(120), tel varchhar(28),phone_num varchar(28));");
            con.createStatement().execute("create table if not exists images (index int, uploader_user_id varchar(20), Path varchar(200), uploaded_date_time varchar(20));");

            for(int i = 0; i<items.getItems().length;i++){
                con.createStatement().execute(String.format("create table if not exists reservation_list_of_%s (index int, customer_user_id varchar(20), start_date_time varchar(20), end_date_time varchar(20), where_to_brought varchar(120), where_to_be_return varchar(120), where_is_item_now varchar(120),is_movable boolean, is_returned boolean);",items.getItems()[i].getMy_index()));

            }
            con.close();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "working";
    }
}
