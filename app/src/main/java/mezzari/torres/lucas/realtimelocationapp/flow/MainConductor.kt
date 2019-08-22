package mezzari.torres.lucas.realtimelocationapp.flow

import android.content.Context
import android.content.Intent
import mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedFlowCycle
import mezzari.torres.lucas.realtimelocationapp.flow.access.AccessActivity
import mezzari.torres.lucas.realtimelocationapp.flow.map.MapsActivity
import mezzari.torres.lucas.realtimelocationapp.persistence.SessionManager
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 22/08/2019
 **/
object MainConductor: AnnotatedConductor() {
    var ipAddress: String = ""
    var username: String = ""

    override fun start() {
        super.start()
        this.ipAddress = SessionManager.ipAddress
        this.username = SessionManager.username
    }

    @ConductorAnnotation(AccessActivity::class, AnnotatedFlowCycle.STEP_INITIATED)
    private fun onAccessActivityInitiated(accessActivity: AccessActivity) {
        start()
        accessActivity.viewModel.apply {
            ipAddress.value = this@MainConductor.ipAddress
            username.value = this@MainConductor.username
        }
    }

    @ConductorAnnotation(AccessActivity::class, AnnotatedFlowCycle.NEXT)
    private fun onAccessActivityNext(accessActivity: AccessActivity) {
        SessionManager.ipAddress = ipAddress
        SessionManager.username = ipAddress
        accessActivity.startActivity(MapsActivity::class)
    }

    @ConductorAnnotation(MapsActivity::class, AnnotatedFlowCycle.STEP_INITIATED)
    private fun onMapsActivityInitiated(mapsActivity: MapsActivity) {
        mapsActivity.viewModel.apply {
            ipAddress = this@MainConductor.ipAddress
            username = this@MainConductor.username
        }
    }

    private fun Context.startActivity(activity: KClass<*>) {
        startActivity(Intent(this, activity.java))
    }
}