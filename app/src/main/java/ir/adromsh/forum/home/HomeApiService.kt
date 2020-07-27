package ir.adromsh.forum.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.api.ApiClient
import ir.adromsh.forum.api.ApiService
import ir.adromsh.forum.models.Codes
import ir.adromsh.forum.models.Course
import ir.adromsh.forum.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeApiService(var context:Application) {
    private var apiService=ApiClient.getClient().create(ApiService::class.java)

    fun getBestUser():MutableLiveData<List<User>>{
        var mutableLiveData=MutableLiveData<List<User>>()
        apiService.getBestUser().enqueue(object:Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(context,"خطای ناشناخته",Toast.LENGTH_SHORT).show()
                Log.i("LOG",t.toString())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                mutableLiveData.value=(response.body())
            }

        })

        return mutableLiveData
    }

    fun getCourses():MutableLiveData<List<Course>>{
        var courseMutable=MutableLiveData<List<Course>>()
        apiService.getCourses().enqueue(object :Callback<List<Course>>{
            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Toast.makeText(context,"خطای ناشناخته",Toast.LENGTH_SHORT).show()
                Log.i("LOG",t.toString())
            }

            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                courseMutable.value=response.body()
            }

        })

        return  courseMutable
    }

    fun getCodes():MutableLiveData<List<Codes>>{
        var codesMutable=MutableLiveData<List<Codes>>()
        apiService.getCodes().enqueue(object :Callback<List<Codes>>{
            override fun onFailure(call: Call<List<Codes>>, t: Throwable) {
                Toast.makeText(context,"خطای ناشناخته",Toast.LENGTH_SHORT).show()
                Log.i("LOG",t.toString())
            }

            override fun onResponse(call: Call<List<Codes>>, response: Response<List<Codes>>) {
                codesMutable.value=response.body()
            }

        })

        return  codesMutable
    }
}