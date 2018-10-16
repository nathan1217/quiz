package com.example.demo.geoquiz

data class Question(val mTextResId: Int, val mAnswerTrue: Boolean) {
    var isRight: Boolean
        get() = isRight
        set(value) {
            this.isRight = value
        }
}