package ru.codemonkeystudio.old57.ecs.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

class MapRenderSystem(val tiledMap: TiledMap, val batch: SpriteBatch, val camera: OrthographicCamera) : EntitySystem() {

    val mapRenderer = OrthogonalTiledMapRenderer(tiledMap, batch)

    override fun update(deltaTime: Float) {
        mapRenderer.setView(camera)
        mapRenderer.render()
    }

}
