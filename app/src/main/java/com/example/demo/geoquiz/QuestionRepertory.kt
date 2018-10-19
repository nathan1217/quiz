package com.example.demo.geoquiz

import io.reactivex.Observable

class QuestionRepertory {
    private val mQuestions = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    fun getQuestion(index: Int): Observable<Pagination<Question>> {
        return Observable.just(Pagination(index, mQuestions.size, mQuestions[index]))
    }
}