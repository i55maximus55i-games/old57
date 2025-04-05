package ru.codemonkeystudio.old57.ecs.entities.enemies.dummy.states

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxArrayOf
import ru.codemonkeystudio.old57.ecs.components.animation
import ru.codemonkeystudio.old57.ecs.components.hit

class DummyHurtState : State<Entity> {

    override fun enter(entity: Entity) {
        val animation = entity.animation
        val hit = entity.hit

        if (animation != null) {
            animation.timer = 0f
            animation.animation = Animation<TextureRegion>(
                1f, gdxArrayOf(
                    TextureRegion(Texture("players/2/fly/1.png"))
                )
            )
        }
        if (hit != null) {
            hit.enabled = false
            hit.damage = 0
        }
    }

    override fun update(entity: Entity) {
    }

    override fun exit(entity: Entity?) {}
    override fun onMessage(entity: Entity?, telegram: Telegram?): Boolean {
        return false
    }
}
