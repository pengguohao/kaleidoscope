package com.pgh.controller;

import org.junit.Test;
import com.pgh.kaleidoscope.core.tool.utils.Func;

/**
 * @author: pengguohao
 * @create: 2020/7/7
 **/
public class AuthControllerTest {

	@Test
	public void testStringUtil(){
		System.out.println(Func.isNoneBlank(" ", "123"));
		System.out.println(Func.isNoneBlank("", "123"));
		System.out.println(Func.isNoneBlank("123", ""));
		System.out.println(Func.isNoneBlank("123", " "));
		System.out.println(Func.isNoneBlank(" ", ""));
		System.out.println(Func.isNoneBlank("", ""));
		System.out.println(Func.isNoneBlank("zxc", "123"));
	}
}
