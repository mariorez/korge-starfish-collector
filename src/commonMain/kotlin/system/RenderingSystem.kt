package system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.IteratingSystem
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import component.RenderComponent
import component.TransformComponent

class RenderingSystem : IteratingSystem(
    allOfComponents = arrayOf(TransformComponent::class, RenderComponent::class)
) {

    private val layerContainer = Inject.dependency<Container>("layer0")
    private val transform = Inject.componentMapper<TransformComponent>()
    private val render = Inject.componentMapper<RenderComponent>()

    override fun onTickEntity(entity: Entity) {
        val position = transform[entity].position
        val sprite = render[entity].sprite

        sprite.position(position)

        layerContainer.addChild(sprite)
    }
}