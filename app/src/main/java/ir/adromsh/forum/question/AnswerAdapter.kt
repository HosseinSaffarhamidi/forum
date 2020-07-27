package ir.adromsh.forum.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Answer

class AnswerAdapter (var context: Context): RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    var list:MutableList<Answer> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        var view=LayoutInflater.from(context).inflate(R.layout.answer_item,parent,false)
        return  AnswerViewHolder(view)
    }

    fun getPagedList(pagedList:List<Answer>){
        list.addAll(pagedList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        if(list[position].toUser=="admin"){
            holder.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.user_logo))
        }else{
            holder.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.logo))
        }
        holder.txtTitle.setText(list[position].title)
        holder.txtText.setText(list[position].text)
        holder.txtCourse.setText(list[position].course)
        holder.txtId.text=list[position].id
        if(list[position].type==0){
            holder.txtType.setText("مشاوره")
        }else{
            holder.txtType.setText("فنی")
        }
        holder.txtDate.setText(list[position].date)
        if(list[position].returned==1){
            holder.wrongTypeFrame.visibility=View.VISIBLE
            holder.parent.setBackgroundColor(ContextCompat.getColor(context,R.color.grey200))
        }else{
            holder.wrongTypeFrame.visibility=View.GONE
        }
    }

    class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parent=itemView.findViewById<RelativeLayout>(R.id.rel_answerItem_parent)
        var image=itemView.findViewById<ImageView>(R.id.img_answerItem_logo)
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_answerItem_title)
        var txtText=itemView.findViewById<TextView>(R.id.txt_answerItem_text)
        var txtCourse=itemView.findViewById<TextView>(R.id.txt_answerItem_course)
        var txtType=itemView.findViewById<TextView>(R.id.txt_answerItem_type)
        var txtDate=itemView.findViewById<TextView>(R.id.txt_answerItem_date)
        var txtId=itemView.findViewById<TextView>(R.id.txt_answerItem_id)
        var wrongTypeFrame=itemView.findViewById<LinearLayout>(R.id.linear_answerItem_wrongTypeFrame)

    }
}