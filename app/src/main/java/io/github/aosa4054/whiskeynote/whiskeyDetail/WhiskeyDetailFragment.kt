package io.github.aosa4054.whiskeynote.whiskeyDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentWhiskeyDetailBinding
import io.github.aosa4054.whiskeynote.generated.callback.OnClickListener
import kotlinx.android.synthetic.main.activity_edit_whiskey.*
import kotlinx.android.synthetic.main.fragment_whiskey_detail.*
import kotlin.math.sqrt


class WhiskeyDetailFragment : Fragment() {

    private lateinit var binding: FragmentWhiskeyDetailBinding

    companion object {
        fun newInstance() = WhiskeyDetailFragment()
    }

    private lateinit var viewModel: WhiskeyDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_whiskey_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar_whiskey_detail.setNavigationOnClickListener {
            activity!!.finish()
        }

        app_bar_whiskey_detail.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener(fun(appBarLayout: AppBarLayout, verticalOffset: Int){
                    val per = Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
                    toolbar_whiskey_detail.alpha = sqrt(per)
                })
        )

        val intent = activity!!.intent
        val whiskeyName = intent.getStringExtra("name")
        viewModel = ViewModelProviders.of(this).get(WhiskeyDetailViewModel::class.java)
        viewModel.setUpWhiskey(whiskeyName)
        binding.viewModel = viewModel

    }
}
