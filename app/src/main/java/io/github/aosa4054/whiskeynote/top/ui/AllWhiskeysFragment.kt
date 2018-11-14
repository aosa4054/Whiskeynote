package io.github.aosa4054.whiskeynote.top.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.data.WhiskeyRepository
import io.github.aosa4054.whiskeynote.top.BaseFragment
import io.github.aosa4054.whiskeynote.top.MainRecyclerAdapter
import io.github.aosa4054.whiskeynote.top.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragments_main.*
import kotlinx.coroutines.experimental.async


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
        val rv = main_recycler
        val manager = LinearLayoutManager(activity)
        manager.orientation = RecyclerView.VERTICAL
        rv.layoutManager = manager
        rv.adapter = MainRecyclerAdapter(viewModel.whiskeys, activity as Context, itemClick = {
            //TODO: 画面遷移
            Log.d("できらぁ", "できらぁ")
        })

        //setListeners()
    }
}
