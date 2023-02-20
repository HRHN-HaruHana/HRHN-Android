package com.hrhn.domain.model

import java.io.Serializable

data class StorageItem(
    val id: Int = 0,
    val content: String = ""
) : Serializable