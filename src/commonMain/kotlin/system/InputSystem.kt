package system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.IteratingSystem
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.degrees
import component.InputComponent
import component.PlayerComponent
import component.TransformComponent

class InputSystem : IteratingSystem(
    allOfComponents = arrayOf(PlayerComponent::class)
) {

    private val input = Inject.componentMapper<InputComponent>()
    private val transform = Inject.componentMapper<TransformComponent>()
    private val speedUp = Point()

    override fun onTickEntity(entity: Entity) {
        input[entity].also { playerInput ->
            if (playerInput.isMoving) {
                transform[entity].apply {
                    speedUp.setTo(acceleration, 0.0).also { speed ->
                        if (playerInput.right) accelerator.add(speed.setToPolar(0.degrees, speed.length))
                        if (playerInput.down) accelerator.add(speed.setToPolar(90.degrees, speed.length))
                        if (playerInput.left) accelerator.add(speed.setToPolar(180.degrees, speed.length))
                        if (playerInput.up) accelerator.add(speed.setToPolar(270.degrees, speed.length))
                    }
                }
            }
        }
    }
}