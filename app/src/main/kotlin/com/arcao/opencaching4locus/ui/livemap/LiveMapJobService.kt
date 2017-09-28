package com.arcao.opencaching4locus.ui.livemap

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import android.support.annotation.UiThread
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.model.Location
import com.arcao.opencaching4locus.model.request.BoundingBox
import com.arcao.opencaching4locus.ui.livemap.task.LiveMapDownloadTask
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import locus.api.android.ActionDisplayPoints
import locus.api.android.objects.PackWaypoints
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import locus.api.objects.extra.Location as LocusLocation

class LiveMapJobService : JobService() {
    @Inject lateinit var task: LiveMapDownloadTask
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

        fun createNewJob(context: Context, mapTopLeft: LocusLocation, mapBottomRight: LocusLocation) {
            val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

            val builder = JobInfo.Builder(JOB_ID, ComponentName(context, LiveMapJobService::class.java)).apply {
                setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                setMinimumLatency(0)
                setExtras(PersistableBundle().apply {
                    putString(BOUNDING_BOX, BoundingBox(
                            Location.from(mapTopLeft),
                            Location.from(mapBottomRight)
                    ).toString())
                })
            }

            scheduler.schedule(builder.build())
        }

        fun cleanMap(context: Context) {
            // TODO magic constants
            val count = 100 * AccountType.values().size / 50

            Observable.range(1, count)
                    .observeOn(Schedulers.computation())
                    .map {
                        PackWaypoints("OC_PACK_$it")
                    }
                    .subscribe {
                        ActionDisplayPoints.sendPackSilent(context, it, false)
                    }
        }
    }
}