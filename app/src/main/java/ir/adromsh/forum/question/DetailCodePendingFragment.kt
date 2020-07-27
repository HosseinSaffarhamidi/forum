package ir.adromsh.forum.question


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ir.adromsh.forum.R


class DetailCodePendingFragment : Fragment() {

    lateinit var viewModel:QuestionViewModel
    lateinit var pendingAdapter:PendingCodeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_detail_code_pending, container, false)
        var txtEmpty=view.findViewById<TextView>(R.id.txt_detailCodePending_empty)
        var frameWaiting=view.findViewById<FrameLayout>(R.id.frame_detailCodePendign_wiating)
        var recycler=view.findViewById<RecyclerView>(R.id.rv_detailCodePending_list)
        recycler.layoutManager=LinearLayoutManager(context)
        viewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        viewModel.getPendignCode().observe(this, Observer {
            frameWaiting.visibility=View.GONE

            if(!it.isEmpty()){
                txtEmpty.visibility=View.GONE
            }

            pendingAdapter= PendingCodeAdapter(context!!,it) {
                viewModel.updateCode(it.id!!,it.codeId!!).observe(this, Observer {
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                })
            }

            recycler.adapter=pendingAdapter
        })
        return view
    }


}
