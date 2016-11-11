/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */
package org.fcrepo.server.storage.translation;

/**
 * Serializes objects in Atom Zip 1.1 format.
 *
 * @author Edwin Shin
 * @since 3.0
 * @version $Id$
 */
public class AtomZip1_1DOSerializer
        extends AtomDOSerializer {

    public AtomZip1_1DOSerializer() {
        this(null);
    }
    public AtomZip1_1DOSerializer(DOTranslationUtility translator) {
        super(ATOM_ZIP1_1, translator);
    }
}
