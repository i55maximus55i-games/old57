package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ktx.ashley.get
import ru.codemonkeystudio.old57.ecs.components.*

class PlayerAttackSystem : IteratingSystem(allOf(PlayerControllerComponent::class, HurtSensorComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val playerController = entity.playerController
        val hurtSensorComponent = entity.hurt

        if (playerController != null && hurtSensorComponent != null) {
            if (playerController.attack) {
                for (i in engine.getEntitiesFor(allOf(HitSensorComponent::class).get())) {
                    if (i == entity) continue
                    if (i[HitSensorComponent.mapper]!!.enabled && hurtSensorComponent.hurtBoxCollider.overlaps(i[HitSensorComponent.mapper]!!.hitBoxCollider)) {
                        println("Attacked")
                    }
                }
            }
        }
    }

}
