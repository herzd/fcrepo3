/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also 
 * available online at http://fedora-commons.org/license/).
 */
package org.fcrepo.server.journal.managementmethods;

import org.fcrepo.server.errors.ServerException;
import org.fcrepo.server.journal.entry.JournalEntry;
import org.fcrepo.server.management.ManagementDelegate;


/**
 * Adapter class for Management.setDatastreamVersionable()
 * 
 * @author Jim Blake
 */
public class SetDatastreamVersionableMethod
        extends ManagementMethod {

    public SetDatastreamVersionableMethod(JournalEntry parent) {
        super(parent);
    }

    @Override
    public Object invoke(ManagementDelegate delegate) throws ServerException {
        return delegate.setDatastreamVersionable(parent.getContext(), parent
                .getStringArgument(ARGUMENT_NAME_PID), parent
                .getStringArgument(ARGUMENT_NAME_DS_ID), parent
                .getBooleanArgument(ARGUMENT_NAME_VERSIONABLE), parent
                .getStringArgument(ARGUMENT_NAME_LOG_MESSAGE));
    }

}
