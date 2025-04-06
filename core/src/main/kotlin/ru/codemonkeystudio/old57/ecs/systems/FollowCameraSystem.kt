package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ru.codemonkeystudio.old57.ecs.components.TransformComponent

class FollowCameraSystem(val camera: OrthographicCamera, val target: TransformComponent, val viewport: FitViewport) : EntitySystem() {

    override fun update(deltaTime: Float) {
        camera.apply {
            position.x = target.position.x
            if (position.x - viewport.worldWidth / 2f < 0f) position.x = viewport.worldWidth / 2f
            if (position.x + viewport.worldWidth / 2f > 3506f) position.x = 3506f - viewport.worldWidth / 2f
            position.y = target.position.y
            if (position.y - viewport.worldHeight / 2f < 0f) position.y = viewport.worldHeight / 2f
            if (position.y + viewport.worldHeight / 2f > 2480f) position.y = 2480f - viewport.worldHeight / 2f
            update()
        }
    }

}
