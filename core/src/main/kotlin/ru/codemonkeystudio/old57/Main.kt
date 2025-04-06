package ru.codemonkeystudio.old57

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.kotcrab.vis.ui.VisUI
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
import ktx.scene2d.Scene2DSkin
import ru.codemonkeystudio.kek.MyScreen
import ru.codemonkeystudio.kek.paperTransitionShader
import ru.codemonkeystudio.kek.paperTransition___1_0
import ru.codemonkeystudio.old57.screens.GameScreen
import ru.codemonkeystudio.service.MyService

class Main(val service: MyService = MyService()) : ManagedGame<MyScreen, ScreenTransition>() {

    companion object {
        val context = Context()

        lateinit var hurtsound: Sound
        lateinit var music: Music

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

        VisUI.load("x1/uiskin.json")
        Scene2DSkin.defaultSkin = VisUI.getSkin()

        context.inject<AssetManager>().apply {
            load<Texture>("1.png")
            finishLoading()
        }

        hurtsound = Gdx.audio.newSound("hurt.mp3".toInternalFile())
        music = Gdx.audio.newMusic("music.mp3".toInternalFile())
        music.apply {
            isLooping = true
            volume = 0.4f
            play()
        }


        screenManager.pushScreen(GameScreen(), paperTransition___1_0)

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
