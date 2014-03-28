import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bm.files.rules.ResultBuilder;
import org.bm.files.rules.Statuses;
import org.bm.rules.Engine;
import org.bm.rules.Entry;
import org.bm.rules.KeyPair;
import org.bm.rules.Result;
import org.bm.rules.ResultFormatter;
import org.bm.rules.Rule;
import org.bm.rules.RuleLoader;
import org.bm.rules.impl.GroovyRulesLoader;
import org.bm.rules.impl.KeyPairImpl;
import org.bm.rules.impl.StringEntry;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

    @Autowired
    private ResultFormatter resultFormatter;

    @Before
    public void before() {
        resultBuilder.clear();
    }

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
        LOG.info("Exiting testEmptyResultBuilder");
    }

    @Test(expected = NullPointerException.class)
    public void testNullOkResultBuilder() {
        // Should throw a NullPointerException because an empty result is not valid.
        LOG.info("Entering testNullOkResultBuilder");
        resultBuilder.with(Statuses.OK).build();
        LOG.info("Exiting testNullOkResultBuilder");
    }

    @Test(expected = NullPointerException.class)
    public void testNullNullResultBuilder() {
        // Should throw a NullPointerException because an empty result is not valid.
        LOG.info("Entering testNullNullResultBuilder");
        resultBuilder.with((Statuses) null).with((KeyPair<Entry, Rule>) null).build();
        LOG.info("Exiting testNullNullResultBuilder");
    }

    @Test
    public void testValidResultBuilder() {
        LOG.info("Entering testValidResultBuilder");
        Result result = resultBuilder.with(new KeyPairImpl<Entry, Rule>(new StringEntry(""), testRule)).with(Statuses.OK).build();
        assertNotNull(result);
        LOG.info("Exiting testValidResultBuilder");
    }

    @Test
    public void testRegExp() {
        Pattern p = (Pattern) context.getBean("optionalHeaderRegexp");

        assertTrue("Pattern should match the string", p.matcher("A1234;FTA       ;FTA       ;PBL            ;20140315021702;20140315021702").matches());

        p = (Pattern) context.getBean("headerRegexp");

        assertTrue("Pattern should match the string", p.matcher("H;WWWWWWWWWWW;DEU;12345678;EUR;SCITITMMXXX;0;D;0.00;14;20140319;201403;WWWWWWWWWWWWWWWWWWWWWW;AAAAAAAAAAA;XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX;XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX;DEU;FR12312312312;").matches());

    }

    @Test
    public void testSpringInRules() throws URISyntaxException, IllegalAccessException, IOException, InstantiationException {
        URL url = getClass().getResource("rules"); // Search in resources/rules directory

        File directory = new File(url.toURI());

        RuleLoader grl = new GroovyRulesLoader(new File[]{directory});
        grl.setApplicationContext(context);

        List<Rule> rules = grl.load();
        assertThat(rules.size(), new BaseMatcher<Integer>() {
            @Override
            public boolean matches(Object o) {
                return ((Integer) o) > 0;
            }

            @Override
            public void describeTo(Description description) {

            }
        });
        assertTrue(rules.size() > 0);

        File testFile = (File) context.getBean("testFile");
        List<String> lines = Files.readLines(testFile, Charset.defaultCharset());

        List<Entry> entries = Lists.newArrayList();
        for(int i = 0 ; i < lines.size() ; i++) {
            Entry entry = new StringEntry(lines.get(i));
            entry.getMetaDatas().put("lineNumber", i);
            entries.add(entry);
        }




        Engine engine = (Engine) context.getBean("engine");

        Map<KeyPair<Entry,Rule>,Result> resultMap = engine.process(entries, rules);

        for(KeyPair<Entry,Rule> keys : resultMap.keySet()) {
            Result result = resultMap.get(keys);
            LOG.info("Result: "+resultFormatter.format(result));
        }


    }


}
