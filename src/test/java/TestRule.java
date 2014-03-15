import org.bm.files.rules.ResultBuilder;
import org.bm.files.rules.Statuses;
import org.bm.rules.Entry;
import org.bm.rules.Result;
import org.bm.rules.Rule;
import org.bm.rules.impl.KeyPairImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    private ApplicationContext applicationContext;

    @Override
    public Result apply(Entry entry) {
        return resultBuilder.with(new KeyPairImpl<Entry, Rule>(entry, this)).with(Statuses.OK).build();
    }

    @Override
    public String getDescription() {
        return "Test Rule returning OK result";
    }

    @Override
    public long getPriority() {
        return 0;
    }

    @Override
    public int compareTo(Rule o) {
        // something is always greater than null.
        if (o == null) {
            return 1;
        }

        if (this == o) {
            return 0;
        }

        return Long.compare(this.getPriority(), o.getPriority());

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
