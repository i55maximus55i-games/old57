package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import ru.codemonkeystudio.old57.ecs.components.TransformComponent

class FollowCameraSystem(val camera: OrthographicCamera, val target: TransformComponent) : EntitySystem() {

    override fun update(deltaTime: Float) {
        camera.apply {
            position.x = target.position.x
            position.y = target.position.y
            update()
        }
    }

}
