package com.arcao.opencaching4locus.model

import java.util.*

data class OkApiError(
        val developer_message: String,
        val reason_stack: Array<String>,
        val status: Int,
        val more_info: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OkApiError

        if (developer_message != other.developer_message) return false
        if (!Arrays.equals(reason_stack, other.reason_stack)) return false
        if (status != other.status) return false
        if (more_info != other.more_info) return false

        return true
    }

    override fun hashCode(): Int {
        var result = developer_message.hashCode()
        result = 31 * result + Arrays.hashCode(reason_stack)
        result = 31 * result + status
        result = 31 * result + more_info.hashCode()
        return result
    }
}