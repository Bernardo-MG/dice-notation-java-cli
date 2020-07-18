
package com.bernardomg.example.picocli.command;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Print command. Received a text and prints it on screen.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
@Command(name = "print", description = "Prints a text in the screen")
public final class PrintTextCommand implements Runnable {

    @Parameters(index = "0", description = "The text to print in the screen",
            paramLabel = "TEXT")
    private String text;

    public PrintTextCommand() {
        super();
    }

    @Override
    public final void run() {
        System.out.println("Printing text: " + text);
    }

}
