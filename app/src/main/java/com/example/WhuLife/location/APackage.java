package com.example.WhuLife.location;

public class APackage {
    private String packageId;
    private String packageLocation;
    private String notifyTime;
    private String packageCompany;

    public APackage(){
        this.packageId = null;
        this.packageLocation = null;
        this.notifyTime = null;
        this.packageCompany = null;
    }

    public APackage(String packageId, String packageLocation, String notifyTime, String packageCompany){
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
