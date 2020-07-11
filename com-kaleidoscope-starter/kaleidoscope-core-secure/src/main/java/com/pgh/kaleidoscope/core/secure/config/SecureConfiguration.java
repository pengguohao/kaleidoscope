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
package com.pgh.kaleidoscope.core.secure.config;


import com.pgh.kaleidoscope.core.secure.props.KaleidoscopeSecureProperties;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.secure.aspect.AuthAspect;
import com.pgh.kaleidoscope.core.secure.interceptor.ClientInterceptor;
import com.pgh.kaleidoscope.core.secure.interceptor.SecureInterceptor;
import com.pgh.kaleidoscope.core.secure.provider.ClientDetailsServiceImpl;
import com.pgh.kaleidoscope.core.secure.provider.IClientDetailsService;
import com.pgh.kaleidoscope.core.secure.registry.SecureRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 *
 * @author Chill
 */
@Order
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({KaleidoscopeSecureProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

	private final SecureRegistry secureRegistry;

	private final KaleidoscopeSecureProperties secureProperties;

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		secureProperties.getClient().forEach(cs -> registry.addInterceptor(new ClientInterceptor(cs.getClientId())).addPathPatterns(cs.getPathPatterns()));

		if (secureRegistry.isEnabled()) {
			registry.addInterceptor(new SecureInterceptor())
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureProperties.getSkipUrl());
		}
	}

	@Bean
	public AuthAspect authAspect() {
		return new AuthAspect();
	}

	@Bean
	@ConditionalOnMissingBean(IClientDetailsService.class)
	public IClientDetailsService clientDetailsService() {
		return new ClientDetailsServiceImpl(jdbcTemplate);
	}

}
