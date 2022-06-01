import com.soywiz.korge.tiled.TiledMap

import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

suspend fun TiledMap.Image.readBitmap(): Bitmap {
    return when (this) {
        is TiledMap.Image.Embedded -> this.image
        is TiledMap.Image.External -> resourcesVfs[this.source].readBitmap()
    }
}