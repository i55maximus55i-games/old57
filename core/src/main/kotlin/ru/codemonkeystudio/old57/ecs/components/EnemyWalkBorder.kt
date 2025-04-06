package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor
import ktx.ashley.optionalPropertyFor

class EnemyWalkBorder : Component {
    companion object {
        val mapper = mapperFor<EnemyWalkBorder>()
    }

    var left = 0f
    var right = 0f
    var dir = true

}

var Entity.border by optionalPropertyFor<EnemyWalkBorder>()
