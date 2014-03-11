package org.bm.files.rules;

import org.bm.rules.Status;

/**
 * .
 *
 * @author Baptiste Morin
 */
public class DefaultFileStatus implements Status {
    private final int severity;
    private final String description;
    private final String possibleResolution;
    private final String code;

    public DefaultFileStatus(int severity, String code, String description, String possibleResolution) {
        this.severity = severity;
        this.code = code;
        this.description = description;
        this.possibleResolution = possibleResolution;
    }

    @Override
    public int getSeverity() {
        return severity;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getPossibleResolution() {
        return possibleResolution;
    }
}
