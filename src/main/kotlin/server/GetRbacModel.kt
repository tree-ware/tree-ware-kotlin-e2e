package server

import io.ktor.server.auth.*
import org.treeWare.model.core.MainModel
import org.treeWare.model.core.MutableMainModel
import org.treeWare.model.operator.rbac.aux.PermissionScope
import org.treeWare.model.operator.rbac.aux.PermissionsAux
import org.treeWare.model.operator.rbac.aux.setPermissionsAux

internal fun getRbacModel(principal: Principal?, meta: MainModel): MainModel? {
    // TODO(replace): with an RBAC model fetched from the DB. Tree-ware will eventually make this easy.
    // Permit all.
    val rbac = MutableMainModel(meta)
    rbac.getOrNewRoot()
    setPermissionsAux(rbac, PermissionsAux(all = PermissionScope.NONE))
    return rbac
}