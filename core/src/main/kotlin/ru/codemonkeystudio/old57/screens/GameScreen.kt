package ru.codemonkeystudio.old57.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.viewport.ExtendViewport
import ktx.ashley.entity
import ktx.assets.disposeSafely
import ktx.box2d.createWorld
import ktx.math.vec2
import ru.codemonkeystudio.kek.MyScreen
import ru.codemonkeystudio.kek.paperTransition___1_0
import ru.codemonkeystudio.old57.Box2dContactListener
import ru.codemonkeystudio.old57.Main
import ru.codemonkeystudio.old57.ecs.components.SpriteComponent
import ru.codemonkeystudio.old57.ecs.components.hit
import ru.codemonkeystudio.old57.ecs.entities.createPlatform
import ru.codemonkeystudio.old57.ecs.entities.enemies.dummy.createDummyEnemy
import ru.codemonkeystudio.old57.ecs.entities.player.createPlayer
import ru.codemonkeystudio.old57.ecs.systems.*

class GameScreen : MyScreen() {


    val camera = OrthographicCamera().apply {
        position.x = 0f
        position.y = 0f
        setToOrtho(false)
        update()
    }
    val viewport = ExtendViewport(1280f, 720f, camera)

    val world = createWorld(gravity = vec2(0f, -16.667f), allowSleep = true).apply {
        setContactListener(Box2dContactListener())
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, false)
        super.resize(width, height)
    }

    // TEST
    var playerEntity = Entity()
    // TEST

    init {
        createSystems()
        createEntities()
    }

    fun createSystems() {
        engine.apply {
            // Логика игроков
            addSystem(UpdatePlayerControllersSystem())
            addSystem(UpdateStateMachineSystem())
            // Движение
            addSystem(PlayerMoveSystem())
            addSystem(PlayerJumpSystem())

            // Атака
            addSystem(PlayerAttackSystem())

            // Обновление позиций
            addSystem(Box2dUpdateWorldSystem(world))
            addSystem(UpdateTransformByBox2dSystem(world))
            addSystem(UpdateHitHurtBoxColliders())

            // Обновление спрайтов
            addSystem(UpdateSpritePositionSystem())
            addSystem(UpdateAnimationsSystem())

            // Отрисовка игры
            addSystem(RenderSystem(spriteBatch, camera))
            addSystem(Box2dDebugRenderSystem(world, camera))
        }
    }
    fun createEntities() {
        engine.entity().apply {
            add(SpriteComponent().apply {
                sprite.setRegion(Texture("backdrop.png"))
                sprite.setSize(1280f, 720f)
            })
        }
        createPlatform(engine, world, position = vec2(640f, 100f), size = vec2(400f, 50f))
        createPlatform(engine, world, position = vec2(1040f, 150f), size = vec2(400f, 50f))
        createPlatform(engine, world, position = vec2(240f, 250f), size = vec2(400f, 50f))

        createDummyEnemy(engine, world, position = vec2(720f, 250f))

        playerEntity = createPlayer(engine, world, position = vec2(640f, 250f))
    }

    override fun render(delta: Float) {
        super.render(delta)
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
            playerEntity.hit!!.damage++
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
            context.inject<Main>().screenManager.pushScreen(GameScreen(), paperTransition___1_0)
        }
    }

    override fun dispose() {
        super.dispose()
        world.disposeSafely()
    }

}
