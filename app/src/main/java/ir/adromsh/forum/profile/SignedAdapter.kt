package ir.adromsh.forum.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.room.RCourse
import com.squareup.picasso.Picasso

class SignedAdapter(var list:List<RCourse>,var onSignedItemClick:(course:RCourse)->Unit) : RecyclerView.Adapter<SignedAdapter.SignedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignedViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.signed_item,parent,false)
        return SignedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SignedViewHolder, position: Int) {
        Picasso.get().load(list[position].image).into(holder.image)
        holder.txtTitle.text=list[position].title
        holder.parent.setOnClickListener{
            onSignedItemClick(list[position])
        }
    }

    class SignedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parent=itemView.findViewById<ConstraintLayout>(R.id.constraint_signedItem_parent)
        var image=itemView.findViewById<ImageView>(R.id.img_signedItem_image)
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_signedItem_title)
    }
}