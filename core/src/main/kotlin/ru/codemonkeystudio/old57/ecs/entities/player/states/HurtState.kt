package ru.codemonkeystudio.old57.ecs.entities.player.states

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf
import ru.codemonkeystudio.old57.ecs.components.*

class HurtState : State<Entity> {

    var stunTimer = 0f

    override fun enter(entity: Entity) {
        val animation = entity.animation
        val jump = entity.jump
        val move = entity.move
        val hit = entity.hit
        val hurt = entity.hurt
        val health = entity.health

        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                1f, gdxArrayOf(
                    TextureRegion(Texture("players/1/fly/1.png"))
                )
            )
        }

        if (jump != null) jump.enabled = false
        if (move != null) move.enabled = false
        if (health != null && hit != null) {
            health.health -= hit.damage
        }
        if (hit != null) {
            hit.enabled = false
            hit.damage = 0
        }
        if (hurt != null) hurt.enabled = false

        stunTimer = 0f

    }

    override fun update(entity: Entity) {
        val state = entity.state
        val health = entity.health

        stunTimer += Gdx.graphics.deltaTime

        if (stunTimer > 0.7f && state != null) {
            if (health != null && health.health <= 0) {
                state.stateMachine.changeState(DeadState())
            } else {
                state.stateMachine.changeState(IdleState())
            }
        }
    }

    override fun exit(entity: Entity) {}
    override fun onMessage(entity: Entity?, telegram: Telegram): Boolean { return false }
}
