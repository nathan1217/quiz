package com.example.demo.geoquiz

import android.os.Parcel
import android.os.Parcelable

data class Question(val mTextResId: Int, val mAnswerTrue: Boolean) : Parcelable {
    var isRight: Boolean = false
    var isAnswered: Boolean = false
    var isCheating: Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
        isRight = parcel.readByte() != 0.toByte()
        isAnswered = parcel.readByte() != 0.toByte()
        isCheating = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mTextResId)
        parcel.writeByte(if (mAnswerTrue) 1 else 0)
        parcel.writeByte(if (isRight) 1 else 0)
        parcel.writeByte(if (isAnswered) 1 else 0)
        parcel.writeByte(if (isCheating) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}