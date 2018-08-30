package cn.feezu.maintweb.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaGraphsDTO {

	private Long groupId;
	private String groupName;
	private Long areaId;
	private List<AreaGraphsContentDTO> content;
	// @RequestParam(name = "id",required = false) Long
	// id,@RequestParam("graphType") Byte graphType,
	// private String points,

	// @RequestParam("areaId") Long areaId, @RequestParam("groupId")
}
