package scene

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.World
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera
import com.soywiz.korge.view.image
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.SizeInt
import component.InputComponent
import component.PlayerComponent
import component.RenderComponent
import component.TransformComponent
import readBitmap
import system.CameraSystem
import system.InputSystem
import system.MovementSystem
import system.RenderingSystem

class GameScene : Scene() {

    override suspend fun Container.sceneMain() {

        lateinit var turtle: Entity
        val turtleMove: InputComponent by lazy { Inject.componentMapper<InputComponent>()[turtle] }

        val tileMap = resourcesVfs["map.tmx"].readTiledMap()
        val atlas: Atlas = resourcesVfs["starfish-collector.atlas"].readAtlas()

        val camera = camera {
            tiledMapView(tileMap) {
                sendChildToBack(image(tiledMap.imageLayers[0].image!!.readBitmap()))
            }
        }

        val world = World {
            inject("camera", camera)
            inject("worldSize", SizeInt(tileMap.pixelWidth, tileMap.pixelHeight))

            component(::PlayerComponent)
            component(::InputComponent)
            component(::TransformComponent)
            component(::RenderComponent)

            system(::InputSystem)
            system(::MovementSystem)
            system(::CameraSystem)
            system(::RenderingSystem)
        }.apply {
            turtle = entity {
                add<PlayerComponent>()
                add<InputComponent>()
                add<TransformComponent> {
                    position.x = 100.0
                    position.y = 100.0
                    acceleration = 400.0
                    deceleration = 400.0
                    maxSpeed = 150.0
                }
                add<RenderComponent> {
                    sprite = Sprite(
                        SpriteAnimation(
                            spriteMap = atlas["turtle"],
                            rows = 1,
                            columns = 6,
                            spriteWidth = atlas["turtle"].width / 6,
                            spriteHeight = atlas["turtle"].height
                        )
                    )
                    centered = true
                    animated = true
                }
            }
        }

        keys {
            down {
                when (it.key) {
                    Key.W -> turtleMove.up = true
                    Key.S -> turtleMove.down = true
                    Key.A -> turtleMove.left = true
                    Key.D -> turtleMove.right = true
                }
            }
            up {
                when (it.key) {
                    Key.W -> turtleMove.up = false
                    Key.S -> turtleMove.down = false
                    Key.A -> turtleMove.left = false
                    Key.D -> turtleMove.right = false
                }
            }
        }

        addUpdater { deltaTime ->
            world.update(deltaTime.seconds.toFloat())
        }
    }
}
