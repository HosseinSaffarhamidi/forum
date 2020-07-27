package ir.adromsh.forum.question


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AnswerFragment : Fragment() {
    lateinit var viewModel:QuestionViewModel
    var page:Int=1
    lateinit var answerAdapter:AnswerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_answer, container, false)
        viewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        var fab=view.findViewById<FloatingActionButton>(R.id.fab_answer)
        var emptyBoxState=view.findViewById<LinearLayout>(R.id.lin_answer_emptyFrame)
        var txtNewQuestion=view.findViewById<TextView>(R.id.txt_answer_askNewQuestion)
        var recycler=view.findViewById<RecyclerView>(R.id.rv_answer_list)
        recycler.layoutManager=LinearLayoutManager(context)


        recycler.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!recycler.canScrollVertically(1)){
                    page++
                    viewModel.getAnswers(page).observe(this@AnswerFragment, Observer {
                        if(!it.isEmpty()){
                            answerAdapter.getPagedList(it)

                        }

                    })
                }
            }
        })


        viewModel.getAnswers(page).observe(this, Observer {
            if(!it.isEmpty()){
                Utils.customAnimation(recycler,animation = Techniques.SlideInRight)
                answerAdapter= AnswerAdapter(context!!)
                answerAdapter.getPagedList(it)
                recycler.adapter=answerAdapter
            }else{
                emptyBoxState.visibility=View.VISIBLE

            }

        })

        txtNewQuestion.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.replace(R.id.main_fragment_frame,AskFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }


        fab.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.replace(R.id.main_fragment_frame,AskFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }


}
