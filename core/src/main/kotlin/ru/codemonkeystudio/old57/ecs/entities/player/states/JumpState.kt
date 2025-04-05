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
    override fun enter(entity: Entity) {
        val animation = entity.animation
        val jump = entity.jump
        val move = entity.move

        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                0.12f, gdxArrayOf(
                    TextureRegion(Texture("players/1/jump/1.png")),
                    TextureRegion(Texture("players/1/jump/2.png"))
                )
            )
        }
    }

    override fun update(entity: Entity) {
        val jump = entity.jump
        val state = entity.state
        val hit = entity.hit

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
