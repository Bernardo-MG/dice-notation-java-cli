
package com.bernardomg.tabletop.dice.cli.version;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import picocli.CommandLine;
import picocli.CommandLine.IVersionProvider;

public class ManifestVersionProvider implements IVersionProvider {

    public ManifestVersionProvider() {
        super();
    }

    @Override
    public String[] getVersion() throws Exception {
        final Enumeration<URL> resources = CommandLine.class.getClassLoader()
                .getResources("META-INF/MANIFEST.MF");
        while (resources.hasMoreElements()) {
            final URL url = resources.nextElement();
            try {
                final Manifest manifest = new Manifest(url.openStream());
                if (isApplicableManifest(manifest)) {
                    final Attributes attr = manifest.getMainAttributes();
                    return new String[] { get(attr, "Implementation-Title")
                            + " version \""
                            + get(attr, "Implementation-Version") + "\"" };
                }
            } catch (final IOException ex) {
                return new String[] {
                        "Unable to read from " + url + ": " + ex };
            }
        }
        return new String[0];
    }

    private boolean isApplicableManifest(final Manifest manifest) {
        final Attributes attributes = manifest.getMainAttributes();
        return "picocli".equals(get(attributes, "Implementation-Title"));
    }

    private static Object get(final Attributes attributes, final String key) {
        return attributes.get(new Attributes.Name(key));
    }

}
