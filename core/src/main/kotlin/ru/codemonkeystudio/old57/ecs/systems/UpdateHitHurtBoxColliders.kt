package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.*

class UpdateHitHurtBoxColliders : EntitySystem() {

    val familyHit = allOf(HitSensorComponent::class, TransformComponent::class).get()
    val familyHurt = allOf(HurtSensorComponent::class, TransformComponent::class).get()

    override fun update(deltaTime: Float) {
        for (entity in engine.getEntitiesFor(familyHit)) {
            entity.hit!!.hitBoxCollider.setX(entity.transform!!.position.x - entity.hit!!.hitBoxCollider.width / 2f)
            entity.hit!!.hitBoxCollider.setY(entity.transform!!.position.y - entity.hit!!.hitBoxCollider.height / 2f)
        }
        for (entity in engine.getEntitiesFor(familyHurt)) {
            entity.hurt!!.hurtBoxCollider.setX(entity.transform!!.position.x - entity.hurt!!.hurtBoxCollider.width / 2f + entity.hurt!!.hurtBoxOffset.x)
            entity.hurt!!.hurtBoxCollider.setY(entity.transform!!.position.y - entity.hurt!!.hurtBoxCollider.height / 2f + entity.hurt!!.hurtBoxOffset.y)
        }
    }

}
