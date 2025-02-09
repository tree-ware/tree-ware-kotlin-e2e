package server.custom

import org.treeWare.model.core.EntityModel
import org.treeWare.model.operator.Response

internal fun customGetRequestValidation(getRequest: EntityModel): Response {
    return Response.Success
}