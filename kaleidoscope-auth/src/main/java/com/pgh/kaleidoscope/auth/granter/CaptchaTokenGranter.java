package com.pgh.kaleidoscope.auth.granter;

import com.pgh.kaleidoscope.auth.utils.TokenUtil;
import com.pgh.kaleidoscope.common.cache.CacheNames;
import com.pgh.kaleidoscope.core.log.exception.ServiceException;
import com.pgh.kaleidoscope.core.tool.utils.RedisUtil;
import com.pgh.kaleidoscope.core.tool.utils.StringUtil;
import com.pgh.kaleidoscope.core.tool.utils.WebUtil;
import com.pgh.kaleidoscope.system.user.entity.UserInfo;
import com.pgh.kaleidoscope.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码TokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "captcha";

	private IUserClient userClient;
	private RedisUtil redisUtil;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		HttpServletRequest request = WebUtil.getRequest();

		String key = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		String code = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
		// 获取验证码
		String redisCode = String.valueOf(redisUtil.get(CacheNames.CAPTCHA_KEY + key));
		// 判断验证码
		if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
			throw new ServiceException(TokenUtil.CAPTCHA_NOT_CORRECT);
		}

		return TokenUtil.parseTokenParam(userClient, tokenParameter);
	}

}
