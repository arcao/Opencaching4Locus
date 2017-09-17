package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.model.response.OkApiError

class OkApiException(val url: String, val error : OkApiError) : Exception("${error.reasonStack.joinToString(", ")}: ${error.developerMessage}")
