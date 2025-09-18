package server

import io.ktor.server.auth.*
import org.treeWare.model.core.EntityModel
import org.treeWare.model.core.MutableEntityModel
import org.treeWare.model.operator.rbac.aux.PermissionScope
import org.treeWare.model.operator.rbac.aux.PermissionsAux
import org.treeWare.model.operator.rbac.aux.setPermissionsAux

internal fun getRbacModel(principal: Principal?, metaModel: EntityModel): EntityModel? {
    // TODO(replace): with an RBAC model fetched from the DB. Tree-ware will eventually make this easy.
    // Permit all.
    val rbac = MutableEntityModel(metaModel, null)
    setPermissionsAux(rbac, PermissionsAux(all = PermissionScope.SUB_TREE))
    return rbac
}