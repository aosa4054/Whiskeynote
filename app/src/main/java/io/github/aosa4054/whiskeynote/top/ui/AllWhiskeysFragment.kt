package io.github.aosa4054.whiskeynote.top.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentsMainBinding
import io.github.aosa4054.whiskeynote.top.BaseFragment
import io.github.aosa4054.whiskeynote.top.MainRecyclerAdapter
import io.github.aosa4054.whiskeynote.top.viewModel.MainViewModel
import io.github.aosa4054.whiskeynote.whiskeyDetail.whiskeyDetailActivity
import kotlinx.android.synthetic.main.fragments_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AllWhiskeysFragment : BaseFragment() {
    companion object {
        fun newInstance() = AllWhiskeysFragment()
    }

    private lateinit var binding: FragmentsMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var progressDialog: ProgressDialog

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

        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("読み込み中...")
        progressDialog.show()


        val adapter = MainRecyclerAdapter(activity as Context,
                itemClick = {
                    val intent = Intent(activity, whiskeyDetailActivity::class.java)
                    intent.putExtra("name", it)
                    startActivity(intent)
                },
                itemLongClick =  {
                    showDeletingDialog(it)
                    true
                }
        )

        main_recycler.adapter = adapter
        main_recycler.layoutManager = LinearLayoutManager(activity)

        viewModel.whiskeys.observe(this, Observer { whiskeys ->
            GlobalScope.launch(Dispatchers.Main) {
                whiskeys?.let {
                    adapter.setWhiskeys(it.reversed())
                    viewModel.countWhiskey(it.size.toString())
                }
                delay(500)  //500でええんかな
                progressDialog.dismiss()
            }
        } )
    }

    private fun showDeletingDialog(name: String) {
        AlertDialog.Builder(activity as Context)
                .setTitle("ウイスキーの削除？")
                .setMessage("${name}を削除しますか？")
                .setPositiveButton("戻る", null)
                .setNegativeButton("削除"){ _, _ -> viewModel.deleteWhiskey(name) }
                .show()
    }
}
