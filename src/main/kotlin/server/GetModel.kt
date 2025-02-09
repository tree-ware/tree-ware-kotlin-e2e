package server

import org.treeWare.model.core.EntityFactory
import org.treeWare.model.core.EntityModel
import org.treeWare.model.operator.*
import org.treeWare.mySql.operator.get
import server.custom.customGetRequestValidation
import javax.sql.DataSource

fun getModel(
    getRequest: EntityModel,
    setEntityDelegates: EntityDelegateRegistry<SetEntityDelegate>?,
    getEntityDelegates: EntityDelegateRegistry<GetEntityDelegate>?,
    mySqlDataSource: DataSource?,
    rootEntityFactory: EntityFactory
): Response {
    val customValidationErrors = customGetRequestValidation(getRequest)
    if (!customValidationErrors.isOk()) return customValidationErrors
    if (mySqlDataSource == null) return Response.ErrorList(
        ErrorCode.SERVER_ERROR,
        listOf(ElementModelError("", "No connection to MySQL"))
    )
    val getResponse = rootEntityFactory(null)
    val response = get(getRequest, setEntityDelegates, getEntityDelegates, mySqlDataSource, getResponse)
    return if (response is Response.Success) Response.Model(getResponse) else response
}