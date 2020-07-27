package ir.adromsh.forum.codes

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
import ir.adromsh.forum.models.Codes
import com.squareup.picasso.Picasso


class CodeAdapter(var context: Context, var list:List<Codes>,var onCodeItemClick:(id:String?,position:Int)->Unit): RecyclerView.Adapter<CodeAdapter.CodeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.code_item,parent,false)
        return CodeViewHolder(view)
    }
    fun setSortedList(sortedList:List<Codes>){
        list=sortedList
        notifyDataSetChanged()
    }

    fun changePoint(updatedCode:Codes){
        var mutableList=list.toMutableList()
        mutableList.set(DetailCodeFragment.updatedPosition,updatedCode)
        notifyDataSetChanged()
    }

    fun searchedList(searchedList:List<Codes>){
        list=searchedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        if(list[position].image!=null){
            Picasso.get().load(list[position].image).into(holder.image)
        }else{
            if(list[position].jensiat==0){
                holder.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.man))
            }else{
                holder.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.woman))
            }
        }

        holder.txtTitle.setText(list[position].title)
        holder.txtText.setText(list[position].text)
        holder.txtPoint.text="${list[position].point} رای مثبت "
        holder.txtDate.setText("  تاریخ : ${list[position].date}")

        holder.parent.setOnClickListener{
            onCodeItemClick(list[position].codeId,position)
        }
    }

    class CodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_codeItem_title)
        var txtText=itemView.findViewById<TextView>(R.id.txt_codeItem_text)
        var txtPoint=itemView.findViewById<TextView>(R.id.txt_codeItem_point)
        var txtDate=itemView.findViewById<TextView>(R.id.txt_codeItem_date)
        var parent=itemView.findViewById<RelativeLayout>(R.id.card_codeItem_parent)
        var image=itemView.findViewById<ImageView>(R.id.img_codeItem_userImage)
    }
}