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

package com.bernardomg.tabletop.dice.cli.menu;

import com.bernardomg.tabletop.dice.cli.command.DiceGathererCommand;
import com.bernardomg.tabletop.dice.cli.command.DiceRollCommand;
import com.bernardomg.tabletop.dice.cli.version.ManifestVersionProvider;

import picocli.CommandLine.Command;

/**
 * Dice roller menu.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Command(description = "Handles roll operations", subcommands = { DiceRollCommand.class, DiceGathererCommand.class },
        mixinStandardHelpOptions = true, versionProvider = ManifestVersionProvider.class)
public class DiceMenu {

    /**
     * Default constructor.
     */
    public DiceMenu() {
        super();
    }

}
