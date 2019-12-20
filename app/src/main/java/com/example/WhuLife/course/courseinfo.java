package com.example.WhuLife.course;


public class courseinfo {
    private String name,room,teach,id;//课程名称、上课教室，教师，课程编号
    int start,step;	//开始上课节次， 一共几节课
    public courseinfo(String name, String room, int start, int step,
                  String teach, String id) {
        super();
        this.name = name;
        this.room = room;
        this.start = start;
        this.step = step;
        this.teach = teach;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getTeach() {
        return teach;
    }

    public void setTeach(String teach) {
        this.teach = teach;
    }


}
