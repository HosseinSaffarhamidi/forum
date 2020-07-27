package ir.adromsh.forum.question


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques


class AdminFragment : Fragment() {
    lateinit var viewModel: QuestionViewModel
    lateinit var dialog:AnswerDialog
    var page:Int=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_admin, container, false)
        var recycler = view.findViewById<RecyclerView>(R.id.rv_admin_list)
        recycler.layoutManager = LinearLayoutManager(context)
        var txtNoquestionWarning=view.findViewById<TextView>(R.id.txt_admin_noQuestionWarning)
        viewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        var imgPending=view.findViewById<ImageView>(R.id.img_admin_pendingItems)

        imgPending.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.add(R.id.main_fragment_frame, PendingFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }



        viewModel.getAnswers(page).observe(this, Observer {
            if(it.isEmpty()){
                txtNoquestionWarning.visibility=View.VISIBLE
                recycler.visibility=View.GONE
            }else{
                txtNoquestionWarning.visibility=View.GONE
                recycler.adapter = AdminAnswerAdapter(context!!, it) {answer,type->
                    if(type=="wrongType"){
                        viewModel.returnedAnswer(answer.id).observe(this, Observer {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        })
                    }else{
                        dialog=AnswerDialog(answer) {
                            viewModel.sendAnswer(answer.id,answer.toUser,answer.course,answer.title,answer.text).observe(this,
                                Observer {
                                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                })
                        }
                        dialog.show(childFragmentManager,null)
                    }


                }
            }

        })
        return view
    }


}
