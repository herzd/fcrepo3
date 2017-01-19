/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */

package org.fcrepo.common.xml.format;

import org.fcrepo.common.xml.namespace.FedoraAccessNamespace;

/**
 * The Fedora Object Datastreams 1.0 XML format.
 *
 * <pre>
 * Format URI        : info:fedora/fedora-system:FedoraObjectDatastreams-1.0
 * Primary Namespace : http://www.fedora.info/definitions/1/0/access/
 * XSD Schema URL    : http://www.fedora-commons.org/definitions/1/0/listDatastreams.xsd
 * </pre>
 *
 * @author Chris Wilper
 */
public class FedoraObjectDatastreams1_0Format
        extends XMLFormat {

    /** The only instance of this class. */
    private static final FedoraObjectDatastreams1_0Format ONLY_INSTANCE =
            new FedoraObjectDatastreams1_0Format();

    /**
     * Constructs the instance.
     */
    private FedoraObjectDatastreams1_0Format() {
        super("info:fedora/fedora-system:FedoraObjectDatastreams-1.0",
              FedoraAccessNamespace.getInstance(),
              "http://www.fedora-commons.org/definitions/1/0/listDatastreams.xsd");
    }

    /**
     * Gets the only instance of this class.
     *
     * @return the instance.
     */
    public static FedoraObjectDatastreams1_0Format getInstance() {
        return ONLY_INSTANCE;
    }

}
