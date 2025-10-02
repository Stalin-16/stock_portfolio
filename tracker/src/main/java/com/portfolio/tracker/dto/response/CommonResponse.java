package com.portfolio.tracker.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse{
    	private int status;
	private Object data;
	private String message;
	private String devMessage;
}
