# tree-ware-kotlin-e2e

* Add tree-ware meta-model JSON files to `src/main/resources/tree_ware/meta_model/`
  * [You can configure IntelliJ with live-templates][1] to make it easy to specify these meta-model JSON files
* Run the `generate` Gradle task in the `tree-ware` group to generate the following:
  * A UML diagram of the meta-model
  * A typesafe Kotlin DSL to create models
  * An OpenAPI spec
  * The generated files will be in `build/treeWare/`
* Set the following in `src/main/resources/application.conf`, or set their corresponding environment variables:
  * `service.name`
  * `service.metaModel.name` (from the meta-model definition)
  * `service.metaModel.package` (from the meta-model definition)
  * Any other variable that needs to be altered to match your deployment
* Start the dependencies by running `docker compose -f ./compose-dependencies.yaml up`
* Run `CreateDatabase.kt` to create the database and tables in MySQL
* Run `RunServer.kt` to start the tree-ware service
* Call the tree-ware API
  * Authentication is not enforced, but a user name and password needs to be specified using Basic auth header

[1]: https://github.com/tree-ware/tree-ware-kotlin-core/tree/master/.idea/templates