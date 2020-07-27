package ir.adromsh.forum.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Rules

class RulesAdapter(var list:List<Rules>) : RecyclerView.Adapter<RulesAdapter.RulesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RulesViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.rules_item,parent,false)
        return RulesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RulesViewHolder, position: Int) {
        holder.txtTitle.setText(list[position].rule)

    }

    class RulesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle = itemView.findViewById<TextView>(R.id.txt_rulesItem_title)
    }
}