package businessLogic

import org.treeWare.server.common.BusinessLogicFunction

fun getBusinessLogicFunctions(): List<BusinessLogicFunction> = listOf(
    ValidatePersonName()
)