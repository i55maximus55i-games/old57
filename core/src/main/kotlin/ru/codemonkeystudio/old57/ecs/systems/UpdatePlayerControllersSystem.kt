package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.PlayerControllerComponent
import ru.codemonkeystudio.old57.ecs.components.playerController

class UpdatePlayerControllersSystem : IteratingSystem(allOf(PlayerControllerComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        // TODO: device attached to player polling
        val playerContoller = entity.playerController
        if (playerContoller != null) {
            playerContoller.move = 0f
            if (Gdx.input.isKeyPressed(Input.Keys.A)) playerContoller.move -= 1f
            if (Gdx.input.isKeyPressed(Input.Keys.D)) playerContoller.move += 1f
            playerContoller.move = MathUtils.clamp(playerContoller.move, -1f, 1f)

            playerContoller.run = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
            playerContoller.jump = Gdx.input.isKeyPressed(Input.Keys.SPACE)
        }
    }

}
