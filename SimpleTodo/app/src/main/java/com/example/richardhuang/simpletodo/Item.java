package com.example.richardhuang.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by richardhuang on 9/22/16.
 */

@Table(name = "Items")
public class Item extends Model {


    @Column(name = "Body")
    public String body;

    // Make sure to have a default constructor for every ActiveAndroid model
    public Item(){
        super();
    }

    public Item(String body){
        super();
        this.body = body;
    }

    public String toString() {
        return body;
    }
}
