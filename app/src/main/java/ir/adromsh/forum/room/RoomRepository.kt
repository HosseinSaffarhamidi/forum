package ir.adromsh.forum.room

import androidx.lifecycle.LiveData

class RoomRepository(var courseDao: CourseDao) {

    fun insert(course:RCourse){
        courseDao.insert(course)
    }

    fun getAllCourse():LiveData<List<RCourse>>{
        return courseDao.getAllCourse()
    }

    fun getSignedCourse(title:String):LiveData<RCourse>{
        return courseDao.getSignedCourse(title)
    }
    fun deleteCourse(course: RCourse){
        courseDao.deleteCourse(course)
    }
}