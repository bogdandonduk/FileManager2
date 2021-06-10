package pro.filemanager.images.folders

import pro.filemanager.core.base.item.BaseFolderItem
import pro.filemanager.core.base.item.BaseLibraryItem
import pro.filemanager.images.library.ImageLibraryItem
import java.io.File

class ImageFolderItem(
        override var path: String,
        override var name: String = File(path).name,
        var containedImageLibraryItems: MutableList<ImageLibraryItem> = mutableListOf(),
        override var totalSize: Long = 0,
) : BaseFolderItem {
    override var containedLibraryItems: MutableList<out BaseLibraryItem> = containedImageLibraryItems
}