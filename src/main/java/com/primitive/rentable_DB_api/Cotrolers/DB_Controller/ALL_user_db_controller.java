package com.primitive.rentable_DB_api.Cotrolers.DB_Controller;

import com.primitive.rentable_DB_api.Cotrolers.DB_Connection_Data;
import com.primitive.rentable_DB_api.Data_object.User_DTO;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@RestController
@RequestMapping("")
public class ALL_user_db_controller {
    private final DB_Connection_Data key = DB_Connection_Data.getInstance();

    @GetMapping("users")
    public User_DTO[] get_all_users(){
        return new User_DTO[1];
    }
    @GetMapping("users/{user_index}")
    public User_DTO get_user_dat(@PathVariable int user_index){
        User_DTO user_dto =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());
            ResultSet rs = con.createStatement().executeQuery("select * from users where idx="+user_index+";");
            if (rs.next()){
                user_dto=new User_DTO();
                user_dto.setMy_index(rs.getInt("idx"));
                user_dto.setUser_ID(rs.getString("user_id"));
                user_dto.setName(rs.getString("name"));
                user_dto.setNickname(rs.getString("nickname"));
                user_dto.setAddress(rs.getString("Address"));
                user_dto.setEmail(rs.getString("email"));
                user_dto.setPhone_num(rs.getString("phone_num"));
            }
        }catch (Exception e) {e.printStackTrace();}
        return user_dto;
    }
    @PostMapping("add/users")
    public String add_user(@RequestBody User_DTO user){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());
            ResultSet rs = con.createStatement().executeQuery("select * from users where user_id='"+user.getUser_ID()+"';");
            if (rs.next()){
                //?????? ?????? ????????? ????????? ??????
                return "?????? ?????? ?????? ????????? ?????????.";
            }
            ResultSet rs2 = con.createStatement().executeQuery("select * from users where email='"+user.getEmail()+"';");
            if (rs2.next()){
                //?????? ?????? ??? ????????????
                return "?????? ???????????? ????????? ?????????.";
            }
            ResultSet rs3 = con.createStatement().executeQuery("select * from users where nickname='"+user.getNickname()+"';");
            if (rs3.next()){
                //?????? ???????????? ????????????
                return "?????? ???????????? ????????? ?????????.";
            }

            ResultSet rs_l= con.createStatement().executeQuery("select * from users order by idx desc limit 1;");
            int last_idx;
            if (rs_l.next()){
                last_idx= rs_l.getInt("idx");
            }else {
                last_idx=-1;
            }
            con.createStatement().execute(String.format("insert users (idx,user_id,name,nickname,Address,email,phone_num) values(%d,'%s','%s','%s','%s','%s','%s')",
                    last_idx+1,user.getUser_ID(),user.getName(),user.getNickname(),user.getAddress(),user.getEmail(),user.getPhone_num()));

        }catch (Exception e){e.printStackTrace();}
        return "??????";
    }
    //Todo %s ?????? ??? ?????? ????????? ???????????? ??????
    //Todo for????????? ????????? ?????? ?????? ????????? ??? ?????? ????????? ???????????? ????????????
    //Todo ?????? ?????? ???????????? ????????? ?????? ????????? ?????? ?????????
    @PatchMapping("edit/users/{user_index}")
    public String edit_user(@PathVariable int user_index, @RequestBody User_DTO user){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());
            ResultSet rs = con.createStatement().executeQuery("select * from users where user_id= '"+user.getUser_ID()+"' ;");
            if (rs.next()){
                //?????? ?????? ????????? ????????? ??????
                return "?????? ?????? ?????? ????????? ?????????.";
            }
            ResultSet rs2 = con.createStatement().executeQuery("select * from users where email= '"+user.getEmail()+"';");
            if (rs2.next()){
                //?????? ?????? ??? ????????????
                return "?????? ???????????? ????????? ?????????.";
            }
            ResultSet rs3 = con.createStatement().executeQuery("select * from users where nickname='"+user.getNickname()+"';");
            if (rs3.next()){
                //?????? ???????????? ????????????
                return "?????? ???????????? ????????? ?????????.";
            }

            con.createStatement().execute(String.format("update users set user_id='%s',  name ='%s', nickname='%s', address='%s', email ='%s', phone_num='%s " ,
                    user.getUser_ID(),user.getName(),user.getNickname(),user.getAddress(),user.getAddress(),user.getEmail(),user.getPhone_num())+"where idx="+user.getMy_index()+";");
        }catch (Exception e){e.printStackTrace();}
        return "??????";
    }
    @DeleteMapping("delete/users/{user_index}")
    public void delete_user(@PathVariable int user_index){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());
            con.createStatement().execute(String.format("update users set name ='%s', nickname='%s', address='%s', email ='%s', phone_num='%s " ,
                    "","","","", "","")+"where idx="+user_index+";");
        }catch (Exception e){e.printStackTrace();}

    }
}
