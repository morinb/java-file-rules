package org.bm.files.rules;

import org.bm.rules.Entry;
import org.bm.rules.KeyPair;
import org.bm.rules.Result;
import org.bm.rules.Rule;
import org.bm.rules.Status;

/**
 * .
 *
 * @author Baptiste Morin
 */
public class DefaultFileResult implements Result {
    private Status status;
    private KeyPair<Entry, Rule> entryRuleKeyPair;

    public DefaultFileResult() {
    }

    public DefaultFileResult(Status status, KeyPair<Entry, Rule> entryRuleKeyPair) {
        this.status = status;
        this.entryRuleKeyPair = entryRuleKeyPair;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void setEntryRuleKeyPair(KeyPair<Entry, Rule> entryRuleKeyPair) {
        this.entryRuleKeyPair = entryRuleKeyPair;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public KeyPair<Entry, Rule> getEntryRuleKeyPair() {
        return entryRuleKeyPair;
    }
}
