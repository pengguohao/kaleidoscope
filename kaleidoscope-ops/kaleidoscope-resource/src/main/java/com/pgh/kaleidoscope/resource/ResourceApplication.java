
package com.pgh.kaleidoscope.resource;

import com.pgh.kaleidoscope.core.launch.KaleidoscopeApplication;
import com.pgh.kaleidoscope.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 资源启动器
 *
 * @author Chill
 */
@SpringCloudApplication
public class ResourceApplication {

	public static void main(String[] args) {
		KaleidoscopeApplication.run(AppConstant.APPLICATION_RESOURCE_NAME, ResourceApplication.class, args);
	}

}

