package ir.adromsh.forum.profile


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import ir.adromsh.forum.R


class ContactusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_contactus, container, false)
        var btnContactus=view.findViewById<Button>(R.id.btn_contactus)
        btnContactus.setOnClickListener{
            var intent= Intent(Intent.ACTION_VIEW, Uri.parse("http://hosseinsaffarhamidi.cvbuilder.me/fa/"))
            startActivity(intent)
        }
        return view
    }




}
