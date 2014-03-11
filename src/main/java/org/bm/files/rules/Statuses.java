package org.bm.files.rules;

import org.bm.rules.Status;

/**
 * .
 *
 * @author Baptiste Morin
 */
public enum Statuses {
    OK(new DefaultFileStatus(Integer.MIN_VALUE, "0000",  "OK", null)),
    INVALID_OPTIONAL_HEADER(new DefaultFileStatus(-8_000,"0001",  "Optional header is invalid", "Check the first line of the file.")),
    INVALID_LINE(new DefaultFileStatus(0, "0002", "The line is invalid, it can not be read by the application", "Check the line.")),;


    private final Status status;

    private Statuses(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}
