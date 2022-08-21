package com.primitive.rentable_DB_api.Cotrolers.DB_Controller;

import com.primitive.rentable_DB_api.Srvieces.Item_DB_service;
import com.primitive.rentable_DB_api.Srvieces.Item_image_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class Items_uploaded_DB_controller {
    @Autowired
    Item_image_service item_image_service;
    @Autowired
    Item_DB_service item_db_service;
    @GetMapping( value = "items/{related_item_index}")
    public String get_item(@PathVariable String related_item_index) throws IOException {
        return item_db_service.get_item(related_item_index);
    }
    @PostMapping("add/items/{related_item_index}")//@modelattribute("key")
    public void post_item(@RequestParam String related_item_index, @RequestParam MultipartFile[] file){
        new Thread(){
            @Override
            public void run() {
                super.run();
                item_db_service.post_item(related_item_index,file);
            }
        }.start();
        return;
    }

    @GetMapping( value = "items/{related_item_index}/{a}")
    public byte[] get_item_image(@PathVariable String related_item_index,@PathVariable int a) throws IOException {
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
