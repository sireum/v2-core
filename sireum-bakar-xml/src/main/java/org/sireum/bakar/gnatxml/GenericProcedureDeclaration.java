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
 * Java class for Generic_Procedure_Declaration complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Generic_Procedure_Declaration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="generic_formal_part_ql" type="{}Element_List"/>
 *         &lt;element name="names_ql" type="{}Defining_Name_List"/>
 *         &lt;element name="parameter_profile_ql" type="{}Parameter_Specification_List"/>
 *         &lt;element name="aspect_specifications_ql" type="{}Element_List"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Generic_Procedure_Declaration", propOrder = { "sloc",
    "genericFormalPartQl", "namesQl", "parameterProfileQl",
    "aspectSpecificationsQl" })
public class GenericProcedureDeclaration {

  @XmlElement(required = true)
  protected SourceLocation sloc;
  @XmlElement(name = "generic_formal_part_ql", required = true)
  protected ElementList genericFormalPartQl;
  @XmlElement(name = "names_ql", required = true)
  protected DefiningNameList namesQl;
  @XmlElement(name = "parameter_profile_ql", required = true)
  protected ParameterSpecificationList parameterProfileQl;
  @XmlElement(name = "aspect_specifications_ql", required = true)
  protected ElementList aspectSpecificationsQl;

  /**
   * Gets the value of the aspectSpecificationsQl property.
   * 
   * @return possible object is {@link ElementList }
   * 
   */
  public ElementList getAspectSpecificationsQl() {
    return this.aspectSpecificationsQl;
  }

  /**
   * Gets the value of the genericFormalPartQl property.
   * 
   * @return possible object is {@link ElementList }
   * 
   */
  public ElementList getGenericFormalPartQl() {
    return this.genericFormalPartQl;
  }

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
   * Gets the value of the parameterProfileQl property.
   * 
   * @return possible object is {@link ParameterSpecificationList }
   * 
   */
  public ParameterSpecificationList getParameterProfileQl() {
    return this.parameterProfileQl;
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
   * Sets the value of the aspectSpecificationsQl property.
   * 
   * @param value
   *          allowed object is {@link ElementList }
   * 
   */
  public void setAspectSpecificationsQl(final ElementList value) {
    this.aspectSpecificationsQl = value;
  }

  /**
   * Sets the value of the genericFormalPartQl property.
   * 
   * @param value
   *          allowed object is {@link ElementList }
   * 
   */
  public void setGenericFormalPartQl(final ElementList value) {
    this.genericFormalPartQl = value;
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
   * Sets the value of the parameterProfileQl property.
   * 
   * @param value
   *          allowed object is {@link ParameterSpecificationList }
   * 
   */
  public void setParameterProfileQl(final ParameterSpecificationList value) {
    this.parameterProfileQl = value;
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
