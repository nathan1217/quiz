package com.example.demo.geoquiz

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuestionViewModel : ViewModel() {
    private var mQuestion: MutableLiveData<Pagination<Question>> = MutableLiveData()
    private var mRepertory: QuestionRepertory = QuestionRepertory()
    private var currentIndex: Int = 0

    fun getQuestion(): MutableLiveData<Pagination<Question>> {
        return mQuestion
    }

    fun loadQuestion() {
        mRepertory.getQuestion(currentIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { question ->
                mQuestion.setValue(question)
            }
    }

    fun nextQuestion() {
        mRepertory.getQuestion(++currentIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe  { question ->
                mQuestion.setValue(question)
            }
    }

    fun previousQuestion() {
        if (currentIndex <= 0) {
            return
        }
        mRepertory.getQuestion(--currentIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe  { question ->
                mQuestion.setValue(question)
            }
    }
}