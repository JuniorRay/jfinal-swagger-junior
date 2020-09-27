package cn.junior.swagger.model;

/**
 * @Title: SwaggerApiInfo
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class SwaggerApiInfo {

    private String groupName;//接口文档的分组名称，配置多个则下拉框可选择
    private String url;//项目打开地址
    private String scanPackageDir;//扫描包含有swagger注解的目录
    private String swaggerVersion;
    private String description = "最终解释权归安软科技公司所有";//项目描述信息
    private String title = "安软科技-API接口文档";//默认标题栏总标题，可以配置。by Junior Ray
    private String termsOfService;//服务url
    private String contact = "Junior Ray";//联系人

    public SwaggerApiInfo(String groupName, String url, String scanPackageDir) {
        this.groupName = groupName;
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        this.scanPackageDir = scanPackageDir;
        this.url = url + "?basePackage=" + this.scanPackageDir;
        this.swaggerVersion = "2.0";
    }

    public String getGroupName() {
        return groupName;
    }

    public SwaggerApiInfo setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SwaggerApiInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getScanPackageDir() {
        return scanPackageDir;
    }

    public SwaggerApiInfo setScanPackageDir(String scanPackageDir) {
        this.scanPackageDir = scanPackageDir;
        return this;
    }

    public String getSwaggerVersion() {
        return swaggerVersion;
    }

    public SwaggerApiInfo setSwaggerVersion(String swaggerVersion) {
        this.swaggerVersion = swaggerVersion;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
