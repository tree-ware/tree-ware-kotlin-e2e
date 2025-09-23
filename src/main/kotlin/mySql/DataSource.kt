package mySql

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.HoconApplicationConfig
import org.lighthousegames.logging.logging
import javax.sql.DataSource

private val logger = logging()

fun getDataSource(config: HoconApplicationConfig): DataSource? {
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