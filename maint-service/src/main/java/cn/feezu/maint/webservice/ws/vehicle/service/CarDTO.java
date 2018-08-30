
package cn.feezu.maint.webservice.ws.vehicle.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>carDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="carDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="activeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="addtime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="annualcheckDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="appStationDTO" type="{http://service.vehicle.ws.meta.manage.wzc.com/}appStationDTO" minOccurs="0"/&gt;
 *         &lt;element name="atm" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *         &lt;element name="avaliParkingStation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="avaliParkingStationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="brandId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="business" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="buyamount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="buydate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="buykm" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="buytax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="carId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="carImg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="carObdDTO" type="{http://service.vehicle.ws.meta.manage.wzc.com/}carObdDTO" minOccurs="0"/&gt;
 *         &lt;element name="carTypeBusinessStatus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="cartypeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cartypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="certificateImages" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="color" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="comCartypeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="comCartypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="currentKm" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="deviceBussinessType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deviceNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="devices" type="{http://service.vehicle.ws.meta.manage.wzc.com/}carDeviceDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="electrombile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="engineno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="findType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="firstRentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fueltype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastResidenceTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="license" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loanAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="maintainKmInterval" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="modifytime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="online" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="operateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="operationStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="outVolume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="packageIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="parkingStation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parkingStationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parkingStoreName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parkingStoreid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="priceIdFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="priceTemplateId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="property" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *         &lt;element name="protocol809" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="publishFlag" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *         &lt;element name="publishTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="published" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="queryEndBuydate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="regionalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="regionalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="relayType" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="renewalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="repayDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="residenceTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="seriesId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stationDTOs" type="{http://service.vehicle.ws.meta.manage.wzc.com/}appStationDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="stationType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *         &lt;element name="statusString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="storeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="storeid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="trackerDeviceNo" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="unPublishTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="vin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "carDTO", propOrder = {
    "activeId",
    "addtime",
    "annualcheckDate",
    "atm",
    "avaliParkingStation",
    "avaliParkingStationId",
    "brandId",
    "business",
    "buyamount",
    "buydate",
    "buykm",
    "buytax",
    "carId",
    "carImg",
    "carTypeBusinessStatus",
    "cartypeId",
    "cartypeName",
    "certificateImages",
    "color",
    "comCartypeId",
    "comCartypeName",
    "companyId",
    "currentKm",
    "deviceBussinessType",
    "deviceNo",
    "electrombile",
    "engineno",
    "findType",
    "firstRentId",
    "fueltype",
    "lastResidenceTime",
    "license",
    "loanAmount",
    "maintainKmInterval",
    "modifytime",
    "online",
    "operateDate",
    "operationStatus",
    "outVolume",
    "packageIds",
    "parkingStation",
    "parkingStationId",
    "parkingStoreName",
    "parkingStoreid",
    "priceIdFlag",
    "priceTemplateId",
    "property",
    "protocol809",
    "publishFlag",
    "publishTime",
    "published",
    "queryEndBuydate",
    "regionalId",
    "regionalName",
    "relayType",
    "remark",
    "renewalDate",
    "repayDate",
    "residenceTime",
    "seriesId",
    "stationType",
    "status",
    "statusString",
    "storeName",
    "storeid",
    "trackerDeviceNo",
    "unPublishTime",
    "vin"
})
public class CarDTO {

    protected String activeId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar addtime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar annualcheckDate;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer atm;
    protected String avaliParkingStation;
    protected String avaliParkingStationId;
    protected String brandId;
    @XmlElement(nillable = true)
    protected List<String> business;
    protected BigDecimal buyamount;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar buydate;
    protected BigDecimal buykm;
    protected BigDecimal buytax;
    protected String carId;
    protected String carImg;
    protected Boolean carTypeBusinessStatus;
    protected String cartypeId;
    protected String cartypeName;
    protected String certificateImages;
    protected String color;
    protected String comCartypeId;
    protected String comCartypeName;
    protected String companyId;
    protected BigDecimal currentKm;
    protected String deviceBussinessType;
    protected String deviceNo;
    protected Integer electrombile;
    protected String engineno;
    protected String findType;
    protected String firstRentId;
    protected String fueltype;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastResidenceTime;
    protected String license;
    protected BigDecimal loanAmount;
    protected BigDecimal maintainKmInterval;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifytime;
    protected boolean online;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operateDate;
    protected String operationStatus;
    protected String outVolume;
    @XmlElement(nillable = true)
    protected List<String> packageIds;
    protected String parkingStation;
    protected String parkingStationId;
    protected String parkingStoreName;
    protected String parkingStoreid;
    protected Integer priceIdFlag;
    protected String priceTemplateId;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer property;
    protected Integer protocol809;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer publishFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar publishTime;
    protected String published;
    protected String queryEndBuydate;
    protected String regionalId;
    protected String regionalName;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer relayType;
    protected String remark;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar renewalDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar repayDate;
    protected String residenceTime;
    protected String seriesId;
    protected Integer stationType;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer status;
    protected String statusString;
    protected String storeName;
    protected String storeid;
    @XmlElement(nillable = true)
    protected List<String> trackerDeviceNo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar unPublishTime;
    protected String vin;

