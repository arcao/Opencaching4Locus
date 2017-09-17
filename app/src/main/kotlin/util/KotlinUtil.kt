@file:Suppress("NOTHING_TO_INLINE")

package util

inline fun CharSequence?.orDefault(default : CharSequence = "") : CharSequence = this ?: default

inline fun CharSequence?.isNotNullOrEmpty() : Boolean = this !== null && this.isNotEmpty()