package ir.adromsh.forum.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ir.adromsh.forum.R
import ir.adromsh.forum.question.QuestionViewModel
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques


class AllCourseFragment : Fragment() {
    lateinit var viewModel:QuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_all_course, container, false)
        viewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        var recycler=view.findViewById<RecyclerView>(R.id.rv_allCourse_list)
        var frame=view.findViewById<FrameLayout>(R.id.frame_allCourse_waitingFrame)
        recycler.layoutManager=LinearLayoutManager(context)
        viewModel.getAllCourse().observe(this, Observer {
            frame.visibility=View.GONE
            recycler.adapter=AllCourseAdapter(it) {
                var transaction = activity!!.supportFragmentManager.beginTransaction()
                Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
                var detailFragment=
                    CourseDetailFragment()
                var bundle=Bundle()
                bundle.putParcelable("course",it)
                detailFragment.arguments=bundle
                transaction.add(R.id.main_fragment_frame, detailFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        return view
    }


}
