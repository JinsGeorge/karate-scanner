package io.github.jinsgeorge.karate.scanner.karate.scanner.cli;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "karate-scanner",
        description = "Karate code quality and linting CLI",
        subcommands = { ScanCommand.class },
        mixinStandardHelpOptions = true
)
public class KarateScannerCli implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(KarateScannerCli.class);

    @Override
    public Integer call() {
        logger.info("Use a subcommand 'scan'. Try --help for more.");
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new KarateScannerCli()).execute(args);
        System.exit(exitCode);
    }
}
