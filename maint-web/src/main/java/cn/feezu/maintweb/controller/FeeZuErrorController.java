package cn.feezu.maintweb.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.feezu.wzc.commom.web.rest.util.HeaderUtil;
import cn.feezu.wzc.commom.web.rest.util.ResponseUtil;


@Controller
public class FeeZuErrorController implements ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(FeeZuErrorController.class);
	
	private static FeeZuErrorController appErrorController;

	/**

	 * Error Attributes in the Application

	 */
	@Autowired
	private ErrorAttributes errorAttributes;

	private final static String ERROR_PATH = "/error";

	/**

	 * Controller for the Error Controller

	 * 

	 * @param errorAttributes

	 * @return

	 */

	public FeeZuErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	public FeeZuErrorController() {
		if (appErrorController == null) {
			appErrorController = new FeeZuErrorController(errorAttributes);
		}
	}

	/**

	 * Supports the HTML Error View

	 * 

	 * @param request

	 * @return

	 */
/*	@RequestMapping(value = ERROR_PATH)

	public ModelAndView errorHtml(HttpServletRequest request) {

		return new ModelAndView("error", getErrorAttributes(request, false));

	}*/
	@RequestMapping(value = ERROR_PATH)
	public Object errorHtml(Model model ,HttpServletRequest request) {
		 
		 
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes, true);
		 
		logger.info("method [error info]: status-" + map.get("status") + ", request url-"+map.get("message") );
		
		logger.info("system error [error info]: " +map );
		model.addAllAttributes(map);
		 
		
		if(map.get("exception")!=null &&map.get("exception").toString().contains("UserNotActivatedException")){
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert("user login", "message", "账号已禁用，请联系管理员"), HttpStatus.UNAUTHORIZED);
		}
		if("401".equals(map.get("status"))){
			return ResponseUtil.badResponse(HeaderUtil.createFailureAlert("user login", "message", "登录信息失效，请重新登录"), HttpStatus.UNAUTHORIZED);
		}
		
		HttpStatus status = getStatus(request);
		return new ResponseEntity<Map<String, Object>>(map, status);
		/*if(401==(Integer)map.get("status")){
			return "redirect:/";
		}
		return "error";*/
	}
	/**

	 * Supports other formats like JSON, XML

	 * 

	 * @param request

	 * @return

	 */
/*	@RequestMapping(value = ERROR_PATH)

	@ResponseBody

	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {

		Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));

		HttpStatus status = getStatus(request);

		return new ResponseEntity<Map<String, Object>>(body, status);

	}*/

	/**

	 * Returns the path of the error page.

	 *

	 * @return the error path

	 */
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	/*	

	 * private boolean getTraceParameter(HttpServletRequest request) {

			String parameter = request.getParameter("trace");

			if (parameter == null) {

				return false;

			}

			return !"false".equals(parameter.toLowerCase());

		}



		private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {

			RequestAttributes requestAttributes = new ServletRequestAttributes(request);

			Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);

			String URL = request.getRequestURL().toString();

			map.put("URL", URL);

			logger.debug("AppErrorController.method [error info]: status-" + map.get("status") + ", request url-" + URL);

			return map;

		}*/

	 
	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode != null) {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (Exception ex) {
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	private WebApplicationContext getContent(HttpServletRequest request){
		return null;//WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
	}
	
	private <T> T  getBean(HttpServletRequest request,Class <T> clazz){
		return  getContent(request).getBean(clazz);
	}
}

