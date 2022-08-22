package com.primitive.rentable_DB_api.Cotrolers.DB_Controller;

import com.primitive.rentable_DB_api.Srvieces.Profile_image_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("")
public class profile_Image_controller {
    @Autowired
    Profile_image_service profile_image_service;


    @GetMapping( value = "images/profile/{user_id}", produces = MediaType.IMAGE_JPEG_VALUE )
    public String get_profile_image(@PathVariable String user_id) throws IOException {
        return profile_image_service.get_profile_image(user_id);
    }
    @PostMapping("add/images/profile/{user_id}")//@modelattribute("key")
    public void set_profile_image(@RequestParam String user_id, @RequestBody String encoded_image) throws IOException {
        new Thread(){
            @Override
            public void run() {
                super.run();
                profile_image_service.post_profile_image(user_id,encoded_image);
            }
        }.start();
        return;
    }


}
