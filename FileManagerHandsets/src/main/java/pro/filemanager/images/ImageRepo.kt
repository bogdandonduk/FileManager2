package pro.filemanager.images

import android.annotation.SuppressLint
import android.database.Cursor
import android.provider.MediaStore
import bogdandonduk.androidlibs.commonpreferencesutilsandroid.exceptions.ExceptionsUtilsExtensionVocabulary
import kotlinx.coroutines.delay
import pro.filemanager.FileManager
import pro.filemanager.core.extensions.MESSAGE_MEDIASTORE_ERROR
import pro.filemanager.images.folders.ImageFolderItem
import pro.filemanager.images.library.ImageLibraryItem
import java.io.File

@SuppressLint("Recycle")
object ImageRepo {
    @Volatile private var itemsByDateRecent: MutableList<ImageLibraryItem>? = null
    @Volatile private var itemsByDateOldest: MutableList<ImageLibraryItem>? = null
    @Volatile private var itemsByNameAlphabetic: MutableList<ImageLibraryItem>? = null
    @Volatile private var itemsByNameReversed: MutableList<ImageLibraryItem>? = null
    @Volatile private var itemsBySizeLargest: MutableList<ImageLibraryItem>? = null
    @Volatile private var itemsBySizeSmallest: MutableList<ImageLibraryItem>? = null

    @Volatile private var loadingItemsByDateRecent = false
    @Volatile private var loadingItemsByDateOldest = false
    @Volatile private var loadingItemsByNameAlphabetic = false
    @Volatile private var loadingItemsByNameReversed = false
    @Volatile private var loadingItemsBySizeLargest = false
    @Volatile private var loadingItemsBySizeSmallest = false

    @Volatile private var foldersByDateRecent: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateRecentByDateRecent: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateRecentByDateOldest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateRecentByNameAlphabetic: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateRecentByNameReversed: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateRecentBySizeLargest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateRecentBySizeSmallest: MutableList<ImageFolderItem>? = null

    @Volatile private var splittingFoldersByDateRecent = false
    @Volatile private var splittingFoldersByDateRecentByDateRecent = false
    @Volatile private var splittingFoldersByDateRecentByDateOldest = false
    @Volatile private var splittingFoldersByDateRecentNameAlphabetic = false
    @Volatile private var splittingFoldersByDateRecentNameReversed = false
    @Volatile private var splittingFoldersByDateRecentBySizeLargest = false
    @Volatile private var splittingFoldersByDateRecentBySizeSmallest = false

    @Volatile private var foldersByDateOldest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateOldestByDateRecent: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateOldestByDateOldest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateOldestByNameAlphabetic: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateOldestByNameReversed: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateOldestBySizeLargest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByDateOldestBySizeSmallest: MutableList<ImageFolderItem>? = null

    @Volatile private var splittingFoldersByDateOldest = false
    @Volatile private var splittingFoldersByDateOldestByDateRecent = false
    @Volatile private var splittingFoldersByDateOldestByDateOldest = false
    @Volatile private var splittingFoldersByDateOldestByNameAlphabetic = false
    @Volatile private var splittingFoldersByDateOldestByNameReversed = false
    @Volatile private var splittingFoldersByDateOldestBySizeLargest = false
    @Volatile private var splittingFoldersByDateOldestBySizeSmallest = false

    @Volatile private var foldersByNameAlphabetic: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameAlphabeticByDateRecent: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameAlphabeticByDateOldest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameAlphabeticByNameAlphabetic: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameAlphabeticByNameReversed: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameAlphabeticBySizeLargest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameAlphabeticBySizeSmallest: MutableList<ImageFolderItem>? = null

    @Volatile private var splittingFoldersByNameAlphabetic = false
    @Volatile private var splittingFoldersByNameAlphabeticByDateRecent = false
    @Volatile private var splittingFoldersByNameAlphabeticByDateOldest = false
    @Volatile private var splittingFoldersByNameAlphabeticByNameAlphabetic = false
    @Volatile private var splittingFoldersByNameAlphabeticByNameReversed = false
    @Volatile private var splittingFoldersByNameAlphabeticBySizeLargest = false
    @Volatile private var splittingFoldersByNameAlphabeticBySizeSmallest = false

    @Volatile private var foldersByNameReversed: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameReversedByDateRecent: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameReversedByDateOldest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameReversedByNameAlphabetic: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameReversedByNameReversed: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameReversedBySizeLargest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersByNameReversedBySizeSmallest: MutableList<ImageFolderItem>? = null

