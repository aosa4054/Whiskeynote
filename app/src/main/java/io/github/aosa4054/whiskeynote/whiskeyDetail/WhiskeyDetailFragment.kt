package io.github.aosa4054.whiskeynote.whiskeyDetail

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentWhiskeyDetailBinding
import kotlinx.android.synthetic.main.fragment_whiskey_detail.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.aosa4054.whiskeynote.extention.setImageByteArray
import io.github.aosa4054.whiskeynote.extention.setRoundImageByBlob
import io.github.aosa4054.whiskeynote.extention.square
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class WhiskeyDetailFragment : Fragment(), WhiskeyDetailViewModel.WhiskeyDetailListener {

    private lateinit var binding: FragmentWhiskeyDetailBinding

    private var isImageShowed = true
    private var imageFormerPosition = 0f
    private var imageBeforeFormerPosition = 0f

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

        setListeners()

        val intent = activity!!.intent
        val whiskeyName = intent.getStringExtra("name")
        viewModel = ViewModelProviders.of(this).get(WhiskeyDetailViewModel::class.java)
        viewModel.setListener(this)
        viewModel.setUpWhiskey(whiskeyName)

        setUpRecyclers()

        binding.viewModel = viewModel
    }

    private fun setListeners(){
        toolbar_whiskey_detail.setNavigationOnClickListener {
            activity!!.finish()
        }

        //スクロール時の丸形imageViewの挙動を設定
        app_bar_whiskey_detail.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener( fun (appBarLayout: AppBarLayout, verticalOffset: Int) {
                    val per = Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
                    toolbar_whiskey_detail.alpha = per.square()

                    if (per < 0.35){
                        if (per < imageFormerPosition && imageFormerPosition < imageBeforeFormerPosition && isImageShowed.not() && imageBeforeFormerPosition >= 0.75){
                            isImageShowed = true
                            val anim = AnimationUtils.loadAnimation(activity, R.anim.show_circle_image)
                            image_whiskey_detail.startAnimation(anim)
                        }
                    }else{

                        whiskey_description.alpha = 1 - per.square()

                        if (per > imageFormerPosition && imageFormerPosition > imageBeforeFormerPosition && isImageShowed){  //上に動かしてる
                            isImageShowed = false
                            val anim = AnimationUtils.loadAnimation(activity, R.anim.hide_circle_image)
                            image_whiskey_detail.startAnimation(anim)
                        }else if (per < imageFormerPosition && imageFormerPosition < imageBeforeFormerPosition && isImageShowed.not()){  //下に動かしている
                            isImageShowed = true
                            val anim = AnimationUtils.loadAnimation(activity, R.anim.show_circle_image)
                            image_whiskey_detail.startAnimation(anim)
                        }
                    }
                    imageBeforeFormerPosition = imageFormerPosition
                    imageFormerPosition = per
                })
        )

        image_whiskey_detail.setOnClickListener {
            //TODO: 別Fragmentで全画面増表示する
        }
    }

    private fun setUpRecyclers(){

        GlobalScope.launch(Dispatchers.Main) {
            recycler_characteristic_taste.adapter = TasteRecyclerAdapter(context = activity as Context,
                    isCharacteristic = true,
                    intFlagList = viewModel.tasteFlags)
            recycler_characteristic_taste.layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.HORIZONTAL, false)
            recycler_characteristic_taste.isNestedScrollingEnabled = false

            if (viewModel.tasteFlags.none { it == 2 }){
                text_no_characteristic_taste.visibility = View.VISIBLE }
        }

        GlobalScope.launch(Dispatchers.Main){
            recycler_taste.adapter = TasteRecyclerAdapter(context = activity as Context,
                    isCharacteristic = false,
                    intFlagList = viewModel.tasteFlags)
            recycler_taste.layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.HORIZONTAL, false)
            recycler_taste.isNestedScrollingEnabled = false

            if (viewModel.tasteFlags.none { it == 1 }){ text_no_taste.visibility = View.VISIBLE }
        }
    }

    override fun setImages(blob: ByteArray?) {
        if (blob != null) {
            header_image.setImageByteArray(blob)
            image_whiskey_detail.setRoundImageByBlob(blob, activity as Context)
        } else {
            image_whiskey_detail.setImageDrawable(ContextCompat.getDrawable(activity as Context, R.drawable.ic_deffault_image))
        }
    }
}
