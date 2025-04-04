package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.Box2dBodyComponent
import ru.codemonkeystudio.old57.ecs.components.TransformComponent
import ru.codemonkeystudio.old57.ecs.components.box2d
import ru.codemonkeystudio.old57.ecs.components.transform
import ru.codemonkeystudio.old57.utils.ppm

class UpdateTransformByBox2dSystem(val world: World) : IteratingSystem(allOf(Box2dBodyComponent::class, TransformComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val box2d = entity.box2d

        if (box2d != null && transform != null) {
            transform.position.set(box2d.body.position).scl(world.ppm)
            if (box2d.body.linearVelocity.x > 0.1f) transform.dir = 1f
            if (box2d.body.linearVelocity.x < -0.1f) transform.dir = -1f
        }
    }

}
