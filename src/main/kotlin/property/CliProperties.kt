package property

object CliProperties {
    val APP_NAME = System.getProperty("appName")?.removeSurrounding("\"") ?: "undefined"
}
