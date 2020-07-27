package ir.adromsh.forum.codes


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import ir.adromsh.forum.R


class NewCodeFragment : Fragment() {

    lateinit var viewModel:CodeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Utils.changeStatusBarColor(activity!!,R.color.blueGreen)
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_new_code, container, false)
        viewModel=ViewModelProviders.of(this).get(CodeViewModel::class.java)
        var edtTitle=view.findViewById<EditText>(R.id.edt_newCode_title)
        var edtText=view.findViewById<EditText>(R.id.edt_newCode_text)
        var edtCode=view.findViewById<EditText>(R.id.edt_newCode_code)
        var btnSend=view.findViewById<Button>(R.id.linear_newCode_send)

        btnSend.setOnClickListener{
            if(edtTitle.text.isEmpty() || edtText.text.isEmpty() || edtCode.text.isEmpty()){
                Toast.makeText(context,"پر کردن همه فیلد ها اجباری است",Toast.LENGTH_SHORT).show()
            }else{

                viewModel.sendCode(edtTitle.text.toString(),edtText.text.toString(),edtCode.text.toString()).observe(this,
                    Observer {
                        if(it.message=="کد شما پس از بررسی نمایش داده خواهد شد"){
                            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context,"مشکل در ارسال کد",Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
        return view
    }


}
