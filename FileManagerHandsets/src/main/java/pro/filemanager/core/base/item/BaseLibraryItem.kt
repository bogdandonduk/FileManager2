package pro.filemanager.core.base.item

interface BaseLibraryItem : BaseItem {
    override val path: String
    override val name: String
    val size: Long
    val dateModified: Long
}