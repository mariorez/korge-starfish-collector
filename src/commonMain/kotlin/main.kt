import com.soywiz.korge.Korge
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import scene.GameScene
import kotlin.reflect.KClass

const val WINDOW_WITH = 800
const val WINDOW_HEIGHT = 600

suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {
    override val title = "Starfish Collector"
    override val bgcolor = Colors["#297dbb"]
    override val size = SizeInt(WINDOW_WITH, WINDOW_HEIGHT)
    override val mainScene: KClass<out Scene> = GameScene::class

    override suspend fun AsyncInjector.configure() {
        mapPrototype { GameScene() }
    }
}
