package component

import com.soywiz.korge.view.Sprite

data class RenderComponent(
    var sprite: Sprite = Sprite(),
    var centered: Boolean = true
)