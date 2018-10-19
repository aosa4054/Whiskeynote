package io.github.aosa4054.whiskeynote.top

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment

import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.fragments_main.*


class AllWhiskeysFragment : Fragment() {

    companion object {
        fun newInstance() = AllWhiskeysFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var backdropShown = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragments_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun transformBackdrop(){

        val animatorSet = AnimatorSet()
        val height: Int

        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels

        backdropShown = !backdropShown

        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY = height - 450 //context.resources.getDimensionPixelSize(R.dimen.backdrop_height)

        val animator = ObjectAnimator.ofFloat(product_grid, "translationY", (if (backdropShown) translateY else 0).toFloat())
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.play(animator)

        animator.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_filter -> {
                transformBackdrop()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
