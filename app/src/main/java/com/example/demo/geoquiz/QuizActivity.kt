package com.example.demo.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.TextView


class QuizActivity : AppCompatActivity() {
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: Button
    private lateinit var mBackButton: Button
    private lateinit var mQuestionTextView: TextView
    private var mCurrentIndex: Int = 0
    private val mQuestionBank = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mQuestionTextView = this.findViewById(R.id.txtViewQuestion)
        mQuestionTextView.setOnClickListener { _ ->
            toNextQuestion()
        }
        updateQuestion()

        mTrueButton = this.findViewById(R.id.btnTrue)
        mTrueButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                checkAnswer(true)
            }
        })

        mFalseButton = this.findViewById(R.id.btnFalse)
        mFalseButton.setOnClickListener { _ ->
            checkAnswer(false)
        }

        mBackButton = this.findViewById(R.id.btnBack)
        mBackButton.setOnClickListener { _ ->
            toBackQuestion()
        }

        mNextButton = this.findViewById(R.id.btnNext)
        mNextButton.setOnClickListener { _ ->
            toNextQuestion()
        }
        this.mBackButton.isEnabled = false;
    }

    private fun toBackQuestion() {
        this.mCurrentIndex = this.mCurrentIndex.dec()
        if (mCurrentIndex >= 1)
            updateQuestion()
        if (mCurrentIndex <= 1)
            this.mBackButton.isEnabled = false
        if (mCurrentIndex <= mQuestionBank.size - 1)
            this.mNextButton.isEnabled = true
    }

    private fun toNextQuestion() {
        if (++this.mCurrentIndex < mQuestionBank.size)
            updateQuestion()
        if (mCurrentIndex >= 1)
            this.mBackButton.isEnabled = true
        if (mCurrentIndex >= mQuestionBank.size - 1)
            this.mNextButton.isEnabled = false
    }

    private fun updateQuestion() {
        val question: Int = mQuestionBank[mCurrentIndex].mTextResId
        mQuestionTextView.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val question: Question = mQuestionBank[mCurrentIndex]
        val answerIsTrue: Boolean = question.mAnswerTrue
        var msgResId: Int = when (answerIsTrue) {
            userPressedTrue -> {
                question.isRight = true
                R.string.correct_toast
            }
            else -> {
                question.isRight = false
                R.string.incorrect_toast
            }
        }
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, mQuestionBank.count { q ->
//            q.getAnswerTag()
//        }.toString(), Toast.LENGTH_LONG).show()
    }
}
