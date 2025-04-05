package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.mapperFor
import ktx.ashley.optionalPropertyFor
import ktx.math.vec2

class HurtSensorComponent : Component {
    companion object {
        val mapper = mapperFor<GroundSensorComponent>()
    }

    var enabled = false
    var hurtValue = 1
    var hurtBoxOffset = vec2()
    var hurtBoxCollider = Rectangle()

}

var Entity.hurt by optionalPropertyFor<HurtSensorComponent>()
