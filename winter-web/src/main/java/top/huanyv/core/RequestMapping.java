package top.huanyv.core;

import top.huanyv.enums.RequestMethod;
import top.huanyv.interfaces.ServletHandler;
import top.huanyv.utils.SystemConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestMapping {

    /**
     * 模糊请求地址，可以是模糊地址，比如 /user/{id}/{name}
     * 对应的变量和值会在请求过来后，到pathVariables中
     */
    private String urlPattern;

    /**
     * 路径变量，key为变量名，value为值
     * key 从模糊请求地址上得到
     * value从请求过来后具体的请求地址上得到
     */
    private Map<String, String> pathVariables;

    /**
     * 请求与处理器映射
     * 格式为：
     * {
     *     GET: handler1
     *     POST: handler2
     *     PUT: handler3
     *     DELETE: handler4
     * }
     */
    private Map<RequestMethod, ServletHandler> handler;

    /**
     * 通过请求方法，获取具体的处理器，如果不存在返回null
     * @param method 请求方式
     * @return 具体处理器
     */
    public ServletHandler getRequestHandler(RequestMethod method) {
        return handler.get(method);
    }

    /**
     * 判断请求方式是否有具体处理器
     * @param method 请求方式
     * @return 是true/否false
     */
    public boolean containsHandler(RequestMethod method) {
        return handler.containsKey(method);
    }

    /**
     * 模糊比较地址
     * @param url 精确地址
     * @return 是否相同
     */
    public boolean compareUrl(String url) {
        // 模糊地址
        String[] arr1 = this.urlPattern.split(SystemConstants.PATH_SEPARATOR);
        // 精确地址
        String[] arr2 = url.split(SystemConstants.PATH_SEPARATOR);
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i].matches(SystemConstants.REQUEST_URI_REGULAR)) {
                continue;
            }
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 从精确地址中解析出变量值来
     * @param url 精确地址
     */
    public void parsePathVars(String url) {
        this.pathVariables = new HashMap<>();
        // 模糊地址
        String[] arr1 = this.urlPattern.split(SystemConstants.PATH_SEPARATOR);
        // 精确地址
        String[] arr2 = url.split(SystemConstants.PATH_SEPARATOR);
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i].matches(SystemConstants.REQUEST_URI_REGULAR)) {
                this.pathVariables.put(arr1[i].substring(1, arr1[i].length() - 1), arr2[i]);
            }
        }
    }

    public String getPathVar(String name) {
        return pathVariables.get(name);
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Map<RequestMethod, ServletHandler> getHandler() {
        return handler;
    }

    public void setHandler(Map<RequestMethod, ServletHandler> handler) {
        this.handler = handler;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, String> pathVariables) {
        this.pathVariables = pathVariables;
    }

    @Override
    public String toString() {
        return "RequestMapping{" +
                "urlPattern='" + urlPattern + '\'' +
                ", pathVariables=" + pathVariables +
                ", handler=" + handler +
                '}';
    }
}