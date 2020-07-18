
package com.bernardomg.tabletop.dice.cli.command;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.history.RollResult;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Print command. Received a text and prints it on screen.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
@Command(name = "roll", description = "Rolls a dice notation expression",
        mixinStandardHelpOptions = true)
public final class DiceRollCommand implements Runnable {

    /**
     * Logger.
     */
    private static final Logger LOGGER          = LoggerFactory
            .getLogger(DiceRollCommand.class);

    @Parameters(index = "0", description = "The expression to roll",
            paramLabel = "EXP")
    private String              expression;

    @Option(names = "-history", description = "Prints the roll history")
    private Boolean             history         = false;

    @Option(names = "-fullHistory",
            description = "Prints a detailed roll history")
    private Boolean             historyDetailed = false;

    public DiceRollCommand() {
        super();
    }

    @Override
    public final void run() {
        final DiceParser parser;
        final DiceInterpreter<RollHistory> roller;
        final RollHistory rolls;
        final Integer totalRoll;
        String valuesText;

        LOGGER.debug("Running expression {}", expression);

        parser = new DefaultDiceParser();
        roller = new DiceRoller();

        rolls = parser.parse(expression, roller);

        totalRoll = rolls.getTotalRoll();

        LOGGER.debug("Total roll {}", totalRoll);
        LOGGER.debug("History: ", rolls.toString());

        // Prints the final result
        System.out.println();
        System.out.println("------------");
        System.out.println("Total roll: " + totalRoll);

        if (history) {
            System.out.println("------------");
            System.out.println("Roll history: " + rolls.toString());
        }

        if (historyDetailed) {
            System.out.println("------------");
            System.out.println("Detailed roll history");
            for (final RollResult result : rolls.getRollResults()) {
                // Values are grouped into a text
                valuesText = StreamSupport
                        .stream(result.getAllRolls().spliterator(), false)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                System.out.println("Rolled " + result.getDice().getQuantity()
                        + "d" + result.getDice().getSides()
                        + " getting values [" + valuesText + "] for a total of "
                        + result.getTotalRoll());
            }
        }

        System.out.println("------------");
        System.out.println();
    }

}
