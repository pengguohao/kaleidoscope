
package com.pgh.kaleidoscope.gateway.props;

import lombok.Data;
import com.pgh.kaleidoscope.core.launch.constant.AppConstant;

/**
 * Swagger聚合文档属性
 *
 * @author Chill
 */
@Data
public class RouteResource {

	/**
	 * 文档名
	 */
	private String name;

	/**
	 * 文档所在服务地址
	 */
	private String location;

	/**
	 * 文档版本
	 */
	private String version = AppConstant.APPLICATION_VERSION;

}
