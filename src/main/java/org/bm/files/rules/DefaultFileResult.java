package org.bm.files.rules;

import org.bm.rules.Entry;
import org.bm.rules.KeyPair;
import org.bm.rules.Rule;
import org.bm.rules.Status;
import org.bm.rules.impl.DefaultResult;

/**
 * .
 *
 * @author Baptiste Morin
 */
public class DefaultFileResult extends DefaultResult {

    public DefaultFileResult() {
        super(null, null);
    }

    public DefaultFileResult(Status status, KeyPair<Entry, Rule> entryRuleKeyPair) {
        super(status, entryRuleKeyPair);
    }

}
