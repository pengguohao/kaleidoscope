package com.pgh.kaleidoscope.desk.vo;

import com.pgh.kaleidoscope.desk.entity.Notice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告视图类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeVO extends Notice {

	@ApiModelProperty(value = "通知类型名")
	private String categoryName;

}
