
package com.example.demo.feign;

import com.example.demo.entity.Notice;
import com.example.demo.mapper.NoticeMapper;
import lombok.AllArgsConstructor;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Notice Feign
 *
 * @author Chill
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class NoticeClient implements INoticeClient {

	private NoticeMapper mapper;

	@Override
	@GetMapping(TOP)
	public CommonResult<List<Notice>> top(Integer number) {
		return CommonResult.data(mapper.topList(number));
	}

}
