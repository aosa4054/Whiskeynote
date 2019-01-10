package io.github.aosa4054.whiskeynote.top.ui

import io.github.aosa4054.whiskeynote.data.Whiskey

class IndividualFragment(val type: String) : BaseFragment() {
    companion object {
        fun newInstance(type: String): IndividualFragment {
            return IndividualFragment(type)
        }
    }

    override fun doFilter(whiskeyList: List<Whiskey>)
            = whiskeyList.filter { it.type == this.type }
}