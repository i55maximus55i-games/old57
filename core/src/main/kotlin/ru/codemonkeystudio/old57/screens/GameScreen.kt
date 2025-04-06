package ru.codemonkeystudio.old57.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.assets.disposeSafely
import ktx.box2d.createWorld
import ktx.math.vec2
import ktx.scene2d.actors
import ktx.scene2d.image
import ktx.scene2d.table
import ktx.tiled.layer
import ktx.tiled.property
import ktx.tiled.x
import ktx.tiled.y
import ru.codemonkeystudio.kek.MyScreen
import ru.codemonkeystudio.kek.paperTransition___2_0
import ru.codemonkeystudio.old57.Box2dContactListener
import ru.codemonkeystudio.old57.Main
import ru.codemonkeystudio.old57.ecs.components.border
import ru.codemonkeystudio.old57.ecs.components.health
import ru.codemonkeystudio.old57.ecs.components.hit
import ru.codemonkeystudio.old57.ecs.components.transform
import ru.codemonkeystudio.old57.ecs.entities.createPlatform
import ru.codemonkeystudio.old57.ecs.entities.enemies.dummy.createDummyEnemy
import ru.codemonkeystudio.old57.ecs.entities.player.createPlayer
import ru.codemonkeystudio.old57.ecs.systems.*
import ru.codemonkeystudio.old57.ecs.systems.UpdateUiSystem

class GameScreen : MyScreen() {


    val camera = OrthographicCamera().apply {
        position.x = 0f
        position.y = 0f
        setToOrtho(false)
        update()
    }
    val viewport = FitViewport(1280f, 720f, camera)

    val world = createWorld(gravity = vec2(0f, -16.667f), allowSleep = true).apply {
        setContactListener(Box2dContactListener())
    }
//    val map = TmxMapLoader().load("map/map${MathUtils.random(1, 9)}.tmx")
    val map = TmxMapLoader().load("map/map8.tmx")

    var hearts = ArrayList<Image>()

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, false)
        super.resize(width, height)
    }

    // TEST
    var playerEntity = Entity()
    // TEST

    init {
        createEntities()
        createSystems()

        stage.actors {
            table {
                setFillParent(true)

                top()
                left()
                for (i in 0 until 5) {
                    hearts.add(
                        image(Texture("heart.png"))
                    )
                }
            }
        }
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
            addSystem(FollowCameraSystem(camera, playerEntity.transform!!, viewport))

            // Обновление спрайтов
            addSystem(UpdateSpritePositionSystem())
            addSystem(UpdateAnimationsSystem())
            addSystem(FlipMovingSpriteSystem())

            // Отрисовка игры
            addSystem(UpdateUiSystem(this@GameScreen))
            addSystem(MapRenderSystem(map, context.inject(), camera))
            addSystem(RenderSystem(spriteBatch, camera))
//            addSystem(Box2dDebugRenderSystem(world, camera))
        }
    }

    fun createEntities() {
        var ass = 0
        for (y in 0 until (map.layer("tiles") as TiledMapTileLayer).height) {
            for (x in 0 until (map.layer("tiles") as TiledMapTileLayer).width) {
                (map.layer("tiles") as TiledMapTileLayer).getCell(x, y).apply {
                    if (this != null && tile != null) {
                        ass++
                    } else {
                        if (ass > 0) createPlatform(
                            engine,
                            world,
                            position = vec2(60f * x - ass * 30f, 60f * y + 30f),
                            size = vec2(60f * ass, 60f)
                        )
                        ass = 0
                    }
                }
            }
        }

        for (i in map.layer("enemies").objects) {
            createDummyEnemy(engine, world, position = vec2(i.x, i.y)).apply {
                border!!.left = i.properties.get("left", Float::class.java)
                border!!.right = i.properties.get("right", Float::class.java)
            }
        }
        for (i in map.layer("players").objects) {
            playerEntity = createPlayer(engine, world, position = vec2(i.x, i.y))
        }
    }

    var deadTimer = 0f
    var newScreen: GameScreen? = null
    override fun render(delta: Float) {
        super.render(delta)
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT_BRACKET)) {
            playerEntity.hit!!.damage++
        }
        if (playerEntity.transform!!.position.y < -100f) playerEntity.hit!!.damage = 10
        if (newScreen != null) deadTimer+= delta
        if (playerEntity.health!!.health <= 0 && newScreen == null) {
            newScreen = GameScreen()
        }
        if (deadTimer > 1.5f) {
            deadTimer = -9999999f
            context.inject<Main>().screenManager.pushScreen(newScreen, paperTransition___2_0)
        }
        if (playerEntity.transform!!.position.x > 3440f) playerEntity.hit!!.damage = 10
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET)) {
            newScreen = GameScreen()
        }
    }

    override fun dispose() {
        super.dispose()
        world.disposeSafely()
    }

}
