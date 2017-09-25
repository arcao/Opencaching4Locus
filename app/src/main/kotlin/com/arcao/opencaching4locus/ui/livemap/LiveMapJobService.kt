package com.arcao.opencaching4locus.ui.livemap

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import android.support.annotation.UiThread
import com.arcao.opencaching4locus.model.request.BoundingBox
import com.arcao.opencaching4locus.ui.livemap.task.LiveMapDownloadTask
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import locus.api.objects.extra.Location
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LiveMapJobService : JobService() {
    @Inject lateinit var task : LiveMapDownloadTask
    private lateinit var subject: Subject<JobParameters>
    private lateinit var disposable: Disposable

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()

        subject = PublishSubject.create()
        disposable = createLastJobObservable(subject) {
            jobFinished(it, false)
        }.subscribe {
            task.run(BoundingBox.from(it.extras.getString(BOUNDING_BOX)))
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun onStartJob(parameters: JobParameters): Boolean {
        subject.onNext(parameters)
        return true
    }

    override fun onStopJob(parameters: JobParameters): Boolean {
        return true
    }

    @UiThread
    private fun createLastJobObservable(subject: Subject<JobParameters>, filtered: (JobParameters) -> Unit): Observable<JobParameters> {
        return subject
                .observeOn(Schedulers.io())
                .buffer(300, TimeUnit.MICROSECONDS)
                .map {
                    for (i in 0 until it.size - 1) filtered(it[i])
                    it.last()
                }
    }

    companion object {
        private const val JOB_ID = 0
        private const val BOUNDING_BOX = "BOUNDING_BOX"

        fun createNewJob(context: Context, mapTopLeft: Location, mapBottomRight: Location) {
            val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

            val builder = JobInfo.Builder(JOB_ID, ComponentName(context, LiveMapJobService::class.java)).apply {
                setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                setMinimumLatency(0)
                setExtras(PersistableBundle().apply {
                    putString(BOUNDING_BOX, BoundingBox(
                            com.arcao.opencaching4locus.model.Location.from(mapTopLeft),
                            com.arcao.opencaching4locus.model.Location.from(mapBottomRight)
                    ).toString())
                })
            }

            scheduler.cancel(JOB_ID)
            scheduler.schedule(builder.build())
        }
    }
}