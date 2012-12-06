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
 * Java class for Formal_Access_To_Function complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Formal_Access_To_Function">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="has_null_exclusion_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="null_exclusion" type="{}Null_Exclusion"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="access_to_subprogram_parameter_profile_ql" type="{}Parameter_Specification_List"/>
 *         &lt;element name="is_not_null_return_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="not_null_return" type="{}Not_Null_Return"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="access_to_function_result_profile_q" type="{}Element_Class"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Formal_Access_To_Function", propOrder = { "sloc",
    "hasNullExclusionQ", "accessToSubprogramParameterProfileQl",
    "isNotNullReturnQ", "accessToFunctionResultProfileQ" })
public class FormalAccessToFunction {

  /**
   * <p>
   * Java class for anonymous complex type.
   * 
   * <p>
   * The following schema fragment specifies the expected content contained
   * within this class.
   * 
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;choice>
   *         &lt;element name="null_exclusion" type="{}Null_Exclusion"/>
   *         &lt;element name="not_an_element" type="{}Not_An_Element"/>
   *       &lt;/choice>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "nullExclusion", "notAnElement" })
  public static class HasNullExclusionQ {

    @XmlElement(name = "null_exclusion")
    protected NullExclusion nullExclusion;
    @XmlElement(name = "not_an_element")
    protected NotAnElement notAnElement;

    /**
     * Gets the value of the notAnElement property.
     * 
     * @return possible object is {@link NotAnElement }
     * 
     */
    public NotAnElement getNotAnElement() {
      return this.notAnElement;
    }

    /**
     * Gets the value of the nullExclusion property.
     * 
     * @return possible object is {@link NullExclusion }
     * 
     */
    public NullExclusion getNullExclusion() {
      return this.nullExclusion;
    }

    /**
     * Sets the value of the notAnElement property.
     * 
     * @param value
     *          allowed object is {@link NotAnElement }
     * 
     */
    public void setNotAnElement(final NotAnElement value) {
      this.notAnElement = value;
    }

    /**
     * Sets the value of the nullExclusion property.
     * 
     * @param value
     *          allowed object is {@link NullExclusion }
     * 
     */
    public void setNullExclusion(final NullExclusion value) {
      this.nullExclusion = value;
    }

  }

  /**
   * <p>
   * Java class for anonymous complex type.
   * 
   * <p>
   * The following schema fragment specifies the expected content contained
   * within this class.
   * 
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;choice>
   *         &lt;element name="not_null_return" type="{}Not_Null_Return"/>
   *         &lt;element name="not_an_element" type="{}Not_An_Element"/>
   *       &lt;/choice>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "notNullReturn", "notAnElement" })
  public static class IsNotNullReturnQ {

    @XmlElement(name = "not_null_return")
    protected NotNullReturn notNullReturn;
    @XmlElement(name = "not_an_element")
    protected NotAnElement notAnElement;

    /**
     * Gets the value of the notAnElement property.
     * 
     * @return possible object is {@link NotAnElement }
     * 
     */
    public NotAnElement getNotAnElement() {
      return this.notAnElement;
    }

    /**
     * Gets the value of the notNullReturn property.
     * 
     * @return possible object is {@link NotNullReturn }
     * 
     */
    public NotNullReturn getNotNullReturn() {
      return this.notNullReturn;
    }

    /**
     * Sets the value of the notAnElement property.
     * 
     * @param value
     *          allowed object is {@link NotAnElement }
     * 
     */
    public void setNotAnElement(final NotAnElement value) {
      this.notAnElement = value;
    }

    /**
     * Sets the value of the notNullReturn property.
     * 
     * @param value
     *          allowed object is {@link NotNullReturn }
     * 
     */
    public void setNotNullReturn(final NotNullReturn value) {
      this.notNullReturn = value;
    }

  }

  @XmlElement(required = true)
  protected SourceLocation sloc;
  @XmlElement(name = "has_null_exclusion_q", required = true)
  protected FormalAccessToFunction.HasNullExclusionQ hasNullExclusionQ;
  @XmlElement(name = "access_to_subprogram_parameter_profile_ql", required = true)
  protected ParameterSpecificationList accessToSubprogramParameterProfileQl;

  @XmlElement(name = "is_not_null_return_q", required = true)
  protected FormalAccessToFunction.IsNotNullReturnQ isNotNullReturnQ;

  @XmlElement(name = "access_to_function_result_profile_q", required = true)
  protected ElementClass accessToFunctionResultProfileQ;

  /**
   * Gets the value of the accessToFunctionResultProfileQ property.
   * 
   * @return possible object is {@link ElementClass }
   * 
   */
  public ElementClass getAccessToFunctionResultProfileQ() {
    return this.accessToFunctionResultProfileQ;
  }

  /**
   * Gets the value of the accessToSubprogramParameterProfileQl property.
   * 
   * @return possible object is {@link ParameterSpecificationList }
   * 
   */
  public ParameterSpecificationList getAccessToSubprogramParameterProfileQl() {
    return this.accessToSubprogramParameterProfileQl;
  }

  /**
   * Gets the value of the hasNullExclusionQ property.
   * 
   * @return possible object is {@link FormalAccessToFunction.HasNullExclusionQ }
   * 
   */
  public FormalAccessToFunction.HasNullExclusionQ getHasNullExclusionQ() {
    return this.hasNullExclusionQ;
  }

  /**
   * Gets the value of the isNotNullReturnQ property.
   * 
   * @return possible object is {@link FormalAccessToFunction.IsNotNullReturnQ }
   * 
   */
  public FormalAccessToFunction.IsNotNullReturnQ getIsNotNullReturnQ() {
    return this.isNotNullReturnQ;
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
   * Sets the value of the accessToFunctionResultProfileQ property.
   * 
   * @param value
   *          allowed object is {@link ElementClass }
   * 
   */
  public void setAccessToFunctionResultProfileQ(final ElementClass value) {
    this.accessToFunctionResultProfileQ = value;
  }

  /**
   * Sets the value of the accessToSubprogramParameterProfileQl property.
   * 
   * @param value
   *          allowed object is {@link ParameterSpecificationList }
   * 
   */
  public void setAccessToSubprogramParameterProfileQl(
      final ParameterSpecificationList value) {
    this.accessToSubprogramParameterProfileQl = value;
  }

  /**
   * Sets the value of the hasNullExclusionQ property.
   * 
   * @param value
   *          allowed object is {@link FormalAccessToFunction.HasNullExclusionQ }
   * 
   */
  public void setHasNullExclusionQ(
      final FormalAccessToFunction.HasNullExclusionQ value) {
    this.hasNullExclusionQ = value;
  }

  /**
   * Sets the value of the isNotNullReturnQ property.
   * 
   * @param value
   *          allowed object is {@link FormalAccessToFunction.IsNotNullReturnQ }
   * 
   */
  public void setIsNotNullReturnQ(
      final FormalAccessToFunction.IsNotNullReturnQ value) {
    this.isNotNullReturnQ = value;
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
