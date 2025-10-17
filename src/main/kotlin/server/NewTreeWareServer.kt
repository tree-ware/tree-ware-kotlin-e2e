package server

import businessLogic.getBusinessLogicFunctions
import org.treeWare.model.core.EntityFactory
import org.treeWare.model.operator.GetOperatorId
import org.treeWare.model.operator.OperatorEntityDelegateRegistry
import org.treeWare.model.operator.Response
import org.treeWare.model.operator.SetOperatorId
import org.treeWare.model.operator.set.aux.SetAuxPlugin
import org.treeWare.mySql.aux.MySqlMetaModelMapAuxPlugin
import org.treeWare.mySql.operator.delegate.registerMySqlOperatorEntityDelegates
import org.treeWare.server.common.TreeWareServer
import java.time.Clock
import javax.sql.DataSource

fun newTreeWareServer(
    environment: String, // TODO: add application.conf setting to decide if environment should be used as DB prefix
    metaModelFiles: List<String>,
    rootEntityFactory: EntityFactory,
    mySqlDataSource: DataSource?,
    clock: Clock = Clock.systemUTC()
): TreeWareServer {
    val operatorEntityDelegateRegistry = OperatorEntityDelegateRegistry()
    registerMySqlOperatorEntityDelegates(operatorEntityDelegateRegistry)
    val setEntityDelegates = operatorEntityDelegateRegistry.get(SetOperatorId)
    val getEntityDelegates = operatorEntityDelegateRegistry.get(GetOperatorId)

    val businessLogicFunctions = getBusinessLogicFunctions()
    return TreeWareServer(
        metaModelFiles,
        rootEntityFactory,
        true,
        listOf(
            MySqlMetaModelMapAuxPlugin(),
        ),
        listOf(SetAuxPlugin()),
        { Response.Success },
        { principal, metaModel -> getRbacModel(principal, metaModel) },
        { setModel(it, setEntityDelegates, mySqlDataSource, clock) },
        { getModel(it, setEntityDelegates, getEntityDelegates, mySqlDataSource, rootEntityFactory) },
        businessLogicFunctions
    )
}