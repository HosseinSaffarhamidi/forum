package ir.adromsh.forum.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.models.Message
import ir.adromsh.forum.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    var apiService = ProfileApiService(application)
    var context=application

    fun getProfileData(): MutableLiveData<User> {
        return apiService.getProfileDate()
    }

    fun updateProfile(
        name: String,
        family: String,
        phone: String,
        age: Int,
        jensiat: Int
    ): MutableLiveData<Message> {
        return apiService.updateProfile(name, family, phone, age, jensiat)
    }

    fun uploadImage(id: RequestBody, filePart: MultipartBody.Part): MutableLiveData<Message> {
        return apiService.uploadImage(id, filePart)
    }

    fun education(phone: String): MutableLiveData<Message> {
        return apiService.educationi(phone)
    }
    fun updateToken(data:String){
        var sharedPreference = context.getSharedPreferences(
            "token",
            Context.MODE_PRIVATE
        )
        var editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("data", data)
        editor.apply()
    }
}