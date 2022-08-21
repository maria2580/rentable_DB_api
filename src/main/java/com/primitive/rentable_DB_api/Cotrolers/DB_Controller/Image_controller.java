package com.primitive.rentable_DB_api.Cotrolers.DB_Controller;

import com.primitive.rentable_DB_api.Srvieces.Item_image_service;
import com.primitive.rentable_DB_api.Srvieces.Profile_image_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class Image_controller {
    @Autowired
    Profile_image_service profile_image_service;



    @GetMapping( value = "images/profile/{user_id}", produces = MediaType.IMAGE_JPEG_VALUE )
    public byte[] get_profile_image(@PathVariable String user_id) throws IOException {
        return profile_image_service.get_profile_image(user_id);
    }
    @PostMapping("add/images/profile/{user_id}")//@modelattribute("key")
    public void set_profile_image(@RequestParam String user_id, @RequestParam MultipartFile file){
        new Thread(){
            @Override
            public void run() {
                super.run();
                profile_image_service.post_profile_image(user_id,file);
            }
        }.start();
        return;
    }


}
