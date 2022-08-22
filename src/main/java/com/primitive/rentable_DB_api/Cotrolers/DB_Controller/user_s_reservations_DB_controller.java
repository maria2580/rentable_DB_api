package com.primitive.rentable_DB_api.Cotrolers.DB_Controller;

import com.primitive.rentable_DB_api.Cotrolers.DB_Connection_Data;
import com.primitive.rentable_DB_api.Data_object.Reservation;
import com.primitive.rentable_DB_api.Data_object.Reservation_pointer;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@RestController
@RequestMapping("")
public class user_s_reservations_DB_controller {
    DB_Connection_Data key=DB_Connection_Data.getInstance();


    @GetMapping("reservation/user/{user_index}/")
    public Reservation_pointer[] get_reservation_pointers(@PathVariable int user_index){
        Reservation_pointer[] rsrv_ptrs =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());
            ResultSet rs0=con.createStatement().executeQuery("select idx from user_"+user_index+" order by idx desc limit 1;");
            int last_idx;
            try {
                last_idx=rs0.getInt("idx");
            }catch (Exception e){
                last_idx=0;
            }
            rsrv_ptrs=new Reservation_pointer[last_idx];
            ResultSet rs=con.createStatement().executeQuery("select * from user_"+user_index+";");

            for (int i  =0;i<last_idx+1;i++) {
                if (rs.next()){
                    rsrv_ptrs[i].setMy_index(rs.getInt("idx"));
                    rsrv_ptrs[i].setOwners_id(rs.getString("owners_id"));
                    rsrv_ptrs[i].setBrought_item_index(rs.getInt("brought_item_index"));
                    rsrv_ptrs[i].setReservation_list_index(rs.getInt("reservation_list_index"));
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return rsrv_ptrs;
    }
    //post 요청은 여기서 하지 않음.
    //유저가 소유자에게 보낸 리퀘스트를 소유자가 승낙하는 메서드가 호출되면 reservation_of_{item_index} 와 users_{user_index} 테이블에 추가 됨.
}
