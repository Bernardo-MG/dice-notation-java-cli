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

import com.bernardomg.tabletop.dice.Dice;
import com.bernardomg.tabletop.dice.interpreter.DiceGatherer;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;
import com.google.common.collect.Iterables;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Dice gatherer command. Receives an expression, gets all the dice sets on it
 * and prints the result on screen.
 * 
 * @author Bernardo Martínez Garrido
 *
 */
@Command(name = "gather", description = "Gathers dice from an expression",
        mixinStandardHelpOptions = true)
public final class DiceGathererCommand implements Runnable {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DiceGathererCommand.class);

    @Parameters(index = "0", description = "The expression to roll",
            paramLabel = "EXP")
    private String              expression;

    public DiceGathererCommand() {
        super();
    }

    @Override
    public final void run() {
        final DiceParser parser;
        final DiceInterpreter<Iterable<Dice>> gatherer;
        final Iterable<Dice> diceSets;
        final String diceText;

        LOGGER.debug("Running expression {}", expression);

        parser = new DefaultDiceParser();
        gatherer = new DiceGatherer();

        diceSets = parser.parse(expression, gatherer);

        LOGGER.debug("Gathered dice sets {}", diceSets);

        // Prints the final result
        System.out.println();
        System.out.println("------------");
        System.out.println("Found " + Iterables.size(diceSets) + " dice sets");
        diceText = StreamSupport.stream(diceSets.spliterator(), false)
                .map(Object::toString).collect(Collectors.joining(", "));
        if (!diceText.isEmpty()) {
            System.out.println("------------");
            System.out.println(diceText);
        }
        System.out.println("------------");
        System.out.println();
    }

}
