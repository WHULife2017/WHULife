package com.example.WhuLife.subscription;

public class Check {
    private String Num,Event,Details,Time,Place;
    private MMessage temp;//MMessage是组员定义的更全面的部分 只是当时我的Check适配器用不了这么多 所以再做了次适配
    public Check(MMessage parma){
        this.Num=String.valueOf(parma.getMid());
        this.Event=parma.getShortInfo();
        this.Time=parma.getTime();
        this.Place=parma.getLocation();
        this.Details=parma.getInfo();
    }

    public String getNum(){
        return this.Num;
    }
    public String getEvent(){
        return this.Event;
    }
    public String getTime(){
        return this.Time;
    }
    public String getPlace(){
        return this.Place;
    }
    public String getDetails(){
        return this.Details;
    }
}
