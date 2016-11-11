/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */

package org.fcrepo.server.storage.translation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.custommonkey.xmlunit.XMLTestCase;
import org.jrdf.graph.URIReference;
import org.fcrepo.common.PID;
import org.fcrepo.server.Context;
import org.fcrepo.server.errors.StreamIOException;
import org.fcrepo.server.storage.types.BasicDigitalObject;
import org.fcrepo.server.storage.types.DSBinding;
import org.fcrepo.server.storage.types.DSBindingMap;
import org.fcrepo.server.storage.types.DatastreamManagedContent;
import org.fcrepo.server.storage.types.DatastreamReferencedContent;
import org.fcrepo.server.storage.types.DatastreamXMLMetadata;
import org.fcrepo.server.storage.types.DigitalObject;
import org.fcrepo.server.storage.types.Disseminator;



/**
 * Convenience superclass for serializer and deserializer tests.
 *
 * @author Chris Wilper
 */
@SuppressWarnings("deprecation")
public abstract class TranslationTest
        extends XMLTestCase {

    protected static final String TEST_PID = "test:pid";

    //---
    // Static helpers
    //---

    protected static DOTranslationUtility translationUtility() {
        Properties transProps = new Properties(System.getProperties());
        if (transProps.getProperty("fedora.hostname") == null) {
            transProps.setProperty("fedora.hostname","localhost");
        }
        if (transProps.getProperty("fedora.port") == null) {
            transProps.setProperty("fedora.port","1024");
        }
        if (transProps.getProperty("fedora.appServerContext") == null) {
            transProps.setProperty("fedora.appServerContext","fedora");
        }
        return new DOTranslationUtility.Impl(transProps, true);
    }
    protected static DigitalObject createTestObject(URIReference... contentModelURIs) {
        DigitalObject obj = new BasicDigitalObject();
        obj.setPid(TEST_PID);
        DatastreamXMLMetadata ds = createXDatastream("RELS-EXT");

        StringBuilder rdf = new StringBuilder();
        rdf
                .append("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" "
                        + "xmlns:fedora-model=\"info:fedora/fedora-system:def/model#\">\n"
                        + "<rdf:Description rdf:about=\"");
        rdf.append(PID.getInstance(TEST_PID).toURI() + "\">\n");

        for (URIReference model : contentModelURIs) {
            rdf.append("<fedora-model:hasModel rdf:resource=\""
                    + model.getURI().toString()
                    + "\"></fedora-model:hasModel>\n");
        }
        rdf.append("</rdf:Description></rdf:RDF>");
        ds.xmlContent = rdf.toString().getBytes();

        obj.addDatastreamVersion(ds, false);
        obj.setCreateDate(new Date());
        obj.setLastModDate(new Date());
        obj.setCreateDate(new Date());
        obj.setLastModDate(new Date());
        return obj;
    }

    protected static DatastreamXMLMetadata createXDatastream(String id) {
        DatastreamXMLMetadata ds = new DatastreamXMLMetadata();
        ds.DatastreamID = id;
        ds.DSVersionID = id + ".0";
        ds.DSControlGrp = "X";
        ds.xmlContent = "<doc/>".getBytes();
        ds.DSCreateDT = new Date();
        return ds;
    }

    protected static DatastreamReferencedContent createRDatastream(String id,
                                                                   String url) {
        DatastreamReferencedContent ds = new DatastreamReferencedContent();
        ds.DatastreamID = id;
        ds.DSVersionID = id + ".0";
        ds.DSControlGrp = "R";
        ds.DSLocation = url;
        return ds;
    }
    
    protected static DatastreamManagedContent createMDatastream(String id, final byte [] content) {
        DatastreamManagedContent dmc = new DatastreamManagedContent(){
            public InputStream getContentStream(Context ctx) throws StreamIOException {
                return new ByteArrayInputStream(content);
            }
        };
        dmc.DatastreamID = id;
        dmc.DSVersionID = id + ".0";
        dmc.DSControlGrp = "M";
        dmc.DSChecksumType = "MD5";
        dmc.DSChecksum = DigestUtils.md5Hex(content);
        return dmc;
    }

    protected static Disseminator createDisseminator(String id, int numBindings) {
        Disseminator diss = new Disseminator();
        diss.dissID = id;
        diss.dissVersionID = id + ".0";
        diss.bDefID = TEST_PID + "bdef";
        diss.sDepID = TEST_PID + "bmech";
        diss.dsBindMap = new DSBindingMap();
        // the following is only needed for METS
        diss.dsBindMapID = id + "bindMap";
        DSBinding[] dsBindings = new DSBinding[numBindings];
        for (int i = 1; i <= numBindings; i++) {
            dsBindings[i - 1] = new DSBinding();
            dsBindings[i - 1].bindKeyName = "KEY" + i;
            dsBindings[i - 1].datastreamID = "DS" + i;
        }
        diss.dsBindMap.dsBindings = dsBindings;
        return diss;
    }

    protected void runConcurrent(Callable<?>[] callables) throws Exception {
        
        final int numThreads = callables.length;
        final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        try {
          final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
          final CountDownLatch afterInitBlocker = new CountDownLatch(1);
          final CountDownLatch allDone = new CountDownLatch(numThreads);
          for (final Callable<?> submittedTestCallable : callables) {
            threadPool.submit(new Callable<Object>() {
              public Object call() throws Exception {
                allExecutorThreadsReady.countDown();
                try {
                  afterInitBlocker.await();
                  return submittedTestCallable.call();
                } finally {
                  allDone.countDown();
                }
              }
            });
          }
          // wait until all threads are ready
          assertTrue("Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent", allExecutorThreadsReady.await(callables.length * 10, TimeUnit.MILLISECONDS));
          // start all test runners
          afterInitBlocker.countDown();
          assertTrue("Thread timeout! More than 5 seconds", allDone.await(5, TimeUnit.SECONDS));
        } finally {
          threadPool.shutdownNow();
        }
    }

}
