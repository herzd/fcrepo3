/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also 
 * available online at http://fedora-commons.org/license/).
 */
package org.fcrepo.server.resourceIndex;

import java.io.OutputStream;
import java.util.List;

import org.trippi.RDFFormat;
import org.trippi.TriplestoreWriter;
import org.fcrepo.server.errors.ResourceIndexException;
import org.fcrepo.server.storage.DOReader;
import org.jrdf.graph.Triple;


/**
 * The main interface to the Fedora Resource Index. The Resource Index (RI)
 * provides read/write access to an RDF representation of all objects in the
 * Fedora repository. The information stored in the RI is derived solely from
 * information stored within the digital objects.
 * 
 * @author Edwin Shin
 * @author Chris Wilper
 */
public interface ResourceIndex
        extends TriplestoreWriter {

    /**
     * At this level, the ResourceIndex will not index anything.
     */
    public static final int INDEX_LEVEL_OFF = 0;

    /**
     * At this level the ResourceIndex will index: object properties datastreams
     * intra-object dependencies
     */
    public static final int INDEX_LEVEL_ON = 1;

    /**
     * Gets the index level of the ResourceIndex.
     * 
     * @return the current index level of the RI, which is either
     *         INDEX_LEVEL_OFF or INDEX_LEVEL_ON.
     */
    int getIndexLevel();

    /**
     * Gets the default state of buffer synchronization. When this is
     * true, all operations are flushed on completion.
     * @return boolean
     */
    boolean getSync();
    /**
     * Adds the appripriate triples implied by the given object to the
     * ResourceIndex.
     * 
     * @param reader
     *        The given object to index.
     * @throws ResourceIndexException
     *         If the triples can't be added for any reason.
     */
    void addObject(DOReader reader) throws ResourceIndexException;

    /**
     * Updates any appropriate triples implied a modified object.
     * 
     * @param oldReader
     *        Pre-modification version of the oject.
     * @param newReader
     *        Post-modification version of the object.
     * @throws ResourceIndexException
     *         If the triples can't be updated for any reason.
     */
    void modifyObject(DOReader oldReader, DOReader newReader)
            throws ResourceIndexException;

    /**
     * Removes the triples implied by a given object from the ResourceIndex.
     * 
     * @param oldReader
     *        Object whose triples shall be removed from the index.
     * @throws ResourceIndexException
     *         If the triples can't be removed for any reason.
     */
    void deleteObject(DOReader oldReader) throws ResourceIndexException;

    /**
     * Export the triples indexed from a given object.
     * Adding an object is the equivalent of inserting these triples.
     * Deleting an object is the equivalent of deleting these triples.
     * Modifying an object is the equivalent of processing the difference
     * between the export before and after a change.
     * @param object
     * @return List&lt;Triple&gt;
     * @throws ResourceIndexException
     */
    List<Triple> exportObject(DOReader object) throws ResourceIndexException;

    /**
     * Exports all triples in the RI.
     * 
     * @param out
     *        the output stream to which the RDF should be written. The caller
     *        is responsible for eventually closing this stream.
     * @param format
     *        the output format (RDF_XML, TURTLE, N_TRIPLESs, etc).
     * @throws ResourceIndexException
     *         if triples in the RI cannot be serialized for any reason.
     */
    void export(OutputStream out, RDFFormat format)
            throws ResourceIndexException;

}
