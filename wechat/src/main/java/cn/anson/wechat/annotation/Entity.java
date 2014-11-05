package cn.anson.wechat.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anson Chan
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Entity {
    boolean firstLevelCache() default false;
    boolean secondLevelCache() default true;
    boolean logChange() default false;
    String tableName() default "";
    /**
     * Split format as follow:
     * Mod:splitField:tableCount
     * Date:splitField:(y:m)
     * @return split define
     */
    String split() default "";
}