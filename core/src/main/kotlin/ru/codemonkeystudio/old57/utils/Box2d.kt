package ru.codemonkeystudio.old57.utils

import com.badlogic.gdx.physics.box2d.World

val World.ppm: Float   // Pixel per Meter
    get() = 60f

val World.velocityIterations: Int
    get() = 10

val World.positionIterations: Int
    get() = 10

fun World.update(delta: Float) = this.step(delta, velocityIterations, positionIterations)
