
package com.bernardomg.tabletop.dice.cli.test.integration.command;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.tabletop.dice.cli.command.DiceRollCommand;

import picocli.CommandLine;

@DisplayName("DiceRollCommand history status")
public class ITDiceRollCommandHistory {

    public ITDiceRollCommandHistory() {
        super();
    }

    @Test
    @DisplayName("The rolls history is activated when the argument is received")
    public final void testExecute_History() {
        final DiceRollCommand command;
        final CommandLine cmd;
        final StringWriter sw;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("1d1", "-history");

        Assertions.assertTrue(command.getHistory());
    }

    @Test
    @DisplayName("The detailed rolls history is activated when the argument is received")
    public final void testExecute_HistoryDetailed() {
        final DiceRollCommand command;
        final CommandLine cmd;
        final StringWriter sw;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("1d1", "-fullHistory");

        Assertions.assertTrue(command.getHistoryDetailed());
    }

    @Test
    @DisplayName("The rolls history is not activated when the argument isn't received")
    public final void testExecute_NoHistory() {
        final DiceRollCommand command;
        final CommandLine cmd;
        final StringWriter sw;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("1d1");

        Assertions.assertFalse(command.getHistory());
    }

    @Test
    @DisplayName("The detailed rolls history is not activated when the argument isn't received")
    public final void testExecute_NoHistoryDetailed() {
        final DiceRollCommand command;
        final CommandLine cmd;
        final StringWriter sw;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        sw = new StringWriter();
        cmd.setOut(new PrintWriter(sw));

        cmd.execute("1d1");

        Assertions.assertFalse(command.getHistoryDetailed());
    }

}
