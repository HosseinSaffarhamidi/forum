package ir.adromsh.forum.question

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.models.*


class QuestionRepository(var context: Context) {
    private var apiService = QuestionApiService(context)

    fun signup(
        name: String,
        family: String,
        email: String,
        pass: String,
        phone: String,
        jensiat: Int
    ): MutableLiveData<Message> {

        return apiService.signup(name, family, email, pass, phone, jensiat)

    }

    fun login(email: String, pass: String): MutableLiveData<Message> {
        return apiService.login(email, pass)
    }

    fun getToken(): String {
        var sharedpreference = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        var token = sharedpreference.getString("key", "")
        return token!!
    }
    fun getData(): String {
        var sharedpreference = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        var data = sharedpreference.getString("data", "")
        return data!!
    }


    fun createToken(token:String,data:String,role:String){
        var sharedPreference = context.getSharedPreferences(
            "token",
            Context.MODE_PRIVATE
        )
        var editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("key", token)
        editor.putString("data", data)
        editor.putString("role", role)
        editor.apply()
    }
    fun getRules():MutableLiveData<List<Rules>>{
        return apiService.getRules()
    }
    fun getAllCourse():MutableLiveData<List<Course>>{
        return apiService.getAllCourse()
    }
    fun ask(title:String,text:String,course:String,type:Int):MutableLiveData<Message>{
        return apiService.ask(title, text, course, type)
    }
    fun getAnswers(page:Int):MutableLiveData<List<Answer>>{
        return apiService.getAnswers(page)
    }
    fun returnedAnswer(id:String):MutableLiveData<Message>{
        return apiService.returnedAnswer(id)
    }
    fun getRole():MutableLiveData<Message>{
        return apiService.getRole()
    }
    fun sendAnswer(id:String,toUser:String,course:String,title:String,text:String):MutableLiveData<Message>{
        return apiService.sendAnswer(id, toUser, course, title, text)
    }
    fun getPendignCode():MutableLiveData<List<Codes>>{
        return apiService.getPendingCode()
    }
    fun updateCode(userId:String,codeId:String):MutableLiveData<Message>{
        return apiService.updateCode(userId, codeId)
    }
}