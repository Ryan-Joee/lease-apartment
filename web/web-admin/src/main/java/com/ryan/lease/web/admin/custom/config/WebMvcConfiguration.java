package com.ryan.lease.web.admin.custom.config;

import com.ryan.lease.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;

    @Override
    // 将自定义的的StringToBaseEnumConverterFactory注册到Spring MVC转换系统中
    // 让 Spring 能在处理 Web 请求参数时自动使用它把 String 转成对应的枚举类型。
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(stringToBaseEnumConverterFactory);
    }
}
