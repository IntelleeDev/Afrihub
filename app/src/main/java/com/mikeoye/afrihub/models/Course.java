package com.mikeoye.afrihub.models;

/**
 * Created by lami on 3/2/2017.
 */

public class Course {

    private String courseCode;
    private String courseTitle;
    private String lectureDay;
    private String timeScheduled;

    public Course() {}

    public Course(String day, String code, String title, String time) {
        courseCode    =  code;
        courseTitle   =  title;
        lectureDay    =  day;
        timeScheduled =  time;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getLectureDay() {
        return lectureDay;
    }

    public void setLectureDay(String lectureDay) {
        this.lectureDay = lectureDay;
    }

    public String getTimeScheduled() {
        return timeScheduled;
    }

    public void setTimeScheduled(String timeScheduled) {
        this.timeScheduled = timeScheduled;
    }

}
