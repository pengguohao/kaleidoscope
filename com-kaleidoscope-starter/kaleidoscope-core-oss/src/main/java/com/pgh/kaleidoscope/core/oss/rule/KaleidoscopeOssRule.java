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
package com.pgh.kaleidoscope.core.oss.rule;

import com.pgh.kaleidoscope.core.tool.utils.DateUtil;
import com.pgh.kaleidoscope.core.tool.utils.FileUtil;
import com.pgh.kaleidoscope.core.tool.utils.StringPool;
import com.pgh.kaleidoscope.core.tool.utils.StringUtil;
import lombok.AllArgsConstructor;

/**
 * 默认存储桶生成规则
 *
 * @author Chill
 */
@AllArgsConstructor
public class KaleidoscopeOssRule implements OssRule {

	@Override
	public String bucketName(String bucketName) {
		return bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
