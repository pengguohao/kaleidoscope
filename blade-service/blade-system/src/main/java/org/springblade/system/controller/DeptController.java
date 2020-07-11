/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.boot.controller.BladeController;
import com.pgh.kaleidoscope.core.mp.support.Condition;
import com.pgh.kaleidoscope.core.secure.KaleidoscopeUser;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.core.tool.constant.KaleidoscopeConstant;
import com.pgh.kaleidoscope.core.tool.node.INode;
import com.pgh.kaleidoscope.core.tool.utils.Func;
import org.springblade.system.entity.Dept;
import org.springblade.system.service.IDeptService;
import org.springblade.system.vo.DeptVO;
import org.springblade.system.wrapper.DeptWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "部门", tags = "部门")
public class DeptController extends BladeController {

	private IDeptService deptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dept")
	public CommonResult<DeptVO> detail(Dept dept) {
		Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
		return CommonResult.data(DeptWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入dept")
	public CommonResult<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dept, KaleidoscopeUser kaleidoscopeUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!kaleidoscopeUser.getTenantId().equals(KaleidoscopeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, kaleidoscopeUser.getTenantId()) : queryWrapper);
		return CommonResult.data(DeptWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取部门树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public CommonResult<List<DeptVO>> tree(String tenantId, KaleidoscopeUser kaleidoscopeUser) {
		List<DeptVO> tree = deptService.tree(Func.toStr(tenantId, kaleidoscopeUser.getTenantId()));
		return CommonResult.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入dept")
	public CommonResult submit(@Valid @RequestBody Dept dept, KaleidoscopeUser user) {
		if (Func.isEmpty(dept.getId())) {
			dept.setTenantId(user.getTenantId());
		}
		return CommonResult.status(deptService.saveOrUpdate(dept));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(deptService.removeByIds(Func.toLongList(ids)));
	}


}
