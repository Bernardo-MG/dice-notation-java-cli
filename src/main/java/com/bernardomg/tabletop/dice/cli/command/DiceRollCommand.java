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
 * Roll command. Receives an expression, rolls it and prints the result on
 * screen.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
@Command(name = "roll", description = "Rolls an expression",
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
