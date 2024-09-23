package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.util.StringUtil;

public enum ProjectSearchOrder {
    PROJECT_ID, CREATED_AT, PAYMENT, APPLICATION_DURATION;

    public static ProjectSearchOrder get(final String value) {
        return ProjectSearchOrder.valueOf(StringUtil.camelToSnake(value).toUpperCase());
    }
}
