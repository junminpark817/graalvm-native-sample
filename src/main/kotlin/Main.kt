import property.CliProperties.APP_NAME
import kotlinx.cli.ArgParser

fun main(args: Array<String>) {
    NativeImageSampleApplication().execute(args)
}

class NativeImageSampleApplication(
) {

    fun execute(args: Array<String>) {
        val argParser = ArgParser(programName = APP_NAME)

        argParser.subcommands(
            PrintCommand(),
        )
        argParser.parse(args.ifEmpty { arrayOf("-h") })
    }
}
