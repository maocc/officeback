package cn.feezu.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author zhangfx
 *
 */
public class Constants {

	private Constants() {

	}

	// csv's default delemiter is ','
	public final static String DEFAULT_DELIMITER = ",";
	// Mark a new line
	public final static String DEFAULT_END = "\r\n";

	// If you do not want a UTF-8 ,just replace the byte array.
	public final static byte commonCsvHead[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };

	public static class DictStatus {

		public static final String ENABLE = "true";

	}

	/**
	 * csv文件附加查看说明
	 */
	public final static String CSV_DESC_APPEND=StringUtils.repeat(DEFAULT_END, 4)
			+"查看说明"+DEFAULT_END
			+"使用excel的导入数据功能查看："+DEFAULT_END+DEFAULT_END
			+DEFAULT_DELIMITER+"“数据”->“自文本”，选择当前文件"+DEFAULT_END
			+"导入第1步"+DEFAULT_DELIMITER+"原始数据类型选择“分隔符号”"+DEFAULT_END
			+"导入第2步"+DEFAULT_DELIMITER+"分隔符号使用“逗号”"+DEFAULT_END
			+"导入第3步"+DEFAULT_DELIMITER+"列数据格式选择“文本”";
	/**
	 * 超时订单设置
	 */
	public static final String ORDER_OVER_TIME = "order_over_time";

	/**
	 * 报修
	 * 
	 * @author zhangfx
	 *
	 */
	public static class Repair {
		/**
		 * 创建：用户从app上报
		 */
		public static final short CREATE = 0;
		/**
		 * 派发工单
		 */
		public static final short CREATE_ORDER = 1;
		/**
		 * 关闭
		 */
		public static final short CLOSED = 2;
		
		public static Map<Short,String> titles=new HashMap<>();
		
		static{
			titles.put(CREATE, "待处理");
			titles.put(CREATE_ORDER, "已处理");
			titles.put(CLOSED, "已处理");
		}
	}

	/**
	 * 工单
	 * 
	 * @author zhangfx
	 *
	 */
	public static class Order {

		/**
		 * 来源
		 */
		public static final String FROM_REPAIR = "0";
		/*
		 * 工单类型
		 */
		public static final String ORDER_TYPE="order_type";
		
		/**
		 * 工单类型
		 * @author changyh
		 *
		 */
		public static class OrderType{
			
			/**
			 * 清洁工单
			 */
			public static final Short CLEAN = 2;
			
			/**
			 * 充电工单
			 */
			public static final Short CHARGING = 1;
			
			/**
			 * 调度工单
			 */
			public static final Short DISPATCH = 3;
		}
		
		/**
		 * 工单来源
		 * @author changyh
		 *
		 */
		public static class OrderSource{
			
			/**
			 * 微租车平台
			 */
			public static final Short WZC = 1;
		}

		/**
		 * 工单状态
		 * 
		 * @author zhangfx
		 *
		 */
		public static class Status {
			/**
			 * 待处理：新建
			 */
			public static final short CREATE = 0;
			/**
			 * 待处理：尚未派单
			 */
			public static final short SENDING=1;
			/**
			 * 待处理：待接单
			 */
			public static final short SENDED=2;
			/**
			 * 处理中：已接单
			 */
			public static final short ACCPEPTED=3;
			/**
			 * 待确认：已报单
			 */
			public static final short CONFIRM=4;
			/**
			 * 待评价：已完成
			 */
			public static final short COMPLATE=5;
			/**
			 * 已取消
			 */
			public static final short CANCEL=6;
			/**
			 * 已评价
			 */
			public static final short EVALUATE=7;
			/**
			 *  待处理：超时未接
			 */
			public static final short ACCEPT_TIMEOUT=8;
			/**
			 * 处理中：超时未完成
			 */
			public static final short COMPLATE_TIMEOUT=9;
			
			public static Map<Short,String> titles=new HashMap<>();
			
			static{
				titles.put(CREATE, "待处理");
				titles.put(SENDING, "待处理");
				titles.put(SENDED, "待处理");
				titles.put(ACCPEPTED, "处理中");
				titles.put(CONFIRM, "待确认");
				titles.put(COMPLATE, "待评价");
				titles.put(CANCEL, "已取消");
				titles.put(EVALUATE, "已评价");
			}
			
		}

		/**
		 * 超时设置显示参数
		 */
		public static final String OVER_TIME = "paramSet";

	}

	/**
	 * 工单流转状态
	 * 
	 * @author zhangfx
	 *
	 */
	public static class OrderOperateType {
		/**
		 * 创建
		 */
		public static final Short CREATE = 0;
		/**
		 * 接单
		 */
		public static final Short ACCEPT = 1;
		/**
		 * 取消
		 */
		public static final Short CANCEL = 2;
		/**
		 * 报单
		 */
		public static final Short SUBMIT = 3;
		
		/**
		 * 完成
		 */
		public static final Short COMPALATE = 4;
		/**
		 * 转派
		 */
		public static final Short RECYCLE = 5;
		
		/**
		 * 评价
		 */
		public static final Short EVALUATE = 6;
	}

	/**
	 * 检查项目类型
	 * 
	 * @author zhangfx
	 *
	 */
	public static class CheckListType {
		/**
		 * 充电
		 */
		public static final Short CHARGE = 1;
		/**
		 * 清洁检查
		 */
		public static final Short CLEAR = 2;

		/**
		 * 车况检查
		 */
		public static final Short CAR_CHECK = 3;
	}
	/**
	 * 检查项
	 * @author zhangfx
	 *
	 */
	public static class CheckList{
		/**
		 * 清洁检查
		 */
		public static final String CLEAR="clear";
		/*
		 * 车况检查
		 */
		public static final String VEHICLE_CONDITION="vehicle_condition";
		/**
		 * 充电检查
		 */
		public static final String CHARGING_CONDITION="charging_condition";
		//评价项，不属于检查项，为了构建统一的展示格式
		public static final String EVALUATE="evaluate";
	}
	/**
	 * 运维组
	 * 
	 * @author zhangfx
	 *
	 */
	public static class Group {
		/**
		 * 正常
		 */
		public static final byte NOMARL = 0;
		
		/**
		 * 删除
		 */
		public static final byte DELETE = 9;
	}
	

	/**
	 * 运维人员
	 * 
	 * @author zhangfx
	 *
	 */
	public static class Member {
		/**
		 * 正常
		 */
		public static final byte NOMARL = 0;
		
		/**
		 * 禁用
		 */
		public static final byte DISABLED = 1;
		
		/**
		 * 删除
		 */
		public static final byte DELETE = 9;
	}

}
