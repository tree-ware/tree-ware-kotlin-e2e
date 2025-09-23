package mySql

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import metaModel
import org.lighthousegames.logging.logging
import org.treeWare.model.operator.OperatorEntityDelegateRegistry
import org.treeWare.model.operator.SetOperatorId
import org.treeWare.model.operator.set.aux.SetAux
import org.treeWare.model.operator.set.aux.setSetAux
import org.treeWare.mySql.operator.GenerateDdlCommandsOperatorId
import org.treeWare.mySql.operator.createDatabase
import org.treeWare.mySql.operator.delegate.registerMySqlOperatorEntityDelegates
import org.treeWare.mySql.operator.set
import rootEntityFactory
import java.time.Clock
import kotlin.system.exitProcess

private val logger = logging()

fun main() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val mysqlDataSource = getDataSource(config)
    if (mysqlDataSource == null) {
        logger.error { "MySQL is not enabled in application.conf" }
        exitProcess(1)
    }

    // TODO: add application.conf setting to decide if environment should be used as DB prefix
    val environment = config.property("service.deployment.environment").getString()

    val operatorEntityDelegateRegistry = OperatorEntityDelegateRegistry()
    registerMySqlOperatorEntityDelegates(operatorEntityDelegateRegistry)
    val createDatabaseDelegates = operatorEntityDelegateRegistry.get(GenerateDdlCommandsOperatorId)

    createDatabase(metaModel, createDatabaseDelegates, mysqlDataSource)

    // Create the root entity in the DB. It is required for creating other entities in the DB.
    val root = rootEntityFactory(null)
    setSetAux(root, SetAux.CREATE)
    val setEntityDelegates = operatorEntityDelegateRegistry.get(SetOperatorId)
    set(root, setEntityDelegates, mysqlDataSource, clock = Clock.systemUTC())
}