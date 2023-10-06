import Property.CliProperties.APP_NAME
import kotlinx.cli.ArgParser
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    SymbolicatorClientApplication().execute(args)
    exitProcess(0)
}

class SymbolicatorClientApplication(
) {

    fun execute(args: Array<String>) {
        val argParser = ArgParser(programName = APP_NAME)

        argParser.subcommands(
            PrintCommand(),
        )
        argParser.parse(args.ifEmpty { arrayOf("-h") })
    }
}