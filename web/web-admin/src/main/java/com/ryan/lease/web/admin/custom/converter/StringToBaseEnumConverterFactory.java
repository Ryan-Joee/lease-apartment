package com.ryan.lease.web.admin.custom.converter;

import com.ryan.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/*
* 这段代码是 Spring 框架中的一个 自定义类型转换器工厂类，
* 用于将 String 类型（一般来自前端参数）自动转换为实现了 BaseEnum 接口的枚举类型。
* 它的主要作用是：当你在 Controller 接收枚举类型参数时，可以自动根据 code 值匹配到对应的枚举项。
*
* */
@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        // 返回一个Converter，使用内部类的方式返回
        return new Converter<String, T>() {
            @Override
            // 这个code就是前端传过来的type = 1/2/...
            public T convert(String code) {
                // 通过targetType目标枚举类型获取到所有的枚举实例
                T[] enumConstants = targetType.getEnumConstants();
                for (T enumConstant : enumConstants) {
                    // 判断前端传来的类型code是否在后端的枚举实例code中存在
                    if (enumConstant.getCode().equals(Integer.valueOf(code))) {
                        return enumConstant;
                    }
                }
                throw new IllegalArgumentException("Invalid ItemType code: " + code);
            }
        };
    }
}
