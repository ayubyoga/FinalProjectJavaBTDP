package com.yoga.bus.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UpperLowerRequest {
	
	@ApiModelProperty (hidden=true)
	private Long id;
	
	private Integer lowerValue;
	private Integer upperValue;
	public Integer getLowerValue() {
		return lowerValue;
	}
	public void setLowerValue(Integer lowerValue) {
		this.lowerValue = lowerValue;
	}
	public Integer getUpperValue() {
		return upperValue;
	}
	public void setUpperValue(Integer upperValue) {
		this.upperValue = upperValue;
	}
}
