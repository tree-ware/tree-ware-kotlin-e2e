package server

import org.treeWare.model.core.EntityModel
import org.treeWare.model.core.MutableEntityModel
import org.treeWare.model.operator.Response
import org.treeWare.model.operator.set.aux.SetAux
import org.treeWare.model.operator.set.aux.setSetAux
import java.time.Clock
import javax.sql.DataSource

fun initialize(metaModel: EntityModel, mySqlDataSource: DataSource?, clock: Clock): Response {
    val response = mySqlDataSource?.let { dataSource ->
        // Create the root entity in the DB. It is required for creating other entities in the DB.
        val model = MutableEntityModel(metaModel, null)
        setSetAux(model, SetAux.CREATE)
        setModel(model, null, dataSource, clock)
    }
    return response ?: Response.Success
}