package io.github.aosa4054.whiskeynote.top

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.aosa4054.whiskeynote.top.ui.AllWhiskeysFragment
import io.github.aosa4054.whiskeynote.top.ui.IndividualFragment

class MainFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf<CharSequence>("全て", "スコッチ", "ジャパニーズ", "アメリカン", "アイリッシュ", "カナディアン", "それ以外")

    private lateinit var fragment: Fragment
    private var position = 0

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> AllWhiskeysFragment.newInstance()
            1 -> IndividualFragment.newInstance("スコッチ")
            2 -> IndividualFragment.newInstance("ジャパニーズ")
            3 -> IndividualFragment.newInstance("アメリカン")
            4 -> IndividualFragment.newInstance("アイリッシュ")
            5 -> IndividualFragment.newInstance("カナディアン")
            6 -> IndividualFragment.newInstance("その他")
            else -> AllWhiskeysFragment.newInstance()
        }
    }

    override fun getCount(): Int  = tabTitles.size

    override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        this.fragment = `object` as Fragment
        this.position = position
    }

    fun getFragment(): Fragment = fragment
    fun getPosition(): Int = position
}