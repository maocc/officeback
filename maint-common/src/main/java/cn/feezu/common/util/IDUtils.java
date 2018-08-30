package cn.feezu.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ID生成
 * @author zhangfx
 *
 */
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class IDUtils {

	/**
	 * 获取Id
	 * @return
	 */
	public static long getId(){
		 
		return IdConfig.getInstance().nextId();
	}
}
