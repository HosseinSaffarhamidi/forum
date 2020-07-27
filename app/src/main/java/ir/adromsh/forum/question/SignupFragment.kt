package ir.adromsh.forum.question


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.R
import ir.adromsh.forum.codes.NewCodeFragment
import ir.adromsh.forum.models.Message
import ir.adromsh.forum.profile.ProfileFragment
import ir.adromsh.forum.utils.Utils
import ir.adromsh.forum.utils.Utils.isEmailValid
import com.daimajia.androidanimations.library.Techniques


class SignupFragment(var destination:String) : Fragment() {
    private lateinit var viewModel: QuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_signup, container, false)
        var edtName = view.findViewById<EditText>(R.id.edt_signup_name)
        var edtFamily = view.findViewById<EditText>(R.id.edt_signup_family)
        var edtEmail = view.findViewById<EditText>(R.id.edt_signup_email)
        var edtPhone = view.findViewById<EditText>(R.id.edt_signup_phone)
        var edtpass = view.findViewById<EditText>(R.id.edt_signup_pass)
        var radioGroup=view.findViewById<RadioGroup>(R.id.rg_signup_radioGroup)

        viewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)



        var btnSignup = view.findViewById<Button>(R.id.btn_signup_signup)
        btnSignup.setOnClickListener {
            if (edtName.text.isEmpty() || edtFamily.text.isEmpty() || edtPhone.text.isEmpty()) {
                Toast.makeText(context, "پر کردن تمامی فیلدها الزامی است", Toast.LENGTH_SHORT)
                    .show()

            } else {
                if (!isEmailValid(edtEmail.text.toString())) {
                    Toast.makeText(context, "ایمیل معتبر نیست", Toast.LENGTH_SHORT).show()
                } else {
                    if (edtpass.text.length < 4 || edtpass.text.isEmpty()) {
                        Toast.makeText(
                            context,
                            "پسود باید حداقل 4 کاراکتر باشد",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        var jensiat=0
                        var selectedId=radioGroup.checkedRadioButtonId
                        var radioButton=view.findViewById<RadioButton>(selectedId)
                        var text=radioButton.text
                        if(text.equals("زن")){
                            jensiat=1
                        }
                        viewModel.signup(
                            edtName.text.toString(),
                            edtFamily.text.toString(),
                            edtEmail.text.toString(),
                            edtpass.text.toString(),
                            edtPhone.text.toString(),
                            jensiat
                        ).observe(this,
                            Observer<Message> {
                                if(!it.message.equals("این ایمیل قبلا ثبت شده است")){
                                    viewModel.createToken(it.message,it.data,it.role)
                                    Toast.makeText(context, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show()
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

                            }
                        )
                    }
                }
            }

        }
        return view
    }


}
