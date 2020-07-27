package ir.adromsh.forum.question

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.models.*
import ir.adromsh.forum.utils.Utils

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = QuestionRepository(application)
    private var context = application
    fun signup(
        name: String,
        family: String,
        email: String,
        pass: String,
        phone: String,
        jensiat: Int
    ): MutableLiveData<Message> {

        return repository.signup(name, family, email, pass, phone, jensiat)

    }

    fun login(email: String, pass: String): MutableLiveData<Message> {
        return repository.login(email, pass)
    }

    fun createToken(token: String, data: String, role: String) {
        Utils.myToken = token
        repository.createToken(token, data, role)
    }

    fun getToken(): String {
        Utils.myToken = repository.getToken()
        return repository.getToken()
    }

    fun getData(): String {
        return repository.getData()
    }

    fun getRole(): MutableLiveData<Message> {
        return repository.getRole()
    }

    fun ask(title: String, text: String, course: String, type: Int): MutableLiveData<Message> {
        return repository.ask(title, text, course, type)
    }

    fun getRules(): MutableLiveData<List<Rules>> {
        return repository.getRules()
    }

    fun getAllCourse(): MutableLiveData<List<Course>> {
        return repository.getAllCourse()
    }

    fun getAnswers(page: Int): MutableLiveData<List<Answer>> {
        return repository.getAnswers(page)
    }

    fun returnedAnswer(id: String): MutableLiveData<Message> {
        return repository.returnedAnswer(id)
    }

    fun sendAnswer(
        id: String,
        toUser: String,
        course: String,
        title: String,
        text: String
    ): MutableLiveData<Message> {
        return repository.sendAnswer(id, toUser, course, title, text)
    }

    fun getPendignCode(): MutableLiveData<List<Codes>> {
        return repository.getPendignCode()
    }
    fun updateCode(userId:String,codeId:String):MutableLiveData<Message>{
        return repository.updateCode(userId, codeId)
    }
}