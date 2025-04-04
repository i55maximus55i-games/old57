package ru.codemonkeystudio.old57

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import ktx.ashley.has
import ru.codemonkeystudio.old57.ecs.components.GroundSensorComponent
import ru.codemonkeystudio.old57.ecs.components.JumpComponent
import ru.codemonkeystudio.old57.ecs.components.jump

class Box2dContactListener : ContactListener {

    override fun beginContact(contact: Contact) {
        val entityA = contact.fixtureA.userData as Entity
        val entityB = contact.fixtureB.userData as Entity

        if (entityA.has(GroundSensorComponent.mapper)) {
            if (entityB.has(JumpComponent.mapper)) {
                entityB.jump!!.onGround = true
                entityB.jump!!.jumpCounter = 0
            }
        }
    }
    override fun endContact(contact: Contact) {
        val entityA = contact.fixtureA.userData as Entity
        val entityB = contact.fixtureB.userData as Entity

        if (entityA.has(GroundSensorComponent.mapper)) {
            if (entityB.has(JumpComponent.mapper)) {
                entityB.jump!!.onGround = false
                entityB.jump!!.jumpCounter = 1
            }
        }
    }
    override fun preSolve(contact: Contact, oldManifold: Manifold) {}
    override fun postSolve(contact: Contact, impulse: ContactImpulse) {}

}
