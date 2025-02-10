# tree-ware-kotlin-e2e-shell

* Add tree-ware meta-model JSON files to `src/main/resources/tree_ware/meta_model/`
  * [You can configure IntelliJ with live-templates][1] to make it easy to specify these meta-model JSON files
* Run the following Gradle tasks in the `tree-ware` group
  * `generateDiagrams`
  * `generateKotlin`
  * `generateOpenApiSpec`
  * The generated files will be in `build/treeWare/`
* At the start of `main()` in `Main.kt`:
  * Set `metaModelFiles` to the generated list of meta-model files
  * Set `rootEntityFactory` to the generated `RootEntityFactory` function
  * The generated list and function will be in `<MetaModelName>MetaModel.kt` file under `build/treeWare/src/main/kotlin`
* Set the environment variables listed in `src/main/resources/application.conf` or stick with the defaults in that file

[1]: https://github.com/tree-ware/tree-ware-kotlin-core/tree/master/.idea/templates