package com.primitive.rentable_DB_api.Data_object;

public class Image {
    private int my_index;
    private String uploader_user_id;
    private String Path;
    private String uploaded_date_time;
    private boolean is_profile;


    public int getMy_index() {
        return my_index;
    }

    public void setMy_index(int my_index) {
        this.my_index = my_index;
    }

    public String getUploader_user_id() {
        return uploader_user_id;
    }

    public void setUploader_user_id(String uploader_user_id) {
        this.uploader_user_id = uploader_user_id;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getUploaded_date_time() {
        return uploaded_date_time;
    }

    public void setUploaded_date_time(String uploaded_date_time) {
        this.uploaded_date_time = uploaded_date_time;
    }
    public boolean isIs_profile() {
        return is_profile;
    }
    public void setIs_profile(boolean is_profile) {
        this.is_profile = is_profile;
    }

}
