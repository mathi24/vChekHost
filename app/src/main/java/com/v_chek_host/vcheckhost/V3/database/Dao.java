package com.v_chek_host.vcheckhost.V3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(TrainModel model);

    @Update
    void update(TrainModel model);

    // below line is use to delete a
    // specific course in our database.
    @Delete
    void delete(TrainModel model);

    // on below line we are making query to
    // delete all courses from our database.
    @Query("DELETE FROM train_models")
    void deleteAllCourses();

    // below line is to read all the courses from our database.
    // in this we are ordering our courses in ascending order
    // with our course name.
    @Query("SELECT * FROM train_models")
    List<TrainModel> getAllCourses();
}
