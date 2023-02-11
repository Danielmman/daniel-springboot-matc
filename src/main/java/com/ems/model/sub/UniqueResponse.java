package com.ems.model.sub;

import java.time.LocalDateTime;
import java.util.List;

import com.ems.base.response.ResponseError;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniqueResponse {

	private String corelationId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime timestamp;

	private int status = 200;

	private String error;
	private String message;
	private List<ResponseError> errorDetails;

	private Object data;

}
