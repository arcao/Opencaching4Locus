package com.arcao.opencaching4locus.data.retrofit.adapter

import com.arcao.opencaching4locus.data.okapi.OkApiException
import com.arcao.opencaching4locus.model.response.OkApiErrorResponse
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxJava2ErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        @Suppress("UNCHECKED_CAST")
        return RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>?)
    }

    private class RxCallAdapterWrapper(private val retrofit: Retrofit, private val wrapped: CallAdapter<Any, Any>?) : CallAdapter<Any, Any> {
        override fun responseType(): Type {
            return wrapped?.responseType() ?: Void.TYPE
        }

        override fun adapt(call: Call<Any>): Any? {
            if (wrapped == null)
                return null

            val adapted = wrapped.adapt(call)
            return when(adapted) {
                is Observable<*> -> adapted.onErrorResumeNext { throwable: Throwable -> Observable.error(asOkApiException(throwable))}
                is Single<*> -> adapted.onErrorResumeNext { throwable: Throwable -> Single.error(asOkApiException(throwable))}
                is Flowable<*> -> adapted.onErrorResumeNext { throwable: Throwable -> Flowable.error(asOkApiException(throwable))}
                is Maybe<*> -> adapted.onErrorResumeNext { throwable: Throwable -> Maybe.error(asOkApiException(throwable))}
                is Completable -> adapted.onErrorResumeNext { throwable: Throwable -> Completable.error(asOkApiException(throwable))}
                else -> adapted
            }
        }

        private fun asOkApiException(throwable: Throwable): Throwable {
            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                val url = response.raw().request().url().toString()

                return try {
                    val converter = retrofit.responseBodyConverter<OkApiErrorResponse>(OkApiErrorResponse::class.java, arrayOfNulls(0))
                    val (error) = converter.convert(response.errorBody())
                    OkApiException(url, error)
                } catch (e: IOException) {
                    throwable
                }

            }

            return throwable
        }
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxJava2ErrorHandlingCallAdapterFactory()
        }
    }
}