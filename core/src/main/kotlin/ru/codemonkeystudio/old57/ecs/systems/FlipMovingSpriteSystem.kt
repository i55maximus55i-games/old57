package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.MoveComponent
import ru.codemonkeystudio.old57.ecs.components.SpriteComponent
import ru.codemonkeystudio.old57.ecs.components.move
import ru.codemonkeystudio.old57.ecs.components.spriteComponent

class FlipMovingSpriteSystem : IteratingSystem(allOf(SpriteComponent::class, MoveComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val move = entity.move
        val sprite = entity.spriteComponent

        if (move != null && sprite != null) {
            sprite.sprite.setFlip(!move.direction, false)
        }
    }

}
