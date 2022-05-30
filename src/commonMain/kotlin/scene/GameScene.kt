package scene

import WINDOW_HEIGHT
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.World
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.addUpdater
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import component.InputComponent
import component.PlayerComponent
import component.RenderComponent
import component.TransformComponent
import system.InputSystem
import system.MovementSystem
import system.RenderingSystem

class GameScene : Scene() {

    override suspend fun Container.sceneMain() {

        val world = World {
            component(::PlayerComponent)
            component(::InputComponent)
            component(::TransformComponent)
            component(::RenderComponent)

            system(::InputSystem)
            system(::MovementSystem)
            system(::RenderingSystem)

            inject("layer0", this@sceneMain)
        }

        val turtle = world.entity {
            add<PlayerComponent>()
            add<InputComponent>()
            add<TransformComponent> {
                position.x = 100.0
                position.y = (WINDOW_HEIGHT - 100).toDouble()
                acceleration = 400.0
                deceleration = 400.0
                maxSpeed = 150.0
            }
            add<RenderComponent> {
                sprite = Sprite(resourcesVfs["turtle.png"].readBitmap())
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
