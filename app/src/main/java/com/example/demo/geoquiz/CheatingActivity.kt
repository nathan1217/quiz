package com.example.demo.geoquiz

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView


class CheatingActivity : AppCompatActivity() {
    private var mAnswerIsTrue: Boolean = false
    private var mAnswerIsShow: Boolean = false
    private lateinit var mAnswerTxtView: TextView
    private lateinit var mShowAnswerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheating)
        mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        mAnswerIsShow = savedInstanceState?.getBoolean(ANSWER_SHOW, false) ?: false
        setAnswerShownResult(mAnswerIsShow)
        mAnswerTxtView = findViewById(R.id.answer_text_view)
        mShowAnswerBtn = findViewById(R.id.show_answer_button)
        mShowAnswerBtn.setOnClickListener { _ ->
            if (mAnswerIsTrue) {
                mAnswerTxtView.setText(R.string.true_button)
            } else {
                mAnswerTxtView.setText(R.string.false_button)
            }
            setAnswerShownResult(true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(ANSWER_SHOW, mAnswerIsTrue)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE: String = "com.example.demo.geoquiz.answer_is_true"
        private const val EXTRA_ANSWER_SHOWN: String = "com.example.demo.geoquiz.answer_shown"
        private const val ANSWER_SHOW: String = "answer_show"
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatingActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }

        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        }
    }
}
