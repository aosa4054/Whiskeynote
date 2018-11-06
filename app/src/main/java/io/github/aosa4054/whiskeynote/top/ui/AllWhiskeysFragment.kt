package io.github.aosa4054.whiskeynote.top.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*

import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.top.BaseFragment
import io.github.aosa4054.whiskeynote.top.MainViewModel


class AllWhiskeysFragment : BaseFragment() {
    companion object {
        fun newInstance() = AllWhiskeysFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragments_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
