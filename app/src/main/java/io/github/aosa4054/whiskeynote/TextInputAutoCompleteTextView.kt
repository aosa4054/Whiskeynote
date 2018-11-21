package io.github.aosa4054.whiskeynote

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TextInputAutoCompleteTextView: AppCompatAutoCompleteTextView{
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    override fun getHint(): CharSequence? {
        val layout = getTextInputLayout()
        if ((layout != null) && layout.hint != null){
            return layout.hint
        }
        return super.getHint()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        val ic = super.onCreateInputConnection(outAttrs)
        if (ic != null && outAttrs.hintText == null){
            val parent = parent
            if (parent is TextInputLayout){
                outAttrs.hintText = parent.hint
            }
        }
        return ic
    }

    private fun getTextInputLayout(): TextInputEditText? {
        var parent = parent
        while (parent is View){
            if (parent is TextInputEditText) {
                return parent
            }
            parent = parent.getParent()
        }
        return null
    }
}