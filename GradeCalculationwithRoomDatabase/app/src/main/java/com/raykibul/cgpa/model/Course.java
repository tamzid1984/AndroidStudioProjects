package com.raykibul.cgpa.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey(autoGenerate = true)
     public int id;
     public String courseName;
     public  double courseGpa;
     public double courseCredit;
     public int semisterId;



    public Course(   double courseGpa, double courseCredit, int semisterId) {

        this.courseGpa = courseGpa;
        this.courseCredit = courseCredit;
        this.semisterId = semisterId;
    }


    public int getSemisterId() {
        return semisterId;
    }

    public void setSemisterId(int semisterId) {
        this.semisterId = semisterId;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseGpa() {
        return courseGpa;
    }

    public void setCourseGpa(double courseGpa) {
        this.courseGpa = courseGpa;
    }

    public double getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(double courseCredit) {
        this.courseCredit = courseCredit;
    }
}
