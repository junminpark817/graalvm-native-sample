import Model.SampleData
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.cli.Subcommand

val objectMapper: ObjectMapper = ObjectMapper()
    .registerKotlinModule()
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .findAndRegisterModules()

class PrintCommand() : Subcommand(name = "print", actionDescription = "print sample data") {

    override fun execute() {
        val json = checkNotNull(javaClass.getResource("META-INF/data.json")).readText()
        val sampleData = readValue<SampleData>(json)
        sampleData.data.forEach {
            println("key: ${it.key} -> value: ${it.value}")
        }
    }
}

private inline fun <reified T> readValue(json: String): T {
    return objectMapper.readValue(json)
}
