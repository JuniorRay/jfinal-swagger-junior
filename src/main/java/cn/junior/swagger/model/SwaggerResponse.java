package cn.junior.swagger.model;

/**
 * @Title: SwaggerResponse
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class SwaggerResponse {

    private String description;

    private SwaggerSchema schema;

    public SwaggerResponse(String description, SwaggerSchema schema) {
        this.description = description;
        this.schema = schema;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SwaggerSchema getSchema() {
        return schema;
    }

    public void setSchema(SwaggerSchema schema) {
        this.schema = schema;
    }

}
