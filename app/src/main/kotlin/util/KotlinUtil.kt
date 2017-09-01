package util


fun String?.orDefault(default : String) : String {
    return this ?: default
}

fun CharSequence?.orDefault(default : CharSequence = "") : CharSequence {
    return this ?: default
}