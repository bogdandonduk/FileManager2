package pro.filemanager.core.base.item

interface BaseFolderItem : BaseItem {
    override val path: String
    override val name: String
    var containedLibraryItems: MutableList<out BaseLibraryItem>
    var totalSize: Long
}
