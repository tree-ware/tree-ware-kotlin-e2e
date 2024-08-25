package server

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
    mySqlDataSource: DataSource?,
    clock: Clock = Clock.systemUTC()
): TreeWareServer {
    val operatorEntityDelegateRegistry = OperatorEntityDelegateRegistry()
    registerMySqlOperatorEntityDelegates(operatorEntityDelegateRegistry)
    val setEntityDelegates = operatorEntityDelegateRegistry.get(SetOperatorId)
    val getEntityDelegates = operatorEntityDelegateRegistry.get(GetOperatorId)

    // TODO(deepak-nulu): drop META_MODEL_FILES parameter from TreeWareServer() since mutableMainModelFactory is passed
    //   to it? But will we be able to support an API editor UI without the JSON version of the meta-model?
    //   If we need the JSON meta-model files, then the tree-ware Gradle plugin must generate a constant that lists
    //   these files.
    return TreeWareServer(
        listOf(""), // TODO(replace): with generated constant for list of meta-model files
        null, // TODO(replace): with generated  MutableMainModelFactory
        true,
        listOf(
            MySqlMetaModelMapAuxPlugin(environment),
        ),
        listOf(SetAuxPlugin()),
        { initialize(it, mySqlDataSource, clock) },
        { principal, mainMeta -> getRbacModel(principal, mainMeta) },
        { setModel(it, setEntityDelegates, mySqlDataSource, clock) },
        { getModel(it, setEntityDelegates, getEntityDelegates, mySqlDataSource) }
    )
}