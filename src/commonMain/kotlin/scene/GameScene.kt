package scene

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.centerOnStage
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korio.file.std.resourcesVfs

class GameScene : Scene() {
    override suspend fun Container.sceneInit() {
        val font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
        text("Game Scene", 64.0, Colors.WHITE, font) {
            centerOnStage()
            onClick {
                sceneContainer.changeTo<HomeScene>()
            }
        }
    }
}