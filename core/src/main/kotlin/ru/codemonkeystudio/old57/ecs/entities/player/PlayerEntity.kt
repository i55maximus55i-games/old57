package ru.codemonkeystudio.old57.ecs.entities.player

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box
import ru.codemonkeystudio.old57.ecs.components.*
import ru.codemonkeystudio.old57.ecs.entities.player.states.IdleState
import ru.codemonkeystudio.old57.utils.ppm

fun createPlayer(engine: PooledEngine, world: World, position: Vector2) {
    val entity = engine.entity()
    entity.apply {
        add(Box2dBodyComponent().apply {
            body = world.body {
                type = BodyDef.BodyType.DynamicBody
                this.position.set(position.x + 40f / 2f, position.y + 60f / 2f).scl(1f / world.ppm)
                fixedRotation = true

                box(width = 40f / world.ppm, height = 60f / world.ppm) {
                    friction = 0.3f
                    userData = entity
                }
            }
        })
        add(TransformComponent())

        add(AnimationComponent())
        add(SpriteComponent().apply {
            sprite.setSize(80f, 80f)
        })

        add(PlayerControllerComponent())

//        add(JumpComponent())
        add(MoveComponent())
        add(StateComponent().apply {
            stateMachine.owner = entity
            stateMachine.changeState(IdleState())
        })
    }
}
