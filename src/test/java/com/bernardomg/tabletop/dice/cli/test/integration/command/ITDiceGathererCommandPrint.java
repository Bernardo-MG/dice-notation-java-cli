
package com.bernardomg.tabletop.dice.cli.test.integration.command;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.tabletop.dice.cli.command.DiceGathererCommand;

import picocli.CommandLine;

@DisplayName("DiceGathererCommand exit codes")
public class ITDiceGathererCommandPrint {

    public ITDiceGathererCommandPrint() {
        super();
    }

    @Test
    @DisplayName("A valid expression prints the expected output")
    public final void testExecute() {
        final Runnable     command;
        final CommandLine  cmd;
        final StringWriter sw;
        final String       expected;

        command = new DiceGathererCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("1d1");

        expected = "\n------------\nFound 1 dice sets\n------------\n1d1\n------------\n\n";
        Assertions.assertEquals(expected.replaceAll("\\n|\\r\\n", System.lineSeparator()), sw.toString());
    }

    @Test
    @DisplayName("An invalid expression prints no output")
    public final void testExecute_Invalid() {
        final Runnable     command;
        final CommandLine  cmd;
        final StringWriter sw;

        command = new DiceGathererCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("abc");

        Assertions.assertEquals("", sw.toString());
    }

}
