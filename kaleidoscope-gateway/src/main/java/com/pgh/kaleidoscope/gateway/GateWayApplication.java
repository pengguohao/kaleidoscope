
package com.pgh.kaleidoscope.gateway;

import com.pgh.kaleidoscope.core.launch.constant.AppConstant;
import com.pgh.kaleidoscope.core.launch.KaleidoscopeApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动
 *
 * @author Chill
 */
@EnableHystrix
@EnableScheduling
@SpringCloudApplication
public class GateWayApplication {

	public static void main(String[] args) {
		KaleidoscopeApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GateWayApplication.class, args);
	}

}
