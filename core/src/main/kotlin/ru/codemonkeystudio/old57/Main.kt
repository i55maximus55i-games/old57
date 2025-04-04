package ru.codemonkeystudio.old57

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.eskalon.commons.core.ManagedGame
import de.eskalon.commons.screen.transition.ScreenTransition
import de.eskalon.commons.screen.transition.impl.GLTransitionsShaderTransition
import de.eskalon.commons.screen.transition.impl.VerticalSlicingTransition
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.load
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.graphics.use
import ktx.inject.Context
import ktx.inject.register
import ru.codemonkeystudio.kek.MyScreen
import ru.codemonkeystudio.kek.paperTransitionShader
import ru.codemonkeystudio.old57.screens.GameScreen
import ru.codemonkeystudio.service.MyService

class Main(val service: MyService = MyService()) : ManagedGame<MyScreen, ScreenTransition>() {

    companion object {
        val context = Context()

//        inline fun <reified T : KtxScreen> setScreen() {
//            context.inject<Main>().setScreen<T>()
//        }
    }

    override fun create() {
        super.create()

        context.register {
            bindSingleton(this@Main)
            bindSingleton(SpriteBatch())
            bindSingleton(service)
            bindSingleton(AssetManager())
        }

        KtxAsync.initiate()
        context.inject<AssetManager>().apply {
            load<Texture>("1.png")
            finishLoading()
        }


        screenManager.pushScreen(GameScreen(), GLTransitionsShaderTransition(paperTransitionShader, 4f))

//        addScreen(FirstScreen())
//        addScreen(GameScreen())
//        setScreen<GameScreen>()
    }

    var disposed = false
    override fun dispose() {
        if (!disposed) {
            disposed = true
            context.disposeSafely()
            super.dispose()
        }
    }

}

class FirstScreen : KtxScreen {
    private val image = Texture("logo.png".toInternalFile(), true).apply { setFilter(Linear, Linear) }
    private val batch = SpriteBatch()

    override fun render(delta: Float) {
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        batch.use {
            it.draw(image, 100f, 160f)
        }
    }

    override fun dispose() {
        image.disposeSafely()
        batch.disposeSafely()
    }
}
