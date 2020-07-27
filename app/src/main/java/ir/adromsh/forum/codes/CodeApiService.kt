package ir.adromsh.forum.codes

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.api.ApiClient
import ir.adromsh.forum.api.ApiService
import ir.adromsh.forum.models.Codes
import ir.adromsh.forum.models.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CodeApiService {
    private var apiService=ApiClient.getClient().create(ApiService::class.java)

    fun getAllCodes(context: Context,sort:Int):MutableLiveData<List<Codes>>{
        var mutableLiveData=MutableLiveData<List<Codes>>()
        apiService.getAllCodes(sort).enqueue(object:Callback<List<Codes>>{
            override fun onFailure(call: Call<List<Codes>>, t: Throwable) {
                Toast.makeText(context,"مشکل در دریافت کدها",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Codes>>, response: Response<List<Codes>>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }
    fun setPoint(context: Context,code:Int,point:Int):MutableLiveData<Message>{
        var mutableLiveData=MutableLiveData<Message>()
        apiService.setPoint(code,point).enqueue(object:Callback<Message>{
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(context,"مشکل در ثبت امتیاز",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }
    fun sendCode(context: Context,title:String,text:String,code:String):MutableLiveData<Message>{
        var mutableLiveData=MutableLiveData<Message>()
        apiService.sendNewCode(title,text,code).enqueue(object:Callback<Message>{
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(context,"مشکل در ثبت امتیاز",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }

    fun codeSearch(context: Context,search:String):MutableLiveData<List<Codes>>{
        var mutableLiveData=MutableLiveData<List<Codes>>()
        apiService.search(search).enqueue(object:Callback<List<Codes>>{
            override fun onFailure(call: Call<List<Codes>>, t: Throwable) {
                Toast.makeText(context,"مشکل در جستجو",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Codes>>, response: Response<List<Codes>>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }
}