package com.raykibul.cgpa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.raykibul.cgpa.model.Semister;

import java.util.List;

@Dao
public interface SemisterDao {

    @Insert
    void InsertSemister(Semister semister);

    @Update
    void UpdateSemister(Semister semister);

    @Delete
    void DeleteSemister(Semister semister);

    @Query("select * from Semister Order by id ASC")
    List<Semister> GetAllSemisters();

}
