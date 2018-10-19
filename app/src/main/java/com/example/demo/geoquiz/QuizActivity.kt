package com.example.demo.geoquiz

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import android.content.Intent
import butterknife.*
import dagger.android.support.DaggerAppCompatActivity

class QuizActivity : DaggerAppCompatActivity() {
    @BindView(R.id.btnTrue)
    lateinit var mTrueButton: Button
    @BindView(R.id.btnFalse)
    lateinit var mFalseButton: Button
    @BindView(R.id.btnNext)
    lateinit var mNextButton: Button
    @BindView(R.id.btnBack)
    lateinit var mBackButton: Button
    @BindView(R.id.txtViewQuestion)
    lateinit var mQuestionTextView: TextView
    @BindString(R.string.question_oceans)
    lateinit var mEmptyQuestion: String

    private lateinit var mViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate")
        setContentView(R.layout.activity_quiz)

        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(QuestionViewModel::class.java)
        mViewModel.getQuestion().observe(this, Observer<Pagination<Question>> { question ->
            mQuestionTextView.setText(question?.data?.mTextResId ?: mEmptyQuestion.toInt())
            togglePageBtn(question)
            toggleAnswerBtn(question)
        })
        ButterKnife.bind(this)
        mViewModel.loadQuestion()
    }

    private fun togglePageBtn(question: Pagination<Question>?) {
        val pageIndex = question?.currentIndex ?: 0
        val count = question?.totalCount ?: 0
        when {
            pageIndex <= 0 -> mBackButton.isEnabled = false
        }
        when {
            pageIndex > 0 -> mBackButton.isEnabled = true
        }
        when {
            pageIndex >= count - 1 -> {
                mNextButton.isEnabled = false
                mQuestionTextView.isClickable = false
            }
        }
        when {
            pageIndex < count - 1 -> {
                mNextButton.isEnabled = true
                mQuestionTextView.isClickable = true
            }
        }
    }

    private fun toggleAnswerBtn(question: Pagination<Question>?) {
        when (question?.data?.isAnswered) {
            true -> {
                this.mFalseButton.isEnabled = false
                this.mTrueButton.isEnabled = false
            }
            else -> {
                this.mFalseButton.isEnabled = true
                this.mTrueButton.isEnabled = true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_CODE_CHEAT -> {
                when (data) {
                    null -> return
                    else -> {
                        var isCheater: Boolean = CheatingActivity.wasAnswerShown(data)
                        var question: Question? = mViewModel.getQuestion().value?.data
                        question?.isCheating = isCheater
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "onSaveInstanceState")
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

    @OnClick(R.id.btnBack)
    fun toBackQuestion() {
        mViewModel.previousQuestion()
    }

    @OnClick(R.id.btnCheating)
    fun cheating() {
        startActivityForResult(
            CheatingActivity.newIntent(
                this@QuizActivity,
                (mViewModel.getQuestion().value?.data as Question).mAnswerTrue
            )
            , REQUEST_CODE_CHEAT
        )
    }

    @OnClick(R.id.btnNext)
    fun bindNextActionToBtn() {
        mViewModel.nextQuestion()
    }

    @OnClick(R.id.txtViewQuestion)
    fun bindNextActionToTxt() {
        mViewModel.nextQuestion()
    }

    @OnClick(R.id.btnTrue)
    fun checkTrue() {
        checkAnswer(true)
    }

    @OnClick(R.id.btnFalse)
    fun checkFalse() {
        checkAnswer(false)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val question: Question = mViewModel.getQuestion().value?.data as Question
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
        toggleAnswerBtn(mViewModel.getQuestion().value)
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val REQUEST_CODE_CHEAT: Int = 1
        private const val LOG_TAG: String = "QuizActivity"
    }
}
