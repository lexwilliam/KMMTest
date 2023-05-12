package com.lexwilliam.kmmtest.domain.model

actual class KmmUUID actual constructor() {
    actual val uuidString: String
        get() = TODO("Not yet implemented")
    actual val hashValue: Int
        get() = TODO("Not yet implemented")

    actual companion object {
        actual fun fromString(uuidString: String): KmmUUID {
            TODO("Not yet implemented")
        }
    }

}