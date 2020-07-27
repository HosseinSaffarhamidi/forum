package ir.adromsh.forum.api

import ir.adromsh.forum.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("user.php")
    fun getBestUser(): Call<List<User>>

    @GET("course.php")
    fun getCourses(): Call<List<Course>>

    @GET("codes.php")
    fun getCodes(): Call<List<Codes>>

    @FormUrlEncoded
    @POST("signup.php")
    fun signup(
        @Field("name") name: String,
        @Field("family") family: String,
        @Field("email") email: String,
        @Field("pass") pass: String,
        @Field("phone") phone: String,
        @Field("jensiat") jensiat: Int
    ): Call<Message>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("pass") pass: String
    ): Call<Message>

    @GET("rules.php")
    fun getRules(): Call<List<Rules>>

    @GET("allcourse.php")
    fun getAllCourse(): Call<List<Course>>

    @FormUrlEncoded
    @POST("ask.php")
    fun ask(
        @Field("title") title: String,
        @Field("text") text: String,
        @Field("course") course: String,
        @Field("type") type: Int
    ): Call<Message>

    @GET("answer.php")
    fun getAnswer(@Query("page") page: Int): Call<List<Answer>>

    @GET("allcodes.php")
    fun getAllCodes(@Query("sort") sort: Int): Call<List<Codes>>


    @GET("point.php")
    fun setPoint(
        @Query("code_id") code: Int,
        @Query("point") point: Int
    ): Call<Message>

    @FormUrlEncoded
    @POST("newcode.php")
    fun sendNewCode(
        @Field("title") title: String,
        @Field("text") text: String,
        @Field("code") code: String
    ): Call<Message>

    @GET("returned.php")
    fun returnedAnswer(@Query("id") id: String): Call<Message>

    @GET("role.php")
    fun getRole(): Call<Message>

    @FormUrlEncoded
    @POST("sendanswer.php")
    fun sendAnswer(
        @Field("id") id: String,
        @Field("to_user") toUser: String,
        @Field("course") course: String,
        @Field("title") title: String,
        @Field("text") text: String
    ): Call<Message>

    @GET("pendingcode.php")
    fun getPendingCode(): Call<List<Codes>>

    @FormUrlEncoded
    @POST("updatecode.php")
    fun updateCode(@Field("user_id") userId: String, @Field("code_id") codeId: String): Call<Message>

    @GET("cart.php")
    fun getProduct(): Call<List<Product>>

    @FormUrlEncoded
    @POST("basket.php")
    fun addToBasket(
        @Field("order_id") productId: String,
        @Field("product_id") orderId: String,
        @Field("token") token: String
    ): Call<Message>

    @GET("getcoin.php")
    fun getCoin(): Call<Coin>

    @GET("profile.php")
    fun getProfileData(): Call<User>

    @FormUrlEncoded
    @POST("updateprofile.php")
    fun updateProfile(
        @Field("name") name: String,
        @Field("family") family: String,
        @Field("phone") phone: String,
        @Field("age") age: Int,
        @Field("jensiat") jensiat: Int
    ): Call<Message>

    @Multipart
    @POST("upload.php")
    fun uploadImage(
        @Part("id") id: RequestBody,
        @Part filePart: MultipartBody.Part
    ): Call<Message>

    @FormUrlEncoded
    @POST("education.php")
    fun education(@Field("phone")phone:String):Call<Message>

    @GET("search.php")
    fun search(@Query("search")search:String):Call<List<Codes>>
}