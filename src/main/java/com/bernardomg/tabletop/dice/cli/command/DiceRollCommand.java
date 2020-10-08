/**
 * Copyright 2020 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.tabletop.dice.cli.command;

import java.io.PrintWriter;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.tabletop.dice.cli.version.ManifestVersionProvider;
import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.history.RollResult;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

/**
 * Roll command. Receives an expression, rolls it and prints the result on
 * screen.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
@Command(name = "roll", description = "Rolls an expression",
        mixinStandardHelpOptions = true,
        versionProvider = ManifestVersionProvider.class)
public final class DiceRollCommand implements Runnable {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DiceRollCommand.class);

    /**
     * Expression to roll.
     */
    @Parameters(index = "0", description = "The expression to roll",
            paramLabel = "EXP")
    private String              expression;

    /**
     * Roll history flag.
     */
    @Option(names = "-history", description = "Prints the roll history",
            defaultValue = "false")
    private Boolean             history;

    /**
     * Full history flag.
     */
    @Option(names = "-fullHistory",
            description = "Prints a detailed roll history",
            defaultValue = "false")
    private Boolean             historyDetailed;

    /**
     * Command specification. Used to get the line output.
     */
    @Spec
    private CommandSpec         spec;

    /**
     * Default constructor.
     */
    public DiceRollCommand() {
        super();
    }

    /**
     * Returns the history flag.
     * 
     * @return the history flag
     */
    public Boolean getHistory() {
        return history;
    }

    /**
     * Returns the detailed history flag.
     * 
     * @return the detailed history flag
     */
    public Boolean getHistoryDetailed() {
        return historyDetailed;
    }

    @Override
    public final void run() {
        final DiceParser parser;
        final DiceInterpreter<RollHistory> roller;
        final RollHistory rolls;
        final Integer totalRoll;
        final PrintWriter writer;
        String valuesText;

        LOGGER.debug("Running expression {}", expression);

        parser = new DefaultDiceParser();
        roller = new DiceRoller();

        rolls = parser.parse(expression, roller);

        totalRoll = rolls.getTotalRoll();

        LOGGER.debug("Total roll {}", totalRoll);
        LOGGER.debug("History: {}", rolls.toString());

        writer = spec.commandLine().getOut();

        // Prints the final result
        writer.println();
        writer.println("------------");
        writer.printf("Total roll: %d%n", totalRoll);

        if (history) {
            LOGGER.debug("Printing roll history");

            writer.println("------------");
            writer.printf("Roll history: %s%n", rolls.toString());
        }

        if (historyDetailed) {
            LOGGER.debug("Printing detailed roll history");

            writer.println("------------");
            writer.println("Detailed roll history");
            for (final RollResult result : rolls.getRollResults()) {
                // Values are grouped into a text
                valuesText = StreamSupport
                        .stream(result.getAllRolls().spliterator(), false)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                writer.printf(
                        "Rolled %dd%d getting values [%s] for a total roll of %d%n",
                        result.getDice().getQuantity(),
                        result.getDice().getSides(), valuesText,
                        result.getTotalRoll());
            }
        }

        writer.println("------------");
        writer.println();
    }

}
