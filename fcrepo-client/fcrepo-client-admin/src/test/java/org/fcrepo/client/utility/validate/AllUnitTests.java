/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */

package org.fcrepo.client.utility.validate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.JUnit4TestAdapter;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        org.fcrepo.client.utility.validate.process.AllUnitTests.class,
        org.fcrepo.client.utility.validate.types.AllUnitTests.class,
        TestObjectValidator.class})
public class AllUnitTests {

    // Supports legacy tests runners
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AllUnitTests.class);
    }
}
