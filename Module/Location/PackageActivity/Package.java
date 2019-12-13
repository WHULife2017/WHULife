package com.example.mylocation;

public class Package {
    private String packageId;
    private String packageLocation;
    private String notifyTime;
    private String packageCompany;

    public Package(String packageId, String packageLocation, String notifyTime, String packageCompany){
        this.packageId = packageId;
        this.packageLocation = packageLocation;
        this.notifyTime = notifyTime;
        this.packageCompany = packageCompany;
    }

    public String getPackageId(){ return packageId; }

    public String getPackageLocation(){
        return packageLocation;
    }

    public String getNotifyTime(){
        return notifyTime;
    }

    public String getPackageCompany() {return packageCompany;}
}
