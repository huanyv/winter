package top.huanyv.core.test.controller;

import top.huanyv.core.test.aop.Aop01;
import top.huanyv.core.test.aop.Aop02;
import top.huanyv.core.test.service.UserService;
import top.huanyv.ioc.anno.Bean;
import top.huanyv.ioc.anno.Component;
import top.huanyv.ioc.anno.Inject;
import top.huanyv.ioc.anno.Scope;
import top.huanyv.ioc.aop.Aop;
import top.huanyv.ioc.core.definition.BeanDefinition;

/**
 * @author huanyv
 * @date 2022/10/20 21:03
 */
@Component
@Aop(Aop01.class)
//@Scope("prototype")
public class UserController {

    @Inject("userService")
    private UserService userService;

    public void getUser() {
        userService.getUser();
    }

    @Aop(Aop02.class)
    public void getUser02() {
        userService.getUser();
    }

}
