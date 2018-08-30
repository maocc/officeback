
package cn.feezu.maint.webservice.ws.authority.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>userDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="userDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="appManager" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="companyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="create_userid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="historyPasswd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orgName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ownerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ownerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="passwdUpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="roleID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="roleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="roleType" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/&gt;
 *         &lt;element name="shortCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sysUserType" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *         &lt;element name="userAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userFullname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userLevel" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/&gt;
 *         &lt;element name="userPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userStatus" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/&gt;
 *         &lt;element name="userTelphone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDTO", propOrder = {
    "appManager",
    "companyId",
    "companyName",
    "createUserid",
    "description",
    "historyPasswd",
    "id",
    "orgId",
    "orgName",
    "ownerId",
    "ownerName",
    "passwdUpdateTime",
    "roleID",
    "roleName",
    "roleType",
    "shortCompanyName",
    "sysUserType",
    "userAccount",
    "userFullname",
    "userLevel",
    "userPassword",
    "userStatus",
    "userTelphone"
})
public class UserDTO {

    protected boolean appManager;
    protected String companyId;
    protected String companyName;
    @XmlElement(name = "create_userid")
    protected String createUserid;
    protected String description;
    protected String historyPasswd;
    protected String id;
    protected String orgId;
    protected String orgName;
    protected String ownerId;
    protected String ownerName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar passwdUpdateTime;
    protected String roleID;
    protected String roleName;
    protected Short roleType;
    protected String shortCompanyName;
    protected Byte sysUserType;
    protected String userAccount;
    protected String userFullname;
    protected Short userLevel;
    protected String userPassword;
    protected Short userStatus;
    protected String userTelphone;

    /**
     * 获取appManager属性的值。
     * 
     */
    public boolean isAppManager() {
        return appManager;
    }

    /**
     * 设置appManager属性的值。
     * 
     */
    public void setAppManager(boolean value) {
        this.appManager = value;
    }

    /**
     * 获取companyId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置companyId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyId(String value) {
        this.companyId = value;
    }

    /**
     * 获取companyName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置companyName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * 获取createUserid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateUserid() {
        return createUserid;
    }

    /**
     * 设置createUserid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateUserid(String value) {
        this.createUserid = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取historyPasswd属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryPasswd() {
        return historyPasswd;
    }

    /**
     * 设置historyPasswd属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryPasswd(String value) {
        this.historyPasswd = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * 获取orgId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 设置orgId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgId(String value) {
        this.orgId = value;
    }

    /**
     * 获取orgName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置orgName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgName(String value) {
        this.orgName = value;
    }

    /**
     * 获取ownerId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 设置ownerId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerId(String value) {
        this.ownerId = value;
    }

    /**
     * 获取ownerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * 设置ownerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerName(String value) {
        this.ownerName = value;
    }

    /**
     * 获取passwdUpdateTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPasswdUpdateTime() {
        return passwdUpdateTime;
    }

    /**
     * 设置passwdUpdateTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPasswdUpdateTime(XMLGregorianCalendar value) {
        this.passwdUpdateTime = value;
    }

    /**
     * 获取roleID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleID() {
        return roleID;
    }

    /**
     * 设置roleID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleID(String value) {
        this.roleID = value;
    }

    /**
     * 获取roleName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置roleName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleName(String value) {
        this.roleName = value;
    }

    /**
     * 获取roleType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getRoleType() {
        return roleType;
    }

    /**
     * 设置roleType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setRoleType(Short value) {
        this.roleType = value;
    }

    /**
     * 获取shortCompanyName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortCompanyName() {
        return shortCompanyName;
    }

    /**
     * 设置shortCompanyName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortCompanyName(String value) {
        this.shortCompanyName = value;
    }

    /**
     * 获取sysUserType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getSysUserType() {
        return sysUserType;
    }

    /**
     * 设置sysUserType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setSysUserType(Byte value) {
        this.sysUserType = value;
    }

    /**
     * 获取userAccount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 设置userAccount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAccount(String value) {
        this.userAccount = value;
    }

    /**
     * 获取userFullname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserFullname() {
        return userFullname;
    }

    /**
     * 设置userFullname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserFullname(String value) {
        this.userFullname = value;
    }

    /**
     * 获取userLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getUserLevel() {
        return userLevel;
    }

    /**
     * 设置userLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setUserLevel(Short value) {
        this.userLevel = value;
    }

    /**
     * 获取userPassword属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 设置userPassword属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPassword(String value) {
        this.userPassword = value;
    }

    /**
     * 获取userStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getUserStatus() {
        return userStatus;
    }

    /**
     * 设置userStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setUserStatus(Short value) {
        this.userStatus = value;
    }

    /**
     * 获取userTelphone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserTelphone() {
        return userTelphone;
    }

    /**
     * 设置userTelphone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserTelphone(String value) {
        this.userTelphone = value;
    }

}
