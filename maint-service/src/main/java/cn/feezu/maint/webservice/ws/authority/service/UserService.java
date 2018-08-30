package cn.feezu.maint.webservice.ws.authority.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-30T14:58:21.105+08:00
 * Generated source version: 3.1.7
 */
@WebService(targetNamespace = "http://service.authority.ws.meta.manage.wzc.com/", name = "UserService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserService {


	@WebMethod
    @WebResult(name = "return", targetNamespace = "http://service.authority.ws.meta.manage.wzc.com/", partName = "return")
    public Result login(@WebParam(partName = "arg0", name = "arg0") String arg0, @WebParam(partName = "arg1", name = "arg1") String arg1);

/*
	@WebMethod
	@WebResult(name = "return", targetNamespace = "http://service.authority.ws.meta.manage.wzc.com/", partName = "return")
	public Result loginForYWD(@WebParam(partName = "arg0", name = "arg0") String arg0, @WebParam(partName = "arg1", name = "arg1") String arg1);

	@WebMethod
	@WebResult(name = "return", targetNamespace = "http://service.authority.ws.meta.manage.wzc.com/", partName = "return")
	public UserDTO findById(@WebParam(partName = "arg0", name = "arg0") String arg0);

	@WebMethod
	@WebResult(name = "return", targetNamespace = "http://service.authority.ws.meta.manage.wzc.com/", partName = "return")
	public UserDTO findByUserName(@WebParam(partName = "arg0", name = "arg0") String arg0);*/
}
