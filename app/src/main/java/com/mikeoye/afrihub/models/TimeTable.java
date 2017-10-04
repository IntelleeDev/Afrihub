package com.mikeoye.afrihub.models;

/**
 * Created by lami on 2/25/2017.
 */

public class TimeTable {

    private String day;
    private String courseCode;
    private String courseTitle;
    private String courseTime;

    public TimeTable(String day, String courseCode, String courseTitle, String courseTime) {
        this.day = day;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseTime = courseTime;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
