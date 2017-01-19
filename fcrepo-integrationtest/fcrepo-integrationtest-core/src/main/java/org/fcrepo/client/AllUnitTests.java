/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */
package org.fcrepo.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.fcrepo.client.AllUnitTests;

@RunWith(Suite.class)
@Suite.SuiteClasses( {org.fcrepo.client.utility.ingest.AllUnitTests.class})
public class AllUnitTests {

    // Supports legacy tests runners
    public static junit.framework.Test suite() throws Exception {

        junit.framework.TestSuite suite =
                new junit.framework.TestSuite(AllUnitTests.class.getName());

        suite.addTest(org.fcrepo.client.utility.ingest.AllUnitTests.suite());

        return suite;
    }
}
