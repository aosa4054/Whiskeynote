package io.github.aosa4054.whiskeynote.top

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.data.Whiskey
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

        view_pager.offscreenPageLimit = viewPagerAdapter.count - 1
        view_pager.adapter = viewPagerAdapter

        tab_layout.setupWithViewPager(view_pager)

        fab.setOnClickListener {
            val intent = Intent(this, EditWhiskeyActivity::class.java)
            startActivity(intent)
        }
    }
}
