package com.primitive.rentable_DB_api.Srvieces.Thereads;

import com.primitive.rentable_DB_api.Data_object.Item;
import com.primitive.rentable_DB_api.Srvieces.Item_DB_service;
import org.springframework.beans.factory.annotation.Autowired;

public class Thread_post_item extends Thread{
    @Autowired
    Item_DB_service item_db_service;
    private Item item;
    @Override
    public void run() {
        super.run();
        item_db_service.post_item(item);
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
