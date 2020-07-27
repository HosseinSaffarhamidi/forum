package ir.adromsh.forum.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager

import ir.adromsh.forum.R
import ir.adromsh.forum.question.QuestionViewModel
import com.google.android.material.tabs.TabLayout


class ProfileFragment : Fragment() {
    lateinit var qViewModel:QuestionViewModel
    lateinit var txtName:TextView
    lateinit var nameFrame:RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_profile, container, false)
        qViewModel=ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        txtName=view.findViewById<TextView>(R.id.txt_profile_name)
        nameFrame=view.findViewById<RelativeLayout>(R.id.rel_profile_profileNameFrame)
        var tabLayout=view.findViewById<TabLayout>(R.id.tab_profile)
        var viewPager=view.findViewById<ViewPager>(R.id.viewPager_profile)


        tabLayout.setupWithViewPager(viewPager)
        var adapter=ProfileViewPagerAdapter(childFragmentManager)
        adapter.addFragments(ContactusFragment(),"ارتباط با ما")
        adapter.addFragments(AccountFragment(),"حساب کاربری")
        viewPager.adapter=adapter

        viewPager.setCurrentItem(1)

        return view
    }

    override fun onResume() {
        if(qViewModel.getToken()==""){
            nameFrame.visibility=View.GONE
        }else{
            txtName.setText(qViewModel.getData())
        }
        super.onResume()

    }

}
