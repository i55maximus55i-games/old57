package ru.codemonkeystudio.old57.ecs.entities.enemies.dummy

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.entity
import ktx.box2d.body
import ktx.box2d.box
import ru.codemonkeystudio.old57.ecs.components.*
import ru.codemonkeystudio.old57.ecs.entities.enemies.dummy.states.DummyIdleState
import ru.codemonkeystudio.old57.utils.ppm

fun createDummyEnemy(engine: PooledEngine, world: World, position: Vector2): Entity {
    val entity = engine.entity()
    entity.apply {
        add(Box2dBodyComponent().apply {
            body = world.body {
                type = BodyDef.BodyType.DynamicBody
                this.position.set(position.x + 60f / 2f, position.y + 60f / 2f).scl(1f / world.ppm)
                fixedRotation = true

                box(width = 60f / world.ppm, height = 60f / world.ppm) {
                    friction = 0.3f
                    userData = entity
                }
            }
        })
        add(TransformComponent())
        add(MoveComponent().apply {
            moveWalkSpeed = 150f / world.ppm
        })

        add(AnimationComponent())
        add(SpriteComponent().apply {
            sprite.setSize(80f, 80f)
        })

        add(EnemyWalkBorder().apply {
            left = 750f
            right = 1000f
        })
        add(StateComponent().apply {
            stateMachine.owner = entity
            stateMachine.changeState(DummyIdleState())
        })

        add(HurtSensorComponent())
        add(HitSensorComponent().apply {
            hitBoxCollider.setSize(70f, 90f)
        })
    }
    return entity
}
