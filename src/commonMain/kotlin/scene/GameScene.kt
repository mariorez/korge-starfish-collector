package scene

import com.github.quillraven.fleks.World
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.addUpdater
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import component.RenderComponent
import component.TransformComponent
import system.RenderingSystem

class GameScene : Scene() {

    override suspend fun Container.sceneMain() {

        val world = World {
            component(::TransformComponent)
            component(::RenderComponent)

            system(::RenderingSystem)

            inject("layer0", this@sceneMain)
        }

        world.apply {
            entity {
                add<TransformComponent> {
                    position = Point(100)
                }
                add<RenderComponent> {
                    sprite = Sprite(resourcesVfs["turtle.png"].readBitmap())
                }
            }
        }

        addUpdater { deltaTime ->
            world.update(deltaTime.seconds.toFloat())
        }
    }
}