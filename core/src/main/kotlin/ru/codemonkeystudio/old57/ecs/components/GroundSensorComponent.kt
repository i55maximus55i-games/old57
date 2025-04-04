package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class GroundSensorComponent : Component {
    companion object {
        val mapper = mapperFor<GroundSensorComponent>()
    }
}
