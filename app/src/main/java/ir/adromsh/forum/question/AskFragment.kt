package ir.adromsh.forum.question


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.R
import ir.adromsh.forum.cart.CartFragment
import ir.adromsh.forum.cart.CartViewModel
import ir.adromsh.forum.models.Course
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques
import org.angmarch.views.NiceSpinner


class AskFragment : Fragment() {

    lateinit var viewModel: QuestionViewModel
    lateinit var cartViewModel:CartViewModel
    var lastCoin=0
    var courseName: MutableList<String> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_ask, container, false)
        var edtTitle = view.findViewById<EditText>(R.id.edt_ask_title)
        var edtText = view.findViewById<EditText>(R.id.edt_ask_text)
        var spinner = view.findViewById<NiceSpinner>(R.id.nice_ask_spinner)
        var btnSend = view.findViewById<Button>(R.id.btn_ask_ask)
        var radioGroup=view.findViewById<RadioGroup>(R.id.rg_ask_radioGroup)
        var btnBuy=view.findViewById<Button>(R.id.btn_ask_buy)
        var txtCoin=view.findViewById<TextView>(R.id.txt_ask_coinText)
        var txtShowRules=view.findViewById<TextView>(R.id.txt_ask_showRules)
        var txtBuy=view.findViewById<TextView>(R.id.txt_ask_rule)
        var faniRadioButton=view.findViewById<RadioButton>(R.id.radio_ask_fanni)

        faniRadioButton.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked){
                Utils.customAnimation(txtBuy,duration = 500,animation = Techniques.FadeInDown)
                Utils.customAnimation(btnBuy,duration = 500,animation = Techniques.FadeInDown)
                txtBuy.visibility=View.VISIBLE
                btnBuy.visibility=View.VISIBLE
            }else{
                Utils.customAnimation(txtBuy,duration = 500,animation = Techniques.FadeOutUp)
                Utils.customAnimation(btnBuy,duration = 500,animation = Techniques.FadeOutUp)

            }

        }

        txtShowRules.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.add(R.id.main_fragment_frame,RulesFragment(),"rulesFragment")
            transaction.addToBackStack(null)
            transaction.commit()
        }

        btnBuy.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.add(R.id.main_fragment_frame,CartFragment(),"rulesFragment")
            transaction.addToBackStack(null)
            transaction.commit()
        }

        viewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        cartViewModel.getCoin().observe(this, Observer {
            lastCoin=it.coin
            txtCoin.text="تعداد سوالات فنی شما ${it.coin}"
        })


        viewModel.getAllCourse().observe(this, Observer<List<Course>> {
            for (i in 0 until it.size) {
                courseName.add(it[i].title!!)
            }
            spinner.attachDataSource(courseName)
        })

        /* spinner.setOnSpinnerItemSelectedListener { parent, view, position, id ->
             var item=parent.getItemAtPosition(position)
         }*/

        btnSend.setOnClickListener {
            var type=0
            var selectedId=radioGroup.checkedRadioButtonId
            var radioButton=view.findViewById<RadioButton>(selectedId)
            var textType=radioButton.text

            if(!textType.contains("مشاوره")) {
                type = 1
                if(lastCoin==0){
                    Toast.makeText(context,"اعتبار کافی نیست",Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.ask(
                edtTitle.text.toString(),
                edtText.text.toString(),
                spinner.selectedItem.toString(),
                type
            ).observe(this, Observer {
                lastCoin--
                txtCoin.text="تعداد سوالات فنی شما $lastCoin"
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
            })
        }
        return view
    }


}
