package server.custom

import org.treeWare.model.core.MainModel
import org.treeWare.model.operator.Response

internal fun customGetRequestValidation(getRequest: MainModel): Response {
    return Response.Success
}