package server

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging
import org.treeWare.server.ktor.commonModule
import org.treeWare.server.ktor.treeWareModule
import javax.sql.DataSource

private const val SERVICE_NAME = "e2e-shell"

private val logger = logging()

fun main() = runBlocking {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val environment = config.property("service.deployment.environment").getString()
    val servicePort = config.property("service.deployment.port").getString().toInt()
    logger.info { "environment: $environment" }
    logger.info { "servicePort: $servicePort" }

    val mySqlDataSource = getMySqlDataSource(config)

    val treeWareServer = newTreeWareServer(environment, mySqlDataSource)

    logger.info { "starting service: $SERVICE_NAME" }
    embeddedServer(Netty, servicePort) {
        commonModule()
        treeWareModule(treeWareServer)
    }.start(wait = false)
    logger.info { "exited service: $SERVICE_NAME" }
}

private fun getMySqlDataSource(config: HoconApplicationConfig): DataSource? {
    val mySqlEnabled = config.propertyOrNull("service.mysql.enabled")?.getString().toBoolean()
    logger.info { "mySqlEnabled: $mySqlEnabled" }
    if (!mySqlEnabled) return null

    val mySqlUrl = config.property("service.mysql.url").getString()
    val mySqlUser = config.property("service.mysql.user").getString()
    val mySqlPassword = config.property("service.mysql.password").getString()
    logger.info { "mySql: $mySqlUrl" }

    return HikariDataSource().apply {
        jdbcUrl = mySqlUrl
        username = mySqlUser
        password = mySqlPassword
        isAutoCommit = false
    }
}