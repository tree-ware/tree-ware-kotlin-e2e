package server

import org.treeWare.model.core.EntityModel
import org.treeWare.model.operator.EntityDelegateRegistry
import org.treeWare.model.operator.Response
import org.treeWare.model.operator.SetEntityDelegate
import org.treeWare.mySql.operator.set
import java.time.Clock
import javax.sql.DataSource

internal fun setModel(
    setRequest: EntityModel,
    entityDelegates: EntityDelegateRegistry<SetEntityDelegate>?,
    mySqlDataSource: DataSource?,
    clock: Clock
): Response {
    return mySqlDataSource?.let { set(setRequest, entityDelegates, it, clock = clock) } ?: Response.Success
}