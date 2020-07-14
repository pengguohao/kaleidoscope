package com.example.demo.controller;

import com.example.demo.props.DemoProperties;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo控制器
 *
 * @author Chill
 */
@RefreshScope
@RestController
@RequestMapping("demo")
@Api(value = "配置接口", tags = "即时刷新配置")
public class DemoController {

	@Value("${demo.name}")
	private String name;

	private final DemoProperties properties;

	public DemoController(DemoProperties properties) {
		this.properties = properties;
	}


	@GetMapping("name")
	public String getName() {
		return name;
	}

	@GetMapping("name-by-props")
	public String getNameByProps() {
		return properties.getName();
	}

}
