package ir.adromsh.forum.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.cart.CartFragment
import ir.adromsh.forum.codes.CodesFragment
import ir.adromsh.forum.codes.DetailCodeFragment
import ir.adromsh.forum.models.Codes
import ir.adromsh.forum.models.Course
import ir.adromsh.forum.models.User
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques


class HomeFragment(var onbottomNavigationItemChange: (currentItem: Int) -> Unit) : Fragment() {

    lateinit var myView: View
    lateinit var viewModel: HomeViewModel
    lateinit var bestUserAdapter: BestUserAdapter
    lateinit var bestCourseAdapter: BestCourseAdapter
    lateinit var bestCodesAdapter: BestCodesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_home, container, false)
        var bestUserRecycler = myView.findViewById<RecyclerView>(R.id.rv_home_bestUsers)
        var bestCourseRecyclerView = myView.findViewById<RecyclerView>(R.id.rv_home_bestCourses)
        var bestCodeRecyclerView = myView.findViewById<RecyclerView>(R.id.rv_home_bestCodes)
        var btnAllCourse = myView.findViewById<RelativeLayout>(R.id.rel_home_allCourse)
        var txtSeeAllCodes = myView.findViewById<TextView>(R.id.txt_home_seeAllCodes)
        var imgBuy=myView.findViewById<ImageView>(R.id.img_home_buy1)

        imgBuy.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(
                activity!!.findViewById(R.id.main_fragment_frame),
                animation = Techniques.SlideInRight
            )
            transaction.add(R.id.main_fragment_frame, CartFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }









        txtSeeAllCodes.setOnClickListener {
            var transaction = activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(
                activity!!.findViewById(R.id.main_fragment_frame),
                animation = Techniques.SlideInRight
            )
            transaction.add(R.id.main_fragment_frame, CodesFragment())
            transaction.commit()
            onbottomNavigationItemChange(1)
        }

        btnAllCourse.setOnClickListener {
            var transaction = activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(
                activity!!.findViewById(R.id.main_fragment_frame),
                animation = Techniques.SlideInRight
            )
            transaction.add(R.id.main_fragment_frame, AllCourseFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        bestUserRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        bestCourseRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        bestCodeRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getBestUser().observe(this,
            Observer<List<User>> {
                bestUserAdapter = BestUserAdapter(it, context!!) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
                bestUserRecycler.adapter = bestUserAdapter
            })

        viewModel.getCourses().observe(this, Observer<List<Course>> {
            bestCourseAdapter = BestCourseAdapter(context!!, it) {
                var transaction = activity!!.supportFragmentManager.beginTransaction()
                Utils.customAnimation(
                    activity!!.findViewById(R.id.main_fragment_frame),
                    animation = Techniques.SlideInRight
                )
                var detailFragment =
                    CourseDetailFragment()
                var bundle = Bundle()
                bundle.putParcelable("course", it)
                detailFragment.arguments = bundle
                transaction.add(R.id.main_fragment_frame, detailFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            bestCourseRecyclerView.adapter = bestCourseAdapter
        })

        viewModel.getCodes().observe(this, Observer<List<Codes>> {
            bestCodesAdapter = BestCodesAdapter(it) {
                var transaction = activity!!.supportFragmentManager.beginTransaction()
                Utils.customAnimation(
                    activity!!.findViewById(R.id.main_fragment_frame),
                    animation = Techniques.SlideInRight
                )
                var detaiCode =
                    DetailCodeFragment() {

                    }
                var bundle = Bundle()
                bundle.putParcelable("codes", it)
                detaiCode.arguments = bundle
                transaction.add(R.id.main_fragment_frame, detaiCode)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            bestCodeRecyclerView.adapter = bestCodesAdapter
        })

        return myView
    }





}




