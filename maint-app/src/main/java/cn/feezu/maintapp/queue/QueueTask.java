package cn.feezu.maintapp.queue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import cn.feezu.maintapp.web.dto.ResultDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueTask<T> {

	/**
	 * 延时返回对象
	 */
	private DeferredResult<ResponseEntity<ResultDTO>> result;

	/**
	 * 任务消息
	 */
	private T message;
	
	/**
	 * 是否超时
	 */
	private boolean isTimeOut;
	
	private String errorMessage;
	
	private boolean error;

	public QueueTask(DeferredResult<ResponseEntity<ResultDTO>> result, T message, boolean isTimeOut) {
		super();
		this.result = result;
		this.message = message;
		this.isTimeOut = isTimeOut;
	}
	
	
}
