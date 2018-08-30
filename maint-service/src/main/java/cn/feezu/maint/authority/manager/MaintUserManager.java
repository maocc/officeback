package cn.feezu.maint.authority.manager;

import cn.feezu.maint.webservice.ws.authority.service.Result;

/**
 * @author maocc
 * @description
 * @date: 2018/3/30 15:54
 */
public interface MaintUserManager {

	public Result maintianUserLogin(String userAccount ,String userPassword);
}
