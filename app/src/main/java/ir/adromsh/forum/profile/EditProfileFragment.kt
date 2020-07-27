package ir.adromsh.forum.profile


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.R
import ir.adromsh.forum.models.User
import ir.adromsh.forum.utils.FileUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class EditProfileFragment : Fragment() {

    lateinit var viewModel: ProfileViewModel
    lateinit var image: ImageView
    var userId = ""
    val REQUEST_CODE=1001
    lateinit var myUser: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        image = view.findViewById<ImageView>(R.id.img_editProfile_image)
        var uploadImage = view.findViewById<ImageView>(R.id.img_editProfile_upload)
        var txtPoint = view.findViewById<TextView>(R.id.txt_editProfile_point)
        var txtCoin = view.findViewById<TextView>(R.id.txt_editProfile_coin)
        var txtEmail = view.findViewById<TextView>(R.id.txt_editProfile_email)
        var edtName = view.findViewById<EditText>(R.id.edt_editProfile_name)
        var edtFamily = view.findViewById<EditText>(R.id.edt_editProfile_family)
        var edtPhone = view.findViewById<EditText>(R.id.edt_editProfile_phone)
        var edtAge = view.findViewById<EditText>(R.id.edt_editProfile_age)
        var radioGroup = view.findViewById<RadioGroup>(R.id.rg_editProfile_radioGroup)
        var radioMan = view.findViewById<RadioButton>(R.id.radio_editProfile_man)
        var radioWoman = view.findViewById<RadioButton>(R.id.radio_editProfile_woman)
        var btnEdit = view.findViewById<Button>(R.id.btn_editProfile_edit)

        uploadImage.setOnClickListener {
            if(Build.VERSION.SDK_INT>=23){
                Dexter.withActivity(activity)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(object :PermissionListener{
                        override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                            chooseImageAndUpload()
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                        ) {
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            Toast.makeText(context,"مجوز لازم برای آپلود تصویر داده نشد",Toast.LENGTH_SHORT).show()
                        }

                    }).check()
            }else{
                chooseImageAndUpload()
            }

        }





        viewModel.getProfileData().observe(this, Observer {
            myUser = it
            userId = it.id
            txtPoint.text = " امتیاز شما : ${it.point}"
            txtCoin.text = " تعداد سوالات فنی : ${it.coin}"
            edtName.setText(it.name)
            edtFamily.setText(it.family)
            txtEmail.text = it.email
            edtPhone.setText(it.phone)
            if (it.age != 0) {
                edtAge.setText(it.age.toString())
            }

            if (it.jensiat == 0) {
                radioMan.isChecked = true
            } else {
                radioWoman.isChecked = true
            }
            if (it.image != null) {
                Picasso.get().load(it.image).into(image)
            } else {
                if (it.jensiat == 0) {
                    image.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.man))
                } else {
                    image.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.woman))
                }
            }

            btnEdit.setOnClickListener {

                var jensiat = 0
                if (edtName.text.toString().length > 0
                    && edtFamily.text.toString().length > 0
                    && edtPhone.text.toString().length > 0
                    && edtAge.text.toString().length > 0
                ) {
                    var radioButtonId = radioGroup.checkedRadioButtonId
                    var checkedRadioButton = radioGroup.findViewById<RadioButton>(radioButtonId)
                    var radioButtonText = checkedRadioButton.text

                    if (radioButtonText == "زن") {
                        jensiat = 1
                    }
                    viewModel.updateProfile(
                        edtName.text.toString(),
                        edtFamily.text.toString(),
                        edtPhone.text.toString(), edtAge.text.toString().toInt(), jensiat
                    ).observe(this, Observer {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        if (it.message == "ویرایش اطلاعات با موفقیت انجام شد") {
                            viewModel.updateToken(myUser.name + " " + myUser.family)
                        }
                    })
                } else {
                    Toast.makeText(
                        context,
                        "نام و نام خانوادگی و شماره همراه و سن نمی تواند خالی باشد",
                        Toast.LENGTH_LONG
                    ).show()
                }


            }

        })
        return view
    }


    fun chooseImageAndUpload() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var file = File(FileUtil.getPath(data!!.data, context))

            var idRequestBody = RequestBody.create(MultipartBody.FORM, userId)
            var requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
            var filePart = MultipartBody.Part.createFormData("upload_file", file.name, requestBody)
            viewModel.uploadImage(idRequestBody, filePart).observe(this, Observer {
                if (it.message == "عکس با موفقیت آپلود شد") {
                    image.setImageURI(data.data)
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    Log.i("LOG", it.data)
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }

            })

        }
    }

}
