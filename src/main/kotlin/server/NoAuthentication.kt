package server

import io.ktor.server.application.*
import io.ktor.server.auth.*

const val NO_AUTHENTICATION_PROVIDER_NAME = "no-authentication-provider"

fun Application.installNoAuthentication() {
    install(Authentication) {
        basic(NO_AUTHENTICATION_PROVIDER_NAME) {
            realm = "/"
            validate { credentials ->
                UserIdPrincipal(credentials.name) // accept any credentials
            }
        }
    }
}
