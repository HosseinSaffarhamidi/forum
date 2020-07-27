package ir.adromsh.forum.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ir.adromsh.forum.R
import ir.adromsh.forum.home.CourseDetailFragment
import ir.adromsh.forum.models.Course
import ir.adromsh.forum.room.RoomViewModel
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques

class SignedFragment : Fragment() {
    lateinit var viewModel:RoomViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_signed, container, false)
        viewModel=ViewModelProviders.of(this).get(RoomViewModel::class.java)
        var recycler=view.findViewById<RecyclerView>(R.id.rv_signed_list)
        var txtWarning=view.findViewById<TextView>(R.id.txt_signed_emptyList)
        recycler.layoutManager=LinearLayoutManager(context)
        viewModel.getAllCourse().observe(this, Observer {
            if(it.isNotEmpty()){
                recycler.adapter=SignedAdapter(it) {
                    var transaction = activity!!.supportFragmentManager.beginTransaction()
                    Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                    var detailFragment=
                        CourseDetailFragment()
                    var bundle=Bundle()
                    var course=Course(it.id.toString(),it.title,it.price,it.image,it.link,it.text,it.priority,"")
                    bundle.putParcelable("course",course)
                    detailFragment.arguments=bundle
                    transaction.add(R.id.main_fragment_frame, detailFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }else{
                recycler.visibility=View.GONE
                txtWarning.visibility=View.VISIBLE
            }

        })
        return view
    }


}
