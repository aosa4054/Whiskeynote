package io.github.aosa4054.whiskeynote.top

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf<CharSequence>("hoge", "huga", "hoge", "huga")

    private lateinit var fragment: Fragment
    private var position = 0

    override fun getItem(position: Int): Fragment {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        when (position){
            0 -> return AllWhiskeysFragment.newInstance()
            1 -> return AllWhiskeysFragment.newInstance()
            else -> return AllWhiskeysFragment.newInstance()
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