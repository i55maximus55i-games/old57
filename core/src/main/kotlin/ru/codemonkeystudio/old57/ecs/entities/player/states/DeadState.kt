package ru.codemonkeystudio.old57.ecs.entities.player.states

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf
import ru.codemonkeystudio.old57.ecs.components.*

class DeadState : State<Entity> {
    override fun enter(entity: Entity) {
        val animation = entity.animation
        val jump = entity.jump
        val move = entity.move
        val hit = entity.hit
        val health = entity.health
        val box2d = entity.box2d

        val texture = Texture("player.png")
        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                0.35f, gdxArrayOf(
                    TextureRegion(texture, 0, 600, 120, 120),
                    TextureRegion(texture, 120, 600, 120, 120),
                    TextureRegion(texture, 240, 600, 120, 120),
                    TextureRegion(texture, 360, 600, 120, 120),
                )
            )
        }

        if (jump != null) jump.enabled = false
        if (move != null) move.enabled = false
        if (box2d != null) {
            box2d.body.setLinearVelocity(0f, box2d.body.linearVelocity.y)
        }
    }

    override fun update(entity: Entity) {}

    override fun exit(entity: Entity?) {}
    override fun onMessage(entity: Entity?, telegram: Telegram?): Boolean {
        return false
    }
}
