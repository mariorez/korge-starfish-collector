package system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.IteratingSystem
import com.soywiz.korge.view.Container
import com.soywiz.korma.geom.SizeInt
import component.PlayerComponent
import component.TransformComponent

class CameraSystem : IteratingSystem(
    allOfComponents = arrayOf(PlayerComponent::class)
) {

    private val camera = Inject.dependency<Container>("camera")
    private val worldSize = Inject.dependency<SizeInt>("worldSize")
    private val transform = Inject.componentMapper<TransformComponent>()
    private val middleX: Double = camera.width / 2
    private val middleY: Double = camera.height / 2
    private val worldLimitX: Double = worldSize.width - middleX
    private val worldLimitY: Double = worldSize.height - middleY

    override fun onTickEntity(entity: Entity) {

        val playerPos = transform[entity].position

        camera.apply {
            x = if (playerPos.x > middleX && playerPos.x < worldLimitX) {
                -(playerPos.x - middleX)
            } else if (playerPos.x > worldLimitX) {
                -(worldSize.width - width)
            } else {
                0.0
            }

            y = if (playerPos.y > middleY && playerPos.y < worldLimitY) {
                -(playerPos.y - middleY)
            } else if (playerPos.y > worldLimitY) {
                -(worldSize.height - height)
            } else {
                0.0
            }
        }
    }
}
