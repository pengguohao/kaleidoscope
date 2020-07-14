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
package com.pgh.kaleidoscope.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pgh.kaleidoscope.core.boot.controller.KaleidoscopeController;
import com.pgh.kaleidoscope.system.entity.Tenant;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.mp.support.Condition;
import com.pgh.kaleidoscope.core.mp.support.Query;
import com.pgh.kaleidoscope.core.secure.KaleidoscopeUser;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.core.tool.constant.KaleidoscopeConstant;
import com.pgh.kaleidoscope.core.tool.utils.Func;
import com.pgh.kaleidoscope.system.service.ITenantService;
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
@RequestMapping("/tenant")
@ApiIgnore
@Api(value = "租户管理", tags = "接口")
public class TenantController extends KaleidoscopeController {

	private ITenantService tenantService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入tenant")
	public CommonResult<Tenant> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(Condition.getQueryWrapper(tenant));
		return CommonResult.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tenantId", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tenantName", value = "角色别名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "contactNumber", value = "联系电话", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "分页", notes = "传入tenant")
	public CommonResult<IPage<Tenant>> list(@ApiIgnore @RequestParam Map<String, Object> tenant, Query query, KaleidoscopeUser kaleidoscopeUser) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant, Tenant.class);
		IPage<Tenant> pages = tenantService.page(Condition.getPage(query), (!kaleidoscopeUser.getTenantId().equals(KaleidoscopeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, kaleidoscopeUser.getTenantId()) : queryWrapper);
		return CommonResult.data(pages);
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	public CommonResult<List<Tenant>> select(Tenant tenant, KaleidoscopeUser kaleidoscopeUser) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant);
		List<Tenant> list = tenantService.list((!kaleidoscopeUser.getTenantId().equals(KaleidoscopeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, kaleidoscopeUser.getTenantId()) : queryWrapper);
		return CommonResult.data(list);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入tenant")
	public CommonResult<IPage<Tenant>> page(Tenant tenant, Query query) {
		IPage<Tenant> pages = tenantService.selectTenantPage(Condition.getPage(query), tenant);
		return CommonResult.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入tenant")
	public CommonResult submit(@Valid @RequestBody Tenant tenant) {
		return CommonResult.status(tenantService.saveTenant(tenant));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(tenantService.deleteLogic(Func.toLongList(ids)));
	}


}