    /**
     * 获取activeId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveId() {
        return activeId;
    }

    /**
     * 设置activeId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveId(String value) {
        this.activeId = value;
    }

    /**
     * 获取addtime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAddtime() {
        return addtime;
    }

    /**
     * 设置addtime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAddtime(XMLGregorianCalendar value) {
        this.addtime = value;
    }

    /**
     * 获取annualcheckDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAnnualcheckDate() {
        return annualcheckDate;
    }

    /**
     * 设置annualcheckDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAnnualcheckDate(XMLGregorianCalendar value) {
        this.annualcheckDate = value;
    }

    /**
     * 获取atm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAtm() {
        return atm;
    }

    /**
     * 设置atm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAtm(Integer value) {
        this.atm = value;
    }

    /**
     * 获取avaliParkingStation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvaliParkingStation() {
        return avaliParkingStation;
    }

    /**
     * 设置avaliParkingStation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvaliParkingStation(String value) {
        this.avaliParkingStation = value;
    }

    /**
     * 获取avaliParkingStationId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvaliParkingStationId() {
        return avaliParkingStationId;
    }

    /**
     * 设置avaliParkingStationId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvaliParkingStationId(String value) {
        this.avaliParkingStationId = value;
    }

    /**
     * 获取brandId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * 设置brandId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandId(String value) {
        this.brandId = value;
    }

    /**
     * Gets the value of the business property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the business property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBusiness().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getBusiness() {
        if (business == null) {
            business = new ArrayList<String>();
        }
        return this.business;
    }

    /**
     * 获取buyamount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBuyamount() {
        return buyamount;
    }

    /**
     * 设置buyamount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBuyamount(BigDecimal value) {
        this.buyamount = value;
    }

    /**
     * 获取buydate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBuydate() {
        return buydate;
    }

    /**
     * 设置buydate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBuydate(XMLGregorianCalendar value) {
        this.buydate = value;
    }

    /**
     * 获取buykm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBuykm() {
        return buykm;
    }

    /**
     * 设置buykm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBuykm(BigDecimal value) {
        this.buykm = value;
    }

    /**
     * 获取buytax属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBuytax() {
        return buytax;
    }

    /**
     * 设置buytax属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBuytax(BigDecimal value) {
        this.buytax = value;
    }

    /**
     * 获取carId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarId() {
        return carId;
    }

    /**
     * 设置carId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarId(String value) {
        this.carId = value;
    }

    /**
     * 获取carImg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarImg() {
        return carImg;
    }

    /**
     * 设置carImg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarImg(String value) {
        this.carImg = value;
    }

    /**
     * 获取carTypeBusinessStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCarTypeBusinessStatus() {
        return carTypeBusinessStatus;
    }

    /**
     * 设置carTypeBusinessStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCarTypeBusinessStatus(Boolean value) {
        this.carTypeBusinessStatus = value;
    }

    /**
     * 获取cartypeId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCartypeId() {
        return cartypeId;
    }

    /**
     * 设置cartypeId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCartypeId(String value) {
        this.cartypeId = value;
    }

    /**
     * 获取cartypeName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCartypeName() {
        return cartypeName;
    }

    /**
     * 设置cartypeName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCartypeName(String value) {
        this.cartypeName = value;
    }

    /**
     * 获取certificateImages属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateImages() {
        return certificateImages;
    }

    /**
     * 设置certificateImages属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateImages(String value) {
        this.certificateImages = value;
    }

    /**
     * 获取color属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置color属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * 获取comCartypeId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComCartypeId() {
        return comCartypeId;
    }

    /**
     * 设置comCartypeId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComCartypeId(String value) {
        this.comCartypeId = value;
    }

    /**
     * 获取comCartypeName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComCartypeName() {
        return comCartypeName;
    }

    /**
     * 设置comCartypeName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComCartypeName(String value) {
        this.comCartypeName = value;
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
     * 获取currentKm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurrentKm() {
        return currentKm;
    }

    /**
     * 设置currentKm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurrentKm(BigDecimal value) {
        this.currentKm = value;
    }

    /**
     * 获取deviceBussinessType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceBussinessType() {
        return deviceBussinessType;
    }

    /**
     * 设置deviceBussinessType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceBussinessType(String value) {
        this.deviceBussinessType = value;
    }

    /**
     * 获取deviceNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * 设置deviceNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceNo(String value) {
        this.deviceNo = value;
    }

    /**
     * 获取electrombile属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getElectrombile() {
        return electrombile;
    }

    /**
     * 设置electrombile属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setElectrombile(Integer value) {
        this.electrombile = value;
    }

    /**
     * 获取engineno属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEngineno() {
        return engineno;
    }

    /**
     * 设置engineno属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEngineno(String value) {
        this.engineno = value;
    }

    /**
     * 获取findType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFindType() {
        return findType;
    }

    /**
     * 设置findType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFindType(String value) {
        this.findType = value;
    }

    /**
     * 获取firstRentId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstRentId() {
        return firstRentId;
    }

    /**
     * 设置firstRentId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstRentId(String value) {
        this.firstRentId = value;
    }

    /**
     * 获取fueltype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFueltype() {
        return fueltype;
    }

    /**
     * 设置fueltype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFueltype(String value) {
        this.fueltype = value;
    }

    /**
     * 获取lastResidenceTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastResidenceTime() {
        return lastResidenceTime;
    }

    /**
     * 设置lastResidenceTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastResidenceTime(XMLGregorianCalendar value) {
        this.lastResidenceTime = value;
    }

    /**
     * 获取license属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicense() {
        return license;
    }

    /**
     * 设置license属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicense(String value) {
        this.license = value;
    }

    /**
     * 获取loanAmount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    /**
     * 设置loanAmount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLoanAmount(BigDecimal value) {
        this.loanAmount = value;
    }

    /**
     * 获取maintainKmInterval属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaintainKmInterval() {
        return maintainKmInterval;
    }

    /**
     * 设置maintainKmInterval属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaintainKmInterval(BigDecimal value) {
        this.maintainKmInterval = value;
    }

    /**
     * 获取modifytime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifytime() {
        return modifytime;
    }

    /**
     * 设置modifytime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifytime(XMLGregorianCalendar value) {
        this.modifytime = value;
    }

    /**
     * 获取online属性的值。
     * 
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * 设置online属性的值。
     * 
     */
    public void setOnline(boolean value) {
        this.online = value;
    }

