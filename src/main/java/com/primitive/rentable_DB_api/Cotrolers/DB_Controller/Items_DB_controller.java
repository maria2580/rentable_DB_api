package com.primitive.rentable_DB_api.Cotrolers.DB_Controller;

import com.primitive.rentable_DB_api.Data_object.Item;
import com.primitive.rentable_DB_api.Srvieces.Item_DB_service;
import com.primitive.rentable_DB_api.Srvieces.Item_image_service;
import com.primitive.rentable_DB_api.Srvieces.Thereads.Thread_post_item;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class Items_DB_controller {
    @Autowired
    Item_image_service item_image_service;
    @Autowired
    Item_DB_service item_db_service;
    @GetMapping( value = "items/{related_item_index}")
    public String get_item(@PathVariable String related_item_index) {
        Item item = item_db_service.get_item(related_item_index);
        JSONObject jsonObject=new JSONObject();
        if(!item.isDeleted()){
            jsonObject.put("my_index",item.getMy_index());
            jsonObject.put("owners_id",item.getOwners_user_id());
            jsonObject.put("title",item.getTitle());
            jsonObject.put("content",item.getContent());
            jsonObject.put("uploaded_date_time",item.getUploaded_date_time());
            jsonObject.put("is_deleted",item.isDeleted());
            jsonObject.put("reservation_table_name",item.getReservation_table_name());
            jsonObject.put("comments_table_name",item.getComments_table_name());
            jsonObject.put("review_table_name",item.getReview_table_name());
            jsonObject.put("images",item.getEncodedImages());
        }
        return jsonObject.toJSONString();
    }
    @PostMapping("add/items/")//@modelattribute("key")
    public void post_item(@RequestBody Item item){
        Thread_post_item t = new Thread_post_item();
        t.setItem(item);
        t.start();
        return;
    }

    @GetMapping( value = "items/{related_item_index}/{a}")
    public String  get_item_image(@PathVariable String related_item_index,@PathVariable int a) throws IOException {
        //
        return item_image_service.get_item_image(related_item_index,a);
    }
    @PostMapping("add/items/{related_item_index}")//@modelattribute("key")
    public void post_item_images(@RequestParam String related_item_index, @RequestParam MultipartFile[] file){
        new Thread(){
            @Override
            public void run() {
                super.run();
                item_image_service.post_item_image(related_item_index,file);
            }
        }.start();
        return;
    }
}
