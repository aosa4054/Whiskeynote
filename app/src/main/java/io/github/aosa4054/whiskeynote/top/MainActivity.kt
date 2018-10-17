package io.github.aosa4054.whiskeynote.top

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.editWhiskey.EditWhiskeyActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewPagerAdapter: MainFragmentPagerAdapter by lazy {
        MainFragmentPagerAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        view_pager.offscreenPageLimit = 3
        view_pager.adapter = viewPagerAdapter

        tab_layout.setupWithViewPager(view_pager)

        fab.setOnClickListener { view ->
            val intent = Intent(this, EditWhiskeyActivity::class.java)
            startActivity(intent)
        }
    }


}
