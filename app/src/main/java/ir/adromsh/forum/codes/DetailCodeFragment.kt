package ir.adromsh.forum.codes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Codes
import com.squareup.picasso.Picasso
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.highlight.ColorTheme


class DetailCodeFragment(var onDetailFragmentClose:(code:Codes)->Unit) : Fragment() {

    lateinit var viewModel:CodeViewModel
    val DISLIKE_POINT=0
    val LIKE_POINT=1
    var lastPoint=0
    companion object{
        var updatedPosition=0
    }
    lateinit var detailCode:Codes
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_detail_code, container, false)
        viewModel=ViewModelProviders.of(this).get(CodeViewModel::class.java)
        var bundle = arguments
        detailCode = bundle!!.getParcelable<Codes>("codes")!!
        updatedPosition=bundle.getInt("position")
        var image = view.findViewById<ImageView>(R.id.img_detailCode_image)
        var txtTitle = view.findViewById<TextView>(R.id.txt_detailCode_title)
        var txtName = view.findViewById<TextView>(R.id.txt_detailCode_name)
        var txtText = view.findViewById<TextView>(R.id.txt_detailCode_text)
        var txtPoint = view.findViewById<TextView>(R.id.txt_detailCode_point)
        var txtDate = view.findViewById<TextView>(R.id.txt_detailCode_date)
        var codeView = view.findViewById<CodeView>(R.id.codeView_detailCode)
        var imgPlus = view.findViewById<ImageView>(R.id.img_codeItem_plus)
        var imgMinus = view.findViewById<ImageView>(R.id.img_detailCode_minus)
        var waitingProgress=view.findViewById<ProgressBar>(R.id.progress_detailCode_waiting)

       

        imgPlus.setOnClickListener{
            waitingProgress.visibility=View.VISIBLE
            txtPoint.visibility=View.GONE
            viewModel.setPoint(detailCode.id!!.toInt(),LIKE_POINT).observe(this, Observer {
                if(it.message!="شما قبلا برای این کد نظر داده اید"){
                    var point=txtPoint.text.toString().toInt()
                    point++
                    txtPoint.visibility=View.VISIBLE
                    waitingProgress.visibility=View.GONE
                    txtPoint.setText(point.toString())
                    lastPoint=txtPoint.text.toString().toInt()
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }else{
                    txtPoint.visibility=View.VISIBLE
                    waitingProgress.visibility=View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }

            })
        }
        imgMinus.setOnClickListener{
            waitingProgress.visibility=View.VISIBLE
            txtPoint.visibility=View.GONE
            viewModel.setPoint(detailCode.id!!.toInt(),DISLIKE_POINT).observe(this, Observer {
                if(it.message!="شما قبلا برای این کد نظر داده اید"){
                    var point=txtPoint.text.toString().toInt()
                    txtPoint.visibility=View.VISIBLE
                    waitingProgress.visibility=View.GONE
                    point--
                    txtPoint.setText(point.toString())
                    lastPoint=txtPoint.text.toString().toInt()
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }else{
                    txtPoint.visibility=View.VISIBLE
                    waitingProgress.visibility=View.GONE
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            })
        }

        if (detailCode.image != null) {
            Picasso.get().load(detailCode.image).into(image)
        } else {
            if (detailCode.jensiat == 0) {
                image.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.man))
            } else {
                image.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.woman))
            }
        }

        txtTitle.setText(detailCode.title)
        txtName.text=(detailCode.name+" "+detailCode.family)
        txtText.setText(detailCode.text)
        txtPoint.setText(detailCode.point.toString())
        lastPoint=detailCode.point
        txtDate.setText("  تاریخ : ${detailCode.date}")
        codeView.setCode(detailCode.codes!!)
        codeView.getOptions()!!.setTheme(ColorTheme.MONOKAI)
        return view
    }

    override fun onStop() {
        detailCode.point=lastPoint
        onDetailFragmentClose(detailCode)
        super.onStop()
    }





}
