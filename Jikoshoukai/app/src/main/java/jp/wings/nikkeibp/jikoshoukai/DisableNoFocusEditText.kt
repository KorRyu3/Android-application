package jp.wings.nikkeibp.jikoshoukai

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View

/**
 * キーボードが閉じたとき、フォーカスを外すEditText
 */
class DisableNoFocusEditText : androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            val parent = parent as View
            parent.isFocusable = true
            parent.isFocusableInTouchMode = true
            parent.requestFocus()
        }
        return super.onKeyPreIme(keyCode, event)
    }
}