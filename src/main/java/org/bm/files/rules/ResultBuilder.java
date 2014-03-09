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
public class ResultBuilder implements Builder<Result> {

    private static final ResultBuilder INSTANCE = new ResultBuilder();

    private ResultBuilder() {}

    private Result result;

    public static ResultBuilder create() {
        return INSTANCE;
    }

    public ResultBuilder with(Status status) {
        if (result == null) {
            result = new DefaultFileResult();
        }

        result.setStatus(status);
        return this;
    }

    public ResultBuilder with(Statuses statuses) {
        if (result == null) {
            result = new DefaultFileResult();
        }

        result.setStatus(statuses.getStatus());
        return this;
    }

    public ResultBuilder with(KeyPair<Entry, Rule> entryRuleKeyPair) {
        if (result == null) {
            result = new DefaultFileResult();
        }

        result.setEntryRuleKeyPair(entryRuleKeyPair);
        return this;
    }


    @Override
    public Result build() {
        if (result.getStatus() == null) {
            throw new NullPointerException("Status must not be null");
        }

        if (result.getEntryRuleKeyPair() == null) {
            throw new NullPointerException("EntryRuleKeyPair must not be null");
        }
        System.out.println("Status :"+result.getStatus().toString());
        System.out.println("ERKP :"+result.getEntryRuleKeyPair().getFirst()+" "+result.getEntryRuleKeyPair().getSecond());
        return result;
    }
}
