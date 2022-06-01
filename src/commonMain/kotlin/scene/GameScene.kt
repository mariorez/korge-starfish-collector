package scene

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.World
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.SizeInt
import component.InputComponent
import component.PlayerComponent
import component.RenderComponent
import component.TransformComponent
import system.CameraSystem
import system.InputSystem
import system.MovementSystem
import system.RenderingSystem

class GameScene : Scene() {

    lateinit var turtle: Entity

    override suspend fun Container.sceneMain() {

        val camera = camera {}
        val background = Sprite(resourcesVfs["water-border.jpg"].readBitmap())

        val world = World {
            inject("camera", camera)
            inject("worldSize", SizeInt(background.width.toInt(), background.height.toInt()))

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
                    sprite = Sprite(resourcesVfs["turtle.png"].readBitmap())
                    centered = true
                }
            }

            entity {
                add<TransformComponent> { position.setTo(0, 0) }
                add<RenderComponent> { sprite = background }
            }
        }

        val turtleMove = Inject.componentMapper<InputComponent>()[turtle]

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
