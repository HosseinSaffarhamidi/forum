package ir.adromsh.forum.question


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Answer

class AnswerDialog(var answer:Answer,var onBtnAnswerClick:(answer:Answer)->Unit) : DialogFragment() {
    lateinit var myView:View
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(context!!)
        myView = LayoutInflater.from(context).inflate(R.layout.dialog_answer, null)
        setupViews()
        builder.setView(myView)
        return builder.create()
    }

    fun setupViews() {
        var txtTitle=myView.findViewById<TextView>(R.id.txt_answerDialog_title)
        var edtAnswer=myView.findViewById<TextView>(R.id.edt_answerDialog_answer)
        var btnAnswer=myView.findViewById<TextView>(R.id.btn_answerDialog_sendAnswer)

        txtTitle.text=answer.title

        btnAnswer.setOnClickListener{
            answer.text=edtAnswer.text.toString()
            answer.toUser=answer.fromUser
            onBtnAnswerClick(answer)
        }


    }
}