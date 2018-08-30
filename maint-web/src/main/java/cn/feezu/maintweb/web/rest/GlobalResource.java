package cn.feezu.maintweb.web.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import cn.feezu.wzc.commom.web.rest.util.CustomTimestampEditor;
import cn.feezu.wzc.commom.web.rest.util.StringEscapeEditor;

@ControllerAdvice
public class GlobalResource {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);*/

		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);
		// 自动转换日期类型的字段格式

		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(/*java.sql.Timestamp.class*/Date.class, new CustomTimestampEditor(datetimeFormat, true));

		// 防止XSS攻击

		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}

}
