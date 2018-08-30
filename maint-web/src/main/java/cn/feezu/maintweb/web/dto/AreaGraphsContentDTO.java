package cn.feezu.maintweb.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaGraphsContentDTO {
	
	@ApiModelProperty(value = "图形类型" + "\t\n Rectangle(矩形):1" + " \t\n 圆形(Circle):2" + " \t\n Polygon(多边形):3")
	private Byte type;
	
	@ApiModelProperty(value = "图形点内容" + "\t\n Rectangle(矩形):1   :左下维度_坐下经度,右上维度_右上经度"
			+ " \t\n 圆形(Circle):2   :31.839064_117.219116,5,维度1_经度1,半径（单位：米）,......."
			+ " \t\n Polygon(多边形):3   :31.839064_117.219116,31.83253_117.219403,维度1_经度1,维度2_经度2,.......")
	private String value;
}
