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
package com.pgh.kaleidoscope.core.boot.controller;

import com.pgh.kaleidoscope.core.secure.KaleidoscopeUser;
import com.pgh.kaleidoscope.core.secure.utils.SecureUtil;
import com.pgh.kaleidoscope.core.boot.file.KaleidoscopeFile;
import com.pgh.kaleidoscope.core.boot.file.KaleidoscopeFileUtil;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Blade控制器封装类
 *
 * @author Chill
 */
public class KaleidoscopeController {

	/**
	 * ============================     REQUEST    =================================================
	 */

	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取request
	 *
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 获取当前用户
	 *
	 * @return BladeUser
	 */
	public KaleidoscopeUser getUser() {
		return SecureUtil.getUser();
	}

	/** ============================     API_RESULT    =================================================  */

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> CommonResult<T> data(T data) {
		return CommonResult.data(data);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> CommonResult<T> data(T data, String msg) {
		return CommonResult.data(data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param code 状态码
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> CommonResult<T> data(T data, String msg, int code) {
		return CommonResult.data(code, data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public CommonResult success(String msg) {
		return CommonResult.success(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public CommonResult fail(String msg) {
		return CommonResult.fail(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param flag 是否成功
	 * @return R
	 */
	public CommonResult status(boolean flag) {
		return CommonResult.status(flag);
	}


	/**============================     FILE    =================================================  */

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file 文件
	 * @return BladeFile
	 */
	public KaleidoscopeFile getFile(MultipartFile file) {
		return KaleidoscopeFileUtil.getFile(file);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file 文件
	 * @param dir  目录
	 * @return BladeFile
	 */
	public KaleidoscopeFile getFile(MultipartFile file, String dir) {
		return KaleidoscopeFileUtil.getFile(file, dir);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param file        文件
	 * @param dir         目录
	 * @param path        路径
	 * @param virtualPath 虚拟路径
	 * @return BladeFile
	 */
	public KaleidoscopeFile getFile(MultipartFile file, String dir, String path, String virtualPath) {
		return KaleidoscopeFileUtil.getFile(file, dir, path, virtualPath);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files 文件集合
	 * @return BladeFile
	 */
	public List<KaleidoscopeFile> getFiles(List<MultipartFile> files) {
		return KaleidoscopeFileUtil.getFiles(files);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files 文件集合
	 * @param dir   目录
	 * @return BladeFile
	 */
	public List<KaleidoscopeFile> getFiles(List<MultipartFile> files, String dir) {
		return KaleidoscopeFileUtil.getFiles(files, dir);
	}

	/**
	 * 获取BladeFile封装类
	 *
	 * @param files       文件集合
	 * @param dir         目录
	 * @param path        目录
	 * @param virtualPath 虚拟路径
	 * @return BladeFile
	 */
	public List<KaleidoscopeFile> getFiles(List<MultipartFile> files, String dir, String path, String virtualPath) {
		return KaleidoscopeFileUtil.getFiles(files, dir, path, virtualPath);
	}


}
