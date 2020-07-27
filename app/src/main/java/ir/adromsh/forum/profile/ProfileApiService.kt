package ir.adromsh.forum.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.api.ApiClient
import ir.adromsh.forum.api.ApiService
import ir.adromsh.forum.models.Message
import ir.adromsh.forum.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileApiService(var context: Context) {
    private var apiService = ApiClient.getClient().create(ApiService::class.java)

    fun getProfileDate(): MutableLiveData<User> {
        var mutableLiveData = MutableLiveData<User>()
        apiService.getProfileData().enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت اطلاعات", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }

    fun updateProfile(
        name: String,
        family: String,
        phone: String,
        age: Int,
        jensiat: Int
    ): MutableLiveData<Message> {
        var mutableLiveData = MutableLiveData<Message>()
        apiService.updateProfile(name, family, phone, age, jensiat)
            .enqueue(object : Callback<Message> {
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Toast.makeText(context, "مشکل در دریافت اطلاعات", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    mutableLiveData.value = response.body()
                }

            })

        return mutableLiveData
    }

    fun uploadImage(id: RequestBody, filePart: MultipartBody.Part): MutableLiveData<Message> {
        var mutableLiveData = MutableLiveData<Message>()
        apiService.uploadImage(id, filePart).enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.i("LOG", t.toString())
                Toast.makeText(context, "مشکل در آپلود تصویر", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }

    fun educationi(phone: String): MutableLiveData<Message> {
        var mutableLiveData = MutableLiveData<Message>()
        apiService.education(phone).enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.i("LOG", t.toString())
                Toast.makeText(context, "مشکل در ثبت نام", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }
}