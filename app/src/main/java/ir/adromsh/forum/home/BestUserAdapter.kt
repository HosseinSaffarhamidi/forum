package ir.adromsh.forum.home

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
import ir.adromsh.forum.models.User
import com.squareup.picasso.Picasso

class BestUserAdapter(var bestUserList: List<User>,var context:Context,var onBestUserItemClick:(id:String)->Unit) : RecyclerView.Adapter<BestUserAdapter.BestUserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestUserViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.best_user_item,parent,false)
        return  BestUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  bestUserList.size
    }

    override fun onBindViewHolder(holder: BestUserViewHolder, position: Int) {
        holder.txtName.setText(bestUserList[position].name+" "+bestUserList[position].family)
        holder.txtPoint.text="${bestUserList[position].point} رای مثبت "
        if(bestUserList[position].image!=null){
            Picasso.get().load(bestUserList[position].image).into(holder.image)
        }else{
            if(bestUserList[position].jensiat==0){
                holder.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.man))
            }else{
                holder.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.woman))
            }
        }
        holder.parent.setOnClickListener{
            onBestUserItemClick(bestUserList[position].id)
        }
    }

    class BestUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image=itemView.findViewById<ImageView>(R.id.img_editProfile_image)
        var txtName=itemView.findViewById<TextView>(R.id.txt_bestUserItem_name)
        var txtPoint=itemView.findViewById<TextView>(R.id.txt_bestUserItem_point)
        var parent=itemView.findViewById<RelativeLayout>(R.id.rel_bestUserItem_parent)
    }
}