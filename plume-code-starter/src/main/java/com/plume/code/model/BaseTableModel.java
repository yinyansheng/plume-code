package com.plume.code.model;

public abstract class BaseTableModel {
    protected String className;
    protected String classComment;

    public abstract void initialize(SettingModel settingModel);
}
