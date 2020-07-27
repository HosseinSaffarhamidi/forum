package ir.adromsh.forum.question

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.api.ApiClient
import ir.adromsh.forum.api.ApiService
import ir.adromsh.forum.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionApiService(var context: Context) {
    var apiService: ApiService = ApiClient.getClient().create(ApiService::class.java)

    fun signup(
        name: String,
        family: String,
        email: String,
        pass: String,
        phone: String,
        jensiat: Int
    ): MutableLiveData<Message> {
        var mutableLiveData = MutableLiveData<Message>()
        apiService.signup(name, family, email, pass, phone, jensiat)
            .enqueue(object : Callback<Message> {
                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Toast.makeText(context, "مشکل در ثبت نام", Toast.LENGTH_SHORT).show()
                    Log.i("LOG", t.toString())
                }

                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    mutableLiveData.value = response.body()
                }

            })

        return mutableLiveData

    }

    fun login(email: String, pass: String): MutableLiveData<Message> {
        var mutableLiveData = MutableLiveData<Message>()
        apiService.login(email, pass).enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.i("LOG",t.toString())
                Toast.makeText(context, "خطا در ورود به حساب کاربری", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value = response.body()
            }

        })
        return mutableLiveData
    }

    fun getRules(): MutableLiveData<List<Rules>> {
        var mutableLiveData = MutableLiveData<List<Rules>>()
        apiService.getRules().enqueue(object : Callback<List<Rules>> {
            override fun onFailure(call: Call<List<Rules>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت قوانین", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Rules>>, response: Response<List<Rules>>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }

    fun getAllCourse(): MutableLiveData<List<Course>> {
        var mutableLiveData = MutableLiveData<List<Course>>()
        apiService.getAllCourse().enqueue(object : Callback<List<Course>> {
            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت دوره ها", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                mutableLiveData.value = response.body()
            }

        })
        return mutableLiveData
    }

    fun ask(title:String,text:String,course:String,type:Int):MutableLiveData<Message>{
        var mutableLiveData=MutableLiveData<Message>()
        apiService.ask(title,text, course, type).enqueue(object:Callback<Message>{
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.i("LOG",t.toString())
                Toast.makeText(context,"مشکل در ارسال سوال",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }

    fun getAnswers(page:Int):MutableLiveData<List<Answer>>{
        var mutableLiveData=MutableLiveData<List<Answer>>()
        apiService.getAnswer(page).enqueue(object :Callback<List<Answer>>{
            override fun onFailure(call: Call<List<Answer>>, t: Throwable) {
                Toast.makeText(context,"مشکل در دریافت اطلاعات",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Answer>>, response: Response<List<Answer>>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }

    fun returnedAnswer(id:String):MutableLiveData<Message>{
        var mutableLiveData=MutableLiveData<Message>()
        apiService.returnedAnswer(id).enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(context,"مشکل در ارسال اطلاعات",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }
    fun getRole():MutableLiveData<Message>{
        var mutableLiveData=MutableLiveData<Message>()
        apiService.getRole().enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(context,"مشکل در ارسال اطلاعات",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }

    fun sendAnswer(id:String,toUser:String,course:String,title:String,text:String):MutableLiveData<Message>{
        var mutableLiveData=MutableLiveData<Message>()
        apiService.sendAnswer(id,toUser, course, title,text).enqueue(object:Callback<Message>{
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.i("LOG",t.toString())
                Toast.makeText(context,"مشکل در پاسخ سوال",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }

    fun getPendingCode(): MutableLiveData<List<Codes>> {
        var mutableLiveData = MutableLiveData<List<Codes>>()
        apiService.getPendingCode().enqueue(object : Callback<List<Codes>> {
            override fun onFailure(call: Call<List<Codes>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت کدها", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Codes>>, response: Response<List<Codes>>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }
    fun updateCode(userId:String,codeId:String):MutableLiveData<Message>{
        var mutableLiveData=MutableLiveData<Message>()
        apiService.updateCode(userId,codeId).enqueue(object:Callback<Message>{
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.i("LOG",t.toString())
                Toast.makeText(context,"مشکل در تایید کد",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value=response.body()
            }

        })

        return mutableLiveData
    }

}


