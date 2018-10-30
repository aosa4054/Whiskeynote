package io.github.aosa4054.whiskeynote.editWhiskey

import android.content.Context
import android.icu.util.ValueIterator
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*


class EditWhiskeyFragment : Fragment() {

    interface EditWhiskeyFragmentListener{
        fun getImage()
    }

    lateinit var binding: FragmentEditWhiskeyBinding
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
        change_image.setOnClickListener { listener?.getImage() } //例外処理
        editing_image.setOnClickListener { listener?.getImage() }
        fab_save.setOnClickListener {
            saveWhiskey()
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                if (text_kind.visibility != View.VISIBLE) text_kind.visibility = View.VISIBLE
                whiskey_types_chip_groups.children.forEach { if (it.visibility == View.VISIBLE) it.visibility = View.GONE } //viewとかで置き換え不可ですか
                when (position){
                    0 -> scotch_chip_group.visibility = View.VISIBLE
                    1 -> japanese_chip_group.visibility = View.VISIBLE
                    2 -> american_chip_group.visibility = View.VISIBLE
                    3 -> irish_chip_group.visibility = View.VISIBLE
                    4 -> canadian_chip_group.visibility = View.VISIBLE
                    else -> text_kind.visibility = View.INVISIBLE
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
                whiskey_types_chip_groups.children.forEach { if (it.visibility == View.VISIBLE) { it.visibility = View.GONE}  }
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
