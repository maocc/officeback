package cn.feezu.maintweb.web.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.feezu.maint.authority.entity.Group;
import cn.feezu.maint.authority.entity.YwAreaGraph;
import cn.feezu.maint.authority.entity.YwMaintArea;
import cn.feezu.maint.authority.manager.GroupManager;
import cn.feezu.maint.authority.manager.MaintAreaManager;
import cn.feezu.maint.authority.manager.YwAreaGraphManager;
import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author maocc
 * @description
 * @date: 2018/4/4 11:22
 */
@RestController
@RequestMapping("/web-api/area")
@Api(tags = "区域管理服务", description = "区域管理")
public class MaintAreaResource extends CoreResource{
	private static final Logger logger = LoggerFactory.getLogger(MaintAreaResource.class);

	private static String ENTITY_NAME = "area";

	@Autowired
	private MaintAreaManager maintAreaManager;

	@Autowired
	private YwAreaGraphManager ywAreaGraphManager;

	@Autowired
	private GroupManager groupManager;

	@ApiOperation("区域列表+模糊查询 不带参数查全部")
	@GetMapping("/list")
	public ResponseEntity<List<YwMaintArea>> list(@RequestParam(name = "name", required = false) String name) {
		 
		YwMaintArea ywMaintArea = new YwMaintArea();
		ywMaintArea.setName(name);
		 
		List<YwMaintArea> ywMaintAreaList = maintAreaManager.findByCompanyId(getCompanyId(),name);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "success")).body(ywMaintAreaList);
	}
	
	@ApiOperation("查询区域图形 不带参数查全部")
	@GetMapping("/getAreaGraph")
	public ResponseEntity<List<YwAreaGraph>> getAreaGraph(@RequestParam(name = "areaId", required = false) Long areaId) {
		YwAreaGraph ywAreaGraph = new YwAreaGraph();
		ywAreaGraph.setAreaId(areaId);
		List<YwAreaGraph> ywAreaGraphList = ywAreaGraphManager.findYwAreaGraphList(ywAreaGraph);

		return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除成功", "success")).body(ywAreaGraphList);
	}
	
/*	@ApiOperation("新增区域")
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestParam(name = "name") String name) {
		YwMaintArea ywMaintArea = new YwMaintArea();
		ywMaintArea.setName(name);
		List<YwMaintArea> ywMaintAreaList = maintAreaManager.findYwMaintAreaList(ywMaintArea);
		if (ywMaintAreaList != null && ywMaintAreaList.size() > 0) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "新增失败")).body("区域名称不允许重复");
		}
		maintAreaManager.save(ywMaintArea);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("新增成功", "success")).body("新增成功");
	}

	*/


/*	@ApiOperation("编辑区域图形 ")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", dataType = "string", required = true, value = "区域Id"),
		@ApiImplicitParam(paramType = "query", name = "graphType", dataType = "double", required = true, 
				value = "图形类型"
						+ "\t\n Rectangle(矩形):1"
						+" \t\n 圆形(Circle):2"
						+" \t\n Polygon(多边形):3"),
		@ApiImplicitParam(paramType = "query", name = "points", dataType = "string", required = true, 
		value = "图形点内容"
				+ "\t\n Rectangle(矩形):1   :左下维度_坐下经度,右上维度_右上经度"
				+" \t\n 圆形(Circle):2   :31.839064_117.219116,5,维度1_经度1,半径（单位：米）,......."
				+" \t\n Polygon(多边形):3   :31.839064_117.219116,31.83253_117.219403,维度1_经度1,维度2_经度2,......."),
		@ApiImplicitParam(paramType = "query", name = "id", dataType = "string", required = true, value = "区域Id"),
	})
	
	@PostMapping("/editAreaGraph")
	public ResponseEntity<String> editAreaGraph(@RequestParam(name = "id",required = false) Long id,@RequestParam("graphType") Byte graphType,
			@RequestParam("points") String points, @RequestParam("areaId") Long areaId, @RequestParam("groupId") Long groupId) {
		//判断区域是否存在
		YwMaintArea ywMaintArea = new YwMaintArea();
		ywMaintArea.setId(areaId);
		List<YwMaintArea> ywMaintAreaList = maintAreaManager.findYwMaintAreaList(ywMaintArea);
		if (ywMaintAreaList.size() == 0) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "新增区域图形")).body("区域不存在");
		}

		YwMaintArea maintArea = ywMaintAreaList.get(0);
		if(groupManager.selectByPrimaryKey(groupId) == null){
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "新增区域图形")).body("group不存在");
		}
		//更改组信息
		if (!groupId.equals(maintArea.getGroupId())) {
			maintArea.setGroupId(groupId);
			maintAreaManager.update(maintArea);
		}
		//保存图形信息
		YwAreaGraph ywAreaGraph = new YwAreaGraph();
		ywAreaGraph.setAreaId(areaId);
		ywAreaGraph.setGraphType(graphType);
		ywAreaGraph.setId(id);
		ywAreaGraph.setPoints(points);
		ywAreaGraphManager.saveYwAreaGraph(ywAreaGraph);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("新增区域图形", "success")).body("保存成功");
	}*/
