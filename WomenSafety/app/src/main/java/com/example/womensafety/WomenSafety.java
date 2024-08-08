package com.example.womensafety;

public class WomenSafety {


    String phno1;
    String phno2;

    public WomenSafety(){

    }
    public WomenSafety(String phno1,String phno2 ){

        this.phno1=phno1;
        this.phno2=phno2;
    }
    public String getPhno1() {
        return phno1;
    }
    public void setPhno1(String phno1){
        this.phno1=phno1;
    }
    public String getPhno2(){
        return phno2;
    }
    public void setPhno2(String phno2){
        this.phno2=phno2;
    }
}
