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

package com.bernardomg.tabletop.dice.cli.version;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.google.common.base.Optional;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.IVersionProvider;

/**
 * Version provider based on the JAR manifest.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class ManifestVersionProvider implements IVersionProvider {

    /**
     * Manifest implementation title key.
     */
    private static final Attributes.Name KEY_TITLE     = new Attributes.Name("Implementation-Title");

    /**
     * Manifest implementation vesion key.
     */
    private static final Attributes.Name KEY_VERSION   = new Attributes.Name("Implementation-Version");

    /**
     * Path to the manifest file.
     */
    private static final String          MANIFEST_PATH = "META-INF/MANIFEST.MF";

    /**
     * Project title. Used to identify the correct manifest.
     */
    private static final String          PROJECT       = "Dice Notation Tools CLI";

    /**
     * Default constructor.
     */
    public ManifestVersionProvider() {
        super();
    }

    @Override
    public final String[] getVersion() throws Exception {
        final Enumeration<URL> resources;
        final String[]         result;
        Optional<String>       version;

        // Acquire URL to manifest file
        resources = CommandLine.class.getClassLoader()
            .getResources(MANIFEST_PATH);

        // Searches for version
        version = Optional.absent();
        while ((!version.isPresent()) && (resources.hasMoreElements())) {
            final Optional<Manifest> manifest;
            final Attributes         attr;

            // Only a single manifest file should exist
            // So this loop would be executed once

            // Tries to acquire the manifest from the URL
            manifest = getManifest(resources.nextElement());

            if ((manifest.isPresent()) && (isValid(manifest.get()))) {
                attr = manifest.get()
                    .getMainAttributes();

                version = Optional.of(getVersion(attr));
            }
        }

        // Check if the version was found
        // If so, it will be returned
        if (version.isPresent()) {
            result = new String[] { version.get() };
        } else {
            result = new String[0];
        }

        return result;
    }

    /**
     * Returns the JAR manifest info structure.
     *
     * @param url
     *            URL to the manifest file
     * @return the manifest structure
     */
    private final Optional<Manifest> getManifest(final URL url) {
        final Manifest     manifest;
        Optional<Manifest> result;

        try {
            manifest = new Manifest(url.openStream());
            result = Optional.of(manifest);
        } catch (final IOException ex) {
            log.error("Unable to read from {}", url);
            result = Optional.absent();
        }

        return result;
    }

    /**
     * Returns the implementation version from the received attributes.
     * <p>
     * It will try to combine the implementation name and version in a string like:
     * <p>
     * {@code Implementation-Title version Implementation-Version}.
     * <p>
     * If the version is missing, then said part of the string will be also missing from the result.
     *
     * @param attr
     *            attributes with the version
     * @return the implementation version
     */
    private final String getVersion(final Attributes attr) {
        final StringBuilder version;

        version = new StringBuilder();

        // Adds implementation title
        version.append(attr.get(KEY_TITLE));
        version.append(" ");

        // Adds implementation version if it exists
        if (attr.containsKey(KEY_VERSION)) {
            version.append("version");
            version.append(" ");
            version.append(attr.get(KEY_VERSION));
        }

        return version.toString();
    }

    /**
     * Checks if the manifest is the correct one.
     *
     * @param manifest
     *            manifest to check
     * @return {@code true} if it is the expected manifest, {@code false} in other case
     */
    private final Boolean isValid(final Manifest manifest) {
        final Attributes attributes;
        final Object     title;
        final Boolean    valid;

        attributes = manifest.getMainAttributes();

        if (attributes.containsKey(KEY_TITLE)) {
            title = attributes.get(KEY_TITLE);
            valid = PROJECT.equals(title);
        } else {
            valid = false;
        }

        return valid;
    }

}
