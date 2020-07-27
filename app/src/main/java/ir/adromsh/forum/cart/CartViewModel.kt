package ir.adromsh.forum.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.models.Coin
import ir.adromsh.forum.models.Message
import ir.adromsh.forum.models.Product

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private var cartApiService = CartApiService(application)

    fun getProduct(): MutableLiveData<List<Product>> {
        return cartApiService.getProduct()
    }
    fun addToBasket(orderId:String,productId:String,token:String):MutableLiveData<Message>{
        return cartApiService.addToBasket(productId,productId, token)
    }
    fun getCoin():MutableLiveData<Coin>{
        return  cartApiService.getCoin()
    }
}