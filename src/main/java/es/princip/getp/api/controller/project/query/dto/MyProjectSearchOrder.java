package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.util.StringUtil;

public enum MyProjectSearchOrder {
    PROJECT_ID, CREATED_AT, PAYMENT, APPLICATION_DURATION;

    public static MyProjectSearchOrder get(final String value) {
        return MyProjectSearchOrder.valueOf(StringUtil.camelToSnake(value).toUpperCase());
    }
}
