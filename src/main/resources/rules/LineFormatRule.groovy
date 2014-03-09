package rules

import org.bm.files.rules.ResultBuilder
import org.bm.files.rules.Statuses
import org.bm.rules.Entry
import org.bm.rules.KeyPair
import org.bm.rules.Result
import org.bm.rules.Rule
import org.bm.rules.impl.KeyPairImpl

/**
 * .
 * @author Baptiste Morin
 */

class LineFormatRule implements Rule {
    /**
     * possible header at the start of the file
     */
    def optionalHeaderPattern = ~/^([a-zA-Z0-9 ]{5});([a-zA-Z0-9 ]{10});([a-zA-Z0-9 ]{15});(\d{14});(\d{14})$/

    /**
     * Real Header pattern.
     */
    def headerPattern = ~/^H;(.*)$/

    /**
     * Records pattern.
     */
    def recordPattern = ~/^R;(.*)$/

    @Override
    Result apply(Entry entry) {
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
        Integer lineNumber = (Integer) entry.metaDatas.get("lineNumber")
        KeyPair<Entry, Rule> keyPair = new KeyPairImpl<>(entry, this)

        ResultBuilder rb = ResultBuilder.create().with(keyPair)

        if (lineNumber != 0) {
            return rb.with(Statuses.INVALID_OPTIONAL_HEADER)
        }
        return rb.with(Statuses.OK)
    }

    private ResultBuilder headerValidation(Entry entry) {
        return null;
    }

    private ResultBuilder recordValidation(Entry entry) {
        return null
    }

    @Override
    String getDescription() {
        return "Check the line format."
    }
}