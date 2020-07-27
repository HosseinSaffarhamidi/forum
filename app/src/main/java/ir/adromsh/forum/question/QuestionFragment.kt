package ir.adromsh.forum.question


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.R
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques
import com.google.android.material.floatingactionbutton.FloatingActionButton


class QuestionFragment : Fragment() {

    lateinit var viewModel:QuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_question, container, false)
        var fab = view.findViewById<FloatingActionButton>(R.id.fab_question_newQuestion)
        var linearRules = view.findViewById<RelativeLayout>(R.id.linear_question_rules)
        var checkBox = view.findViewById<CheckBox>(R.id.ch_quesetion_agree)
        var txtRules=view.findViewById<TextView>(R.id.txt_question_agreeWhitRules)
        var questionList=view.findViewById<RelativeLayout>(R.id.rel_question_questionList)
        viewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Utils.customAnimation(checkBox,animation = Techniques.FadeOut)
                Utils.customAnimation(txtRules,animation = Techniques.FadeOut)
                Utils.customAnimation(fab,animation = Techniques.SlideInRight)
                fab.show()
            }else{
                fab.hide()
            }
        }

        linearRules.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.add(R.id.main_fragment_frame,RulesFragment(),"rulesFragment")
            transaction.addToBackStack(null)
            transaction.commit()
        }
        fab.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.add(R.id.main_fragment_frame,AskFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        questionList.setOnClickListener{

            viewModel.getRole().observe(this, Observer {
                var role=it.role
                if(role=="user"){
                    var transaction=activity!!.supportFragmentManager.beginTransaction()
                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                    transaction.add(R.id.main_fragment_frame,AnswerFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }else{
                    var transaction=activity!!.supportFragmentManager.beginTransaction()
                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                    transaction.add(R.id.main_fragment_frame,AdminFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            })


        }
        return view

    }




}
