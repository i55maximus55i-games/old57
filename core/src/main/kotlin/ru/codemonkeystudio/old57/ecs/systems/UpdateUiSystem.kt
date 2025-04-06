package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import ru.codemonkeystudio.old57.ecs.components.health
import ru.codemonkeystudio.old57.screens.GameScreen

class UpdateUiSystem(val gameScreen: GameScreen) : EntitySystem() {

    override fun update(deltaTime: Float) {
        for (i in 0 until 5) {
            gameScreen.hearts[i].isVisible = gameScreen.playerEntity.health!!.health > i
        }
    }
}
