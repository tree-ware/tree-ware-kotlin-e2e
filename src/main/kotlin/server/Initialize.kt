package server

import org.treeWare.model.core.MainModel
import org.treeWare.model.core.MutableMainModel
import org.treeWare.model.operator.set.aux.SetAux
import org.treeWare.model.operator.set.aux.setSetAux
import java.time.Clock
import javax.sql.DataSource

fun initialize(mainMeta: MainModel, mySqlDataSource: DataSource?, clock: Clock) {
    mySqlDataSource?.also { dataSource ->
        // Create the root entity in the DB. It is required for creating other entities in the DB.
        val model = MutableMainModel(mainMeta)
        setSetAux(model.getOrNewRoot(), SetAux.CREATE)
        setModel(model, null, dataSource, clock)
    }
}