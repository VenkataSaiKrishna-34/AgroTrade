package com.example.mapproject;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class Item_layout extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item_layout);
//    }
//}

import java.io.Serializable;
import java.util.Comparator;

public class Item implements Serializable {
    int imgRef, rate, imgRefB;
    String imgTitle;
    String loc;
    String pno;
    String fName;
    String des ;

    Item() {

    }

    public Item(int imgRef, String imgTitle, String loc, int rate, String des, String fName, String pno, int imgRefB) {
        this.imgRef = imgRef;
        this.imgTitle = imgTitle;
        this.loc = loc;
        this.rate = rate;
        this.des = des;
        this.fName = fName;
        this.pno = pno;
        this.imgRefB = imgRefB;
    }


    public int getImgRef() {
        return imgRef;
    }

    public void setImgRef(int imgRef) {
        this.imgRef = imgRef;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getImgRefB() {
        return imgRefB;
    }

    public void setImgRefB(int imgRefB) {
        this.imgRefB = imgRefB;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }



    public static final Comparator<Item> ASCENDING = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {

            return o1.getRate()-o2.getRate();
        }
    };

    public static final Comparator<Item> DESCENDING = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {

            return o2.getRate()-o1.getRate();
        }
    };


}