    @Volatile private var splittingFoldersByNameReversed = false
    @Volatile private var splittingFoldersByNameReversedByDateRecent = false
    @Volatile private var splittingFoldersByNameReversedByDateOldest = false
    @Volatile private var splittingFoldersByNameReversedByNameAlphabetic = false
    @Volatile private var splittingFoldersByNameReversedByNameReversed = false
    @Volatile private var splittingFoldersByNameReversedBySizeLargest = false
    @Volatile private var splittingFoldersByNameReversedBySizeSmallest = false

    @Volatile private var foldersBySizeLargest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeLargestByDateRecent: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeLargestByDateOldest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeLargestByNameAlphabetic: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeLargestByNameReversed: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeLargestBySizeLargest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeLargestBySizeSmallest: MutableList<ImageFolderItem>? = null

    @Volatile private var splittingFoldersBySizeLargest = false
    @Volatile private var splittingFoldersBySizeLargestByDateRecent = false
    @Volatile private var splittingFoldersBySizeLargestByDateOldest = false
    @Volatile private var splittingFoldersBySizeLargestNameAlphabetic = false
    @Volatile private var splittingFoldersBySizeLargestNameReversed = false
    @Volatile private var splittingFoldersBySizeLargestBySizeLargest = false
    @Volatile private var splittingFoldersBySizeLargestBySizeSmallest = false

    @Volatile private var foldersBySizeSmallest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeSmallestByDateRecent: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeSmallestByDateOldest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeSmallestByNameAlphabetic: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeSmallestByNameReversed: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeSmallestBySizeLargest: MutableList<ImageFolderItem>? = null
    @Volatile private var foldersBySizeSmallestBySizeSmallest: MutableList<ImageFolderItem>? = null

    @Volatile private var splittingFoldersBySizeSmallest = false
    @Volatile private var splittingFoldersBySizeSmallestByDateRecent = false
    @Volatile private var splittingFoldersBySizeSmallestByDateOldest = false
    @Volatile private var splittingFoldersBySizeSmallestByNameAlphabetic = false
    @Volatile private var splittingFoldersBySizeSmallestByNameReversed = false
    @Volatile private var splittingFoldersBySizeSmallestBySizeLargest = false
    @Volatile private var splittingFoldersBySizeSmallestBySizeSmallest = false

