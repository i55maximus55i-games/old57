package ru.codemonkeystudio.old57.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.fsm.DefaultStateMachine
import com.badlogic.gdx.ai.fsm.State
import ktx.ashley.optionalPropertyFor

class StateComponent : Component {

    var stateMachine = DefaultStateMachine<Entity, State<Entity>>()

}

var Entity.state by optionalPropertyFor<StateComponent>()
