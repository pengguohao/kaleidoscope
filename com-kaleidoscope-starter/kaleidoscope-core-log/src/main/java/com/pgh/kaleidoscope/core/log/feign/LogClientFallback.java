package com.pgh.kaleidoscope.core.log.feign;

import com.pgh.kaleidoscope.core.log.model.LogApi;
import lombok.extern.slf4j.Slf4j;
import com.pgh.kaleidoscope.core.log.model.LogError;
import com.pgh.kaleidoscope.core.log.model.LogUsual;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import org.springframework.stereotype.Component;

/**
 * 日志fallback
 *
 * @author jiang
 */
@Slf4j
@Component
public class LogClientFallback implements ILogClient {

	@Override
	public CommonResult<Boolean> saveUsualLog(LogUsual log) {
		return CommonResult.fail("usual log send fail");
	}

	@Override
	public CommonResult<Boolean> saveApiLog(LogApi log) {
		return CommonResult.fail("api log send fail");
	}

	@Override
	public CommonResult<Boolean> saveErrorLog(LogError log) {
		return CommonResult.fail("error log send fail");
	}
}
