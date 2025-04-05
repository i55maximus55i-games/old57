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

class PunchState : State<Entity> {

    var timer = 0f

    override fun enter(entity: Entity) {
        val animation = entity.animation
        val jump = entity.jump
        val move = entity.move
        val hurt = entity.hurt

        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                0.1f, gdxArrayOf(
                    TextureRegion(Texture("players/1/jab/3.png")),
                    TextureRegion(Texture("players/1/jab/4.png"))
                )
            )
        }

        if (jump != null) jump.enabled = false
        if (move != null) move.enabled = false
        if (hurt != null) hurt.enabled = false
    }
    override fun update(entity: Entity) {
        val state = entity.state
        timer += Gdx.graphics.deltaTime

        if (timer > 0.2f && state != null) {
            state.stateMachine.changeState(IdleState())
        }
    }

    override fun exit(entity: Entity) {}
    override fun onMessage(entity: Entity?, telegram: Telegram?): Boolean {
        return false
    }
}
