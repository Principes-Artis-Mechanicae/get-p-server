package es.princip.getp.domain.project.query.dto;

import es.princip.getp.common.util.StringUtil;

public enum MyProjectSearchOrder {
    PROJECT_ID, CREATED_AT, PAYMENT, APPLICATION_DURATION;

    public static MyProjectSearchOrder get(final String value) {
        return MyProjectSearchOrder.valueOf(StringUtil.camelToSnake(value).toUpperCase());
    }
}
