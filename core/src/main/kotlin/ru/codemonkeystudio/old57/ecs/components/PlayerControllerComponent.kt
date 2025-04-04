package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.optionalPropertyFor

class PlayerControllerComponent : Component {

    var move = 0f
    var run = false

    var up = false
    var down = false

    var jump = false
    var attack = false

}

var Entity.playerController by optionalPropertyFor<PlayerControllerComponent>()
