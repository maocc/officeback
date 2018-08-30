package cn.feezu.maintapp.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAccept {

	private Long userId;
	private Long orderId;
	
}
