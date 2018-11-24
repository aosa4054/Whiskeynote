package io.github.aosa4054.whiskeynote.top.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentsMainBinding
import io.github.aosa4054.whiskeynote.top.BaseFragment
import io.github.aosa4054.whiskeynote.top.MainRecyclerAdapter
import io.github.aosa4054.whiskeynote.top.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragments_main.*


class AllWhiskeysFragment : BaseFragment() {
    companion object {
        fun newInstance() = AllWhiskeysFragment()
    }

    private lateinit var binding: FragmentsMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragments_main, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        //setListeners()
    }

    private fun setUpRecyclerView(){
        val rv = main_recycler
        val manager = LinearLayoutManager(activity)
        manager.orientation = RecyclerView.VERTICAL
        rv.layoutManager = manager
        rv.adapter = MainRecyclerAdapter(viewModel.whiskeys, activity as Context,
                itemClick = {
                    //TODO: 画面遷移
                },
                itemLongClick =  {
                    showDeletingDialog(it)
                    true
                }
        )
    }

    private fun showDeletingDialog(name: String) {
        AlertDialog.Builder(activity as Context)

                .setTitle("ウイスキーの削除？")
                .setMessage("${name}を削除しますか？")
                .setPositiveButton("戻る", null)
                .setNegativeButton("削除"){ _, _ ->
                    viewModel.deleteWhiskey(name)
                    //TODO: reload list
                }
                .show()
    }
}
