//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
//

package org.sireum.bakar.gnatxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Choice_Parameter_Specification complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Choice_Parameter_Specification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="names_ql" type="{}Defining_Name_List"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Choice_Parameter_Specification", propOrder = { "sloc",
    "namesQl" })
public class ChoiceParameterSpecification {

  @XmlElement(required = true)
  protected SourceLocation sloc;
  @XmlElement(name = "names_ql", required = true)
  protected DefiningNameList namesQl;

  /**
   * Gets the value of the namesQl property.
   * 
   * @return possible object is {@link DefiningNameList }
   * 
   */
  public DefiningNameList getNamesQl() {
    return this.namesQl;
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
   * Sets the value of the namesQl property.
   * 
   * @param value
   *          allowed object is {@link DefiningNameList }
   * 
   */
  public void setNamesQl(final DefiningNameList value) {
    this.namesQl = value;
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

}
