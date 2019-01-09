package io.github.aosa4054.whiskeynote.whiskeyDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.whiskeyDetail.ui.WhiskeyDetailFragment


class whiskeyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whiskey_detail)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, WhiskeyDetailFragment.newInstance())
                    .commitNow()
        }
    }

}
