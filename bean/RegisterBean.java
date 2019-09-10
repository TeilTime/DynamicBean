package com.jnetdata.simple.test.bean;

import com.jnetdata.simple.core.swagger.MobileAppApi;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;

/**
 * @author Administrator
 */
@Data
@Component
public class RegisterBean implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @PostConstruct
    public void register(){

        System.out.println("applicationContext = " + context);

        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)context;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        //BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(TestController.class);

        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
        //beanDefinitionBuilder.addPropertyReference("userService", "userService");

        // 注册bean
        //defaultListableBeanFactory.registerBeanDefinition("testController", createMobileApi());

        defaultListableBeanFactory.registerSingleton("testSwaggerApp", createMobileApi());

        //TestController userController = (TestController) context.getBean("testController");
        //System.out.println("userController = " + userController);

    }

    public Docket createMobileApi() {
        String groupName = "testApp";
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(groupName)
                .description("App测试接口")
                .version("1.0")
                .build();

        ApiSelectorBuilder selectorBuilder = new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(MobileAppApi.class))
                .paths(PathSelectors.any());
        return selectorBuilder.build();
    }
}
