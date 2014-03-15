package rules

import org.bm.files.rules.ResultBuilder
import org.bm.files.rules.Statuses
import org.bm.rules.Entry
import org.bm.rules.KeyPair
import org.bm.rules.Result
import org.bm.rules.Rule
import org.bm.rules.impl.KeyPairImpl
import org.springframework.context.ApplicationContext

import java.util.regex.Pattern

/**
 * .
 * @author Baptiste Morin
 */

class LineFormatRule implements Rule {


    @Override
    Result apply(Entry entry) {
        /**
         * possible header at the start of the file
         */
        def optionalHeaderPattern = (Pattern) context.getBean("optionalHeaderRegexp")

        /**
         * Real Header pattern.
         */
        def headerPattern = (Pattern) context.getBean("headerRegexp")

        /**
         * Records pattern.
         */
        def recordPattern = (Pattern) context.getBean("recordRegexp")

        // Because the will be more record than header and more headers than optional header, check the record first.
        if (recordPattern.matcher(entry.content).matches()) {
            return recordValidation(entry).build()
        } else if (headerPattern.matcher(entry.content).matches()) {
            return headerValidation(entry).build()
        } else if (optionalHeaderPattern.matcher(entry.content).matches()) {
            return optionalHeaderValidation(entry).build()
        }


        return ResultBuilder.create().with(new KeyPairImpl<Entry, Rule>(entry, this)).with(Statuses.INVALID_LINE).build();
    }

    private ResultBuilder optionalHeaderValidation(Entry entry) {
        // Optional header MUST be the first line of the file
        final Integer lineNumber = (Integer) entry.metaDatas.get("lineNumber")
        final KeyPair<Entry, Rule> keyPair = new KeyPairImpl<>(entry, this)

        final ResultBuilder rb = ResultBuilder.create().with(keyPair)

        if (lineNumber != 0) {
            return rb.with(Statuses.INVALID_OPTIONAL_HEADER)
        }
        return rb.with(Statuses.OK)
    }

    private ResultBuilder headerValidation(Entry entry) {
        final KeyPair<Entry, Rule> keyPair = new KeyPairImpl<>(entry, this)
        final ResultBuilder rb = ResultBuilder.create().with(keyPair)

        // match item in the header.
        // We already know that it matches the format of a header.

        return rb.with(Statuses.OK)
    }

    private ResultBuilder recordValidation(Entry entry) {
        final KeyPair<Entry, Rule> keyPair = new KeyPairImpl<>(entry, this)
        final ResultBuilder rb = ResultBuilder.create().with(keyPair)

        // match item in the records
        // We already know that it matches the format of a record.


        return rb.with(Statuses.OK)
    }

    @Override
    String getDescription() {
        return "Check the line format."
    }

    @Override
    long getPriority() {
        return 0
    }


    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    int compareTo(Rule o) {
        // something is always greater than null.
        if (o == null) {
            return 1
        }

        if (this == o) {
            return 0
        }

        return this.priority.compareTo(o.priority);
    }
}