package ir.adromsh.forum.question


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.R
import ir.adromsh.forum.codes.NewCodeFragment
import ir.adromsh.forum.models.Message
import ir.adromsh.forum.profile.ProfileFragment
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques


class LoginFragment(var destination:String) : Fragment() {
    lateinit var viewModel:QuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        viewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        var txtSignup=view.findViewById<TextView>(R.id.txt_login_signup)
        var btnLogin=view.findViewById<Button>(R.id.btn_login_login)
        var edtEmail=view.findViewById<EditText>(R.id.edt_login_email)
        var edtPass=view.findViewById<EditText>(R.id.edt_login_pass)


        txtSignup.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_frame,SignupFragment(destination))
            transaction.commit()
        }

        btnLogin.setOnClickListener{
            if(Utils.isEmailValid(edtEmail.text.toString()) ) {
                if(edtPass.text.length>4){
                    viewModel.login(edtEmail.text.toString(),edtPass.text.toString()).observe(this,
                        Observer<Message>{
                            if(it.message.equals("ایمیل یا کلمه عبور اشتباه است")){
                                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                            }else{
                                viewModel.createToken(it.message,it.data,it.role)
                                Toast.makeText(context,"ورود با موفقیت انجام شد",Toast.LENGTH_SHORT).show()
                                if(destination=="account"){
                                    var transaction = activity!!.supportFragmentManager.beginTransaction()
                                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                                    transaction.replace(R.id.main_fragment_frame, ProfileFragment())
                                    transaction.commit()
                                }else if(destination=="code"){
                                    var transaction = activity!!.supportFragmentManager.beginTransaction()
                                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                                    transaction.replace(R.id.main_fragment_frame, NewCodeFragment())
                                    transaction.commit()
                                }else{
                                    var transaction = activity!!.supportFragmentManager.beginTransaction()
                                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                                    transaction.replace(R.id.main_fragment_frame, QuestionFragment())
                                    transaction.commit()
                                }

                            }
                        })
                }else{
                    Toast.makeText(context,"پسورد باید حداقل 4 کاراکتر باشد",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"ایمیل معتبر نیست",Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }


}
