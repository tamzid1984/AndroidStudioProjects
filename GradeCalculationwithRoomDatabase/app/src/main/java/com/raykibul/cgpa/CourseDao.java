package com.raykibul.cgpa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.raykibul.cgpa.model.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void InsertCourse(Course course);
    @Delete
    void DeleteCourse(Course course);
    @Update
    void UpdateCourse(Course course);

    @Query("select * from Course where semisterId Like :semisterId")
    List<Course>GetCoursesBySemisterId(int semisterId);

    @Insert
    void InsertCourseList(List<Course> courses);

    @Query("Delete from Course")
    void DeleteAllCourses();
}
