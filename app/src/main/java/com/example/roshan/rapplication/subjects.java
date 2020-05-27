package com.example.roshan.rapplication;

public class subjects {

    String SubjectName;
    String desc;
    int img;
    String price;

    public String getName() {

        return SubjectName;
    }

    public  void setName(String TempName) {

        this.SubjectName = TempName;
    }
    public String getDesc()
    {
        return  desc;
    }
    public void setDesc(String desc)
    {
        this.desc=desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}