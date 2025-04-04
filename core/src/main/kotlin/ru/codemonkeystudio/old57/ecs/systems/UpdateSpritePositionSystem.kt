package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.TransformComponent
import ru.codemonkeystudio.old57.ecs.components.spriteComponent
import ru.codemonkeystudio.old57.ecs.components.transform

class UpdateSpritePositionSystem : IteratingSystem(allOf(TransformComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform
        val sprite = entity.spriteComponent

        if (sprite != null && transform != null) {
            sprite.sprite.setCenter(transform.position.x, transform.position.y + 10f)
            sprite.sprite.setFlip(transform.dir < 0f, false)
        }
    }

}
