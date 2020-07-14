package com.pgh.kaleidoscope.auth.granter;

import com.pgh.kaleidoscope.auth.utils.TokenUtil;
import com.pgh.kaleidoscope.system.user.entity.UserInfo;
import com.pgh.kaleidoscope.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";

	private IUserClient userClient;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		return TokenUtil.parseTokenParam(userClient, tokenParameter);
	}

}
