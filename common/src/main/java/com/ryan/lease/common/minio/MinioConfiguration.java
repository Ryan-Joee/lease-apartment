package com.ryan.lease.common.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfiguration {

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 这段代码的作用是：当 Spring 容器启动时，就会自动初始化 MinioClient 并将其注入为 Bean。
     * 连接是何时建立的？
     * 关键点：MinioClient 是懒连接（lazy connection）机制。
     * 调用 MinioClient.builder().build() 并不会立即和 MinIO 服务器建立连接；
     * 只有当你调用具体的方法（比如 bucketExists()、putObject()）时，才会真正尝试连接服务器；
     * 所以你看到的连接其实是“使用时再连接”，而不是“启动时连接”。
     * @return Minio对象
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }
}
