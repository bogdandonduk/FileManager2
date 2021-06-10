package pro.filemanager.core.base.viewmodel

import bogdandonduk.androidlibs.permissionsandroid.PermissionRequesterPersistableHost

open class BasePermissionRequesterViewModel : BaseViewModel(), PermissionRequesterPersistableHost {
    override val requestedPermissionsCodesMap: MutableMap<String, Int?> = mutableMapOf()
}