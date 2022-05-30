package system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.Inject
import com.github.quillraven.fleks.IteratingSystem
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.Point
import com.soywiz.korma.math.clamp
import component.PlayerComponent
import component.TransformComponent
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

class MovementSystem : IteratingSystem(
    allOfComponents = arrayOf(PlayerComponent::class)
) {

    private val transform = Inject.componentMapper<TransformComponent>()

    override fun onTickEntity(entity: Entity) {

        transform[entity].apply {
            // apply acceleration
            velocity.add(accelerator.mul(deltaTime))

            var speed = velocity.length

            // decrease speed (decelerate) when not accelerating
            if (accelerator.length == 0.0) {
                speed -= deceleration * deltaTime
            }

            // keep speed within set bounds
            speed = speed.clamp(0.0, maxSpeed)

            // update velocity
            if (velocity.length == 0.0) {
                velocity.setTo(speed, 0.0)
            } else {
                velocity = setLength(velocity, speed)
            }

            // move by
            if (velocity.x != 0.0 || velocity.y != 0.0) {
                position.add(velocity.x * deltaTime, velocity.y * deltaTime)
            }

            // set rotation when moving
            if (velocity.length > 0) {
                rotation = getAngle(velocity)
            }

            // reset acceleration
            accelerator.setTo(0f, 0f)
        }
    }

    private fun setLength(point: Point, length: Double): Point {
        val len2 = length * length
        val oldLen2 = point.x * point.x + point.y * point.y

        return if (oldLen2 == 0.0 || oldLen2 == len2) {
            point
        } else {
            val scalar = sqrt((len2 / oldLen2))
            val x = point.x * scalar
            val y = point.y * scalar
            Point(x, y)
        }
    }

    private fun getAngle(point: Point): Angle {
        var angle: Double = atan2(point.y, point.x) * (180.0 / PI)
        if (angle < 0) angle += 360.0
        return Angle.fromDegrees(angle)
    }
}