package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Body
import ktx.ashley.optionalPropertyFor

class Box2dBodyComponent : Component {

    lateinit var body: Body

}

var Entity.box2d by optionalPropertyFor<Box2dBodyComponent>()
