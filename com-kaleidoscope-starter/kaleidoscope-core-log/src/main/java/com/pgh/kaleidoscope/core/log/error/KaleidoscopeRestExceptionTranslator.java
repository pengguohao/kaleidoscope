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
package com.pgh.kaleidoscope.core.log.error;

import com.pgh.kaleidoscope.core.secure.exception.SecureException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import com.pgh.kaleidoscope.core.log.exception.ServiceException;
import com.pgh.kaleidoscope.core.log.publisher.ErrorLogPublisher;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.core.tool.api.ResultCode;
import com.pgh.kaleidoscope.core.tool.utils.Func;
import com.pgh.kaleidoscope.core.tool.utils.UrlUtil;
import com.pgh.kaleidoscope.core.tool.utils.WebUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.Servlet;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理，处理可预见的异常
 *
 * @author Chill
 */
@Slf4j
@Configuration
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
public class KaleidoscopeRestExceptionTranslator {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleError(MissingServletRequestParameterException e) {
		log.warn("缺少请求参数", e.getMessage());
		String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
		return CommonResult.fail(ResultCode.PARAM_MISS, message);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleError(MethodArgumentTypeMismatchException e) {
		log.warn("请求参数格式错误", e.getMessage());
		String message = String.format("请求参数格式错误: %s", e.getName());
		return CommonResult.fail(ResultCode.PARAM_TYPE_ERROR, message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleError(MethodArgumentNotValidException e) {
		log.warn("参数验证失败", e.getMessage());
		return handleError(e.getBindingResult());
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleError(BindException e) {
		log.warn("参数绑定失败", e.getMessage());
		return handleError(e.getBindingResult());
	}

	private CommonResult handleError(BindingResult result) {
		FieldError error = result.getFieldError();
		String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
		return CommonResult.fail(ResultCode.PARAM_BIND_ERROR, message);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleError(ConstraintViolationException e) {
		log.warn("参数验证失败", e.getMessage());
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
		String message = String.format("%s:%s", path, violation.getMessage());
		return CommonResult.fail(ResultCode.PARAM_VALID_ERROR, message);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public CommonResult handleError(NoHandlerFoundException e) {
		log.error("404没找到请求:{}", e.getMessage());
		return CommonResult.fail(ResultCode.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleError(HttpMessageNotReadableException e) {
		log.error("消息不能读取:{}", e.getMessage());
		return CommonResult.fail(ResultCode.MSG_NOT_READABLE, e.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public CommonResult handleError(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法:{}", e.getMessage());
		return CommonResult.fail(ResultCode.METHOD_NOT_SUPPORTED, e.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public CommonResult handleError(HttpMediaTypeNotSupportedException e) {
		log.error("不支持当前媒体类型:{}", e.getMessage());
		return CommonResult.fail(ResultCode.MEDIA_TYPE_NOT_SUPPORTED, e.getMessage());
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleError(ServiceException e) {
		log.error("业务异常", e);
		return CommonResult.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(SecureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonResult handleError(SecureException e) {
		log.error("认证异常", e);
		return CommonResult.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult handleError(Throwable e) {
		log.error("服务器异常", e);
		//发送服务异常事件
		ErrorLogPublisher.publishEvent(e, UrlUtil.getPath(WebUtil.getRequest().getRequestURI()));
		return CommonResult.fail(ResultCode.INTERNAL_SERVER_ERROR, (Func.isEmpty(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage()));
	}

}
