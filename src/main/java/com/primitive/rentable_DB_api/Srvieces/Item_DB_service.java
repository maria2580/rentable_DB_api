package com.primitive.rentable_DB_api.Srvieces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class Item_DB_service {
    public String get_item(String related_item_index) {
        JSONObject jsonObject = new JSONObject();
        JSONArray req_array = new JSONArray();



        jsonObject.put("어쩌고 저쩌고",req_array);
        return jsonObject.toJSONString();
    }

    public void post_item(String related_item_index, MultipartFile[] file) {
    }
}
