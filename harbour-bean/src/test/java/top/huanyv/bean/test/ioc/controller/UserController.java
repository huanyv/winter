package top.huanyv.bean.test.ioc.controller;

import top.huanyv.bean.annotation.Component;
import top.huanyv.bean.annotation.Inject;
import top.huanyv.bean.annotation.Scope;
import top.huanyv.bean.ioc.definition.BeanDefinition;
import top.huanyv.bean.test.entity.User;
import top.huanyv.bean.test.ioc.service.UserService;

/**
 * @author huanyv
 * @date 2022/11/18 14:26
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserController {

    @Inject
    private UserService userService;

    public User getUserById(Integer id) {
        return userService.getUserById(id);
    }
}