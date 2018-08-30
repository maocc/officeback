
package cn.feezu.maint.webservice.ws.vehicle.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>carObdDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="carObdDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accStatus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="allowStartCar" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="batteryKm" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="batteryPower" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="carId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="centralLockStatus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="chargeStatus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="currentKm" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="doorAndTrunkStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doorStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="electrombile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fuelPercentage" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="lightStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="soc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeStamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="vehicleSpeed" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="windowStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "carObdDTO", propOrder = {
    "accStatus",
    "allowStartCar",
    "batteryKm",
    "batteryPower",
    "carId",
    "centralLockStatus",
    "chargeStatus",
    "currentKm",
    "doorAndTrunkStatus",
    "doorStatus",
    "electrombile",
    "fuelPercentage",
    "lightStatus",
    "soc",
    "timeStamp",
    "vehicleSpeed",
    "windowStatus"
})
public class CarObdDTO {

    protected Integer accStatus;
    protected Integer allowStartCar;
    protected Double batteryKm;
    protected Double batteryPower;
    protected String carId;
    protected Integer centralLockStatus;
    protected Integer chargeStatus;
    protected Double currentKm;
    protected String doorAndTrunkStatus;
    protected String doorStatus;
    protected Integer electrombile;
    protected Float fuelPercentage;
    protected String lightStatus;
    protected String soc;
    protected long timeStamp;
    protected Float vehicleSpeed;
    protected String windowStatus;

    /**
     * 获取accStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccStatus() {
        return accStatus;
    }

    /**
     * 设置accStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccStatus(Integer value) {
        this.accStatus = value;
    }

    /**
     * 获取allowStartCar属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAllowStartCar() {
        return allowStartCar;
    }

    /**
     * 设置allowStartCar属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAllowStartCar(Integer value) {
        this.allowStartCar = value;
    }

    /**
     * 获取batteryKm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBatteryKm() {
        return batteryKm;
    }

    /**
     * 设置batteryKm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBatteryKm(Double value) {
        this.batteryKm = value;
    }

    /**
     * 获取batteryPower属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBatteryPower() {
        return batteryPower;
    }

    /**
     * 设置batteryPower属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBatteryPower(Double value) {
        this.batteryPower = value;
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
     * 获取centralLockStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCentralLockStatus() {
        return centralLockStatus;
    }

    /**
     * 设置centralLockStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCentralLockStatus(Integer value) {
        this.centralLockStatus = value;
    }

    /**
     * 获取chargeStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChargeStatus() {
        return chargeStatus;
    }

    /**
     * 设置chargeStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChargeStatus(Integer value) {
        this.chargeStatus = value;
    }

    /**
     * 获取currentKm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCurrentKm() {
        return currentKm;
    }

    /**
     * 设置currentKm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCurrentKm(Double value) {
        this.currentKm = value;
    }

    /**
     * 获取doorAndTrunkStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoorAndTrunkStatus() {
        return doorAndTrunkStatus;
    }

    /**
     * 设置doorAndTrunkStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoorAndTrunkStatus(String value) {
        this.doorAndTrunkStatus = value;
    }

    /**
     * 获取doorStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoorStatus() {
        return doorStatus;
    }

    /**
     * 设置doorStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoorStatus(String value) {
        this.doorStatus = value;
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
     * 获取fuelPercentage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFuelPercentage() {
        return fuelPercentage;
    }

    /**
     * 设置fuelPercentage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFuelPercentage(Float value) {
        this.fuelPercentage = value;
    }

    /**
     * 获取lightStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLightStatus() {
        return lightStatus;
    }

    /**
     * 设置lightStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLightStatus(String value) {
        this.lightStatus = value;
    }

    /**
     * 获取soc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoc() {
        return soc;
    }

    /**
     * 设置soc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoc(String value) {
        this.soc = value;
    }

    /**
     * 获取timeStamp属性的值。
     * 
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * 设置timeStamp属性的值。
     * 
     */
    public void setTimeStamp(long value) {
        this.timeStamp = value;
    }

    /**
     * 获取vehicleSpeed属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getVehicleSpeed() {
        return vehicleSpeed;
    }

    /**
     * 设置vehicleSpeed属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setVehicleSpeed(Float value) {
        this.vehicleSpeed = value;
    }

    /**
     * 获取windowStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWindowStatus() {
        return windowStatus;
    }

    /**
     * 设置windowStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWindowStatus(String value) {
        this.windowStatus = value;
    }

}
