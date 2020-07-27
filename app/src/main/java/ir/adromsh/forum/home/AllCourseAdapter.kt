package ir.adromsh.forum.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Course
import com.squareup.picasso.Picasso

class AllCourseAdapter(var list:List<Course>,var onCourseItemClick:(course:Course)->Unit) : RecyclerView.Adapter<AllCourseAdapter.AllCourseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCourseViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.all_course_item,parent,false)
        return AllCourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AllCourseViewHolder, position: Int) {
        holder.txtTitle.text=list[position].title
        holder.txtPrice.text=list[position].price
        holder.txtPriority.text=list[position].priority.toString()
        Picasso.get().load(list[position].image).into(holder.image)
        holder.parent.setOnClickListener{
            onCourseItemClick(list[position])
        }
    }

    class AllCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parent=itemView.findViewById<ConstraintLayout>(R.id.constraint_allCourseItem_parent)
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_allCourseItem_title)
        var image=itemView.findViewById<ImageView>(R.id.img_allCourseItem_image)
        var txtPrice=itemView.findViewById<TextView>(R.id.txt_allCourseItem_price)
        var txtPriority=itemView.findViewById<TextView>(R.id.txt_allCourseItem_priority)
    }
}