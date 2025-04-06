package ru.codemonkeystudio.old57.ecs.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.entity
import ktx.assets.getAsset
import ktx.box2d.body
import ktx.box2d.box
import ktx.math.vec2
import ru.codemonkeystudio.old57.Main
import ru.codemonkeystudio.old57.ecs.components.Box2dBodyComponent
import ru.codemonkeystudio.old57.ecs.components.GroundSensorComponent
import ru.codemonkeystudio.old57.ecs.components.SpriteComponent
import ru.codemonkeystudio.old57.utils.ppm

fun createPlatform(engine: PooledEngine, world: World, position: Vector2, size: Vector2): Entity {
    val platformEntity = engine.entity()
    platformEntity.apply {
        add(Box2dBodyComponent().apply {
            body = world.body {
                type = BodyDef.BodyType.StaticBody
                this.position.set(position).scl(1f / world.ppm)

                box(width = size.x / world.ppm, height = size.y / world.ppm) {
                    friction = 0f
                    userData = platformEntity
                }
            }
        })
//        add(SpriteComponent().apply {
//            sprite.apply {
//                texture = Main.context.inject<AssetManager>().getAsset<Texture>("1.png")
//                setRegion(texture)
//                setSize(size.x, size.y)
//                setCenter(position.x, position.y)
//            }
//        })
    }
    val groundSensorEntity = engine.entity()
    groundSensorEntity.apply {
        add(Box2dBodyComponent().apply {
            body = world.body {
                type = BodyDef.BodyType.StaticBody
                this.position.set(position).scl(1f / world.ppm)

                box(width = (size.x - 1f) / world.ppm, height = 1f / world.ppm, position = vec2(0f, size.y / 2f / world.ppm)) {
                    isSensor = true
                    userData = groundSensorEntity
                }
            }
        })
        add(GroundSensorComponent())
    }
    return platformEntity
}
