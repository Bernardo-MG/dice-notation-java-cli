
package com.bernardomg.tabletop.dice.cli.test.integration.command;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.tabletop.dice.cli.command.DiceRollCommand;

import picocli.CommandLine;

@DisplayName("DiceRollCommand exit codes")
public class ITDiceRollCommandPrint {

    public ITDiceRollCommandPrint() {
        super();
    }

    @Test
    @DisplayName("A valid expression prints the expected output")
    public final void testExecute() {
        final Runnable     command;
        final CommandLine  cmd;
        final StringWriter sw;
        final String       expected;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("1d1");

        expected = "\n------------\nTotal roll: 1\n------------\n\n";
        Assertions.assertEquals(expected.replaceAll("\\n|\\r\\n", System.lineSeparator()), sw.toString());
    }

    @Test
    @DisplayName("The rolls history is printed when the argument is received")
    public final void testExecute_History() {
        final Runnable     command;
        final CommandLine  cmd;
        final StringWriter sw;
        final String       expected;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("1d1", "-history");

        expected = "\n------------\nTotal roll: 1\n------------\nRoll history: 1\n------------\n\n";
        Assertions.assertEquals(expected.replaceAll("\\n|\\r\\n", System.lineSeparator()), sw.toString());
    }

    @Test
    @DisplayName("An invalid expression prints no output")
    public final void testExecute_Invalid() {
        final Runnable     command;
        final CommandLine  cmd;
        final StringWriter sw;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("abc");

        Assertions.assertEquals("", sw.toString());
    }

}
