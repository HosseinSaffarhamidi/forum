package ir.adromsh.forum.home

import android.app.Application
import androidx.lifecycle.LiveData
import ir.adromsh.forum.models.Codes
import ir.adromsh.forum.models.Course
import ir.adromsh.forum.models.User

class HomeRepository(context:Application) {
    private var homeApiService = HomeApiService(context)

    fun getBestUser(): LiveData<List<User>> {
        return homeApiService.getBestUser()
    }

    fun getCourses():LiveData<List<Course>>{
        return homeApiService.getCourses()
    }

    fun getCodes():LiveData<List<Codes>>{
        return homeApiService.getCodes()
    }
}