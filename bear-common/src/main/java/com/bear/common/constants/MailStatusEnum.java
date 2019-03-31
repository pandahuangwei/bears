package com.bear.common.constants;

/**
 * @author panda.
 * @since 2018-11-25
 */
public enum MailStatusEnum {
    DRAFT(0, "草稿"), SUCCESS(1, "发送成功"), ERROR(2, "发送失败");

    private int code;
    private String desc;

    MailStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean isSuccess(Integer status) {
        return status == null ? false : SUCCESS.getCode() == status.intValue();
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
