package system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.IteratingSystem
import com.soywiz.korge.view.Container
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
    private val layer0 = Inject.dependency<Container>("layer0")

    var updatePosition = 0.0

    override fun onTickEntity(entity: Entity) {

        val transform = transform[entity]

        val sprite = render[entity].sprite.apply {
            center()
            position(transform.position)
            rotation(transform.rotation)
        }

        layer0.addChild(sprite)
    }
}