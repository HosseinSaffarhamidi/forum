package ir.adromsh.forum.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete


import androidx.room.Insert
import androidx.room.Query

@Dao
interface CourseDao {
    @Insert
    fun insert(course: RCourse)

    @Query("SELECT * FROM rcourse")
    fun getAllCourse(): LiveData<List<RCourse>>

    @Query("SELECT * FROM RCourse WHERE title IN (:title)")
    fun getSignedCourse(title:String):LiveData<RCourse>

    @Delete
    fun deleteCourse(course:RCourse)

}

