package ru.codemonkeystudio.old57.ecs.entities.player.states

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf
import ru.codemonkeystudio.old57.ecs.components.*
import kotlin.math.abs

class IdleState : State<Entity> {

    override fun enter(entity: Entity) {
        val animation = entity.animation
        val jump = entity.jump
        val move = entity.move

        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                1f, gdxArrayOf(
                    TextureRegion(Texture("players/1/idle/1.png"))
                )
            )
        }
        if (jump != null) jump.enabled = true
        if (move != null) move.enabled = true
    }

    override fun update(entity: Entity) {
        val box2d = entity.box2d
        val state = entity.state
        val jump = entity.jump
        val hit = entity.hit

        if (box2d != null && state != null) {
            if (abs(box2d.body.linearVelocity.x) > 0.1f) {
                state.stateMachine.changeState(WalkState())
            }
            if (jump != null) {
                if (jump.jumpCounter > 0) {
                    state.stateMachine.changeState(JumpState())
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
