package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.optionalPropertyFor

class MoveComponent : Component {

    var enabled = false

    var ppm = 60f

    var targetSpeed = 0f

    var moveWalkSpeed = 250f / ppm
    var moveRunSpeed = 500f / ppm
    var maxAcceleration = 2000f / ppm

}

var Entity.move by optionalPropertyFor<MoveComponent>()
