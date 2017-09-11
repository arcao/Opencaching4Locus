package com.arcao.opencaching4locus.data.okapi

enum class OkApiServiceType(val serviceName: String, val endpoint: String) {
    OPENCACHING_PL("Opencaching.PL", "https://www.opencaching.pl/okapi/"),
    OPENCACHING_DE("Opencaching.DE", "https://www.opencaching.de/okapi/"),
    OPENCACHING_US("Opencaching.US", "https://www.opencaching.us/okapi/"),
    OPENCACHING_NL("Opencaching.NL", "https://www.opencaching.nl/okapi/"),
    OPENCACHING_RO("Opencaching.RO", "https://www.opencaching.ro/okapi/"),
    OPENCACHING_UK("Opencaching.UK", "https://www.opencaching.uk/okapi/")
}