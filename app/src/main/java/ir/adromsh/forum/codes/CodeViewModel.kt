package ir.adromsh.forum.codes

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.models.Codes
import ir.adromsh.forum.models.Message

class CodeViewModel(application: Application) : AndroidViewModel(application) {
    private var apiService = CodeApiService()
    private var context = application

    fun getAllCodes(sort: Int): MutableLiveData<List<Codes>> {
        return apiService.getAllCodes(context, sort)
    }

    fun setPoint(code: Int, point: Int): MutableLiveData<Message> {
        return apiService.setPoint(context, code, point)
    }
    fun sendCode(title:String,text:String,code:String): MutableLiveData<Message> {
        return apiService.sendCode(context, title,text,code)
    }
    fun codeSearch(context: Context,search:String):MutableLiveData<List<Codes>>{
        return apiService.codeSearch(context,search)
    }
}