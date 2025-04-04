package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Disposable
import ktx.assets.disposeSafely
import ru.codemonkeystudio.old57.utils.ppm

class Box2dDebugRenderSystem(var world: World, var camera: OrthographicCamera) : EntitySystem(), Disposable {

    val debugRenderer = Box2DDebugRenderer(true, true, false, true, true, true)

    override fun update(deltaTime: Float) {
        camera.position.x /= world.ppm
        camera.position.y /= world.ppm
        camera.zoom /= world.ppm
        camera.update()

        debugRenderer.render(world, camera.combined)

        camera.position.x *= world.ppm
        camera.position.y *= world.ppm
        camera.zoom *= world.ppm
        camera.update()
    }

    override fun dispose() {
        debugRenderer.disposeSafely()
    }

}
