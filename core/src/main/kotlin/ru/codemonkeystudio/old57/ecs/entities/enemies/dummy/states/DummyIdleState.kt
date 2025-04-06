package ru.codemonkeystudio.old57.ecs.entities.enemies.dummy.states

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf
import ru.codemonkeystudio.old57.ecs.components.*
import ru.codemonkeystudio.old57.utils.ppm

class DummyIdleState : State<Entity> {

    override fun enter(entity: Entity) {
        val animation = entity.animation
        val hit = entity.hit
        val move = entity.move

        val texture = Texture("enemy.png")
        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                0.4f, gdxArrayOf(
                    TextureRegion(texture, 0, 0, 60, 60),
                    TextureRegion(texture, 60, 0, 60, 60),
                ), Animation.PlayMode.LOOP
            )
        }

        if (move != null) move.enabled = true
    }

    override fun update(entity: Entity) {
        val hit = entity.hit
        val state = entity.state
        val border = entity.border
        val body = entity.box2d

        if (border != null && body != null) {
            if (border.dir) {
                if (border.right < body.body.position.x * body.body.world.ppm) border.dir = false
            } else {
                if (border.left > body.body.position.x * body.body.world.ppm) border.dir = true
            }
        }
    }

    override fun exit(entity: Entity) {}
    override fun onMessage(entity: Entity, telegram: Telegram): Boolean {
        return false
    }

}
