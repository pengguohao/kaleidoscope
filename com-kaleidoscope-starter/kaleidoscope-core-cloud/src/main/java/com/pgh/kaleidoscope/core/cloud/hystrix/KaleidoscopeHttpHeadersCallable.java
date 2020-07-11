/**
 * Copyright (c) 2018-2028, DreamLu 卢春梦 (qq596392912@gmail.com).
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
package com.pgh.kaleidoscope.core.cloud.hystrix;

import com.pgh.kaleidoscope.core.cloud.props.KaleidoscopeHystrixHeadersProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;

/**
 * HttpHeaders hystrix Callable
 *
 * @param <V> 泛型标记
 * @author L.cm
 */
public class KaleidoscopeHttpHeadersCallable<V> implements Callable<V> {
	private final Callable<V> delegate;
	@Nullable
	private HttpHeaders httpHeaders;

	public KaleidoscopeHttpHeadersCallable(Callable<V> delegate,
										   @Nullable KaleidoscopeHystrixAccountGetter accountGetter,
										   KaleidoscopeHystrixHeadersProperties properties) {
		this.delegate = delegate;
		this.httpHeaders = KaleidoscopeHttpHeadersContextHolder.toHeaders(accountGetter, properties);
	}

	@Override
	public V call() throws Exception {
		if (httpHeaders == null) {
			return delegate.call();
		}
		try {
			KaleidoscopeHttpHeadersContextHolder.set(httpHeaders);
			return delegate.call();
		} finally {
			KaleidoscopeHttpHeadersContextHolder.remove();
			httpHeaders.clear();
			httpHeaders = null;
		}
	}
}
