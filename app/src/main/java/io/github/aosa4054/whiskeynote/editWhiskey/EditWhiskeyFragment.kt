package io.github.aosa4054.whiskeynote.editWhiskey

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
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
        binding.scotch = EditWhiskeyViewModel.ScotchChipController()

        viewModel.setNavigator(activity as EditWhiskeyActivity)
        listener = activity as EditWhiskeyActivity
        // TODO: Use the ViewModel
        setListeners()
    }

    private fun setListeners(){
        change_image.setOnClickListener { listener?.getImage() } //例外処理
        editing_image.setOnClickListener { listener?.getImage() }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position){
                    0 -> scotch_chip_group.visibility = View.VISIBLE
                    5 -> {
                        scotch_chip_group.visibility = View.GONE
                    }
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        back.setOnTouchListener { _, _ ->
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(back.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            back.requestFocus()
        }
    }
}
