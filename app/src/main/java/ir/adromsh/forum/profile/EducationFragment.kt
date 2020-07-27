package ir.adromsh.forum.profile


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




class EducationFragment : Fragment() {
    lateinit var viewModel:ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view= inflater.inflate(R.layout.fragment_education, container, false)
        viewModel=ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        var edtPhone=view.findViewById<EditText>(R.id.edt_education_phone)
        var btnSubmit=view.findViewById<Button>(R.id.btn_education_submit)

        btnSubmit.setOnClickListener{
            if(edtPhone.text.length>=11){
                viewModel.education(edtPhone.text.toString()).observe(this, Observer {
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                })
            }else{
                Toast.makeText(context,"شماره همراه را به شکل صحیح وارد کنید",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}
