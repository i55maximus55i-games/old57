package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.*
import kotlin.math.abs

class PlayerMoveSystem : IteratingSystem(allOf(Box2dBodyComponent::class, MoveComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val box2d = entity.box2d
        val move = entity.move
        val playerController = entity.playerController
        val enemyWalkBorder = entity.border

        if (box2d != null && move != null) {
            if (move.enabled) {
                move.targetSpeed = 0f
                if (playerController != null) {
                    move.targetSpeed = playerController.move * if (playerController.run) move.moveRunSpeed else move.moveWalkSpeed
                }
                if (enemyWalkBorder != null) {
                    move.targetSpeed = move.moveWalkSpeed * if(enemyWalkBorder.dir) 1f else -1f
                }

                val dt = if (deltaTime < MathUtils.FLOAT_ROUNDING_ERROR) 0.25f else deltaTime
                val mass = box2d.body.mass
                var acceleration = (move.targetSpeed - box2d.body.linearVelocity.x) / dt
                acceleration = MathUtils.clamp(acceleration, -move.maxAcceleration, move.maxAcceleration)
                box2d.body.applyForceToCenter(acceleration * mass, 0f, true)
            }
            if (abs(box2d.body.linearVelocity.x) > 0.1f) move.direction = box2d.body.linearVelocity.x > 0f
        }
    }

}

