package io.github.aosa4054.whiskeynote.top.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentsMainBinding
import io.github.aosa4054.whiskeynote.top.viewModel.MainViewModel
import io.github.aosa4054.whiskeynote.whiskeyDetail.WhiskeyDetailActivity
import kotlinx.android.synthetic.main.fragments_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.top.MainRecyclerAdapter

abstract class BaseFragment: Fragment() {

    private lateinit var binding: FragmentsMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var progressDialog: ProgressDialog

    private var backdropShown = false

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
                    val intent = Intent(activity, WhiskeyDetailActivity::class.java)
                    intent.putExtra("name", it)
                    startActivity(intent)
                },
                itemLongClick = {
                    showDeletingDialog(it)
                    true
                }
        )

        main_recycler.adapter = adapter
        main_recycler.layoutManager = LinearLayoutManager(activity)

        viewModel.whiskeys.observe(this, Observer { whiskeys ->
            GlobalScope.launch(Dispatchers.Main) {
                whiskeys?.let {
                    val filteredList = doFilter(it)
                    adapter.setWhiskeys(filteredList.reversed())
                    viewModel.countWhiskey(filteredList.size.toString())
                }
                delay(500)  //500でええんかな
                progressDialog.dismiss()
            }
        } )

        product_grid.setOnClickListener {
            if (backdropShown){
                transformBackdrop()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_filter -> {
                Toast.makeText(activity as Context, "未実装のボタン", Toast.LENGTH_SHORT).show()
                //TODO: transformBackdrop()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun transformBackdrop(){

        val animatorSet = AnimatorSet()
        val height: Int

        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels

        backdropShown = !backdropShown

        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY = height - 450

        val animator = ObjectAnimator.ofFloat(product_grid, "translationY", (if (backdropShown) translateY else 0).toFloat())
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(animator)

        animator.start()
    }

    private fun showDeletingDialog(name: String) {
        AlertDialog.Builder(activity as Context)
                .setTitle("ウイスキーの削除？")
                .setMessage("${name}を削除しますか？")
                .setPositiveButton("戻る", null)
                .setNegativeButton("削除"){ _, _ -> viewModel.deleteWhiskey(name) }
                .show()
    }

    abstract fun doFilter(whiskeyList: List<Whiskey>): List<Whiskey>
}