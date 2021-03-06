package io.github.aosa4054.whiskeynote.whiskeyDetail.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentWhiskeyDetailBinding
import kotlinx.android.synthetic.main.fragment_whiskey_detail.*
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import io.github.aosa4054.whiskeynote.extention.setImageByteArray
import io.github.aosa4054.whiskeynote.extention.setRoundImageByBlob
import io.github.aosa4054.whiskeynote.extention.square
import io.github.aosa4054.whiskeynote.whiskeyDetail.TasteRecyclerAdapter
import io.github.aosa4054.whiskeynote.whiskeyDetail.viewModel.WhiskeyDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class WhiskeyDetailFragment : Fragment(), WhiskeyDetailViewModel.WhiskeyDetailListener {

    private lateinit var binding: FragmentWhiskeyDetailBinding

    private var isImageShowed = true
    private var imageFormerPosition = 0f
    private var imageBeforeFormerPosition = 0f

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

        setChip()

        binding.viewModel = viewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isImageShowed", isImageShowed)
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
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

                    if (per < imageFormerPosition && imageFormerPosition < imageBeforeFormerPosition && isImageShowed.not()){  //下に動かしている
                        isImageShowed = true
                        val anim = AnimationUtils.loadAnimation(activity, R.anim.show_circle_image)
                        image_whiskey_detail.startAnimation(anim)
                    }

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
                        }
                    }
                    imageBeforeFormerPosition = imageFormerPosition
                    imageFormerPosition = per
                })
        )

        image_whiskey_detail.setOnLongClickListener {
            if (viewModel.blob != null){
                Navigation.findNavController(it).navigate(WhiskeyDetailFragmentDirections.actionShowImage().apply {
                    whiskeyName = viewModel.name
                })
            }
            true
        }
    }

    private fun setUpRecyclers(){

        GlobalScope.launch(Dispatchers.Main) {
            recycler_characteristic_taste.adapter = TasteRecyclerAdapter(context = activity as Context,
                    isCharacteristic = true,
                    intFlagList = viewModel.tasteFlags)
            recycler_characteristic_taste.layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.HORIZONTAL, false)
            recycler_characteristic_taste.isNestedScrollingEnabled = false

            if (viewModel.tasteFlags.none { it == 2 }){ text_no_characteristic_taste.visibility = View.VISIBLE }
        }

        GlobalScope.launch(Dispatchers.Main){
            recycler_taste.adapter = TasteRecyclerAdapter(context = activity as Context,
                    isCharacteristic = false,
                    intFlagList = viewModel.tasteFlags)
            recycler_taste.layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.HORIZONTAL, false)
            recycler_taste.isNestedScrollingEnabled = false

            if (viewModel.tasteFlags.none { it == 1 }){ text_no_taste.visibility = View.VISIBLE }

            //FIXME: これの書き場所の再考
            //setChip()
            if (viewModel.memo.isNullOrEmpty()) cardMemo.visibility = View.GONE
        }
    }

    private fun setChip(){
        fun determinateChip(i: Int, s: String){
            if (i == 1){
                val chip = Chip(activity)
                chip.text = s
                chipGroup.addView(chip)
            }
        }


        determinateChip(viewModel.isDelicate, "繊細")
        determinateChip(viewModel.isLight, "ライト")
        determinateChip(viewModel.isMild, "マイルド")
        determinateChip(viewModel.isComplex, "複雑")
        determinateChip(viewModel.isRich, "リッチ")
        determinateChip(viewModel.isElegant, "エレガント")
        determinateChip(viewModel.isFlesh, "フレッシュ")

        if (viewModel.features.all { it == 0 }) {
            chipGroup.visibility = View.GONE
            text_no_feature.visibility = View.VISIBLE
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
