package server

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging
import org.treeWare.server.ktor.commonModule
import org.treeWare.server.ktor.treeWareModule
import kotlin.system.exitProcess

private val logger = logging()

fun main() = runBlocking {
    val config = HoconApplicationConfig(ConfigFactory.load())

    val metaModelInfo = metaModel.getInfo(config)
    if (metaModelInfo == null) {
        logger.error { metaModel.INFO_NOT_FOUND_ERROR }
        exitProcess(1)
    }

    val serviceName = config.property("service.name").getString()
    val environment = config.property("service.deployment.environment").getString()
    val servicePort = config.property("service.deployment.port").getString().toInt()
    logger.info { "environment: $environment" }
    logger.info { "servicePort: $servicePort" }

    val mySqlDataSource = mySql.getDataSource(config)
    val treeWareServer = newTreeWareServer(environment, metaModelInfo.metaModelFiles, metaModelInfo::rootEntityFactory, mySqlDataSource)

    logger.info { "starting service: $serviceName" }
    embeddedServer(Netty, servicePort) {
        installNoAuthentication()
        commonModule()
        treeWareModule(treeWareServer, NO_AUTHENTICATION_PROVIDER_NAME)
    }.start(wait = true)
    logger.info { "exited service: $serviceName" }
}