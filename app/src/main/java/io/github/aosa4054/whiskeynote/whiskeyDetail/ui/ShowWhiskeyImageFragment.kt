package io.github.aosa4054.whiskeynote.whiskeyDetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.extention.setImageByteArray
import io.github.aosa4054.whiskeynote.whiskeyDetail.ShowWhiskeyImageViewModel
import kotlinx.android.synthetic.main.fragment_show_whiskey_image.*

class ShowWhiskeyImageFragment: Fragment(), ShowWhiskeyImageViewModel.ShowImageListener {

    private var whiskeyName = ""
    private lateinit var viewModel: ShowWhiskeyImageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val args = ShowWhiskeyImageFragmentArgs.fromBundle(arguments!!)
        this.whiskeyName = args.whiskeyName
        return inflater.inflate(R.layout.fragment_show_whiskey_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowWhiskeyImageViewModel::class.java)
        viewModel.setListener(this)
        viewModel.activateImage(whiskeyName)
    }

    override fun setImages(blob: ByteArray) {
        image.setImageByteArray(blob)
    }
}