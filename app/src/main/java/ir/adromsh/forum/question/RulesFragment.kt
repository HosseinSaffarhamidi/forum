package ir.adromsh.forum.question


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ir.adromsh.forum.R
import ir.adromsh.forum.models.Rules
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques


class RulesFragment : Fragment() {

    lateinit var viewModel:QuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_rules, container, false)
        var recycler=view.findViewById<RecyclerView>(R.id.rv_rules_rules)
        recycler.layoutManager=LinearLayoutManager(context)
        viewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        viewModel.getRules().observe(this, Observer<List<Rules>> {
            Utils.customAnimation(recycler,duration = 1000,animation = Techniques.BounceInDown)
            recycler.adapter=RulesAdapter(it)
        })
        return  view
    }




}
