package top.huanyv.start.loader;

import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import top.huanyv.bean.annotation.Bean;
import top.huanyv.bean.ioc.ApplicationContext;
import top.huanyv.bean.utils.BeanFactoryUtil;
import top.huanyv.start.anntation.Conditional;
import top.huanyv.start.anntation.ConfigurationProperties;
import top.huanyv.start.config.AppArguments;
import top.huanyv.webmvc.view.ViewResolver;
import top.huanyv.webmvc.view.thymeleaf.ThymeleafViewResolver;

import java.nio.charset.StandardCharsets;

/**
 * @author huanyv
 * @date 2022/12/18 14:55
 */
@ConfigurationProperties(prefix = "harbour.view")
public class ViewResolverLoader implements ApplicationLoader {

    private String prefix = "templates/";

    private String suffix = ".html";

    private String encoding = StandardCharsets.UTF_8.name();

    @Bean
    @Conditional(ConditionOnMissingBean.class)
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver(new ClassLoaderTemplateResolver());
        viewResolver.setPrefix(this.prefix);
        viewResolver.setSuffix(this.suffix);
        viewResolver.setCharacterEncoding(this.encoding);
        viewResolver.setTemplateMode(TemplateMode.HTML);
        return viewResolver;
    }

    public static class ConditionOnMissingBean implements Condition {

        @Override
        public boolean matchers(ApplicationContext applicationContext, AppArguments appArguments) {
            return BeanFactoryUtil.isNotPresent(applicationContext, ViewResolverLoader.class);
        }
    }

}
