package cn.feezu.maintapp.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;
import cn.feezu.wzc.common.util.fastdfs.FastDFSTemplate;
import cn.feezu.wzc.common.util.fastdfs.FastDfsRv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Controller
@RequestMapping("/system")
@Api(tags = "系统相关", description = "提供系统相关")
public class SystemResource extends CoreResource{
	static private final Logger logger = LoggerFactory.getLogger(SystemResource.class);
	@Autowired
	private FastDFSTemplate fastDFSTemplate;

	@ApiOperation("关于我们")
	@GetMapping("/aboutus")
	public String aboutus() {
		// 会议上讨论的是把所有的待处理工单查处理啊，然后内存排序返回
		return "Hello World!";
	}

	/**
	 * 车辆图片上传接口
	 * 
	 * @param file
	 * @return
	 */
	@ApiOperation("图片上传")
	@PostMapping(value = "/file")
	public ResponseEntity<FileUploadDTO> uploadFile(@RequestParam("file") MultipartFile file) {
		// check version & filename
		boolean res = true;
		String realFileName = "";

		try {
			logger.info("file = {}", file.getOriginalFilename());
			realFileName = file.getOriginalFilename();

			String extensionName = realFileName.indexOf(".") == -1 ? null
					: realFileName.substring(realFileName.lastIndexOf(".") + 1);

			FastDfsRv dfs = fastDFSTemplate.upload(file.getBytes(), extensionName);
			realFileName = dfs.getPath();
			logger.info("file = {}", realFileName);
		} catch (Exception e) {
			e.printStackTrace();
			res = false; // 上传文件失败
			logger.info("file = {}", e.getStackTrace().toString());
		}
		if (!res) {
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert("file", "file_upload_error", "上传失败"));
		}

		return ResponseEntity.ok().headers(HeaderUtil.createAlert("file", "上传成功")).body(new FileUploadDTO(realFileName));
	}
}

@Data
class FileUploadDTO{
	@ApiModelProperty(value="文件地址")
	private String path;

	public FileUploadDTO(String path) {
		super();
		this.path = path;
	}
}