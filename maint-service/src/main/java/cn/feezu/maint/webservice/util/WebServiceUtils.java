package cn.feezu.maint.webservice.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.feezu.maint.webservice.ws.authority.service.Result;
import cn.feezu.maint.webservice.ws.authority.service.UserService;
import cn.feezu.maint.webservice.ws.vehicle.service.CarObdDTO;
import cn.feezu.maint.webservice.ws.vehicle.service.CarService;
import io.jsonwebtoken.lang.Arrays;
public class WebServiceUtils {

	final static Logger logger = LoggerFactory.getLogger(WebServiceUtils.class);
	 
	/**
	 * 用户wsdl
	 */
	private static final String USER_SERVICE_URL = "%s/metadata/services/authority/userService?wsdl";

	/**
	 * 车辆wsdl
	 */
	private static final String CAR_SERVICE_URL = "%s/metadata/services/vehicle/carService?wsdl";
	
	private static UserService userService(String host) throws MalformedURLException {
		// 创建WSDL的URL，注意不是服务地址
		URL url = new URL(String.format(USER_SERVICE_URL, host));

		// 创建服务名称
		QName qname = new QName("http://service.authority.meta.manage.wzc.com/", "UserService");
		// 创建服务视图
		Service service = Service.create(url, qname);
		// 获取服务实现类
		UserService cService = service.getPort(UserService.class);

		return cService;
	}

	private static CarService carService(String host) throws MalformedURLException {
		// 创建WSDL的URL，注意不是服务地址
		URL url = new URL(String.format(CAR_SERVICE_URL, host));

		// 创建服务名称
		QName qname = new QName("http://service.vehicle.meta.manage.wzc.com/", "carService");
		// 创建服务视图
		Service service = Service.create(url, qname);
		// 获取服务实现类
		CarService cService = service.getPort(CarService.class);

		return cService;
	}
	
	/**
	 * 获取登录用户信息
	 * 
	 * @param host
	 * @param pagesNum
	 * @param rows
	 * @return
	 */
	public static Result login(String host, String username, String password) {
		
		 Result result =new Result();
		try {
			 result = userService(host).login(username, password);
			 return result;
		 } catch (MalformedURLException e) {
			logger.error("获取用戶信息失敗", e);
		} catch (WebServiceException e) {
			logger.error("用户服务器连接失败", e);
			result.setErrors(java.util.Arrays.asList("用户服务器连接失败"));
		}
		result.setSuccess(false);
		if(CollectionUtils.isEmpty(result.getErrors())){
			result.setErrors(java.util.Arrays.asList("连接失败"));
		}
		return result; 
	}

	public static CarObdDTO getCarObdInfoById(String host,String id){
		try {
			return carService(host).getCarObdInfoById(id);
		} catch (MalformedURLException e) {
			logger.error("获取车辆信息失敗", e);
		}
		return null;
	}
	 
}
//cn.feezu.maint.webservice.authority.service
// wsimport -p cn.feezu.maint.webservice.authority.service -keep https://www.feezu.cn/metadata/services/authority/userService?wsdl
//wsimport -p cn.feezu.maint.webservice.ws.vehicle.service -keep http://www.feezu.cn/metadata/services/vehicle/carService?wsdl
