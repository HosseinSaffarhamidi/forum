package ir.adromsh.forum.home


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Course
import ir.adromsh.forum.room.RCourse
import ir.adromsh.forum.room.RoomViewModel
import ir.adromsh.forum.utils.Utils
import com.squareup.picasso.Picasso


class CourseDetailFragment : Fragment() {

    lateinit var viewModel:RoomViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_course_detail, container, false)
        //Utils.changeStatusBarColor(activity!!,R.color.colorPrimary)
        var bundle=arguments
        var course=bundle!!.getParcelable<Course>("course")
        viewModel=ViewModelProviders.of(this).get(RoomViewModel::class.java)
        var image=view.findViewById<ImageView>(R.id.img_courseDetail_image)
        var txtTitle=view.findViewById<TextView>(R.id.txt_courseDetail_title)
        var txtText=view.findViewById<TextView>(R.id.txt_courseDetail_text)
        var txtPrice=view.findViewById<TextView>(R.id.txt_courseDetail_price)
        var txtPriority=view.findViewById<TextView>(R.id.txt_courseDetail_priority)
        var txtLink=view.findViewById<TextView>(R.id.txt_courseDetail_link)
        var imgBookmark=view.findViewById<ImageView>(R.id.img_courseDetail_bookmark)
        var imgShare=view.findViewById<ImageView>(R.id.img_courseDetail_share)

        imgShare.setOnClickListener{
            var intent=Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,course!!.title+"\n \n"+course.link)
            startActivity(Intent.createChooser(intent," انتشار "+course.title))
            startActivity(intent)
        }

        viewModel.getSignedCourse(course!!.title!!).observe(this, Observer {
            if(it!=null){
                imgBookmark.setColorFilter(ContextCompat.getColor(context!!,
                    R.color.grey400
                ), android.graphics.PorterDuff.Mode.SRC_IN)
                imgBookmark.tag="signed"

            }
        })

          // activity!!.supportFragmentManager.beginTransaction().remove(this).commit()


        imgBookmark.setOnClickListener{
            if(imgBookmark.tag=="signed"){
                imgBookmark.setColorFilter(ContextCompat.getColor(context!!,
                    R.color.white
                ), android.graphics.PorterDuff.Mode.SRC_IN)
                imgBookmark.tag="unsigned"
                var rCourse=RCourse(course.id!!.toInt(),course!!.image!!,course!!.title!!,course!!.link!!,course!!.text!!,course!!.priority!!,course!!.price!!)
                viewModel.deleteCourse(rCourse)
                Toast.makeText(context,"دوره با موفقیت از نشان شده ها حذف شد",Toast.LENGTH_SHORT).show()
            }else{
                var rCourse=RCourse(course.id!!.toInt(),course!!.image!!,course!!.title!!,course!!.link!!,course!!.text!!,course!!.priority!!,course!!.price!!)
                viewModel.insert(rCourse)
                Toast.makeText(context,"دوره با موفقیت به نشان شده ها افزوده شد",Toast.LENGTH_SHORT).show()
            }

        }

        txtLink.setOnClickListener{
            var intent=Intent(Intent.ACTION_VIEW, Uri.parse(course!!.link))
            startActivity(intent)
        }
        Picasso.get().load(course!!.image).into(image)
        txtTitle.setText(course.title)
        txtText.setText(course.text)
        txtPrice.setText(course.price)
        txtPriority.setText(" اولویت زمانی دیدن این دوره : ${course.priority} ")
        return view
    }

    override fun onStop() {
        Utils.changeStatusBarColor(activity!!)
        super.onStop()
    }

}
