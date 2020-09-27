package cn.junior.swagger.model;

/**
 * @Title: SwaggerParameter
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class SwaggerParameter {
    private String name;
    private String in;
    private String description;
    private boolean required;
    private String type;
    private String defaultValue;
    private String paramType;

    public SwaggerParameter(String name, String description, boolean required, String type, String defaultValue, String paramType) {
        this.name = name;
        this.description = description;
        this.required = required;
        this.in = "query";
        this.type = type;
        this.paramType = paramType;
        this.defaultValue = defaultValue;
    }

    public SwaggerParameter(String name, String in, String description, boolean required, String type, String defaultValue, String paramType) {
        this.name = name;
        this.in = in;
        this.description = description;
        this.required = required;
        this.type = type;
        this.paramType = paramType;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
}
