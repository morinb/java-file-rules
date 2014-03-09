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

    public DefaultFileStatus(int severity, String description, String possibleResolution) {
        this.severity = severity;
        this.description = description;
        this.possibleResolution = possibleResolution;
    }

    @Override
    public int getSeverity() {
        return severity;
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
