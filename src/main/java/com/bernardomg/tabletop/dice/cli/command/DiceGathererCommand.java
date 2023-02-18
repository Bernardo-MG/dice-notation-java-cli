/**
 * Copyright 2020-2023 the original author or authors
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

import com.bernardomg.tabletop.dice.Dice;
import com.bernardomg.tabletop.dice.cli.version.ManifestVersionProvider;
import com.bernardomg.tabletop.dice.interpreter.DiceGatherer;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;
import com.google.common.collect.Iterables;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

/**
 * Dice gatherer command. Receives an expression, gets all the dice sets on it and prints the result on screen.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Command(name = "gather", description = "Gathers dice from an expression", mixinStandardHelpOptions = true,
        versionProvider = ManifestVersionProvider.class)
@Slf4j
public final class DiceGathererCommand implements Runnable {

    /**
     * Expression to roll.
     */
    @Parameters(index = "0", description = "The expression to roll", paramLabel = "EXP")
    private String      expression;

    /**
     * Command specification. Used to get the line output.
     */
    @Spec
    private CommandSpec spec;

    /**
     * Default constructor.
     */
    public DiceGathererCommand() {
        super();
    }

    @Override
    public final void run() {
        final DiceParser                      parser;
        final DiceInterpreter<Iterable<Dice>> gatherer;
        final Iterable<Dice>                  diceSets;
        final String                          diceText;
        final PrintWriter                     writer;

        log.debug("Running expression {}", expression);

        parser = new DefaultDiceParser();
        gatherer = new DiceGatherer();

        diceSets = parser.parse(expression, gatherer);

        log.debug("Gathered dice sets {}", diceSets);

        writer = spec.commandLine()
            .getOut();

        // Builds the text
        diceText = StreamSupport.stream(diceSets.spliterator(), false)
            .map(this::getText)
            .collect(Collectors.joining(", "));

        // Prints the final result
        writer.println();
        writer.println("------------");
        writer.printf("Found %d dice sets%n", Iterables.size(diceSets));
        if (!diceText.isEmpty()) {
            writer.println("------------");
            writer.println(diceText);
        }
        writer.println("------------");
        writer.println();
    }

    private final String getText(final Dice dice) {
        return String.format("%dd%d", dice.getQuantity(), dice.getSides());
    }

}
