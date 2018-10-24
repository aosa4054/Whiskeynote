package io.github.aosa4054.whiskeynote.editWhiskey

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*

class EditWhiskeyFragment : Fragment() {

    interface EditWhiskeyFragmentListener{
        fun getImage()
    }

    private lateinit var viewModel: EditWhiskeyViewModel
    private var listener: EditWhiskeyFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = DataBindingUtil.inflate<io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding>(inflater, R.layout.fragment_edit_whiskey, container, false)
        //TODO: bindしなきゃね

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EditWhiskeyViewModel::class.java)
        viewModel.setNavigator(activity as EditWhiskeyActivity)
        listener = activity as EditWhiskeyActivity
        // TODO: Use the ViewModel
        setListeners()
    }

    private fun setListeners(){
        change_image.setOnClickListener { listener?.getImage() } //例外処理
        editing_image.setOnClickListener { listener?.getImage() }
        spinner.setOnItemClickListener { _, _, position, _ ->
            when (position){/*TODO
                0 -> //Scotch
                1 -> //Japanese
                2 -> //American
                3 -> //Irish
                4 -> //Canadian
                5 -> //Others*/
            }
        }
        back.setOnTouchListener { _, _ ->
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(back.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            back.requestFocus()
        }
    }
}
