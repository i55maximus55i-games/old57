package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.*

class PlayerJumpSystem : IteratingSystem(allOf(PlayerControllerComponent::class, JumpComponent::class, Box2dBodyComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val jump = entity.jump
        val playerController = entity.playerController
        val box2d = entity.box2d

        if (playerController != null && jump != null && box2d != null) {
            if (jump.enabled) {
                if (playerController.jump) {
                    if (!jump.isJumping && jump.jumpCounter < jump.maxJumpCount) {
                        val velocity = box2d.body.linearVelocity
                        velocity.y = 700f / 60f
                        box2d.body.linearVelocity = velocity

                        jump.isJumping = true
                        jump.jumpCounter++
                    }
                } else {
                    if (jump.isJumping) {
                        val velocity = box2d.body.linearVelocity
                        if (velocity.y > 0) velocity.y = 10f / 60f
                        box2d.body.linearVelocity = velocity
                        jump.isJumping = false
                    }
                }
            }
        }
    }

}
