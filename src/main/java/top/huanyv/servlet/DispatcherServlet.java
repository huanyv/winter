package top.huanyv.servlet;

import top.huanyv.core.RequestHandlerRegistry;
import top.huanyv.enums.RequestMethod;
import top.huanyv.interfaces.ServletHandler;
import top.huanyv.utils.SystemConstants;
import top.huanyv.utils.WebUtil;
import top.huanyv.view.StaticResourceHandler;
import top.huanyv.view.TemplateEngineInstance;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.EndpointReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求分发器
 * 所有请求都会到这个servlet
 */
public class DispatcherServlet extends HttpServlet {

    /**
     * 请求注册容器
     */
    private RequestHandlerRegistry registry;

    @Override
    public void init() throws ServletException {
        registry = RequestHandlerRegistry.single();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = WebUtil.getRequestURI(req);

        // 处理静态资源
        boolean staticHandle = staticHandle(uri, resp);
        if (staticHandle) {
            return;
        }

        // 处理请求
        int status = registry.handle(req, resp);
        if (status == 404){
            resp.sendError(404,"resources not found.");
        } else if (status == 405) {
            resp.sendError(405, "request method not exists.");
        }

    }

    /**
     * 静态资源处理
     * @param uri 请求uri，为static下的文件路径
     * @param resp 响应对象
     * @return 是否存在静态资源
     */
    public boolean staticHandle(String uri, HttpServletResponse resp) throws IOException {
        StaticResourceHandler resourceHandler = StaticResourceHandler.single();
        // 判断是否有这个静态资源
        if (resourceHandler.hasResource(uri)) {
            // 跳转
            resourceHandler.process(uri, resp);
            return true;
        }
        return false;
    }

}