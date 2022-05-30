package component

import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.Point

data class TransformComponent(
    var position: Point = Point(),
    var velocity: Point = Point(),
    var accelerator: Point = Point(),
    var acceleration: Double = 0.0,
    var deceleration: Double = 0.0,
    var maxSpeed: Double = 0.0,
    var rotation: Angle = Angle.ZERO
)
