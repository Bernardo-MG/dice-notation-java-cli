
package com.bernardomg.example.picocli.command;

import com.bernardomg.example.picocli.version.ManifestVersionProvider;

import picocli.CommandLine.Command;

/**
 * Main command, grouping the other available commands.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
@Command(name = "print", description = "Prints a text in the screen",
        subcommands = { PrintTextCommand.class },
        mixinStandardHelpOptions = true,
        versionProvider = ManifestVersionProvider.class)
public final class MenuCommand implements Runnable {

    @Override
    public final void run() {}

}
