package com.example.WhuLife.course;

public class course_display {
    private String day;
    private int start_week;
    private int end_week;
    private int start_time;
    private int hour;
    private int minute;
    private double latitude;
    private double longtitude;
    private String location;
    public course_display(String Day,int Start_week,int End_week,int Start_time,double Latitude,double Longtitude,String Location){
        day=Day;
        start_week=Start_week;
        end_week=End_week;
        start_time=Start_time;
        latitude=Latitude;
        longtitude=Longtitude;
        location=Location;
        switch(start_time){
            case 1:hour=8;minute=0;break;
            case 3:hour=20;minute=42;break;
            case 6:hour=22;minute=5;break;
            case 8:hour=15;minute=40;break;
            case 11:hour=18;minute=30;break;
            default:hour=0;minute=0;

        }
    }

    public int getday(){
        switch(day){
            case "Sun":return 1;
            case "Mon":return 2;
            case "Tue":return 3;
            case "Wed":return 4;
            case "Thu":return 5;
            case "Fri":return 6;
            case "Sat":return 7;
            default:return 0;
        }
    }
    public int gethour(){
        return hour;
    }
    public int getminute(){
        return minute;
    }
    public double getlatitude(){
        return latitude;
    }
    public double getlongtitude(){
        return longtitude;
    }
    public String getlocation(){
        return location;
    }
}
