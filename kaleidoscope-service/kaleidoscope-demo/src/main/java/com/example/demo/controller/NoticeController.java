package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Notice;
import com.example.demo.service.INoticeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.pgh.kaleidoscope.core.boot.controller.KaleidoscopeController;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.common.cache.CacheNames;
import com.pgh.kaleidoscope.core.mp.support.Condition;
import com.pgh.kaleidoscope.core.mp.support.Query;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@RequestMapping("notice")
@AllArgsConstructor
@Api(value = "用户博客", tags = "博客接口")
public class NoticeController extends KaleidoscopeController implements CacheNames {

	private INoticeService noticeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public CommonResult<Notice> detail(Notice notice) {
		Notice detail = noticeService.getOne(Condition.getQueryWrapper(notice));
		return CommonResult.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "category", value = "公告类型", paramType = "query", dataType = "integer"),
		@ApiImplicitParam(name = "title", value = "公告标题", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入notice")
	public CommonResult<IPage<Notice>> list(@ApiIgnore @RequestParam Map<String, Object> notice, Query query) {
		IPage<Notice> pages = noticeService.page(Condition.getPage(query), Condition.getQueryWrapper(notice, Notice.class));
		return CommonResult.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入notice")
	public CommonResult save(@RequestBody Notice notice) {
		return CommonResult.status(noticeService.save(notice));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入notice")
	public CommonResult update(@RequestBody Notice notice) {
		return CommonResult.status(noticeService.updateById(notice));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入notice")
	public CommonResult submit(@RequestBody Notice notice) {
		return CommonResult.status(noticeService.saveOrUpdate(notice));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入notice")
	public CommonResult remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		boolean temp = noticeService.deleteLogic(Func.toLongList(ids));
		return CommonResult.status(temp);
	}

}
