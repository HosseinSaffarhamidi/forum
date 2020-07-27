package ir.adromsh.forum.home

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import ir.adromsh.forum.util.IabHelper
import ir.adromsh.forum.util.IabResult
import ir.adromsh.forum.util.Inventory

class BazarViewModel(application: Application) : AndroidViewModel(application) {
    var context = application

    var productList = mutableListOf<String>()

    init {
        productList.add("primium_account")
        productList.add("ten_question")
    }

    fun iabPurchase(
        iabHelper: IabHelper,
        activity: Activity,
        productId: String,
        requestId: Int,
        token: String,
        onPurchaseFlowComplete:(orderId:String,sku:String,token:String)->Unit
    ) {
        iabHelper.launchPurchaseFlow(
            activity, productId, requestId, { result, info ->
                if (result.isSuccess) {
                    if (info != null) {
                        Log.i(
                            "LOG",
                            "order Id is: ${info.orderId} and sku is : ${info.sku} and token is:${info.token}"
                        )

                        iabHelper.consumeAsync(info) { purchase, result ->
                            if (result.isSuccess) {
                                Log.i("LOG", "consume success")
                            }
                        }

                        onPurchaseFlowComplete(info.orderId,info.sku,info.token)
                    }
                } else {
                    Toast.makeText(context, "خرید انجام نشد", Toast.LENGTH_SHORT).show()
                }
            }, token
        )
    }
    fun iabSync(iabHelper: IabHelper,onInventorySync: (message: String) -> Unit) {
        iabHelper.queryInventoryAsync(
            true,
            productList
        ) { result: IabResult?, inv: Inventory? ->
            if (result!!.isSuccess) {
                onInventorySync("success")
            } else {
                onInventorySync("failed")
            }
        }

    }



}



