package com.mikeoye.afrihub.models;

/**
 * Created by lami on 2/26/2017.
 */

public class Student {

    private String level;
    private String fullname;
    private String studentId;
    private String emailAddress;

    public Student() {
    }

    public Student(String studentId, String fullname, String emailAddress) {
        this.studentId      =   studentId;
        this.fullname       =   fullname;
        this.emailAddress   =   emailAddress;
    }

    public Student(String studentId, String fullname, String emailAddress, String level) {
        this.studentId    = studentId;
        this.fullname     = fullname;
        this.emailAddress = emailAddress;
        this.level = level;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
