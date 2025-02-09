package server

import org.treeWare.model.core.MutableEntityModel
import org.treeWare.model.operator.EntityDelegateRegistry
import org.treeWare.model.operator.Response
import org.treeWare.model.operator.SetEntityDelegate
import org.treeWare.mySql.operator.set
import server.custom.customSetRequestValidationAndBusinessLogic
import java.time.Clock
import javax.sql.DataSource

internal fun setModel(
    setRequest: MutableEntityModel,
    entityDelegates: EntityDelegateRegistry<SetEntityDelegate>?,
    mySqlDataSource: DataSource?,
    clock: Clock
): Response {
    val customValidationErrors = customSetRequestValidationAndBusinessLogic(setRequest)
    if (!customValidationErrors.isOk()) return customValidationErrors
    return mySqlDataSource?.let { set(setRequest, entityDelegates, it, clock = clock) } ?: Response.Success
}