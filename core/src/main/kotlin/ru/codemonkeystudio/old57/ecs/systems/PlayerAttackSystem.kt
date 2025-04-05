package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ktx.ashley.get
import ru.codemonkeystudio.old57.ecs.components.*
import ru.codemonkeystudio.old57.ecs.entities.player.states.PunchState

class PlayerAttackSystem : IteratingSystem(allOf(PlayerControllerComponent::class, HurtSensorComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val playerController = entity.playerController
        val hurtSensorComponent = entity.hurt
        val state = entity.state

        if (playerController != null && hurtSensorComponent != null) {
            if (hurtSensorComponent.enabled && playerController.attack) {
                if (state != null) {
                    state.stateMachine.changeState(PunchState())
                }
                for (i in engine.getEntitiesFor(allOf(HitSensorComponent::class).get())) {
                    if (i == entity) continue
                    if (i[HitSensorComponent.mapper]!!.enabled && hurtSensorComponent.hurtBoxCollider.overlaps(i[HitSensorComponent.mapper]!!.hitBoxCollider)) {
                        i[HitSensorComponent.mapper]!!.damage += 1
                    }
                }
            }
        }
    }

}
