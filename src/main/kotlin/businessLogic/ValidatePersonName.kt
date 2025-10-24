package businessLogic

import org.treeWare.demo.movieHub.Root
import org.treeWare.demo.movieHub.movieHub
import org.treeWare.model.core.EntityModel
import org.treeWare.model.operator.ElementModelError
import org.treeWare.model.operator.ErrorCode
import org.treeWare.model.operator.Response
import org.treeWare.server.common.BusinessLogicFunction

class ValidatePersonName : BusinessLogicFunction {
    override val inputShape: EntityModel = movieHub {
        people {
            person {
                id = null
                name = null
            }
        }
    }

    override val outputShape: EntityModel? = null

    override fun invoke(model: EntityModel): Response {
        val errors = mutableListOf<ElementModelError>()
        val root = model as Root
        root.people?.forEach { person ->
            if (person.name == "") {
                val nameFieldPath = "/people/${person.id}/name"
                errors.add(ElementModelError(nameFieldPath, "name is empty"))
            }
        }
        if (errors.isEmpty()) return Response.Success
        return Response.ErrorList(ErrorCode.CLIENT_ERROR, errors)
    }
}