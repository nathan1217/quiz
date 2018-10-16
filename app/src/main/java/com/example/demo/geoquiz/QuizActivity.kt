package com.example.demo.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class QuizActivity : AppCompatActivity() {
    private var mTrueButton: Button? = null
    private var mFalseButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        mTrueButton = this.findViewById(R.id.btnTrue)
        mTrueButton?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                Toast.makeText(this@QuizActivity, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
        })
        mFalseButton = this.findViewById(R.id.btnFalse)
        mFalseButton?.setOnClickListener { _ ->
            Toast.makeText(
                this@QuizActivity,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