    /**
     * 获取operateDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOperateDate() {
        return operateDate;
    }

    /**
     * 设置operateDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperateDate(XMLGregorianCalendar value) {
        this.operateDate = value;
    }

    /**
     * 获取operationStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationStatus() {
        return operationStatus;
    }

    /**
     * 设置operationStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationStatus(String value) {
        this.operationStatus = value;
    }

    /**
     * 获取outVolume属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutVolume() {
        return outVolume;
    }

    /**
     * 设置outVolume属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutVolume(String value) {
        this.outVolume = value;
    }

    /**
     * Gets the value of the packageIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packageIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackageIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPackageIds() {
        if (packageIds == null) {
            packageIds = new ArrayList<String>();
        }
        return this.packageIds;
    }

    /**
     * 获取parkingStation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParkingStation() {
        return parkingStation;
    }

    /**
     * 设置parkingStation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParkingStation(String value) {
        this.parkingStation = value;
    }

    /**
     * 获取parkingStationId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParkingStationId() {
        return parkingStationId;
    }

    /**
     * 设置parkingStationId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParkingStationId(String value) {
        this.parkingStationId = value;
    }

    /**
     * 获取parkingStoreName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParkingStoreName() {
        return parkingStoreName;
    }

    /**
     * 设置parkingStoreName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParkingStoreName(String value) {
        this.parkingStoreName = value;
    }

    /**
     * 获取parkingStoreid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParkingStoreid() {
        return parkingStoreid;
    }

    /**
     * 设置parkingStoreid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParkingStoreid(String value) {
        this.parkingStoreid = value;
    }

    /**
     * 获取priceIdFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriceIdFlag() {
        return priceIdFlag;
    }

    /**
     * 设置priceIdFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriceIdFlag(Integer value) {
        this.priceIdFlag = value;
    }

    /**
     * 获取priceTemplateId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceTemplateId() {
        return priceTemplateId;
    }

    /**
     * 设置priceTemplateId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceTemplateId(String value) {
        this.priceTemplateId = value;
    }

    /**
     * 获取property属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProperty() {
        return property;
    }

    /**
     * 设置property属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProperty(Integer value) {
        this.property = value;
    }

    /**
     * 获取protocol809属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProtocol809() {
        return protocol809;
    }

    /**
     * 设置protocol809属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProtocol809(Integer value) {
        this.protocol809 = value;
    }

    /**
     * 获取publishFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPublishFlag() {
        return publishFlag;
    }

    /**
     * 设置publishFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPublishFlag(Integer value) {
        this.publishFlag = value;
    }

    /**
     * 获取publishTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPublishTime() {
        return publishTime;
    }

    /**
     * 设置publishTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPublishTime(XMLGregorianCalendar value) {
        this.publishTime = value;
    }

    /**
     * 获取published属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublished() {
        return published;
    }

    /**
     * 设置published属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublished(String value) {
        this.published = value;
    }

    /**
     * 获取queryEndBuydate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryEndBuydate() {
        return queryEndBuydate;
    }

    /**
     * 设置queryEndBuydate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryEndBuydate(String value) {
        this.queryEndBuydate = value;
    }

    /**
     * 获取regionalId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionalId() {
        return regionalId;
    }

    /**
     * 设置regionalId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionalId(String value) {
        this.regionalId = value;
    }

    /**
     * 获取regionalName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionalName() {
        return regionalName;
    }

    /**
     * 设置regionalName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionalName(String value) {
        this.regionalName = value;
    }

    /**
     * 获取relayType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRelayType() {
        return relayType;
    }

    /**
     * 设置relayType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRelayType(Integer value) {
        this.relayType = value;
    }

    /**
     * 获取remark属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置remark属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * 获取renewalDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRenewalDate() {
        return renewalDate;
    }

    /**
     * 设置renewalDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRenewalDate(XMLGregorianCalendar value) {
        this.renewalDate = value;
    }

    /**
     * 获取repayDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRepayDate() {
        return repayDate;
    }

    /**
     * 设置repayDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRepayDate(XMLGregorianCalendar value) {
        this.repayDate = value;
    }

    /**
     * 获取residenceTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenceTime() {
        return residenceTime;
    }

    /**
     * 设置residenceTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenceTime(String value) {
        this.residenceTime = value;
    }

    /**
     * 获取seriesId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeriesId() {
        return seriesId;
    }

    /**
     * 设置seriesId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeriesId(String value) {
        this.seriesId = value;
    }


    /**
     * 获取stationType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStationType() {
        return stationType;
    }

    /**
     * 设置stationType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStationType(Integer value) {
        this.stationType = value;
    }

    /**
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    /**
     * 获取statusString属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusString() {
        return statusString;
    }

    /**
     * 设置statusString属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusString(String value) {
        this.statusString = value;
    }

    /**
     * 获取storeName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * 设置storeName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreName(String value) {
        this.storeName = value;
    }

    /**
     * 获取storeid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreid() {
        return storeid;
    }

    /**
     * 设置storeid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreid(String value) {
        this.storeid = value;
    }

    /**
     * Gets the value of the trackerDeviceNo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trackerDeviceNo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrackerDeviceNo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTrackerDeviceNo() {
        if (trackerDeviceNo == null) {
            trackerDeviceNo = new ArrayList<String>();
        }
        return this.trackerDeviceNo;
    }

    /**
     * 获取unPublishTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUnPublishTime() {
        return unPublishTime;
    }

    /**
     * 设置unPublishTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUnPublishTime(XMLGregorianCalendar value) {
        this.unPublishTime = value;
    }

    /**
     * 获取vin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVin() {
        return vin;
    }

    /**
     * 设置vin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVin(String value) {
        this.vin = value;
    }

}
