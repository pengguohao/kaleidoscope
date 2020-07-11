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
import org.springblade.system.entity.Role;
import org.springblade.system.service.IRoleService;
import org.springblade.system.vo.RoleVO;
import org.springblade.system.wrapper.RoleWrapper;
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
@RequestMapping("/role")
@Api(value = "角色", tags = "角色")
public class RoleController extends BladeController {

	private IRoleService roleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	public CommonResult<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return CommonResult.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public CommonResult<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> role, KaleidoscopeUser kaleidoscopeUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!kaleidoscopeUser.getTenantId().equals(KaleidoscopeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, kaleidoscopeUser.getTenantId()) : queryWrapper);
		return CommonResult.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public CommonResult<List<RoleVO>> tree(String tenantId, KaleidoscopeUser kaleidoscopeUser) {
		List<RoleVO> tree = roleService.tree(Func.toStr(tenantId, kaleidoscopeUser.getTenantId()));
		return CommonResult.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public CommonResult submit(@Valid @RequestBody Role role, KaleidoscopeUser user) {
		if (Func.isEmpty(role.getId())) {
			role.setTenantId(user.getTenantId());
		}
		return CommonResult.status(roleService.saveOrUpdate(role));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 设置菜单权限
	 *
	 * @param roleIds
	 * @param menuIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public CommonResult grant(@ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds,
							  @ApiParam(value = "menuId集合", required = true) @RequestParam String menuIds) {
		boolean temp = roleService.grant(Func.toLongList(roleIds), Func.toLongList(menuIds));
		return CommonResult.status(temp);
	}

}
