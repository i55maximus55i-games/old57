package ru.codemonkeystudio.old57.ecs.entities.player.states

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import ktx.collections.gdxArrayOf
import ru.codemonkeystudio.old57.Main
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
        val box2d = entity.box2d

        val texture = Texture("player.png")
        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                0.08f, gdxArrayOf(
                    TextureRegion(texture, 0, 480, 60, 120)
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
        if (box2d != null) {
            box2d.body.setLinearVelocity(MathUtils.random(-6f, 6f), MathUtils.random(10f, 4f))
        }

        stunTimer = 0f

        Main.hurtsound.play()

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
