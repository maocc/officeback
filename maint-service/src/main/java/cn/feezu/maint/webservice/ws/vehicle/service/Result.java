
package cn.feezu.maint.webservice.ws.vehicle.service;

import cn.feezu.maint.webservice.ws.authority.service.BaseResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>result complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="result"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.authority.ws.meta.manage.wzc.com/}baseResult"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="errors" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="returnValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result",namespace="vehicle", propOrder = {
    "errors",
    "returnValue"
})
public class Result
    extends BaseResult
{

    @XmlElement(nillable = true)
    protected List<String> errors;
    protected Object returnValue;

    /**
     * Gets the value of the errors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getErrors() {
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    /**
     * 获取returnValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * 设置returnValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setReturnValue(Object value) {
        this.returnValue = value;
    }

}
