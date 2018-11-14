package io.github.aosa4054.whiskeynote.editWhiskey

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.data.Whiskey
import io.github.aosa4054.whiskeynote.databinding.FragmentEditWhiskeyBinding
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*

class EditWhiskeyFragment : Fragment() {

    interface EditWhiskeyFragmentListener{
        fun getImage()
    }

    private lateinit var binding: FragmentEditWhiskeyBinding
    private lateinit var viewModel: EditWhiskeyViewModel
    private var listener: EditWhiskeyFragmentListener? = null

    private val autoCompleteHints = arrayOf(
            "デュワーズ・ホワイト・ラベル", "ジェムソン", "カナディアンクラブ", "ブラックニッカ　スペシャル", "フォアローゼス",
            "ジョニーウォーカー ブラックラベル", "シーバスリーガル12年", "メーカーズマーク",
            "竹鶴ピュアモルト", "知多", "ジャックダニエル", "ヘネシーV.s", "グレンモーレンジィ　オリジナル", "ザ・グレンリベット12年",
            "グレングラント10年", "ボウモア12年", "ラフロイグ10年", "アードベッグ10年",    //ここまでHub
            "ザ・ニッカ12年", "余市", "宮城峡", "山崎", "白州", "響 Japanese Harmony", "富士山麓",
            "ザ・グレンリベット18年", "グレンフィディック12年", "ザ・マッカラン　ファインオーク12年",
            "グレンモーレンジィ　オリジナル", "グレンモーレンジィ18年", "クライヌリッシュ14年", "ウルフバーン　オーロラ",
            "タリスカー10年", "タリスカー18年", "タリスカー　ストーム", "ハイランドパーク12年", "アランモルト10年",
            "スプリングバンク10年", "オーヘントッシャン12年", "ボウモア18年", "ブナハーブン12年", "カリラ12年", "ラガヴーリン16年",
            "バランタイン　ファイネスト", "バランタイン12年", "バランタイン17年", "バランタイン30年", "ジョニーウォーカー　ブルーラベル",
            "ロイヤルサルート21年", "ロイヤルハウスホールド", "ジムビーム", "ジムビーム・ライ", "I.W.ハーパー12年",
            "I.W.ハーパー　ゴールドメダル", "フォアローゼス　ブラック", "フォアローゼス　プラチナ"
    )    //11/11現在Hub + 原価バーのみ

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        setHasOptionsMenu(true)
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

        val autoCompleteAdapter= ArrayAdapter<String>(activity as Context, android.R.layout.simple_dropdown_item_1line, autoCompleteHints)
        input_name.setAdapter(autoCompleteAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.save_edit_whiskey, menu)
        menu?.findItem(R.id.action_save)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_save) saveWhiskey()
        return true
    }

    private fun setListeners(){
        val inAnimation = AnimationUtils.loadAnimation(activity, R.anim.in_animation)
        val outAnimation = AnimationUtils.loadAnimation(activity, R.anim.out_animation)

        change_image.setOnClickListener { listener?.getImage() } //異常系の処理
        editing_image.setOnClickListener { listener?.getImage() }
        /*fab_save.setOnClickListener {
            saveWhiskey()
        }*/
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

    fun saveWhiskey(){
        val name = input_name.text.toString()
        val type = spinner.selectedItem.toString()
        val kind = viewModel.getTypes(spinner.selectedItemPosition)

        val fruity = seekbar_fruity.progress
        val smokey = seekbar_smokey.progress
        val salty = seekbar_salty.progress
        val malty = seekbar_malty.progress
        val floral = seekbar_floral.progress
        val woody = seekbar_woody.progress

        val memo = memo.text.toString()
        val blob = (activity as EditWhiskeyActivity).blob

        val newWhiskey = Whiskey(name, type, kind, fruity, smokey, salty, malty, floral, woody, memo, blob)
        viewModel.save(newWhiskey)

        Toast.makeText(activity, "保存しました", Toast.LENGTH_SHORT).show()
        (activity as EditWhiskeyActivity).finish()
    }
}
