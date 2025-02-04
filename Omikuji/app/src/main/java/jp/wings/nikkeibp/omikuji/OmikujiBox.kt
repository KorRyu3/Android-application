package jp.wings.nikkeibp.omikuji

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.omikuji.*
import java.util.*

class OmikujiBox: Animation.AnimationListener {

    lateinit var omikujiView: ImageView
    lateinit var omikujiText: TextView
    var finish = false     //箱から出たか
    val number : Int //くじ番号 (0~19の乱数)
    get() {
        val rnd = Random()
        return rnd.nextInt(20)
    }

    fun shake() {
        val translate = TranslateAnimation(0f, 0f, 0f, -200f)
        translate.repeatMode = Animation.REVERSE
        translate.repeatCount = 5
        translate.duration = 100

        val rotate = RotateAnimation(0f, -36f, omikujiView.width/2f, omikujiView.height/2f)
        rotate.duration = 200

        val set = AnimationSet(true)
        set.addAnimation(rotate)
        set.addAnimation(translate)

        set.setAnimationListener(this)

        omikujiView.startAnimation(set)

        finish = true
    }

    override fun onAnimationStart(p0: Animation?) {

    }

    override fun onAnimationEnd(p0: Animation?) {
        omikujiView.setImageResource(R.drawable.omikuji2)
        omikujiText.visibility = View.VISIBLE

    }

    override fun onAnimationRepeat(p0: Animation?) {

    }
}