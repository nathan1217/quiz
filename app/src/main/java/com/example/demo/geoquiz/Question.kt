package com.example.demo.geoquiz

data class Question(val mTextResId: Int, val mAnswerTrue: Boolean) {
    var isRight: Boolean = false
}