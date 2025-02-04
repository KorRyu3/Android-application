package jp.wings.nikkeibp.jikoshoukai

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlinx.android.synthetic.main.count.*
import kotlinx.android.synthetic.main.jikoshoukai.*
import java.util.*
import kotlin.math.*

class Counter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.count)
        counter()
        button2.setOnClickListener {  //MainActivityに戻る
            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun counter() {
        calculation(10)  //下の計算関数のrndIntの初期値

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // エンターキーを押したらやりたい処理
                this.hideKeyboard()  //キーボードを隠し、フォーカスを外す
            }
            return@setOnEditorActionListener true
        }

        probability_bt.setOnClickListener {  //決定ボタンを押したら
            this.hideKeyboard()
            val editText = editText.text.toString()  //EditTextに打ち込まれた文字を入れる
            if (editText.isEmpty()) {  //EditTextに入った値が空文字かどうか調べる
                calculation(10)
                Counter.text = "数字を入力してください"
            } else {  //EditTextが空文字でないなら
                val editTextInt: Int = editText.toInt()  //EditTextを文字列から数値に変換する
                calculation(editTextInt)  //下の計算関数のrndIntにEditTextで入力された値を入れる
                Counter.text = "0 円"
                if (editTextInt == 0 || editTextInt < 0) {  //EditTextに入った値が0以下かどうか調べる
                    calculation(10)
                    Counter.text = "自然数を入力してください"
                }
            }
            doSpringAnimation(probability_bt)
        }
        reset.setOnClickListener {  //resetボタン
            calculation(10)  //下の計算関数の初期化
            Counter.text = "0 円"  //TextViewの初期化
            editText.editableText.clear()  //EditText内に書かれている数字を消す
            doSpringAnimation(reset)
        }

        editText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //キーボード非表示
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        background.setOnTouchListener { v, event ->
            background.requestFocus()
            v?.onTouchEvent(event) ?: true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculation(rndInt: Int) {
        Title.text = "1/$rndInt の確率ですべてを失うゲーム"

        var count = 0  //はじめは0円
        var pow = 100.0  //百分率
        Counter.text = "$count 円"

        Count_bt.setOnClickListener {  //百円ボタンを押したら
            val rnd = Random().nextInt(rndInt)  //100円ボタンを押すたびに抽選してる
            if (rnd == 0) {  //rndIntが0になったときに、count(金額)とpow(確率)を初期化する
                count = 0
                pow = 100.0
                Counter.text = "$count 円"
                shiverDoSpringAnimation(Count_bt)  //百円ボタンが弾むアニメーション
            } else {
                count += 100  //100円ずつ足す
                pow *= 1 - (1 / rndInt.toDouble())   //0以外を引く確率を求めている ここと下の処理で確率を出している
                pow = ((pow * 10000.0).roundToInt().toDouble()) / 10000.0  //小数第五位から切り捨てる
                Counter.text = "$count 円 \n 確率 $pow %"
                doSpringAnimation(Count_bt)  //百円ボタンが震える動きのアニメーション
            }
        }
    }

    private fun hideKeyboard() {  //キーボードを隠してフォーカスを外す処理
        val view = this@Counter.currentFocus
        if (view != null) {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        editText.isFocusableInTouchMode = false
        editText.isFocusable = false
        editText.isFocusableInTouchMode = true
        editText.isFocusable = true
    }

    private fun doSpringAnimation(sender: View) {  //物理法則のアニメーション

        sender.scaleX = 0.8f
        sender.scaleY = 0.8f

        val (anim1X, anim1Y) = sender.let { view ->
            SpringAnimation(view, DynamicAnimation.SCALE_X, 1.0f) to
                    SpringAnimation(view, DynamicAnimation.SCALE_Y, 1.0f)
        }
        anim1X.spring.apply {
            dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY  //バウンドの高さ
            stiffness = SpringForce.STIFFNESS_LOW  //戻ってくるまでの長さ
        }
        anim1Y.spring.apply {
            dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            stiffness = SpringForce.STIFFNESS_LOW
        }
        anim1X.start()
        anim1Y.start()
    }

    private fun shiverDoSpringAnimation(sender: View) {  //物理法則のアニメーション(すべてを失ったバージョン)

        sender.scaleX = 0.5f
        sender.scaleY = 0.5f

        val (anim1X, anim1Y) = sender.let { view ->
            SpringAnimation(view, DynamicAnimation.SCALE_X, 1.0f) to
                    SpringAnimation(view, DynamicAnimation.SCALE_Y, 1.0f)
        }
        anim1X.spring.apply {
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            stiffness = SpringForce.STIFFNESS_HIGH
        }
        anim1Y.spring.apply {
            dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            stiffness = SpringForce.STIFFNESS_LOW
        }
        anim1X.start()
        anim1Y.start()
    }

}