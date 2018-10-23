package io.github.aosa4054.whiskeynote.editWhiskey

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val view = inflater.inflate(R.layout.fragment_edit_whiskey, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EditWhiskeyViewModel::class.java)
        viewModel.setNavigator(activity as EditWhiskeyActivity)
        listener = activity as EditWhiskeyActivity
        // TODO: Use the ViewModel
        change_image.setOnClickListener { listener?.getImage() } //例外処理
        editing_image.setOnClickListener { listener?.getImage() }
    }
}
