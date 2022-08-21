package com.primitive.rentable_DB_api.DAO;

public class Item {
    private int my_index;
    private String owners_id;
    private String uploaded_date_time;
    private boolean is_deleted;
    private String reservation_table_name;
    private String comments_table_name;
    private String review_table_name;

    public int getMy_index() {
        return my_index;
    }

    public void setMy_index(int my_index) {
        this.my_index = my_index;
    }

    public String getOwners_id() {
        return owners_id;
    }

    public void setOwners_id(String owners_id) {
        this.owners_id = owners_id;
    }

    public String getUploaded_date_time() {
        return uploaded_date_time;
    }

    public void setUploaded_date_time(String uploaded_date_time) {
        this.uploaded_date_time = uploaded_date_time;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
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
