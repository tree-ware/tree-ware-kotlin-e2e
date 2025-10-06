package metaModel

import io.ktor.server.config.HoconApplicationConfig
import org.treeWare.metaModel.MetaModelInfo
import org.treeWare.util.snakeCaseToLowerCamelCase
import org.treeWare.util.snakeCaseToUpperCamelCase

const val INFO_NOT_FOUND_ERROR =
    "Unable to find meta-model information. The meta-model package and name must be specified in the application.conf file. They need to be lower_snake_case, just as in the meta-model JSON files."

fun getInfo(config: HoconApplicationConfig): MetaModelInfo? {
    val treeWarePackageName = config.property("service.metaModel.package").getString()
    val kotlinPackageName = treeWarePackageName.split(".").joinToString(".") { it.snakeCaseToLowerCamelCase() }

    val metaModelName = config.property("service.metaModel.name").getString()
    val metaModelInfoClassName = "${metaModelName.snakeCaseToUpperCamelCase()}MetaModelInfo"

    val fullyQualifiedName = "$kotlinPackageName.$metaModelInfoClassName"
    return runCatching { Class.forName(fullyQualifiedName).kotlin.objectInstance as MetaModelInfo }.getOrNull()
}