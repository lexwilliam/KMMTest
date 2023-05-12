package com.lexwilliam.kmmtest.domain.model

expect class KmmUUID() {
    val uuidString: String
    val hashValue: Int

    companion object {
        fun fromString(uuidString: String): KmmUUID
    }
}