package ru.codemonkeystudio.old57.ecs.entities.player.states

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf
import ru.codemonkeystudio.old57.ecs.components.*

class JumpState : State<Entity> {

    var jumpCount = 0

    override fun enter(entity: Entity) {
        val animation = entity.animation
        val jump = entity.jump
        val hurt = entity.hurt
        val move = entity.move

        val texture = Texture("player.png")
        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                0.08f, gdxArrayOf(
                    TextureRegion(texture, 0, 360, 60, 120),
                    TextureRegion(texture, 60, 360, 60, 120),
                    TextureRegion(texture, 120, 360, 60, 120),
                )
            )
        }

        if (jump != null) {
            jump.enabled = true
            jumpCount = jump.jumpCounter
        }
        if (move != null) move.enabled = true
        if (hurt != null) hurt.enabled = true
    }

    override fun update(entity: Entity) {
        val animation = entity.animation
        val jump = entity.jump
        val state = entity.state
        val hit = entity.hit

        if (animation != null && jump != null) {
            if (jump.jumpCounter != jumpCount) {
                jumpCount = jump.jumpCounter
                animation.timer = 0f
            }
        }
        if (state != null) {
            if (jump != null) {
                if (jump.onGround) {
                    state.stateMachine.changeState(IdleState())
                }
            }
            if (hit != null) {
                if (hit.damage > 0) {
                    state.stateMachine.changeState(HurtState())
                }
            }
        }
    }

    override fun exit(entity: Entity) {}
    override fun onMessage(entity: Entity, telegram: Telegram): Boolean {
        return false
    }
}
