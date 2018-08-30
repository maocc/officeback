package cn.feezu.common.util;


import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Pageable;
/**
 * 
 * @author zhangfx
 *
 */
public class CommonUtils {
	/**
	 * 
	 * session key
	 * 
	 */
	public static final String LOGIN_SESSION_KEY = "login_session_key";
	/**
	 * 管理员
	 */
	public static final String ADMIN_LOGIN_SESSION_KEY = "admin_login_session_key";
	
	/**
	 * 
	 * session user Id
	 * 
	 */
	public static final String USER_ID_SESSION_KEY = "user";
	/**
	 * 
	 * 验证码
	 * 
	 */
	public static final String SESSION_KEYWORD = "yzkeyword";
	
	/**
	 * 授权标识
	 */
	public static final String HEAD_KEY="Authorization";

	 private static Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	
	 private static Pattern p = Pattern.compile("^[a-zA-Z0-9，。,.\u4E00-\u9FA5]+$");
	 /**
	 * 
	 * 过滤html代码
	 * 
	 * @param s
	 * 
	 * @return
	 * 
	 */
	public static String replaceHtml(final String s) {
		if (s != null && !s.equals("")) {
			return s.replaceAll("<[.[^<]]*>", "");
		}
		return s;
	}
	/**

	 * 生成32位随机ID

	 * @return

	 */
	public static String getUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	 /**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }

	 /**
	  * 验证手机号码
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkMobileNumber(String mobileNumber){
	  boolean flag = false;
	  try{
	   
	    Matcher matcher = regex.matcher(mobileNumber);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	 /**
	  * 验证只能包含数字英文汉子逗号句号
	  * @param content
	  * @return
	  */
	public static boolean checkVerifyString(String content) {
		Matcher m = p.matcher(content);
		return m.find();
	}
	
	/**
	 * 从集合中获取当前页数所需的数据
	 * @param content
	 * @param pageable
	 * @return
	 */
	public static <T> List<T> subList(List<T> content, Pageable pageable) {
		if (content.size() > pageable.getPageSize()) {

			int end = Math.min(pageable.getOffset() + pageable.getPageSize(), content.size());
			if(pageable.getOffset()<end ){
				content = content.subList(pageable.getOffset(),
						end );
			}else{
				content.clear();
			}
		}
		return content;
	}
}
