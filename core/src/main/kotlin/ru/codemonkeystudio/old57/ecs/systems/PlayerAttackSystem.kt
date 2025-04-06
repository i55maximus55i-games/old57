package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ktx.ashley.get
import ru.codemonkeystudio.old57.Main
import ru.codemonkeystudio.old57.ecs.components.*
import ru.codemonkeystudio.old57.ecs.entities.player.states.PunchState
import ru.codemonkeystudio.old57.screens.GameScreen

class PlayerAttackSystem : IteratingSystem(allOf(HurtSensorComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val hurtSensorComponent = entity.hurt

        if (hurtSensorComponent != null) {
            if (hurtSensorComponent.enabled) {
                for (i in engine.getEntitiesFor(allOf(HitSensorComponent::class).get())) {
                    if (i == entity) continue
                    if (i.hit!!.enabled && hurtSensorComponent.hurtBoxCollider.overlaps(i.hit!!.hitBoxCollider)) {
                        (Main.context.inject<Main>().screenManager.currentScreen as GameScreen).playerEntity.hit!!.damage++
                    }
                }
            }
        }
    }

}
