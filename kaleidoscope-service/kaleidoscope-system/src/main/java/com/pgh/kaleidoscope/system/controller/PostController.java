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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.pgh.kaleidoscope.core.boot.controller.KaleidoscopeController;
import com.pgh.kaleidoscope.system.entity.Post;
import com.pgh.kaleidoscope.system.vo.PostVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.mp.support.Condition;
import com.pgh.kaleidoscope.core.mp.support.Query;
import com.pgh.kaleidoscope.core.secure.KaleidoscopeUser;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.core.tool.utils.Func;
import com.pgh.kaleidoscope.system.service.IPostService;
import com.pgh.kaleidoscope.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 岗位表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/post")
@Api(value = "岗位表", tags = "岗位表接口")
public class PostController extends KaleidoscopeController {

	private IPostService postService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入post")
	public CommonResult<PostVO> detail(Post post) {
		Post detail = postService.getOne(Condition.getQueryWrapper(post));
		return CommonResult.data(PostWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 岗位表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入post")
	public CommonResult<IPage<PostVO>> list(Post post, Query query) {
		IPage<Post> pages = postService.page(Condition.getPage(query), Condition.getQueryWrapper(post));
		return CommonResult.data(PostWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 岗位表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入post")
	public CommonResult<IPage<PostVO>> page(PostVO post, Query query) {
		IPage<PostVO> pages = postService.selectPostPage(Condition.getPage(query), post);
		return CommonResult.data(pages);
	}

	/**
	 * 新增 岗位表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入post")
	public CommonResult save(@Valid @RequestBody Post post) {
		return CommonResult.status(postService.save(post));
	}

	/**
	 * 修改 岗位表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入post")
	public CommonResult update(@Valid @RequestBody Post post) {
		return CommonResult.status(postService.updateById(post));
	}

	/**
	 * 新增或修改 岗位表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入post")
	public CommonResult submit(@Valid @RequestBody Post post) {
		return CommonResult.status(postService.saveOrUpdate(post));
	}


	/**
	 * 删除 岗位表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(postService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "下拉数据源", notes = "传入post")
	public CommonResult<List<Post>> select(String tenantId, KaleidoscopeUser kaleidoscopeUser) {
		List<Post> list = postService.list(Wrappers.<Post>query().lambda().eq(Post::getTenantId, Func.toStr(tenantId, kaleidoscopeUser.getTenantId())));
		return CommonResult.data(list);
	}

}
