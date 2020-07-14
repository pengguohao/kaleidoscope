package com.pgh.kaleidoscope.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum KaleidosUserEnum {

	/**
	 * web
	 */
	WEB("web", 1),

	/**
	 * app
	 */
	APP("app", 2),
	;

	final String name;
	final int category;

}
