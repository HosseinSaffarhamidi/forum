package ir.adromsh.forum.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class RoomViewModel(application: Application) : AndroidViewModel(application) {
     var repository:RoomRepository

    init {
        var courseDao=AppDatabase.getDatabase(application).courseDao()
        repository= RoomRepository(courseDao)
    }

    fun insert(rCourse: RCourse){
        repository.insert(rCourse)
    }
    fun getAllCourse():LiveData<List<RCourse>>{
        return  repository.getAllCourse()
    }
    fun getSignedCourse(title:String):LiveData<RCourse>{
        return repository.getSignedCourse(title)
    }
    fun deleteCourse(course: RCourse){
        repository.deleteCourse(course)
    }
}