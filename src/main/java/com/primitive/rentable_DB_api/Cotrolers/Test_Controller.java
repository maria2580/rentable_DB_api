package com.primitive.rentable_DB_api.Cotrolers;

import com.primitive.rentable_DB_api.Data_object.Items_DAO;
import com.primitive.rentable_DB_api.Data_object.Users_DAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@RestController
@RequestMapping("")
public class Test_Controller {
    Items_DAO items;
    Users_DAO users;

    private final DB_Connection_Data key = DB_Connection_Data.getInstance();

    @GetMapping
    public String testController(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());//Todo 유저아이디 길이제한 (영어)20자  이름 길이제한 한글10자  닉네임 길이제한 한글(2바이트)10자  주소지 길이제한 한글60자 전화번호 길이제한 14자 - 기호 제외 +8210xxxxyyyy 로 저장
            //업로드 된 아이템 테이블
            con.createStatement().execute("create table if not exists items_uploaded (idx int, owners_user_index varchar(20), uploaded_date_time varchar(20), title varchar(80), content varchar(4000), appended_image_count int, is_deleted boolean, reservation_table_name varchar(50), comment_table_name varchar(50), reviews_table_name varchar(50));");
            //모든 사용자 테이블
            con.createStatement().execute("create table if not exists users (idx int, user_id varchar(20), name varchar(20), nickname varchar(20), Address varchar(120), email varchar(40),phone_num varchar(28));");

            con.createStatement().execute("create table if not exists profile_images (idx int, owners_user_index varchar(20), Path varchar(200), uploaded_date_time varchar(20));");
            con.createStatement().execute("create table if not exists item_images (idx int, related_item_index int, Path varchar(200), uploaded_date_time varchar(20));");
            ResultSet rs = con.createStatement().executeQuery("select idx from users order by idx desc limit 1");
            if (rs.next()){
                final int user_num = rs.getInt("idx");
                for (int i = 0; i < user_num+1; i++) {
                    //특정 유저가 빌린 아이템 리스트
                    con.createStatement().execute(String.format("create table if not exists user_%s (idx int, owners_id varchar(20),brought_item_index int,reservation_list_index int);",items.getItems()[i].getMy_index()));
                }
            }
            ResultSet rs2 = con.createStatement().executeQuery("select idx from items_uploaded order by idx desc limit 1");
            if (rs2.next()){
                final int item_count=rs.getInt("idx");
                for(int i = 0; i<item_count+1;i++){
                    //특정 아이템의 예약 리스트. 테이블 명 : resercation_list_of_{item_index}
                    con.createStatement().execute(String.format("create table if not exists reservation_list_of_%s (idx int, customer_user_index varchar(20), start_date_time varchar(20), end_date_time varchar(20), where_to_brought varchar(120), where_to_be_return varchar(120), where_is_item_now varchar(120),is_movable boolean, is_returned boolean);",items.getItems()[i].getMy_index()));
                }
            }
            con.close();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "working";
    }
}
