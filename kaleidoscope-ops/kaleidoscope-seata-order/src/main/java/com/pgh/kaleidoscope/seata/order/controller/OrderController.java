package com.pgh.kaleidoscope.seata.order.controller;

import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.seata.order.service.IOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderController
 *
 * @author Chill
 */
@RestController
@RequestMapping("order")
@AllArgsConstructor
public class OrderController {

	private IOrderService orderService;

	/**
	 * 创建订单
	 *
	 * @param userId        用户id
	 * @param commodityCode 商品代码
	 * @param count         数量
	 * @return boolean
	 */
	@RequestMapping("/create")
	public CommonResult createOrder(String userId, String commodityCode, Integer count) {
		return CommonResult.status(orderService.createOrder(userId, commodityCode, count));
	}

}
