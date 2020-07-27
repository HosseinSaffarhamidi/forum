package ir.adromsh.forum.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Codes
import com.squareup.picasso.Picasso
import io.github.kbiakov.codeview.CodeView

class PendingCodeAdapter(var context: Context, var list:List<Codes>,var onButtonSubmitClick:(code:Codes)->Unit): RecyclerView.Adapter<PendingCodeAdapter.PendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.pending_code_item,parent,false);
        return PendingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
        holder.txtTitle.text=list[position].title
        holder.txtText.text=list[position].text
        holder.txtName.text=list[position].name+" "+list[position].family
        holder.txtdate.text=list[position].date
        if (list[position].image != null) {
            Picasso.get().load(list[position].image).into(holder.image)
        } else {
            if (list[position].jensiat == 0) {
                holder.image.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.man))
            } else {
                holder.image.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.woman))
            }
        }

        holder.codeView.setCode(list[position].codes!!)
        holder.btnSubmit.setOnClickListener{
            onButtonSubmitClick(list[position])
        }
    }

    class PendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image=itemView.findViewById<ImageView>(R.id.img_pendingItem_image)
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_pendingItem_title)
        var txtText=itemView.findViewById<TextView>(R.id.txt_pendingItem_text)
        var txtName=itemView.findViewById<TextView>(R.id.txt_pendingItem_name)
        var txtdate=itemView.findViewById<TextView>(R.id.txt_pendingItem_date)
        var codeView=itemView.findViewById<CodeView>(R.id.codeView_pendingItem)
        var btnSubmit=itemView.findViewById<Button>(R.id.btn_pendingItem_submit)
    }

}