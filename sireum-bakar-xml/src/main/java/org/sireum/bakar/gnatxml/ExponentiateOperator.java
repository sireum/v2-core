//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//

package org.sireum.bakar.gnatxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Exponentiate_Operator complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Exponentiate_Operator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ref_name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ref" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Exponentiate_Operator", propOrder = { "sloc" })
public class ExponentiateOperator {

  @XmlElement(required = true)
  protected SourceLocation sloc;
  @XmlAttribute(name = "ref_name", required = true)
  protected String refName;
  @XmlAttribute(name = "ref", required = true)
  protected String ref;
  @XmlAttribute(name = "type", required = true)
  protected String type;

  /**
   * Gets the value of the ref property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getRef() {
    return this.ref;
  }

  /**
   * Gets the value of the refName property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getRefName() {
    return this.refName;
  }

  /**
   * Gets the value of the sloc property.
   * 
   * @return possible object is {@link SourceLocation }
   * 
   */
  public SourceLocation getSloc() {
    return this.sloc;
  }

  /**
   * Gets the value of the type property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getType() {
    return this.type;
  }

  /**
   * Sets the value of the ref property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setRef(final String value) {
    this.ref = value;
  }

  /**
   * Sets the value of the refName property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setRefName(final String value) {
    this.refName = value;
  }

  /**
   * Sets the value of the sloc property.
   * 
   * @param value
   *          allowed object is {@link SourceLocation }
   * 
   */
  public void setSloc(final SourceLocation value) {
    this.sloc = value;
  }

  /**
   * Sets the value of the type property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setType(final String value) {
    this.type = value;
  }

}
