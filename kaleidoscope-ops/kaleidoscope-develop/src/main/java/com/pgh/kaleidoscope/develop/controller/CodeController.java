
package com.pgh.kaleidoscope.develop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.pgh.kaleidoscope.core.boot.controller.KaleidoscopeController;
import com.pgh.kaleidoscope.develop.service.ICodeService;
import com.pgh.kaleidoscope.develop.service.IDatasourceService;
import com.pgh.kaleidoscope.develop.support.KaleidoscopeCodeGenerator;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.mp.support.Condition;
import com.pgh.kaleidoscope.core.mp.support.Query;
import com.pgh.kaleidoscope.core.secure.annotation.PreAuth;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.core.tool.constant.RoleConstant;
import com.pgh.kaleidoscope.core.tool.utils.Func;
import com.pgh.kaleidoscope.develop.entity.Code;
import com.pgh.kaleidoscope.develop.entity.Datasource;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/code")
@Api(value = "代码生成", tags = "代码生成")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class CodeController extends KaleidoscopeController {

	private ICodeService codeService;
	private IDatasourceService datasourceService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入code")
	public CommonResult<Code> detail(Code code) {
		Code detail = codeService.getOne(Condition.getQueryWrapper(code));
		return CommonResult.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "codeName", value = "模块名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "modelName", value = "实体名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入code")
	public CommonResult<IPage<Code>> list(@ApiIgnore @RequestParam Map<String, Object> code, Query query) {
		IPage<Code> pages = codeService.page(Condition.getPage(query), Condition.getQueryWrapper(code, Code.class));
		return CommonResult.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入code")
	public CommonResult submit(@Valid @RequestBody Code code) {
		return CommonResult.status(codeService.submit(code));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(codeService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 复制
	 */
	@PostMapping("/copy")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "复制", notes = "传入id")
	public CommonResult copy(@ApiParam(value = "主键", required = true) @RequestParam Long id) {
		Code code = codeService.getById(id);
		code.setId(null);
		code.setCodeName(code.getCodeName() + "-copy");
		return CommonResult.status(codeService.save(code));
	}

	/**
	 * 代码生成
	 */
	@PostMapping("/gen-code")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "代码生成", notes = "传入ids")
	public CommonResult genCode(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, @RequestParam(defaultValue = "sword") String system) {
		Collection<Code> codes = codeService.listByIds(Func.toLongList(ids));
		codes.forEach(code -> {
			KaleidoscopeCodeGenerator generator = new KaleidoscopeCodeGenerator();
			// 设置数据源
			Datasource datasource = datasourceService.getById(code.getDatasourceId());
			generator.setDriverName(datasource.getDriverClass());
			generator.setUrl(datasource.getUrl());
			generator.setUsername(datasource.getUsername());
			generator.setPassword(datasource.getPassword());
			// 设置基础配置
			generator.setSystemName(system);
			generator.setServiceName(code.getServiceName());
			generator.setPackageName(code.getPackageName());
			generator.setPackageDir(code.getApiPath());
			generator.setPackageWebDir(code.getWebPath());
			generator.setTablePrefix(Func.toStrArray(code.getTablePrefix()));
			generator.setIncludeTables(Func.toStrArray(code.getTableName()));
			// 设置是否继承基础业务字段
			generator.setHasSuperEntity(code.getBaseMode() == 2);
			// 设置是否开启包装器模式
			generator.setHasWrapper(code.getWrapMode() == 2);
			generator.run();
		});
		return CommonResult.success("代码生成成功");
	}

}
