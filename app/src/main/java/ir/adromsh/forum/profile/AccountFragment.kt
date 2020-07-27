package ir.adromsh.forum.profile


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ir.adromsh.forum.R
import ir.adromsh.forum.models.ProfileAccount
import ir.adromsh.forum.question.LoginFragment
import ir.adromsh.forum.question.QuestionViewModel
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques


class AccountFragment : Fragment() {

    lateinit var qViewModel:QuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_account, container, false)
        qViewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        var recycler=view.findViewById<RecyclerView>(R.id.rv_account_list)
        recycler.layoutManager=LinearLayoutManager(context)
        recycler.adapter=AccountAdapter(context!!,createMenuList()) {
            when(it){
                "نشان شده ها"->{
                    var transaction=activity!!.supportFragmentManager.beginTransaction()
                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                    transaction.replace(R.id.main_fragment_frame, SignedFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                "حساب کاربری"->{
                    var transaction=activity!!.supportFragmentManager.beginTransaction()
                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                    transaction.replace(R.id.main_fragment_frame, EditProfileFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                "کارآموزی"->{
                    var transaction=activity!!.supportFragmentManager.beginTransaction()
                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                    transaction.replace(R.id.main_fragment_frame, EducationFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                "اشتراک گذاری"->{
                    var intent= Intent(Intent.ACTION_SEND)
                    intent.type="text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT,"")
                    startActivity(Intent.createChooser(intent," انتشار اپلیکیشن پرسش و پاسخ"))
                    startActivity(intent)
                }


            }
        }
        var txtLogin=view.findViewById<TextView>(R.id.txt_account_login)

        if(qViewModel.getToken()==""){
            txtLogin.visibility=View.VISIBLE
            recycler.visibility=View.GONE
        }else{
            txtLogin.visibility=View.GONE
            recycler.visibility=View.VISIBLE
        }
        txtLogin.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.add(R.id.main_fragment_frame, LoginFragment("account"))
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    private fun createMenuList():MutableList<ProfileAccount> {
        var mutableList= mutableListOf<ProfileAccount>()
        var menu1=ProfileAccount("نشان شده ها",R.drawable.bookmark_plus)
        mutableList.add(menu1)
        var menu2=ProfileAccount("حساب کاربری",R.drawable.account_card_details)
        mutableList.add(menu2)
        var menu3=ProfileAccount("کارآموزی",R.drawable.cast_education)
        mutableList.add(menu3)
        var menu4=ProfileAccount("اشتراک گذاری",R.drawable.share_variant)
        mutableList.add(menu4)

        return mutableList
    }


}
