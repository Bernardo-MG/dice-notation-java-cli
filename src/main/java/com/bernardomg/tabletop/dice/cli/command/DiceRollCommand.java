
package com.bernardomg.tabletop.dice.cli.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.notation.DiceNotationExpression;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Print command. Received a text and prints it on screen.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
@Command(name = "roll", description = "Rolls a dice notation expression")
public final class DiceRollCommand implements Runnable {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DiceRollCommand.class);

    @Parameters(index = "0", description = "The expression to roll",
            paramLabel = "EXP")
    private String              expression;

    public DiceRollCommand() {
        super();
    }

    @Override
    public final void run() {
        final DiceParser parser;
        final DiceNotationExpression parsed;
        final DiceInterpreter<RollHistory> roller;
        final RollHistory rolls;

        LOGGER.debug("Running expression {}", expression);

        parser = new DefaultDiceParser();

        parsed = parser.parse(expression);

        roller = new DiceRoller();

        rolls = roller.transform(parsed);

        // Prints the final result
        System.out.println(rolls.getTotalRoll());
    }

}
