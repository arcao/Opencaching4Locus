package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.model.OkApiError

class OkApiException(val url: String, val error : OkApiError) : Exception("${error.reason_stack.joinToString(", ")}: ${error.developer_message}")
