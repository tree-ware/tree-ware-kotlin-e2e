package server

import org.treeWare.model.core.EntityFactory
import org.treeWare.model.operator.GetOperatorId
import org.treeWare.model.operator.OperatorEntityDelegateRegistry
import org.treeWare.model.operator.SetOperatorId
import org.treeWare.model.operator.set.aux.SetAuxPlugin
import org.treeWare.mySql.aux.MySqlMetaModelMapAuxPlugin
import org.treeWare.mySql.operator.delegate.registerMySqlOperatorEntityDelegates
import org.treeWare.server.common.TreeWareServer
import java.time.Clock
import javax.sql.DataSource

fun newTreeWareServer(
    environment: String,
    metaModelFiles: List<String>,
    rootEntityFactory: EntityFactory,
    mySqlDataSource: DataSource?,
    clock: Clock = Clock.systemUTC()
): TreeWareServer {
    val operatorEntityDelegateRegistry = OperatorEntityDelegateRegistry()
    registerMySqlOperatorEntityDelegates(operatorEntityDelegateRegistry)
    val setEntityDelegates = operatorEntityDelegateRegistry.get(SetOperatorId)
    val getEntityDelegates = operatorEntityDelegateRegistry.get(GetOperatorId)

    return TreeWareServer(
        metaModelFiles,
        rootEntityFactory,
        true,
        listOf(
            MySqlMetaModelMapAuxPlugin(environment),
        ),
        listOf(SetAuxPlugin()),
        { initialize(it, mySqlDataSource, clock) },
        { principal, metaModel -> getRbacModel(principal, metaModel) },
        { setModel(it, setEntityDelegates, mySqlDataSource, clock) },
        { getModel(it, setEntityDelegates, getEntityDelegates, mySqlDataSource, rootEntityFactory) }
    )
}