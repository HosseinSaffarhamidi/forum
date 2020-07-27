package ir.adromsh.forum.cart

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import ir.adromsh.forum.api.ApiClient
import ir.adromsh.forum.api.ApiService
import ir.adromsh.forum.models.Coin
import ir.adromsh.forum.models.Message
import ir.adromsh.forum.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartApiService(var context: Context) {
    private var apiService = ApiClient.getClient().create(ApiService::class.java)

    fun getProduct(): MutableLiveData<List<Product>> {
        var mutableLiveData = MutableLiveData<List<Product>>()
        apiService.getProduct().enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(context, "مشکل در دریافت محصولات", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }

    fun addToBasket(ordreId: String, productId: String, token: String): MutableLiveData<Message> {
        var mutableLiveData = MutableLiveData<Message>()
        apiService.addToBasket(ordreId, productId, token).enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>, t: Throwable) {
                Toast.makeText(context, "مشکل در افزودن اطلاعات", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }

    fun getCoin(): MutableLiveData<Coin> {
        var mutableLiveData = MutableLiveData<Coin>()
        apiService.getCoin().enqueue(object : Callback<Coin> {
            override fun onFailure(call: Call<Coin>, t: Throwable) {
                Toast.makeText(context, "مشکل در افزودن اطلاعات", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Coin>, response: Response<Coin>) {
                mutableLiveData.value = response.body()
            }

        })

        return mutableLiveData
    }
}