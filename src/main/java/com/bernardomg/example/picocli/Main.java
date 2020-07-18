
package com.bernardomg.example.picocli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.example.picocli.command.MenuCommand;

import picocli.CommandLine;

/**
 * Main executable class.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
public class Main {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public Main() {
        super();
    }

    public static void main(final String[] args) {
        final Integer exitCode;

        exitCode = new CommandLine(new MenuCommand()).execute(args);

        LOGGER.debug("Exited with code {}", exitCode);

        System.exit(exitCode);
    }

}
