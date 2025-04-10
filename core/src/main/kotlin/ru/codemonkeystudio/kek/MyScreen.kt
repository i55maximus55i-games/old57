package ru.codemonkeystudio.kek

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import de.eskalon.commons.screen.ManagedScreen
import ktx.actors.stage
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ru.codemonkeystudio.old57.Main

open class MyScreen : ManagedScreen() {

    val context = Main.context

    val spriteBatch: SpriteBatch = context.inject()
    val stage = stage(viewport = ScreenViewport()).apply {
        addInputProcessor(this)
    }

    val engine = PooledEngine()

    override fun render(delta: Float) {
        engine.update(delta)
        stage.apply {
            act(delta)
            draw()
        }
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.disposeSafely()
        for (entity in engine.entities) {
            for (component in entity.components) {
                if (component is Disposable) {
                    component.disposeSafely()
                }
            }
        }
        for (system in engine.systems) {
            if (system is Disposable) {
                system.disposeSafely()
            }
        }
    }

}
