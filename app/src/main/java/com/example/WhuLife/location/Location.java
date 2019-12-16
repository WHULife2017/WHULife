package com.example.WhuLife.location;

import java.io.Serializable;

/**
 * 描述
 * <p>
 * Created by liyujiang on 2017/9/4 15:53.
 */
public class Location implements Serializable {
    private double latitude,longitude;
    private String company;
    private String fetchLocation;

    public Location(double latitude,double longitude, String fetchLocation) {
        this.latitude=latitude;
        this.longitude=longitude;
        this.fetchLocation = fetchLocation;
        company = "null";
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getfetchLocation() {
        return fetchLocation;
    }

    public void setfetchLocation(String fetchLocation) {
        this.fetchLocation = fetchLocation;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public String getCompany(){
        return company;
    }

    @Override
    public String toString() {
        //重写该方法，作为选择器显示的名称
        return fetchLocation;
    }

}
