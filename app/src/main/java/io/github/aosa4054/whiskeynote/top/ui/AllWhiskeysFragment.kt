package io.github.aosa4054.whiskeynote.top.ui

import io.github.aosa4054.whiskeynote.data.Whiskey

class AllWhiskeysFragment : BaseFragment() {
    companion object {
        fun newInstance() = AllWhiskeysFragment()
    }

    override fun doFilter(whiskeyList: List<Whiskey>) = whiskeyList
}
