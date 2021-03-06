package system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.IteratingSystem
import com.soywiz.korge.view.center
import com.soywiz.korge.view.position
import com.soywiz.korge.view.rotation
import component.RenderComponent
import component.TransformComponent

class RenderingSystem : IteratingSystem(
    allOfComponents = arrayOf(TransformComponent::class, RenderComponent::class)
) {

    private val transform = Inject.componentMapper<TransformComponent>()
    private val render = Inject.componentMapper<RenderComponent>()

    override fun onTickEntity(entity: Entity) {

        val transform = transform[entity]

        render[entity].apply {
            sprite.apply {
                if (animated) {
                    if (transform.isMoving) playAnimationLooped(spriteDisplayTime = frameDuration)
                    else stopAnimation()
                }
                if (centered) center()
                position(transform.position)
                rotation(transform.rotation)
            }
        }
    }
}
