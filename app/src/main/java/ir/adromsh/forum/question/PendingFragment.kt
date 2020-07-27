package ir.adromsh.forum.question


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import ir.adromsh.forum.R
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques


class PendingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_pending, container, false)
        var cardCodes=view.findViewById<CardView>(R.id.card_pending_codes)
        cardCodes.setOnClickListener{
            var transaction=activity!!.supportFragmentManager.beginTransaction()
            Utils.customAnimation(activity!!.findViewById(R.id.main_fragment_frame),animation = Techniques.SlideInRight)
            transaction.add(R.id.main_fragment_frame, DetailCodePendingFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        var cardSearch=view.findViewById<CardView>(R.id.card_pending_searchQuestionByName)
        return view
    }


}
