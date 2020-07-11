/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pgh.kaleidoscope.core.log.config;

import com.pgh.kaleidoscope.core.log.event.ApiLogListener;
import com.pgh.kaleidoscope.core.log.event.ErrorLogListener;
import com.pgh.kaleidoscope.core.log.event.UsualLogListener;
import com.pgh.kaleidoscope.core.log.feign.ILogClient;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.log.aspect.ApiLogAspect;
import com.pgh.kaleidoscope.core.log.logger.KaleidoscopeLogger;
import com.pgh.kaleidoscope.core.launch.props.KaleidoscopeProperties;
import com.pgh.kaleidoscope.core.launch.server.ServerInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class KaleidoscopeLogToolAutoConfiguration {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final KaleidoscopeProperties kaleidoscopeProperties;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public KaleidoscopeLogger bladeLogger() {
		return new KaleidoscopeLogger();
	}

	@Bean
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, kaleidoscopeProperties);
	}

	@Bean
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, kaleidoscopeProperties);
	}

	@Bean
	public UsualLogListener bladeEventListener() {
		return new UsualLogListener(logService, serverInfo, kaleidoscopeProperties);
	}

}
