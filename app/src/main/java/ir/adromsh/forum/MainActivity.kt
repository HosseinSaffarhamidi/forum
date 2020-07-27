package ir.adromsh.forum

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import ir.adromsh.forum.cart.CartFragment
import ir.adromsh.forum.codes.CodesFragment
import ir.adromsh.forum.home.BazarViewModel
import ir.adromsh.forum.home.HomeFragment
import ir.adromsh.forum.profile.ProfileFragment
import ir.adromsh.forum.question.LoginFragment
import ir.adromsh.forum.question.QuestionFragment
import ir.adromsh.forum.question.QuestionViewModel
import ir.adromsh.forum.util.IabHelper
import ir.adromsh.forum.utils.Utils
import com.daimajia.androidanimations.library.Techniques
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: QuestionViewModel
    lateinit var bazarViewModel: BazarViewModel
    lateinit var iabHelper: IabHelper
    lateinit var bottomNavigation: MeowBottomNavigation
    var backPressedOnce = false


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment is CartFragment) {
                    fragment.onActivityResult(requestCode, resultCode, data)
                }
            }
        }

    }

    override fun onBackPressed() {
        var bakStackCount = supportFragmentManager.backStackEntryCount
        if (bakStackCount == 0) {
            if (bottomNavigation.isShowing(4)) {
                if (backPressedOnce) {
                    super.onBackPressed()
                    return
                } else {
                    backPressedOnce = true
                    Toast.makeText(
                        this,
                        "برای خروج دوباره دکمه بازگشت را بزنید",
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler().postDelayed(
                        {
                            backPressedOnce=false
                        }, 2000
                    )
                }

            } else {
                bottomNavigation.show(4)
                var transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.main_fragment_frame, HomeFragment {
                    bottom_navigation.show(it + 1)
                })
                transaction.commit()
            }

        } else {

            super.onBackPressed()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var splashScreen = findViewById<RelativeLayout>(R.id.rel_main_splash)
        iabHelper = IabHelper(this, resources.getString(R.string.rsa))
        iabHelper.startSetup {
            if (it.isSuccess) {
                bazarViewModel.iabSync(iabHelper) {
                    if (it == "success") {
                        Utils.customAnimation(
                            findViewById(R.id.rel_main_splash),
                            animation = Techniques.FadeOut
                        )
                        Toast.makeText(this, "you are my customer", Toast.LENGTH_SHORT).show()
                        Utils.changeStatusBarColor(this)
                    } else {
                        splashScreen.visibility = View.GONE
                        Toast.makeText(this, "you are not my customer", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        bazarViewModel = ViewModelProviders.of(this).get(BazarViewModel::class.java)




        viewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        viewModel.getToken()


        setupCurvedBottomNavigation()

        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_frame, HomeFragment {
            bottom_navigation.show(it + 1)
        })
        transaction.commit()
    }


    private fun setupCurvedBottomNavigation() {
        bottomNavigation = findViewById<MeowBottomNavigation>(R.id.bottom_navigation)
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_people_black_24dp))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_code_black_24dp))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_question_black_24dp))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.ic_home_black_24dp))

        bottomNavigation.show(4, true)

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                4 -> {
                    var transaction = supportFragmentManager.beginTransaction()
                    Utils.customAnimation(
                        findViewById(R.id.main_fragment_frame),
                        animation = Techniques.FadeIn
                    )
                    transaction.replace(R.id.main_fragment_frame, HomeFragment {
                        bottomNavigation.show(it + 1, true)
                    })
                    transaction.commit()
                }
                3 -> {

                    var token = viewModel.getToken()
                    if (token.equals("")) {
                        var transaction = supportFragmentManager.beginTransaction()
                        Utils.customAnimation(
                            findViewById(R.id.main_fragment_frame),
                            animation = Techniques.FadeIn
                        )
                        transaction.replace(R.id.main_fragment_frame, LoginFragment("question"))
                        transaction.commit()
                    } else {
                        var transaction = supportFragmentManager.beginTransaction()
                        Utils.customAnimation(
                            findViewById(R.id.main_fragment_frame),
                            animation = Techniques.FadeIn
                        )
                        transaction.replace(R.id.main_fragment_frame, QuestionFragment())
                        transaction.commit()
                    }


                }
                2 -> {
                    var transaction = supportFragmentManager.beginTransaction()
                    Utils.customAnimation(
                        findViewById(R.id.main_fragment_frame),
                        animation = Techniques.FadeIn
                    )
                    transaction.replace(R.id.main_fragment_frame, CodesFragment())
                    transaction.commit()
                }
                1 -> {
                    var transaction = supportFragmentManager.beginTransaction()
                    Utils.customAnimation(
                        findViewById(R.id.main_fragment_frame),
                        animation = Techniques.FadeIn
                    )
                    transaction.replace(R.id.main_fragment_frame, ProfileFragment())
                    transaction.commit()
                }

            }


        }


    }

    /* override fun onBackPressed() {
         var fragment = supportFragmentManager.findFragmentByTag("rulesFragment")
         if (fragment != null) {
             var transaction = supportFragmentManager.beginTransaction()
             Utils.customAnimation(
                 findViewById(R.id.main_fragment_frame),
                 animation = Techniques.SlideInLeft
             )
             transaction.replace(R.id.main_fragment_frame, QuestionFragment())
             transaction.commit()
         }
         super.onBackPressed()
     }*/

    override fun onDestroy() {
        super.onDestroy()
        if (iabHelper != null) {
            iabHelper.dispose()

        }
    }


}
