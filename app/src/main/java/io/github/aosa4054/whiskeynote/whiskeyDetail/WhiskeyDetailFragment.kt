package io.github.aosa4054.whiskeynote.whiskeyDetail

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import io.github.aosa4054.whiskeynote.R
import io.github.aosa4054.whiskeynote.databinding.FragmentWhiskeyDetailBinding
import kotlinx.android.synthetic.main.fragment_whiskey_detail.*
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import android.opengl.ETC1.getWidth
import android.graphics.*
import androidx.core.content.ContextCompat


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
        binding.viewModel = viewModel
    }

    private fun setListeners(){
        toolbar_whiskey_detail.setNavigationOnClickListener {
            activity!!.finish()
        }

        app_bar_whiskey_detail.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener( fun (appBarLayout: AppBarLayout, verticalOffset: Int) {
                    val per = Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
                    toolbar_whiskey_detail.alpha = square(per)
                    contents_appbar.alpha = 1 - square(per)

                    if (per < 0.75){
                        image_whiskey_detail.scaleX = 1 - square(per)
                        image_whiskey_detail.scaleY = 1 - square(per)
                        if (per < imageFormerPosition && imageFormerPosition < imageBeforeFormerPosition && isImageShowed.not() && imageBeforeFormerPosition >= 0.75){
                            isImageShowed = true
                            val anim = AnimationUtils.loadAnimation(activity, R.anim.show_circle_image)
                            image_whiskey_detail.startAnimation(anim)
                        }
                    }else{
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

    private fun square(i: Float) = i * i

    override fun setImage(blob: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.size)

        //val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(activity!!.resources, bitmap)
        //roundedBitmapDrawable.cornerRadius = 350f

        val roundedBitmapDrawable = createRoundedBitmapDrawableWithBorder(bitmap)
        image_whiskey_detail.setImageDrawable(roundedBitmapDrawable)
    }

    private fun createRoundedBitmapDrawableWithBorder(bitmap: Bitmap): RoundedBitmapDrawable {
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height
        val borderWidthHalf = 24 // In pixels
        //Toast.makeText(mContext,""+bitmapWidth+"|"+bitmapHeight,Toast.LENGTH_SHORT).show();

        // Calculate the bitmap radius
        val bitmapRadius = Math.min(bitmapWidth, bitmapHeight) / 2

        val bitmapSquareWidth = Math.min(bitmapWidth, bitmapHeight)
        //Toast.makeText(mContext,""+bitmapMin,Toast.LENGTH_SHORT).show();

        val newBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf
        //Toast.makeText(mContext,""+newBitmapMin,Toast.LENGTH_SHORT).show();

        /*
            Initializing a new empty bitmap.
            Set the bitmap size from source bitmap
            Also add the border space to new bitmap
        */
        val roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth, newBitmapSquareWidth, Bitmap.Config.ARGB_8888)

        /*
            Canvas
                The Canvas class holds the "draw" calls. To draw something, you need 4 basic
                components: A Bitmap to hold the pixels, a Canvas to host the draw calls (writing
                into the bitmap), a drawing primitive (e.g. Rect, Path, text, Bitmap), and a paint
                (to describe the colors and styles for the drawing).

            Canvas(Bitmap bitmap)
                Construct a canvas with the specified bitmap to draw into.
        */
        // Initialize a new Canvas to draw empty bitmap
        val canvas = Canvas(roundedBitmap)

        /*
            drawColor(int color)
                Fill the entire canvas' bitmap (restricted to the current clip) with the specified
                color, using srcover porterduff mode.
        */
        // Draw a solid color to canvas
        //canvas.drawColor(R.color.colorPrimary as Int)

        // Calculation to draw bitmap at the circular bitmap center position
        val x = (borderWidthHalf + bitmapSquareWidth - bitmapWidth).toFloat()
        val y = (borderWidthHalf + bitmapSquareWidth - bitmapHeight).toFloat()

        /*
            drawBitmap(Bitmap bitmap, float left, float top, Paint paint)
                Draw the specified bitmap, with its top/left corner at (x,y), using the specified
                paint, transformed by the current matrix.
        */
        /*
            Now draw the bitmap to canvas.
            Bitmap will draw its center to circular bitmap center by keeping border spaces
        */
        canvas.drawBitmap(bitmap, x, y, null)

        // Initializing a new Paint instance to draw circular border
        val borderPaint = Paint()
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidthHalf * 2f
        borderPaint.color = ContextCompat.getColor(activity as Context, R.color.colorPrimary)

        /*
            drawCircle(float cx, float cy, float radius, Paint paint)
                Draw the specified circle using the specified paint.
        */
        /*
            Draw the circular border to bitmap.
            Draw the circle at the center of canvas.
         */
        canvas.drawCircle(canvas.width / 2f, canvas.width / 2f, newBitmapSquareWidth / 2f, borderPaint)

        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(activity!!.resources, roundedBitmap)

        roundedBitmapDrawable.cornerRadius = bitmapRadius.toFloat()

        /*
            setAntiAlias(boolean aa)
                Enables or disables anti-aliasing for this drawable.
        */
        roundedBitmapDrawable.setAntiAlias(true)

        return roundedBitmapDrawable
    }
}
