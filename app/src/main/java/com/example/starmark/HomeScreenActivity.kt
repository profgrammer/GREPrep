package com.example.starmark

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when(item.itemId){
            R.id.bottom_allWords -> {
                println("all words pressed")
                replaceFragment(AllWordsFragment(), intent.getStringExtra("userId"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_myWords -> {
                println("my words pressed")
                replaceFragment(MyWordsFragment(), intent.getStringExtra("userId"))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        println("in home screen" + intent.getStringExtra("userId"))
        replaceFragment(AllWordsFragment(), intent.getStringExtra("userId"))

//        code begins here..


        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        auth = FirebaseAuth.getInstance()



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menulist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) : Boolean = when(item?.itemId){
        R.id.logout -> {
            auth.signOut()
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        this.finishAffinity()
        finishAndRemoveTask()
    }


    private fun replaceFragment(fragment: Fragment, stringExtra: String){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("userId", stringExtra)
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.rl_fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
