package component

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.korge.view.Sprite

data class RenderComponent(
    var sprite: Sprite = Sprite(),
    var centered: Boolean = false,
    var animated: Boolean = false,
    var frameDuration: TimeSpan = 100.milliseconds
)