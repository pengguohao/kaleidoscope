package com.example.demo.config;


import com.example.demo.props.DemoProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 配置feign、mybatis包名、properties
 *
 * @author Chill
 */
@Configuration
@EnableFeignClients({"com.pgh.kaleidoscope", "com.example"})
@MapperScan({"com.pgh.kaleidoscope.**.mapper.**", "com.example.**.mapper.**"})
@EnableConfigurationProperties(DemoProperties.class)
public class DemoConfiguration {

}
