package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor
import ktx.ashley.optionalPropertyFor

class HealthComponent : Component {
    companion object {
        val mapper = mapperFor<HealthComponent>()
    }

    var health = 5
}

var Entity.health by optionalPropertyFor<HealthComponent>()
