
package com.pgh.kaleidoscope.gateway.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由配置类
 *
 * @author Chill
 */
@Data
@RefreshScope
@ConfigurationProperties("kaleidoscope.document")
public class RouteProperties {

	private final List<RouteResource> resources = new ArrayList<>();

}
