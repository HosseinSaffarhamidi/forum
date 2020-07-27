package ir.adromsh.forum.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ir.adromsh.forum.models.Codes
import ir.adromsh.forum.models.Course
import ir.adromsh.forum.models.User

class HomeViewModel(application: Application) :AndroidViewModel(application) {
    private var repository=HomeRepository(application)

    fun getBestUser():LiveData<List<User>>{
        return repository.getBestUser()
    }
    fun getCourses():LiveData<List<Course>>{
        return repository.getCourses()
    }
    fun getCodes():LiveData<List<Codes>>{
        return repository.getCodes()
    }
}