package com.primitive.rentable_DB_api.Cotrolers.DB_Controller;

import com.primitive.rentable_DB_api.Cotrolers.DB_Connection_Data;
import com.primitive.rentable_DB_api.Data_object.Reservation;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


@RestController
@RequestMapping("")
public class Reservation_DB_controller {
    private final DB_Connection_Data key = DB_Connection_Data.getInstance();
    @GetMapping("reservation_list/{item_index}")//유저가 자신이 예약이 가능한 시간을 확인 하기 위해 호출함
    public Reservation[] get_reservation_list(@PathVariable int item_index){
        Reservation[] reservations =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());
            ResultSet rs0=con.createStatement().executeQuery("select idx from reservation_list_of"+item_index+" order by idx desc limit 1;");
            int last_idx;
            try {
                last_idx=rs0.getInt("idx");
            }catch (Exception e){
                last_idx=0;
            }

            ResultSet rs = con.createStatement().executeQuery("select * from reservation_list_of_"+item_index+";");
            for (int i  =0;i<last_idx+1;i++){
                if (rs.next()){
                    reservations[i].setMy_index(rs.getInt("idx"));
                    reservations[i].setCustomer_user_index(rs.getString("customer_user_index"));
                    reservations[i].setStart_date_time(rs.getString("start_date_time"));
                    reservations[i].setEnd_date_time(rs.getString("end_date_time"));
                    reservations[i].setWhere_to_brought(rs.getString("where_to_brought"));
                    reservations[i].setWhere_to_be_return(rs.getString("where_to_be_return"));
                    reservations[i].setWhere_is_item_now(rs.getString("where_is_item_now"));
                    reservations[i].setIs_movable(rs.getBoolean("is_movable"));
                    reservations[i].setIs_returned(rs.getBoolean("is_returned"));
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return reservations;
    }
    @GetMapping("reservation_list/{item_index}/{reservation_index}")//유저가 자신이 예약한 목록을 확인 하기 위해 호출함
    public Reservation get_reservation_data(@PathVariable int item_index, @PathVariable int reservation_index){
        Reservation reservation =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());

            ResultSet rs = con.createStatement().executeQuery("select * from reservation_list_of_"+item_index+" where idx = " +reservation_index+";");
            if(rs.next()){
                reservation.setMy_index(rs.getInt("idx"));
                reservation.setCustomer_user_index(rs.getString("customer_user_index"));
                reservation.setStart_date_time(rs.getString("start_date_time"));
                reservation.setEnd_date_time(rs.getString("end_date_time"));
                reservation.setWhere_to_brought(rs.getString("where_to_brought"));
                reservation.setWhere_to_be_return(rs.getString("where_to_be_return"));
                reservation.setWhere_is_item_now(rs.getString("where_is_item_now"));
                reservation.setIs_movable(rs.getBoolean("is_movable"));
                reservation.setIs_returned(rs.getBoolean("is_returned"));
            }
        }catch (Exception e){e.printStackTrace();}
        return reservation;
        //예약 내역을 외부에서 post 할 수 없음.
        //예약은 판매자가 예약 리퀘스트를 허가 하는 경우


    }

    //post 요청은 여기서 처리하지 않음.
    //유저가 소유자에게 보낸 리퀘스트를 소유자가 승낙하는 메서드가 호출되면 reservation_of_{item_index} 와 users_{user_index} 테이블에 추가 됨.




}
