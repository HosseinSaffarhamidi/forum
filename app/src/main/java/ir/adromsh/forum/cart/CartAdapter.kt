package ir.adromsh.forum.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Product

class CartAdapter(var list: List<Product>,var onCartItemClick:(productId:String)->Unit): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.txtTitle.text=list[position].title
        holder.txtText.text=list[position].description
        holder.txtPrice.text=list[position].price
        holder.parent.setOnClickListener{
            onCartItemClick(list[position].productId)
        }
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtTitle=itemView.findViewById<TextView>(R.id.txt_cartItem_title)
        var txtText=itemView.findViewById<TextView>(R.id.txt_cartItem_text)
        var txtPrice=itemView.findViewById<TextView>(R.id.txt_cartItem_price)
        var parent=itemView.findViewById<RelativeLayout>(R.id.rel_cartItem_parent)
    }
}