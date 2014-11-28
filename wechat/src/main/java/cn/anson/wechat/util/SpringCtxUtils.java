package cn.anson.wechat.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * ��web������ȡspring����
 */
public class SpringCtxUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringCtxUtils.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> type) {

        try {
            return applicationContext.getBean(type);
        } catch (NoUniqueBeanDefinitionException e) {   //���ֶ����ѡ��һ��
            String beanName = applicationContext.getBeanNamesForType(type)[0];
            return applicationContext.getBean(beanName, type);
        }
    }

    public static <T> T getBean(String beanName, Class<T> type) {

        return applicationContext.getBean(beanName, type);
    }
};

