import org.apache.log4j.Logger;
import org.bm.files.rules.ResultBuilder;
import org.bm.files.rules.Statuses;
import org.bm.rules.Entry;
import org.bm.rules.KeyPair;
import org.bm.rules.Result;
import org.bm.rules.Rule;
import org.bm.rules.impl.KeyPairImpl;
import org.bm.rules.impl.StringEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * .
 *
 * @author Baptiste Morin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-spring-context.xml"})
public class Tests {

    private final Logger LOG = Logger.getLogger(Tests.class);

    @Autowired
    private ResultBuilder resultBuilder;

    @Autowired
    private Rule testRule;


    @Autowired
    private ApplicationContext context;



    @Test
    public void testStatuses() {
        for (Statuses status : Statuses.values()) {
            assertNotNull("Status " + status.name() + " must not be null", status.getStatus());
        }
    }

    @Test
    public void testSpring() {

        assertNotNull(resultBuilder);
        assertNotNull(testRule);
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyResultBuilder() {
        // Should throw a NullPointerException because an empty result is not valid.
        LOG.info("Entering testEmptyResultBuilder");
        resultBuilder.build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullOkResultBuilder() {
        // Should throw a NullPointerException because an empty result is not valid.
        LOG.info("Entering testNullOkResultBuilder");
        resultBuilder.with(Statuses.OK).build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullNullResultBuilder() {
        // Should throw a NullPointerException because an empty result is not valid.
        LOG.info("Entering testNullNullResultBuilder");
        resultBuilder.with((Statuses) null).with((KeyPair<Entry, Rule>) null).build();
    }

    @Test
    public void testValidResultBuilder() {
        LOG.info("Entering testValidResultBuilder");
        Result result = resultBuilder.with(new KeyPairImpl<Entry, Rule>(new StringEntry(""), testRule)).with(Statuses.OK).build();
        assertNotNull(result);
    }


}
