package jp.wings.nikkeibp.jikoshoukai

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.jikoshoukai.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jikoshoukai)

        animateRotation(fish)
        animateTranslation(fish)

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, Counter::class.java)
            startActivity(intent)
        }
    }

    private fun animateRotation(img: ImageView) {  //回転処理
        val objectAnimator = ObjectAnimator.ofFloat(img, "rotation", 1000f)
        objectAnimator.duration = 1000
        objectAnimator.repeatCount = -1
        objectAnimator.start()
    }

    private fun animateTranslation(img: ImageView) {  //横に移動
        val objectAnimator = ObjectAnimator.ofFloat(img, "translationX", 1550f)
        objectAnimator.duration = 1000
        objectAnimator.repeatCount = -1
        objectAnimator.start()
    }

}