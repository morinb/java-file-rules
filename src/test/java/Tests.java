import org.bm.files.rules.Statuses;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * .
 *
 * @author Baptiste Morin
 */
public class Tests {

    @Test
    public void testStatuses() {
        for(Statuses status : Statuses.values()) {
            assertNotNull("Status "+status.name()+" must not be null", status.getStatus());
        }
    }

}
