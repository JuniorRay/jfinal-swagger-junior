package cn.junior.swagger.config;

import cn.junior.swagger.annotation.*;
import cn.junior.swagger.model.*;
import com.jfinal.config.Routes;
import com.jfinal.config.Routes.Route;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Title: JuniorSwaggerPlugin
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class JuniorSwaggerPlugin implements IPlugin {

    private static ConcurrentMap<String, SwaggerDoc> swaggerDocMap;
    private static List<SwaggerApiInfo> apiInfoList;
    private static Map<String, SwaggerDto> definitions;

    /**
     * 是否启用swagger
     */
    private static boolean ENABLE;

    public JuniorSwaggerPlugin(boolean enable) {
        super();
        ENABLE = enable;
        if (ENABLE) {
            swaggerDocMap = new ConcurrentHashMap<>();
            apiInfoList = new ArrayList<>();
            definitions = new LinkedHashMap<>();
        }
    }

    public static boolean isEnable() {
        return ENABLE;
    }

    public static List<SwaggerApiInfo> getApiInfo() {
        if(ENABLE){
            return apiInfoList;
        }else {
            return Collections.emptyList();
        }
    }

    public JuniorSwaggerPlugin addSwaggerDoc(SwaggerDoc doc) {
        if (ENABLE) {
            swaggerDocMap.put(doc.getbasePackage(), doc);
            apiInfoList.add(doc.getInfo());
        }
        return this;
    }


    public static SwaggerDoc getDoc(String basePackage) {
        if(ENABLE) {
            if (StrKit.isBlank(basePackage)) {
                basePackage = swaggerDocMap.keySet().iterator().next();
            }
            SwaggerDoc swaggerDoc = swaggerDocMap.get(basePackage);
            if (swaggerDoc != null) {
                swaggerDoc.setDefinitions(definitions);
            }
            return swaggerDoc;
        }else {
            return null;
        }
    }

    @Override
    public boolean start() {

        if (swaggerDocMap.isEmpty()) {
            throw new RuntimeException("The swagger start fail , SwaggerDoc can not be null");
        }

        List<Route> routeList = getAllRouteList();

        Route route;

        Set<String> excludedMethod = buildExcludedMethodName();

        for (Route aRouteList : routeList) {

            route = aRouteList;

            Class<? extends Controller> cls = route.getControllerClass();

            if (!cls.isAnnotationPresent(Api.class)) {
                continue;
            }

            Api api = cls.getAnnotation(Api.class);

            if (api.hidden()) {
                continue;
            }

            Method[] methods = cls.getDeclaredMethods();

            for (Method method : methods) {

                if (excludedMethod.contains(method.getName())) {
                    continue;
                }


                //接口
                ApiOperation apiOperation = getApiOperation(method);

                if (apiOperation.hidden()) {
                    continue;
                }

                SwaggerApiMethod apiMethod = getApiMethod(method, apiOperation, cls);

                String clsName = null;
                for (String key : swaggerDocMap.keySet()) {
                    if (cls.getName().startsWith(key)) {
                        clsName = key;
                    }
                }
                if (StrKit.isBlank(clsName)) {
                    continue;
                }
                SwaggerDoc doc = swaggerDocMap.get(clsName);
                if (doc != null) {
                    Map<String, Map<String, SwaggerApiMethod>> paths = doc.getPaths();
                    Map<String, SwaggerApiMethod> methodMap = initMethodMap(method, apiMethod);
                    String actionUrl = getActionUrl(route, method, apiOperation);
                    paths.put(actionUrl, methodMap);
                }
            }

            addApiTags(cls, api);
        }

        return true;
    }

    private String getApiMethodDefaultTag(Class<? extends Controller> cls, Api api) {
        return getDefaultTag(cls, api);
    }

    private List<Route> getAllRouteList() {
        List<Routes> routesList = Routes.getRoutesList();

        List<Route> routeList = new ArrayList<>();

        for (Routes routes : routesList) {
            routeList.addAll(routes.getRouteItemList());
        }
        return routeList;
    }

    private ApiOperation getApiOperation(Method method) {
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);

        if (apiOperation == null) {
            apiOperation = JuniorSwaggerConstant.defaultApiOperation;
        }
        return apiOperation;
    }

    private void addApiTags(Class<? extends Controller> cls, Api api) {
        String apiDescription = api.description();
        if (apiDescription.length() == 0) {
            if (api.tags().length == 1) {
                apiDescription = api.tags()[0];
            } else {
                apiDescription = cls.getSimpleName() + " ";
            }
        }

        String clsName = null;
        for (String key : swaggerDocMap.keySet()) {
            if (cls.getName().startsWith(key)) {
                clsName = key;
            }
        }

        if (StrKit.isBlank(clsName)) {
            return;
        }

        for (String tagName : api.tags()) {
            getDoc(clsName).addTag(tagName, apiDescription);
        }
        if (api.tags().length == 0) {
            getDoc(clsName).addTag(cls.getSimpleName(), apiDescription);
        }
    }

    private String getDefaultTag(Class<? extends Controller> cls, Api api) {
        String defaultApiMethodTag = api.value();

        if (StrKit.isBlank(defaultApiMethodTag)) {
            defaultApiMethodTag = cls.getSimpleName();
        }
        return defaultApiMethodTag;
    }

    private String getActionUrl(Route route, Method method, ApiOperation apiOperation) {

        if (StrKit.notBlank(apiOperation.value())) {
            return apiOperation.value();
        }

        ActionKey actionKey = method.getAnnotation(ActionKey.class);

        if (actionKey != null) {
            return actionKey.value();
        }

        return route.getControllerKey() + "/" + method.getName();
    }

    private SwaggerApiMethod getApiMethod(Method method, ApiOperation apiOperation, Class<? extends Controller> cls) {
        SwaggerApiMethod apiMethod = getApiMethodAndParms(method);

        apiMethod.setProduces(apiOperation.produces());
        apiMethod.setTags(apiOperation.tags());

        apiMethod.setSummary(apiOperation.summary());
        apiMethod.setDescription(apiOperation.description());
        apiMethod.setConsumes(apiOperation.consumes());

        Api api = cls.getAnnotation(Api.class);

        if (api == null) {
            return apiMethod;
        }

        if (apiMethod.getTags() == null || apiMethod.getTags().length == 0) {
            apiMethod.setTags(new String[]{getApiMethodDefaultTag(cls, api)});
        }

        return apiMethod;
    }

    private void initResponseParams(Method method, SwaggerApiMethod apiMethod) {
        ApiResponses[] apiResponses = method.getAnnotationsByType(ApiResponses.class);

        if (apiResponses == null) {
            return;
        }
        Map<String, SwaggerResponse> responses = new HashMap<>();
        for (ApiResponses apiRespons : apiResponses) {
            ApiResponse[] params = apiRespons.value();
            for (ApiResponse param : params) {
                responses.put(param.code(),
                        new SwaggerResponse(param.message(), new SwaggerSchema("#/definitions/" + param.response().getSimpleName())));
                apiMethod.setResponses(responses);

                addResponseParams(param.response());
            }
        }

    }

    private SwaggerApiMethod getApiMethodAndParms(Method method) {

        SwaggerApiMethod apiMethod = new SwaggerApiMethod();

        initRequestParams(method, apiMethod);

        initResponseParams(method, apiMethod);


        return apiMethod;
    }

    /**
     * 返回参数
     *
     * @param cls
     */
    private void addResponseParams(Class<?> cls) {
        ApiModel apiModel = cls.getAnnotation(ApiModel.class);

        String description = apiModel == null || StrKit.isBlank(apiModel.description()) ? cls.getSimpleName() : apiModel.description();

        SwaggerDto swaggerDto = new SwaggerDto();
        swaggerDto.setTitle(cls.getSimpleName());
        swaggerDto.setType(getEnd(cls.getTypeName()));
        swaggerDto.setDescription(description);

        Map<String, SwaggerDtoProperties> swaggerDtoPropertiesMap = new LinkedHashMap<>();

        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            if (!f.isAnnotationPresent(ApiParam.class)) {
                continue;
            }
            ApiParam apiParam = f.getAnnotation(ApiParam.class);
            SwaggerDtoProperties dtoProperties = new SwaggerDtoProperties();
            dtoProperties.setDescription(apiParam.description());
            dtoProperties.setType(getEnd(f.getGenericType().getTypeName()));
            dtoProperties.setExample(apiParam.defaultValue());
            swaggerDtoPropertiesMap.put(f.getName(), dtoProperties);
        }
        swaggerDto.setProperties(swaggerDtoPropertiesMap);
        definitions.put(cls.getSimpleName(), swaggerDto);
    }

    /**
     * 请求参数
     *
     * @param method
     * @param apiMethod
     */
    private void initRequestParams(Method method, SwaggerApiMethod apiMethod) {
        ApiParams[] apiParamsArr = method.getAnnotationsByType(ApiParams.class);

        if (apiParamsArr != null && apiParamsArr.length != 0) {
            for (ApiParams apiParams : apiParamsArr) {
                ApiParam[] params = apiParams.value();
                for (ApiParam apiParam : params) {
                    SwaggerParameter para;
                    if (DataType.File.toLowerCase().equals(apiParam.dataType().toLowerCase())) {
                        para = getFilePara(apiParam);
                    } else {
                        para = getPara(apiParam);
                    }
                    apiMethod.addParameter(para);
                }
            }
        }
        ApiRequests[] apiRequests = method.getAnnotationsByType(ApiRequests.class);
        if (apiRequests != null && apiRequests.length != 0) {
            for (ApiRequests apiRequest : apiRequests) {
                Class classObj = apiRequest.request();
                Field[] fields = classObj.getDeclaredFields();
                for (Field f : fields) {
                    if (!f.isAnnotationPresent(ApiParam.class)) {
                        continue;
                    }
                    ApiParam apiParam = f.getAnnotation(ApiParam.class);
                    SwaggerParameter swaggerParameter = new SwaggerParameter(f.getName(), apiParam.description(),
                            apiParam.required(), getEnd(f.getGenericType().getTypeName()),
                            apiParam.defaultValue(), getEnd(f.getGenericType().getTypeName()));
                    apiMethod.addParameter(swaggerParameter);
                }

            }

        }
    }

    private Set<String> buildExcludedMethodName() {
        Set<String> excludedMethodName = new HashSet<String>();
        Method[] methods = Controller.class.getMethods();
        for (Method m : methods) {
            excludedMethodName.add(m.getName());
        }
        return excludedMethodName;
    }

    private SwaggerParameter getFilePara(ApiParam apiParam) {
        return new SwaggerParameter(apiParam.name(), "formData", apiParam.description(), apiParam.required(),
                apiParam.dataType(), apiParam.defaultValue(), apiParam.paramType());
    }


    private SwaggerParameter getPara(ApiParam apiParam) {
        return new SwaggerParameter(apiParam.name(), apiParam.description(), apiParam.required(), apiParam.dataType(),
                apiParam.defaultValue(), apiParam.paramType());
    }

    private Map<String, SwaggerApiMethod> initMethodMap(Method method, SwaggerApiMethod apiMethod) {
        ApiOperation apiOperation = getApiOperation(method);

        RequestMethod[] requestMethods = apiOperation.methods();

        if (requestMethods.length == 0) {
            requestMethods = RequestMethod.all();
        }

        return getMethodMap(requestMethods, apiMethod, method.getName());
    }

    private Map<String, SwaggerApiMethod> getMethodMap(RequestMethod[] methods, SwaggerApiMethod apiMethod,
                                                       String methodName) {

        Map<String, SwaggerApiMethod> methodMap = new HashMap<>();

        SwaggerApiMethod putApiMethod = null;
        for (RequestMethod requestMethod : methods) {

            try {
                putApiMethod = (SwaggerApiMethod) apiMethod.clone();
                putApiMethod.setOperationId(methodName.concat("Using").concat(requestMethod.toString()));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            methodMap.put(requestMethod.toString(), putApiMethod);
        }
        return methodMap;
    }

    private static String getEnd(String str) {
        String[] a = str.split("\\.");
        return a[a.length - 1];
    }

    @Override
    public boolean stop() {
        return true;
    }

}
