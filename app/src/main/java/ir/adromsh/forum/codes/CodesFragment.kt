package ir.adromsh.forum.codes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.adromsh.forum.R
import ir.adromsh.forum.models.Codes
import ir.adromsh.forum.question.LoginFragment
import ir.adromsh.forum.question.QuestionViewModel
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CodesFragment : Fragment() {

    lateinit var viewModel: CodeViewModel
    lateinit var qViewModel: QuestionViewModel
    lateinit var codeAdapter: CodeAdapter
    lateinit var recycler:RecyclerView
    lateinit var loadingFragme:FrameLayout
    val SORT_NEWEST = 1
    val SORT_POPULAR = 0
    var lastPosition = 0
    var last_sort = SORT_POPULAR
    var myList: List<Codes> = listOf<Codes>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_codes, container, false)
        viewModel = ViewModelProviders.of(this).get(CodeViewModel::class.java)
        qViewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        recycler = view.findViewById<RecyclerView>(R.id.rv_code_codeList)
        recycler.layoutManager = LinearLayoutManager(context)
        var txtPopular = view.findViewById<TextView>(R.id.txt_code_popular)
        var txtNewest = view.findViewById<TextView>(R.id.txt_code_newest)
        var edtSearch=view.findViewById<SearchView>(R.id.edt_code_search)
        loadingFragme = view.findViewById<FrameLayout>(R.id.frame_codes_loadingCodes)
        var fab = view.findViewById<FloatingActionButton>(R.id.fab_code_newCode)

        loadCodes()


        txtNewest.setOnClickListener {
            if (last_sort != SORT_NEWEST) {
                loadingFragme.visibility = View.VISIBLE
                txtPopular.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                txtPopular.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
                txtNewest.setBackgroundColor(ContextCompat.getColor(context!!, R.color.blueGreen))
                txtNewest.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                viewModel.getAllCodes(SORT_NEWEST).observe(this, Observer {
                    myList = it
                    codeAdapter.setSortedList(it)
                    loadingFragme.visibility = View.GONE
                })
                last_sort = SORT_NEWEST
            }

        }

        edtSearch.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.codeSearch(context!!,query!!).observe(this@CodesFragment, Observer {
                    codeAdapter.searchedList(it)
                })

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isEmpty()){
                    loadCodes()
                }
                return true
            }


        })
        fab.setOnClickListener {
            if (qViewModel.getToken() == "") {
                var transaction = activity!!.supportFragmentManager.beginTransaction()
                Utils.customAnimation(
                    activity!!.findViewById(R.id.main_fragment_frame),
                    animation = Techniques.SlideInRight
                )
                transaction.add(R.id.main_fragment_frame, LoginFragment("code"))
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                var transaction = activity!!.supportFragmentManager.beginTransaction()
                Utils.customAnimation(
                    activity!!.findViewById(R.id.main_fragment_frame),
                    animation = Techniques.SlideInRight
                )
                transaction.add(R.id.main_fragment_frame, NewCodeFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }

        }

        txtPopular.setOnClickListener {
            if (last_sort != SORT_POPULAR) {
                loadingFragme.visibility = View.VISIBLE
                txtPopular.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                txtPopular.setBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.blueGreen
                    )
                )
                txtNewest.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
                txtNewest.setTextColor(ContextCompat.getColor(context!!, R.color.black))
                viewModel.getAllCodes(SORT_POPULAR).observe(this, Observer {
                    myList = it
                    codeAdapter.setSortedList(it)
                    loadingFragme.visibility = View.GONE
                })
                last_sort = SORT_POPULAR
            }


        }

        return view
    }

    fun loadCodes(){
        viewModel.getAllCodes(SORT_POPULAR).observe(this, Observer {
            myList = it
            Utils.customAnimation(recycler, animation = Techniques.Landing)
            codeAdapter = CodeAdapter(context!!, it) { codeId, position ->
                var transaction = activity!!.supportFragmentManager.beginTransaction()
                lastPosition = position
                var detailCodeFragment = DetailCodeFragment {
                    codeAdapter.changePoint(it)
                }
                var selectedCode: Codes? = null
                for (i in 0 until myList.size) {
                    var id = myList[i].codeId
                    if (id == codeId) {
                        selectedCode = myList[i]
                    }
                }
                var bundle = Bundle()
                bundle.putParcelable("codes", selectedCode)
                bundle.putInt("position", lastPosition)
                detailCodeFragment.arguments = bundle
                Utils.customAnimation(
                    activity!!.findViewById(R.id.main_fragment_frame),
                    animation = Techniques.SlideInRight
                )
                transaction.add(R.id.main_fragment_frame, detailCodeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            recycler.adapter = codeAdapter
            loadingFragme.visibility = View.GONE
        })
    }


}
