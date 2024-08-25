package server

import org.treeWare.model.core.MainModel
import org.treeWare.model.core.MutableMainModel
import org.treeWare.model.operator.*
import org.treeWare.mySql.operator.get
import server.custom.customGetRequestValidation
import javax.sql.DataSource

fun getModel(
    getRequest: MainModel,
    setEntityDelegates: EntityDelegateRegistry<SetEntityDelegate>?,
    getEntityDelegates: EntityDelegateRegistry<GetEntityDelegate>?,
    mySqlDataSource: DataSource?
): Response {
    val customValidationErrors = customGetRequestValidation(getRequest)
    if (!customValidationErrors.isOk()) return customValidationErrors
    if (mySqlDataSource == null) return Response.ErrorList(
        ErrorCode.SERVER_ERROR,
        listOf(ElementModelError("", "No connection to MySQL"))
    )
    val getResponse = MutableMainModel(getRequest.mainMeta) // TODO(deepak-nulu): pass & use MutableMainModelFactory
    val response = get(getRequest, setEntityDelegates, getEntityDelegates, mySqlDataSource, getResponse)
    // TODO(deepak-nulu): change get() to return a response model instead of taking in a response model parameter.
    return if (response is Response.Success) Response.Model(getResponse) else response
}