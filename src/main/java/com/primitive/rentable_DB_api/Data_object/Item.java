package com.primitive.rentable_DB_api.Data_object;

public class Item {
    private int my_index;
    private String owners_user_id;
    private String title;
    private String content;
    private String uploaded_date_time;
    private boolean deleted;
    private String reservation_table_name;
    private String comments_table_name;
    private String review_table_name;
    private int appended_image_count;

    public int getAppended_image_count() {
        return appended_image_count;
    }

    public void setAppended_image_count(int appended_image_count) {
        this.appended_image_count = appended_image_count;
    }

    private String[] encodedImages;

    public String[] getEncodedImages() {
        return encodedImages;
    }

    public void setEncodedImages(String[] encodedImages) {
        this.encodedImages = encodedImages;
    }

    public int getMy_index() {
        return my_index;
    }

    public void setMy_index(int my_index) {
        this.my_index = my_index;
    }

    public String getOwners_user_id() {
        return owners_user_id;
    }

    public void setOwners_user_id(String owners_user_id) {
        this.owners_user_id = owners_user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUploaded_date_time() {
        return uploaded_date_time;
    }

    public void setUploaded_date_time(String uploaded_date_time) {
        this.uploaded_date_time = uploaded_date_time;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getReservation_table_name() {
        return reservation_table_name;
    }

    public void setReservation_table_name(String reservation_table_name) {
        this.reservation_table_name = reservation_table_name;
    }

    public String getComments_table_name() {
        return comments_table_name;
    }

    public void setComments_table_name(String comments_table_name) {
        this.comments_table_name = comments_table_name;
    }

    public String getReview_table_name() {
        return review_table_name;
    }

    public void setReview_table_name(String review_table_name) {
        this.review_table_name = review_table_name;
    }
}
