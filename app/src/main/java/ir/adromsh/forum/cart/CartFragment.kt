package ir.adromsh.forum.cart


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.home.BazarViewModel
import ir.adromsh.forum.util.IabHelper
import ir.adromsh.forum.utils.Utils


class CartFragment : Fragment() {

    lateinit var viewModel: CartViewModel
    lateinit var bazarViewModel: BazarViewModel
    lateinit var txtCoin:TextView
    var lastCoin=0
    var userSku=""
    val FIVE="sku_five_question"
    val TEN="sku_ten_question"
    val TWENTY="sku_twenty_question"
    val THIRTY="sku_thirty_question"

    companion object {
        lateinit var iabHelper: IabHelper

    }

    var productId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_cart, container, false)
        var recycler = view.findViewById<RecyclerView>(R.id.rv_cart_list)
        var imgBack=view.findViewById<ImageView>(R.id.img_cart_back)
        txtCoin=view.findViewById<TextView>(R.id.txt_cart_coinText)
        recycler.layoutManager = LinearLayoutManager(context)
        var loading = view.findViewById<FrameLayout>(R.id.frame_cart_loading)
        iabHelper = IabHelper(activity, activity!!.resources.getString(R.string.rsa))
        iabHelper.startSetup {
            if (it.isSuccess) {
                loading.visibility = View.GONE
            } else {
                loading.visibility = View.GONE
                recycler.visibility = View.GONE
            }
        }
        viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        bazarViewModel = ViewModelProviders.of(this).get(BazarViewModel::class.java)


        viewModel.getCoin().observe(this, Observer {
            txtCoin.text="تعداد سوالات فنی شما ${it.coin}"
            lastCoin=it.coin
        })

        viewModel.getProduct().observe(this, Observer {
            recycler.adapter = CartAdapter(it) {
                productId = it
                bazarViewModel.iabPurchase(iabHelper,activity!!, it, 1001, Utils.myToken) { orderId, sku, token ->
                    userSku=sku
                    viewModel.addToBasket(orderId, productId, token).observe(this, Observer {
                        Toast.makeText(context,"خرید با موفقیت انجام شد",Toast.LENGTH_SHORT).show()

                    })
                }

            }
        })
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1001) {
            iabHelper.handleActivityResult(requestCode, resultCode, data)
//            Log.i("LOG", "on activity if $userSku ")
            when(userSku){
                FIVE->{
                    lastCoin+=5
                    txtCoin.text="تعداد سوالات فنی شما $lastCoin"
                }
                TEN->{
                    lastCoin+=10
                    txtCoin.text="تعداد سوالات فنی شما $lastCoin"
                }
                TWENTY->{
                    lastCoin+=20
                    txtCoin.text="تعداد سوالات فنی شما $lastCoin"
                }
                THIRTY->{
                    lastCoin+=30
                    txtCoin.text="تعداد سوالات فنی شما $lastCoin"
                }
            }


        } else {
//            Log.i("LOG", "on activity else")
            super.onActivityResult(requestCode, resultCode, data)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LOG","iabHelper disposed")
        iabHelper.dispose()

    }


}
