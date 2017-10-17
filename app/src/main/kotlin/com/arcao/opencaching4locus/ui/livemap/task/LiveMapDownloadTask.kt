package com.arcao.opencaching4locus.ui.livemap.task

import android.content.Context
import android.support.annotation.WorkerThread
import com.arcao.opencaching4locus.data.account.AccountManager
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.data.locusmap.mapper.GeocacheLocusMapper
import com.arcao.opencaching4locus.data.okapi.OkApiService
import com.arcao.opencaching4locus.data.okapi.OkApiServiceType
import com.arcao.opencaching4locus.model.request.BoundingBox
import com.arcao.opencaching4locus.model.response.Geocache
import com.arcao.opencaching4locus.ui.livemap.manager.LiveMapNotificationManager
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import locus.api.android.ActionDisplayPoints
import locus.api.android.objects.PackWaypoints
import javax.inject.Inject

class LiveMapDownloadTask @Inject constructor(
        private val context: Context,
        private val manager: LiveMapNotificationManager,
        //private val sharedPreferences: SharedPreferences,
        private val services: Map<OkApiServiceType, @JvmSuppressWildcards OkApiService>,
        private val accountManager: AccountManager,
        private val mapper: GeocacheLocusMapper
) {
    @WorkerThread
    fun run(boundingBox: BoundingBox) {
        var wpIndex = 1
        var accountTypeIndex = 1
        val accountTypeSize = AccountType.values().size

        manager.showProgress(0, accountTypeSize)

        Flowable.fromArray(*AccountType.values())
                .observeOn(Schedulers.io())
                .filter { accountManager.getAccount(it).liveMapEnabled }
                .filter { it.toServiceType().consumerKey.isNotEmpty() }
                .flatMap { retrieveGeocaches(it, boundingBox) }
                .map(mapper::toLiveMapWaypoint)
                .doOnNext { manager.showProgress(accountTypeIndex++, accountTypeSize) }
                .buffer(50)
                .map { createWaypointPack(it, wpIndex++) }
                .subscribe(
                        { ActionDisplayPoints.sendPackSilent(context, it, false) },
                        { manager.handleError(it) }
                )

    }

    private fun retrieveGeocaches(accountType: AccountType, boundingBox: BoundingBox): Flowable<Geocache> {
        val service = services[OkApiServiceType.from(accountType)]!!
        return service.liveMapGeocaches(boundingBox).observeOn(Schedulers.io())
    }

    private fun createWaypointPack(waypoints: MutableList<locus.api.objects.extra.Waypoint>, index: Int): PackWaypoints = PackWaypoints("OC_PACK_$index").apply {
        waypoints.forEach {
            addWaypoint(it)
        }
    }
}