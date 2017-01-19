/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */

package org.fcrepo.server.journal.readerwriter.multicast.request;

import org.fcrepo.server.journal.JournalException;
import org.fcrepo.server.journal.readerwriter.multicast.Transport;

/**
 * The base class for a request that can be sent to each Transport in turn.
 */
public abstract class TransportRequest {

    public abstract void performRequest(Transport transport)
            throws JournalException;
}