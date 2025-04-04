package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import ktx.ashley.optionalPropertyFor

class SpriteComponent : Component {

    var sprite = Sprite()

}

var Entity.spriteComponent by optionalPropertyFor<SpriteComponent>()
