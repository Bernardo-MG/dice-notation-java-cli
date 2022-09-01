
package com.bernardomg.tabletop.dice.cli.test.integration.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.tabletop.dice.cli.command.DiceRollCommand;

import picocli.CommandLine;

@DisplayName("DiceRollCommand exit codes")
public class ITDiceRollCommandExitCode {

    public ITDiceRollCommandExitCode() {
        super();
    }

    @Test
    @DisplayName("An invalid expression returns an error exit code")
    public final void testExecute_Invalid_ExitCode() {
        final Runnable    command;
        final CommandLine cmd;
        final Integer     exitCode;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        exitCode = cmd.execute("abc");

        Assertions.assertEquals(1, exitCode);
    }

    @Test
    @DisplayName("A valid expression returns an OK exit code")
    public final void testExecute_Valid_ExitCode() {
        final Runnable    command;
        final CommandLine cmd;
        final Integer     exitCode;

        command = new DiceRollCommand();
        cmd = new CommandLine(command);

        exitCode = cmd.execute("1d6");

        Assertions.assertEquals(0, exitCode);
    }

}
