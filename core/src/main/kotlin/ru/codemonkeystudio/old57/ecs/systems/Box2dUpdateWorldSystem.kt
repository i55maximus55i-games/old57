package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.World
import ru.codemonkeystudio.old57.utils.update

class Box2dUpdateWorldSystem(val world: World) : EntitySystem() {

    override fun update(deltaTime: Float) {
        world.update(deltaTime)
    }

}