//--------------------------------------------------------------------------------------------------------------------------
	
	@ApiOperation("查询区域及图形")
	@GetMapping("/{id:.+}")
	public ResponseEntity<YwMaintArea> findById(@PathVariable Long id) {
		Optional<YwMaintArea> area = maintAreaManager.findById(id);
		
		if(!area.isPresent()){
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "query", "区域不存在"), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改成功", "success")).body(area.get());
	}

	@ApiOperation("删除图形区域")
	@DeleteMapping("/{id:.+}")
	public ResponseEntity<Map<String, Object>> deleteArea(@PathVariable Long id) {
		Optional<YwMaintArea> area = maintAreaManager.findById(id);
		
		if(!area.isPresent()){
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "区域不存在"), HttpStatus.BAD_REQUEST);
		}
		boolean result =maintAreaManager.deleteById(id);
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除成功", "success")).body(results("result", result));
	}
	
	@ApiOperation("修改区域及图形")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "name", dataType = "string", required = true, value = "区域名称"),
		@ApiImplicitParam(paramType = "query", name = "groupId", dataType = "string", required = true, value = "归属组"),
		@ApiImplicitParam(paramType = "query", name = "graphType", dataType = "double", required = true, 
				value = "图形类型"
						+ "\t\n Rectangle(矩形):1"
						+" \t\n 圆形(Circle):2"
						+" \t\n Polygon(多边形):3"),
		@ApiImplicitParam(paramType = "query", name = "points", dataType = "string", required = true, 
		value = "图形点内容"
				+ "\t\n Rectangle(矩形):1   :左下维度_坐下经度,右上维度_右上经度"
				+" \t\n 圆形(Circle):2   :31.839064_117.219116,5,维度1_经度1,半径（单位：米）,......."
				+" \t\n Polygon(多边形):3   :31.839064_117.219116,31.83253_117.219403,维度1_经度1,维度2_经度2,......."),
	})
	@PostMapping("/{id:.+}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,@RequestParam("name")String name, @RequestParam("groupId") Long groupId,
			@RequestParam("graphType") Byte graphType,@RequestParam("points") String points) {
		
		Group group = groupManager.selectByPrimaryKey(groupId);
	 
		if( group== null){
			return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "修改失败，运维组不存在"), HttpStatus.BAD_REQUEST);
		}
		
		Optional<YwMaintArea> area = maintAreaManager.findById(id);
		
		if(!area.isPresent()){
			return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "区域不存在"), HttpStatus.BAD_REQUEST);
		}
		if (maintAreaManager.exists(name,getGroupIds())) {
			logger.info("区域名称重复：{}",name);
			return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "区域名称不允许重复"), HttpStatus.BAD_REQUEST);
		}
		
		ywAreaGraphManager.updateYwAreaGraph(id,name, groupId, graphType, points.replaceAll("&quot;", "\""));
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改成功", "success")).body(results("result", true));
	}
	
	@ApiOperation("新增区域及图形")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "name", dataType = "string", required = true, value = "区域名称"),
		@ApiImplicitParam(paramType = "query", name = "groupId", dataType = "string", required = true, value = "归属组"),
		@ApiImplicitParam(paramType = "query", name = "graphType", dataType = "double", required = true, 
				value = "图形类型"
						+ "\t\n Rectangle(矩形):1"
						+" \t\n 圆形(Circle):2"
						+" \t\n Polygon(多边形):3"),
		@ApiImplicitParam(paramType = "query", name = "points", dataType = "string", required = true, 
		value = "图形点内容"
				+ "\t\n Rectangle(矩形):1   :左下维度_坐下经度,右上维度_右上经度"
				+" \t\n 圆形(Circle):2   :31.839064_117.219116,5,维度1_经度1,半径（单位：米）,......."
				+" \t\n Polygon(多边形):3   :31.839064_117.219116,31.83253_117.219403,维度1_经度1,维度2_经度2,......."),
	})
	@PostMapping()
	public ResponseEntity<Map<String, Object>> save(@RequestParam("name")String name, @RequestParam("groupId") Long groupId,
			@RequestParam("graphType") Byte graphType,@RequestParam("points") String points) {
		
		Group group = groupManager.selectByPrimaryKey(groupId);
	 
		if( group== null){
			return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "新增区域图形失败"), HttpStatus.BAD_REQUEST);
		}
		if (maintAreaManager.exists(name,getGroupIds())) {
			return badResponse(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "区域名称不允许重复"), HttpStatus.BAD_REQUEST);
		}
		
		ywAreaGraphManager.saveYwAreaGraph(name, groupId, graphType, points.replaceAll("&quot;", "\""));
		
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("新增成功", "success")).body(results("result", true));
	}

/*	@ApiOperation("编辑区域图形 (多图形)废弃")
	@PostMapping("/editAreaGraphs")
	public ResponseEntity<String> editAreaGraphs(@RequestBody AreaGraphsDTO content) {
		Long areaId =content.getAreaId();
		//判断区域是否存在
		YwMaintArea ywMaintArea = new YwMaintArea();
		ywMaintArea.setId(areaId);
		List<YwMaintArea> ywMaintAreaList = maintAreaManager.findYwMaintAreaList(ywMaintArea);
		if (ywMaintAreaList.size() == 0) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "新增区域图形")).body("区域不存在");
		}
		Long groupId = content.getGroupId();
		YwMaintArea maintArea = ywMaintAreaList.get(0);
		if(groupManager.selectByPrimaryKey(groupId) == null){
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "add", "新增区域图形")).body("group不存在");
		}
		//更改组信息
		if (!groupId.equals(maintArea.getGroupId())) {
			maintArea.setGroupId(groupId);
			maintAreaManager.update(maintArea);
		}
		//保存图形信息
		
		List<AreaGraphsContentDTO> graphsContent = content.getContent();
		
		List<YwAreaGraph> entities = graphsContent.stream()
				.map(entity ->new YwAreaGraph(entity.getType(), entity.getValue(), areaId))
				.collect(Collectors.toList());
		
		ywAreaGraphManager.saveYwAreaGraph(entities);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("新增区域图形", "success")).body("保存成功");
	}*/
}
