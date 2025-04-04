package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.optionalPropertyFor
import ktx.math.vec2

class TransformComponent : Component {

    val position = vec2()
    var dir = 1f

}

var Entity.transform by optionalPropertyFor<TransformComponent>()
