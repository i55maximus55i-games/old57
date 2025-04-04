package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ru.codemonkeystudio.old57.ecs.components.state

class UpdateStateMachineSystem : IteratingSystem(allOf().get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.state?.stateMachine?.update()
    }

}
