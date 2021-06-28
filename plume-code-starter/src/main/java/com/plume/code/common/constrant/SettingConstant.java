package com.plume.code.common.constrant;

/**
 * @author yinyansheng
 * setting constant
 */
public class SettingConstant {

    public interface ServiceMode {
        Integer NONE = 0;
        Integer SERVICE = 1;
    }

    public interface RepositoryMode {
        Integer NONE = 0;
        Integer MYBATIS = 1;
        Integer MYBATIS_PLUS = 2;
        Integer JPA = 3;
        Integer HIBERNATE = 4;
    }
}
