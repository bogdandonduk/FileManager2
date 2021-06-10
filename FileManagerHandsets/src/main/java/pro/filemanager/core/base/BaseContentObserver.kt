package pro.filemanager.core.base

import android.database.ContentObserver
import android.os.Handler

open class BaseContentObserver(handler: Handler) : ContentObserver(handler)