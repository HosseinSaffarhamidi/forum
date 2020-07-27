package ir.adromsh.forum.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Course
import com.squareup.picasso.Picasso


class BestCourseAdapter(var context: Context, var list:List<Course>, var onCoursItemClick:(item:Course)->Unit): RecyclerView.Adapter<BestCourseAdapter.BestCourseViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestCourseViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.best_course_item,parent,false)
        return BestCourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: BestCourseViewHolder, position: Int) {
        Picasso.get().load(list[position].image).placeholder(R.drawable.loading).into(holder.image)
        holder.txtTitle.setText(list[position].title)
        holder.txtPrice.setText(list[position].price)
        holder.txtPriority.setText(list[position].priority.toString())

      /*  val background=holder.parent.background
        val wrappedDrawable = DrawableCompat.wrap(background!!)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(list[position].color))*/



        holder.parent.setOnClickListener{
            onCoursItemClick(list[position])
        }
    }

    class BestCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parent=itemView.findViewById<RelativeLayout>(R.id.rel_bestCourseItem_parent)
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_bestCourse_title)
        var image=itemView.findViewById<ImageView>(R.id.img_allCourseItem_image)
        var txtPrice=itemView.findViewById<TextView>(R.id.txt_bestCourse_price)
        var txtPriority=itemView.findViewById<TextView>(R.id.txt_bestCourse_priority)
    }
}