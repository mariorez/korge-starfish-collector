import com.soywiz.korge.Korge
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import scene.GameScene
import kotlin.reflect.KClass

suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
    override val size = SizeInt(800, 600)
    override val bgcolor = Colors.BLUE
    override val mainScene: KClass<out Scene> = GameScene::class

    override suspend fun AsyncInjector.configure() {
        mapPrototype { GameScene() }
    }
}
