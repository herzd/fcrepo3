
package org.fcrepo.server.security.xacml.util;

import java.util.Map;
import java.util.Set;

import org.fcrepo.server.security.xacml.MelcoeXacmlException;


public interface RelationshipResolver {


    /**
     * Retrieves the relationships for this subject. Values for each relationship
     * are placed in a map.  Return empty map if none found
     *
     * @param subject
     *        the subject to return relationships for - either ns:pid,
     *        ns:pid/datastream or the info:fedora/ forms
     * @return The map of relationships and values.
     * @throws MelcoeXacmlException
     */
    public Map<String, Set<String>> getRelationships(String subject)
            throws MelcoeXacmlException;


    /**
     * Retrieves relationships for this subject, relationship and object.  Return
     * empty map if none found.  Null means any.
     *
     * @param subject
     * @param relationship
     * @return Map&lt;String, Set&lt;String&gt;&gt; relationships
     * @throws MelcoeXacmlException
     */
    public Map<String, Set<String>> getRelationships(String subject,
                                                      String relationship) throws MelcoeXacmlException;

    /**
     * Generates a REST based representation of an object and its parents. For
     * example, given the parameter b, and if b belongs to collection a, then we
     * will end up with /a/b
     *
     * @param pid
     *        the pid whose parents we need to find
     * @return the REST representation of the pid and its parents
     * @throws MelcoeXacmlException
     */
    public String buildRESTParentHierarchy(String pid)
            throws MelcoeXacmlException;

    /**
     * Get attributes defined by a query.
     *
     * The values returned are defined by the variable defined in the query
     *
     * Only distinct values are returned.
     *
     * @param query The query to run
     * @param queryLang Language of the query - itql, sparql, spo
     * @param variable - the output variable to return (for spo, specify "s", "p" or "o")
     * @return Set&lt;String&gt; attributes
     * @throws MelcoeXacmlException
     */
    public Set<String> getAttributesFromQuery(String query, String queryLang, String variable) throws MelcoeXacmlException;
}
