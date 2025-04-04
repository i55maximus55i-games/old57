package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.optionalPropertyFor

class AnimationComponent : Component {

    lateinit var animation: Animation<TextureRegion>
    var timer = 0f

}

var Entity.animation by optionalPropertyFor<AnimationComponent>()