    suspend fun loadItemsByDateRecent(forceLoad: Boolean) : MutableList<ImageLibraryItem> {
        return if(!itemsByDateRecent.isNullOrEmpty() && !forceLoad) {
            itemsByDateRecent!!
        } else {
            if(!loadingItemsByDateRecent) {
                loadingItemsByDateRecent = true

                val cursor: Cursor = FileManager.context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT
                ), null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + " DESC", null)!!

                val items = mutableListOf<ImageLibraryItem>()

                if(cursor.moveToFirst()) {
                    while(!cursor.isAfterLast) {
                        File(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))).let {
                            if(it.exists() && !it.isDirectory) {
                                items.add(
                                        ImageLibraryItem(
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT))
                                        )
                                )
                            }
                        }

                        cursor.moveToNext()
                    }
                }

                cursor.close()

                loadingItemsByDateRecent = false

                itemsByDateRecent = items

                itemsByDateRecent!!
            } else {
                val timeout = System.currentTimeMillis() + 12000

                while(itemsByDateRecent.isNullOrEmpty() && loadingItemsByDateRecent && System.currentTimeMillis() < timeout) {
                    delay(25)
                }

                itemsByDateRecent ?: throw IllegalStateException(ExceptionsUtilsExtensionVocabulary.MESSAGE_MEDIASTORE_ERROR)
            }
        }
    }

    suspend fun loadItemsByDateOldest(forceLoad: Boolean) : MutableList<ImageLibraryItem> {
        return if(!itemsByDateOldest.isNullOrEmpty() && !forceLoad) {
            itemsByDateOldest!!
        } else {
            if(!loadingItemsByDateOldest) {
                loadingItemsByDateOldest = true

                val cursor: Cursor = FileManager.context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT
                ), null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + " ASC", null)!!

                val items = mutableListOf<ImageLibraryItem>()

                if(cursor.moveToFirst()) {
                    while(!cursor.isAfterLast) {
                        File(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))).let {
                            if(it.exists() && !it.isDirectory) {
                                items.add(
                                        ImageLibraryItem(
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT))
                                        )
                                )
                            }
                        }

                        cursor.moveToNext()
                    }
                }

                cursor.close()

                loadingItemsByDateOldest = false

                itemsByDateOldest = items

                itemsByDateOldest!!
            } else {
                val timeout = System.currentTimeMillis() + 30000

                while(itemsByDateOldest.isNullOrEmpty() && loadingItemsByDateOldest && System.currentTimeMillis() < timeout) {
                    delay(25)
                }

                itemsByDateOldest ?: throw IllegalStateException(ExceptionsUtilsExtensionVocabulary.MESSAGE_MEDIASTORE_ERROR)
            }
        }
    }

    suspend fun loadItemsByNameAlphabetic(forceLoad: Boolean) : MutableList<ImageLibraryItem> {
        return if(!itemsByNameAlphabetic.isNullOrEmpty() && !forceLoad) {
            itemsByNameAlphabetic!!
        } else {
            if(!loadingItemsByNameAlphabetic) {
                loadingItemsByNameAlphabetic = true

                val cursor: Cursor = FileManager.context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT
                ), null, null, MediaStore.Images.ImageColumns.DISPLAY_NAME + " ASC", null)!!

                val items = mutableListOf<ImageLibraryItem>()

                if(cursor.moveToFirst()) {
                    while(!cursor.isAfterLast) {
                        File(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))).let {
                            if(it.exists() && !it.isDirectory) {
                                items.add(
                                        ImageLibraryItem(
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT))
                                        )
                                )
                            }
                        }

                        cursor.moveToNext()
                    }
                }

                cursor.close()

                loadingItemsByNameAlphabetic = false

                itemsByNameAlphabetic = items

                itemsByNameAlphabetic!!
            } else {
                val timeout = System.currentTimeMillis() + 30000

                while(itemsByNameAlphabetic.isNullOrEmpty() && loadingItemsByNameAlphabetic && System.currentTimeMillis() < timeout) {
                    delay(25)
                }

                itemsByNameAlphabetic ?: throw IllegalStateException(ExceptionsUtilsExtensionVocabulary.MESSAGE_MEDIASTORE_ERROR)
            }
        }
    }

    suspend fun loadItemsByNameReversed(forceLoad: Boolean) : MutableList<ImageLibraryItem> {
        return if(!itemsByNameReversed.isNullOrEmpty() && !forceLoad) {
            itemsByNameReversed!!
        } else {
            if(!loadingItemsByNameReversed) {
                loadingItemsByNameReversed = true

                val cursor: Cursor = FileManager.context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT
                ), null, null, MediaStore.Images.ImageColumns.DISPLAY_NAME + " DESC", null)!!

                val items = mutableListOf<ImageLibraryItem>()

                if(cursor.moveToFirst()) {
                    while(!cursor.isAfterLast) {
                        File(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))).let {
                            if(it.exists() && !it.isDirectory) {
                                items.add(
                                        ImageLibraryItem(
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT))
                                        )
                                )
                            }
                        }

                        cursor.moveToNext()
                    }
                }

                cursor.close()

                loadingItemsByNameReversed = false

                itemsByNameReversed = items

                itemsByNameReversed!!
            } else {
                val timeout = System.currentTimeMillis() + 30000

                while(itemsByNameReversed.isNullOrEmpty() && loadingItemsByNameReversed && System.currentTimeMillis() < timeout) {
                    delay(25)
                }

                itemsByNameReversed ?: throw IllegalStateException(ExceptionsUtilsExtensionVocabulary.MESSAGE_MEDIASTORE_ERROR)
            }
        }
    }

    suspend fun loadItemsBySizeLargest(forceLoad: Boolean) : MutableList<ImageLibraryItem> {
        return if(!itemsBySizeLargest.isNullOrEmpty() && !forceLoad) {
            itemsBySizeLargest!!
        } else {
            if(!loadingItemsBySizeLargest) {
                loadingItemsBySizeLargest = true

                val cursor: Cursor = FileManager.context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT
                ), null, null, MediaStore.Images.ImageColumns.SIZE + " DESC", null)!!

                val items = mutableListOf<ImageLibraryItem>()

                if(cursor.moveToFirst()) {
                    while(!cursor.isAfterLast) {
                        File(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))).let {
                            if(it.exists() && !it.isDirectory) {
                                items.add(
                                        ImageLibraryItem(
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT))
                                        )
                                )
                            }
                        }

                        cursor.moveToNext()
                    }
                }

                cursor.close()

                loadingItemsBySizeLargest = false

                itemsBySizeLargest = items

                itemsBySizeLargest!!
            } else {
                val timeout = System.currentTimeMillis() + 30000

                while(itemsBySizeLargest.isNullOrEmpty() && loadingItemsBySizeLargest && System.currentTimeMillis() < timeout) {
                    delay(25)
                }

                itemsBySizeLargest ?: throw IllegalStateException(ExceptionsUtilsExtensionVocabulary.MESSAGE_MEDIASTORE_ERROR)
            }
        }
    }

    suspend fun loadItemsBySizeSmallest(forceLoad: Boolean) : MutableList<ImageLibraryItem> {
        return if(!itemsBySizeSmallest.isNullOrEmpty() && !forceLoad) {
            itemsBySizeSmallest!!
        } else {
            if(!loadingItemsBySizeSmallest) {
                loadingItemsBySizeSmallest = true

                val cursor: Cursor = FileManager.context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE,
                        MediaStore.Images.ImageColumns.DATE_MODIFIED,
                        MediaStore.Images.ImageColumns.WIDTH,
                        MediaStore.Images.ImageColumns.HEIGHT
                ), null, null, MediaStore.Images.ImageColumns.SIZE + " ASC", null)!!

                val items = mutableListOf<ImageLibraryItem>()

                if(cursor.moveToFirst()) {
                    while(!cursor.isAfterLast) {
                        File(cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))).let {
                            if(it.exists() && !it.isDirectory) {
                                items.add(
                                        ImageLibraryItem(
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)),
                                                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)),
                                                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)),
                                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT))
                                        )
                                )
                            }
                        }

                        cursor.moveToNext()
                    }
                }

                cursor.close()

                loadingItemsBySizeSmallest = false

                itemsBySizeSmallest = items

                itemsBySizeSmallest!!
            } else {
                val timeout = System.currentTimeMillis() + 30000

                while(itemsBySizeSmallest.isNullOrEmpty() && loadingItemsBySizeSmallest && System.currentTimeMillis() < timeout) {
                    delay(25)
                }

                itemsBySizeSmallest ?: throw IllegalStateException(ExceptionsUtilsExtensionVocabulary.MESSAGE_MEDIASTORE_ERROR)
            }
        }
    }

    suspend fun loadAll(forceLoad: Boolean) {
        loadItemsByDateRecent(forceLoad)
        loadItemsByDateOldest(forceLoad)
        loadItemsByNameAlphabetic(forceLoad)
        loadItemsByNameReversed(forceLoad)
        loadItemsBySizeLargest(forceLoad)
        loadItemsBySizeSmallest(forceLoad)
    }

    fun clear() {
        itemsByDateRecent?.clear()
        itemsByDateOldest?.clear()
        itemsByNameAlphabetic?.clear()
        itemsByNameReversed?.clear()
        itemsBySizeLargest?.clear()
        itemsBySizeSmallest?.clear()

        foldersByDateRecent?.clear()
        foldersByDateRecentByDateRecent?.clear()
        foldersByDateRecentByDateOldest?.clear()
        foldersByDateRecentByNameAlphabetic?.clear()
        foldersByDateRecentByNameReversed?.clear()
        foldersByDateRecentBySizeLargest?.clear()
        foldersByDateRecentBySizeSmallest?.clear()

        foldersByDateOldest?.clear()
        foldersByDateOldestByDateRecent?.clear()
        foldersByDateOldestByDateOldest?.clear()
        foldersByDateOldestByNameAlphabetic?.clear()
        foldersByDateOldestByNameReversed?.clear()
        foldersByDateOldestBySizeLargest?.clear()
        foldersByDateOldestBySizeSmallest?.clear()

        foldersByNameAlphabetic?.clear()
        foldersByNameAlphabeticByDateRecent?.clear()
        foldersByNameAlphabeticByDateOldest?.clear()
        foldersByNameAlphabeticByNameAlphabetic?.clear()
        foldersByNameAlphabeticByNameReversed?.clear()
        foldersByNameAlphabeticBySizeLargest?.clear()
        foldersByNameAlphabeticBySizeSmallest?.clear()

        foldersByNameReversed?.clear()
        foldersByNameReversedByDateRecent?.clear()
        foldersByNameReversedByDateOldest?.clear()
        foldersByNameReversedByNameAlphabetic?.clear()
        foldersByNameReversedByNameReversed?.clear()
        foldersByNameReversedBySizeLargest?.clear()
        foldersByNameReversedBySizeSmallest?.clear()

        foldersBySizeLargest?.clear()
        foldersBySizeLargestByDateRecent?.clear()
        foldersBySizeLargestByDateOldest?.clear()
        foldersBySizeLargestByNameAlphabetic?.clear()
        foldersBySizeLargestByNameReversed?.clear()
        foldersBySizeLargestBySizeLargest?.clear()
        foldersBySizeLargestBySizeSmallest?.clear()

        foldersBySizeSmallest?.clear()
        foldersBySizeSmallestByDateRecent?.clear()
        foldersBySizeSmallestByDateOldest?.clear()
        foldersBySizeSmallestByNameAlphabetic?.clear()
        foldersBySizeSmallestByNameReversed?.clear()
        foldersBySizeSmallestBySizeLargest?.clear()
        foldersBySizeSmallestBySizeSmallest?.clear()
    }
}