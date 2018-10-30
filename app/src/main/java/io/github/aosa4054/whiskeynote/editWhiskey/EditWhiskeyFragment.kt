package io.github.aosa4054.whiskeynote.editWhiskey

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*

class EditWhiskeyFragment : Fragment() {

    interface EditWhiskeyFragmentListener{
        fun getImage()
    }

    private lateinit var binding: FragmentEditWhiskeyBinding
    private lateinit var viewModel: EditWhiskeyViewModel
    private var listener: EditWhiskeyFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_whiskey, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EditWhiskeyViewModel::class.java)
        binding.viewModel = viewModel
        binding = viewModel.bind(binding)

        viewModel.setNavigator(activity as EditWhiskeyActivity)
        listener = activity as EditWhiskeyActivity
        // TODO: Use the ViewModel
        setListeners()
    }

    private fun setListeners(){
        val inAnimation = AnimationUtils.loadAnimation(activity, R.anim.in_animation)
        val outAnimation = AnimationUtils.loadAnimation(activity, R.anim.out_animation)

        change_image.setOnClickListener { listener?.getImage() } //異常系の処理
        editing_image.setOnClickListener { listener?.getImage() }
        fab_save.setOnClickListener {
            saveWhiskey()
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            fun View.appear(){
                this.startAnimation(inAnimation)
                this.visibility = View.VISIBLE
            }
            fun View.disappear(visibility: Int){
                this.startAnimation(outAnimation)
                this.visibility = visibility
            }

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                if (text_kind.visibility != View.VISIBLE) text_kind.appear()
                whiskey_types_chip_groups.children.forEach { if (it.visibility == View.VISIBLE) it.disappear(View.GONE) }
                when (position){
                    0 -> scotch_chip_group.appear()
                    1 -> japanese_chip_group.appear()
                    2 -> american_chip_group.appear()
                    3 -> irish_chip_group.appear()
                    4 -> canadian_chip_group.appear()
                    else -> {
                        text_kind.disappear(View.INVISIBLE)
                        scotch_chip_group.visibility = View.INVISIBLE
                    }
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
                text_kind.disappear(View.INVISIBLE)
                whiskey_types_chip_groups.children.forEach { if (it.visibility == View.VISIBLE)  it.disappear(View.GONE)  }
            }
        }

        back.setOnTouchListener { _, _ ->
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(back.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            back.requestFocus()
        }
    }

    private fun saveWhiskey(){
        val kind = viewModel.getTypes(spinner.selectedItemPosition)
        Log.d("チップ", kind)
    }
}
