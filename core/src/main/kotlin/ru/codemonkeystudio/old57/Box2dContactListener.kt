package ru.codemonkeystudio.old57

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import ktx.ashley.has

class Box2dContactListener : ContactListener {

    override fun beginContact(contact: Contact) {
        val entityA = contact.fixtureA.userData as Entity
        val entityB = contact.fixtureB.userData as Entity

//        if (entityA.has(GroundSensorComponent.mapper)) {
//            if (entityB.has(JumpComponent.mapper)) {
//                entityB.jump!!.onGround = true
//                entityB.jump!!.jumpCounter = 0
//            }
//        }
//        if (entityA.has(WallSlideSensorColliderComponent.mapper) && entityB.has(WallSlideComponent.mapper)) {
//            entityB.wallSlide?.apply {
//                onWall = true
//                dir = entityA.wallSlideSensor?.dir ?: 0f
//            }
//        }
    }
    override fun endContact(contact: Contact) {
        val entityA = contact.fixtureA.userData as Entity
        val entityB = contact.fixtureB.userData as Entity

//        if (entityA.has(GroundSensorComponent.mapper)) {
//            if (entityB.has(JumpComponent.mapper)) {
//                entityB.jump!!.onGround = false
//                entityB.jump!!.jumpCounter = 1
//            }
//        }
//        if (entityA.has(WallSlideSensorColliderComponent.mapper) && entityB.has(WallSlideComponent.mapper)) {
//            entityB.wallSlide?.apply {
//                onWall = false
//            }
//        }
    }
    override fun preSolve(contact: Contact, oldManifold: Manifold) {}
    override fun postSolve(contact: Contact, impulse: ContactImpulse) {}

}
