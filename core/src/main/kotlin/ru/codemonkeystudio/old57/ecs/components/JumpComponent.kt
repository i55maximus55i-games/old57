package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.mapperFor
import ktx.ashley.optionalPropertyFor

class JumpComponent : Component {

    companion object {
        val mapper = mapperFor<JumpComponent>()
    }

    var enabled = false
    var onGround = false
    var isJumping = false

    var jumpCounter = 0
    var maxJumpCount = 2

}

var Entity.jump by optionalPropertyFor<JumpComponent>()

