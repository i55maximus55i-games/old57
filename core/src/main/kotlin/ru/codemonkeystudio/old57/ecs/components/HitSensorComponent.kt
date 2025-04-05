package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.mapperFor
import ktx.ashley.optionalPropertyFor

class HitSensorComponent : Component {
    companion object {
        val mapper = mapperFor<HitSensorComponent>()
    }

    var enabled = true
    var damage = 0

    var hitBoxCollider = Rectangle()

}

var Entity.hit by optionalPropertyFor<HitSensorComponent>()
