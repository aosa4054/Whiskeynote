package io.github.aosa4054.whiskeynote.editWhiskey

import android.graphics.Outline
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.graphics.drawable.toDrawable
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*

class EditWhiskeyFragment : Fragment() {

    companion object {
        fun newInstance() = EditWhiskeyFragment()
    }

    private lateinit var viewModel: EditWhiskeyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_edit_whiskey, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //fab_save.outlineProvider = ExtendedFabOutlineProvider()
        //fab_save.clipToOutline = true

        viewModel = ViewModelProviders.of(this).get(EditWhiskeyViewModel::class.java)
        // TODO: Use the ViewModel
    }

    class ExtendedFabOutlineProvider : ViewOutlineProvider() {

        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, view.height / 2f)
            view.background = R.color.colorAccent.toDrawable()
        }
    }
}
