package com.raykibul.cgpa;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.raykibul.cgpa.model.Course;
import com.raykibul.cgpa.model.Semister;

@Database(entities = {Course.class, Semister.class},version = 1,exportSchema = false)
public abstract class GradeDatabase extends RoomDatabase {
    public abstract  CourseDao courseDao();
    public  abstract SemisterDao  semisterDao();

    public  static volatile GradeDatabase INSTANCE;

    static  GradeDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),GradeDatabase.class,
                    "GRADEDATABASE").fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
