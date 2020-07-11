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
package com.pgh.kaleidoscope.core.mp.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pgh.kaleidoscope.core.secure.KaleidoscopeUser;
import com.pgh.kaleidoscope.core.secure.utils.SecureUtil;
import com.pgh.kaleidoscope.core.tool.constant.KaleidoscopeConstant;
import com.pgh.kaleidoscope.core.tool.utils.DateUtil;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author Chill
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

	@Override
	public boolean save(T entity) {
		KaleidoscopeUser user = SecureUtil.getUser();
		if (user != null) {
			entity.setCreateUser(user.getUserId());
			entity.setUpdateUser(user.getUserId());
		}
		Date now = DateUtil.now();
		entity.setCreateTime(now);
		entity.setUpdateTime(now);
		if (entity.getStatus() == null) {
			entity.setStatus(KaleidoscopeConstant.DB_STATUS_NORMAL);
		}
		entity.setIsDeleted(KaleidoscopeConstant.DB_NOT_DELETED);
		return super.save(entity);
	}

	@Override
	public boolean updateById(T entity) {
		KaleidoscopeUser user = SecureUtil.getUser();
		if (user != null) {
			entity.setUpdateUser(user.getUserId());
		}
		entity.setUpdateTime(DateUtil.now());
		return super.updateById(entity);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Long> ids) {
		return super.removeByIds(ids);
	}

}
