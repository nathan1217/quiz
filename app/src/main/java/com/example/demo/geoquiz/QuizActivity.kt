package com.example.demo.geoquiz

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import android.content.Intent


class QuizActivity : AppCompatActivity() {
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: Button
    private lateinit var mBackButton: Button
    private lateinit var mCheatingButton: Button
    private lateinit var mQuestionTextView: TextView
    private var mCurrentIndex: Int = 0
    private lateinit var mQuestionBank: Array<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate")
        setContentView(R.layout.activity_quiz)
        mCurrentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        mQuestionBank = savedInstanceState?.getParcelableArray(KEY_QUESTION_ARRAY) as Array<Question>? ?: arrayOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
        )

        mQuestionTextView = this.findViewById(R.id.txtViewQuestion)
        mQuestionTextView.setOnClickListener { _ ->
            toNextQuestion()
        }

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

        mCheatingButton = this.findViewById(R.id.btnCheating)
        mCheatingButton.setOnClickListener { _ ->
            startActivityForResult(
                CheatingActivity.newIntent(
                    this@QuizActivity,
                    mQuestionBank[mCurrentIndex].mAnswerTrue
                )
                , REQUEST_CODE_CHEAT
            )
        }

        updateQuestion()
        if (mCurrentIndex <= 0)
            this.mBackButton.isEnabled = false
        if (mCurrentIndex >= mQuestionBank.size - 1)
            this.mNextButton.isEnabled = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_CODE_CHEAT -> {
                when (data) {
                    null -> return
                    else -> {
                        var isCheater: Boolean = CheatingActivity.wasAnswerShown(data)
                        var question: Question = mQuestionBank[mCurrentIndex]
                        question.isCheating = isCheater
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "onSaveInstanceState")
        outState?.putInt(KEY_INDEX, mCurrentIndex)
        outState?.putParcelableArray(KEY_QUESTION_ARRAY, mQuestionBank)
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }

    private fun toBackQuestion() {
        this.mCurrentIndex = this.mCurrentIndex.dec()
        if (mCurrentIndex >= 0)
            updateQuestion()
        if (mCurrentIndex <= 0)
            this.mBackButton.isEnabled = false
        if (mCurrentIndex <= mQuestionBank.size - 1)
            this.mNextButton.isEnabled = true
    }

    private fun toNextQuestion() {
        if (this.mCurrentIndex++ < mQuestionBank.size - 1)
            updateQuestion()
        if (mCurrentIndex >= 1)
            this.mBackButton.isEnabled = true
        if (mCurrentIndex >= mQuestionBank.size - 1)
            this.mNextButton.isEnabled = false
    }

    private fun updateQuestion() {
        val question: Question = mQuestionBank[mCurrentIndex]
        mQuestionTextView.setText(question.mTextResId)
        toggleBtnAnswer(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val question: Question = mQuestionBank[mCurrentIndex]
        val answerIsTrue: Boolean = question.mAnswerTrue
        question.isAnswered = true
        var msgResId: Int = when {
            question.isCheating -> R.string.judgment_toast
            else -> {
                when (answerIsTrue) {
                    userPressedTrue -> R.string.correct_toast
                    else -> R.string.incorrect_toast
                }
            }
        }
        toggleBtnAnswer(question)
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, mQuestionBank.count { q ->
//            q.getAnswerTag()
//        }.toString(), Toast.LENGTH_LONG).show()
    }

    private fun toggleBtnAnswer(question: Question) {
        if (question.isAnswered) {
            this.mFalseButton.isEnabled = false
            this.mTrueButton.isEnabled = false
        } else {
            this.mFalseButton.isEnabled = true
            this.mTrueButton.isEnabled = true
        }
    }


    companion object {
        private const val REQUEST_CODE_CHEAT: Int = 1
        private const val LOG_TAG: String = "QuizActivity"
        private const val KEY_INDEX: String = "currentIndex"
        private const val KEY_QUESTION_ARRAY: String = "currentQuestionArray"
    }
}
