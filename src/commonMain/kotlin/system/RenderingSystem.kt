package system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.IteratingSystem
import com.soywiz.korge.view.Camera
import com.soywiz.korge.view.center
import com.soywiz.korge.view.position
import com.soywiz.korge.view.rotation
import component.RenderComponent
import component.TransformComponent

class RenderingSystem : IteratingSystem(
    allOfComponents = arrayOf(TransformComponent::class, RenderComponent::class)
) {

    private val camera = Inject.dependency<Camera>("camera")
    private val transform = Inject.componentMapper<TransformComponent>()
    private val render = Inject.componentMapper<RenderComponent>()

    override fun onTickEntity(entity: Entity) {

        val transform = transform[entity]

        render[entity].apply {
            if (centered) sprite.center()
            sprite.position(transform.position)
            sprite.rotation(transform.rotation)
            camera.addChild(sprite)
        }
    }
}