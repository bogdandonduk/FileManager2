package pro.filemanager.images.library

import pro.filemanager.core.base.BaseLibraryItem

class ImageLibraryItem (
        override var path: String,
        override var name: String,
        override var size: Long,
        override var dateModified: Long,
        val width: Int,
        val height: Int
) : BaseLibraryItem {
        override fun equals(other: Any?): Boolean =
                other != null &&
                        this::javaClass == other.javaClass &&
                        this.path == (other as ImageLibraryItem).path &&
                        this.name == other.name &&
                        this.size == other.size &&
                        this.dateModified == other.dateModified &&
                        this.width == other.width &&
                        this.height == other.height

        override fun hashCode(): Int {
                var result = path.hashCode()
                        result = 31 * result + name.hashCode()
                        result = 31 * result + size.hashCode()
                        result = 31 * result + dateModified.hashCode()
                        result = 31 * result + width.hashCode()
                        result = 31 * result + height.hashCode()
                return result
        }

        override fun toString(): String {
                return path
        }
}