import org.bm.files.rules.ResultBuilder;
import org.bm.files.rules.Statuses;
import org.bm.rules.Entry;
import org.bm.rules.Result;
import org.bm.rules.Rule;
import org.bm.rules.impl.KeyPairImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * .
 *
 * @author Baptiste Morin
 */
@ContextConfiguration(locations = {"/test-spring-context.xml"})
public class TestRule implements Rule {
    @Autowired
    private ResultBuilder resultBuilder;

    @Override
    public Result apply(Entry entry) {
        return resultBuilder.with(new KeyPairImpl<Entry, Rule>(entry, this)).with(Statuses.OK).build();
    }

    @Override
    public String getDescription() {
        return "Test Rule returning OK result";
    }
}
