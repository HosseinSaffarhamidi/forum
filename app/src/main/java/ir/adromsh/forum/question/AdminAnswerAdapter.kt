package ir.adromsh.forum.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Answer

class AdminAnswerAdapter (var context: Context, var list:List<Answer>,var onAdminAnswerItemClick:(answer:Answer,type:String)->Unit): RecyclerView.Adapter<AdminAnswerAdapter.AnswerAdminViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerAdminViewHolder {
        var view= LayoutInflater.from(context).inflate(R.layout.answer_admin_item,parent,false)
        return  AnswerAdminViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnswerAdminViewHolder, position: Int) {
        if(list[position].toUser=="admin"){
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_logo))
        }else{
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo))
        }
        holder.txtTitle.setText(list[position].title)
        holder.txtText.setText(list[position].text)
        holder.txtCourse.setText(list[position].course)
        holder.txtId.text=list[position].id
        holder.txtName.text=list[position].name+" "+list[position].family
        if(list[position].type==0){
            holder.txtType.setText("مشاوره")
        }else{
            holder.txtType.setText("فنی")
        }
        holder.txtDate.setText(list[position].date)

        holder.btnWrongType.setOnClickListener{
            onAdminAnswerItemClick(list[position],"wrongType")
        }

        holder.btnSendAnswer.setOnClickListener{
            onAdminAnswerItemClick(list[position],"answer")

        }


    }

    class AnswerAdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parent=itemView.findViewById<RelativeLayout>(R.id.rel_adminItem_parent)
        var image=itemView.findViewById<ImageView>(R.id.img_adminItem_logo)
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_adminItem_title)
        var txtText=itemView.findViewById<TextView>(R.id.txt_adminItem_text)
        var txtCourse=itemView.findViewById<TextView>(R.id.txt_adminItem_course)
        var txtType=itemView.findViewById<TextView>(R.id.txt_adminItem_type)
        var txtDate=itemView.findViewById<TextView>(R.id.txt_adminItem_date)
        var txtId=itemView.findViewById<TextView>(R.id.txt_adminItem_id)
        var btnWrongType=itemView.findViewById<TextView>(R.id.btn_adminItem_wrongType)
        var btnSendAnswer=itemView.findViewById<TextView>(R.id.btn_adminItem_answer)
        var txtName=itemView.findViewById<TextView>(R.id.txt_adminItem_name)

    }
}