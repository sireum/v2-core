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
 * Java class for Formal_Function_Declaration complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Formal_Function_Declaration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="names_ql" type="{}Defining_Name_List"/>
 *         &lt;element name="parameter_profile_ql" type="{}Parameter_Specification_List"/>
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
 *         &lt;element name="result_profile_q" type="{}Element_Class"/>
 *         &lt;element name="formal_subprogram_default_q" type="{}Expression_Class"/>
 *         &lt;element name="has_abstract_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="abstract" type="{}Abstract"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
@XmlType(name = "Formal_Function_Declaration", propOrder = { "sloc", "namesQl",
    "parameterProfileQl", "isNotNullReturnQ", "resultProfileQ",
    "formalSubprogramDefaultQ", "hasAbstractQ", "aspectSpecificationsQl" })
public class FormalFunctionDeclaration {

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
   *         &lt;element name="abstract" type="{}Abstract"/>
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
  @XmlType(name = "", propOrder = { "_abstract", "notAnElement" })
  public static class HasAbstractQ {

    @XmlElement(name = "abstract")
    protected Abstract _abstract;
    @XmlElement(name = "not_an_element")
    protected NotAnElement notAnElement;

    /**
     * Gets the value of the abstract property.
     * 
     * @return possible object is {@link Abstract }
     * 
     */
    public Abstract getAbstract() {
      return this._abstract;
    }

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
     * Sets the value of the abstract property.
     * 
     * @param value
     *          allowed object is {@link Abstract }
     * 
     */
    public void setAbstract(final Abstract value) {
      this._abstract = value;
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
  @XmlElement(name = "names_ql", required = true)
  protected DefiningNameList namesQl;
  @XmlElement(name = "parameter_profile_ql", required = true)
  protected ParameterSpecificationList parameterProfileQl;
  @XmlElement(name = "is_not_null_return_q", required = true)
  protected FormalFunctionDeclaration.IsNotNullReturnQ isNotNullReturnQ;
  @XmlElement(name = "result_profile_q", required = true)
  protected ElementClass resultProfileQ;
  @XmlElement(name = "formal_subprogram_default_q", required = true)
  protected ExpressionClass formalSubprogramDefaultQ;

  @XmlElement(name = "has_abstract_q", required = true)
  protected FormalFunctionDeclaration.HasAbstractQ hasAbstractQ;

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
   * Gets the value of the formalSubprogramDefaultQ property.
   * 
   * @return possible object is {@link ExpressionClass }
   * 
   */
  public ExpressionClass getFormalSubprogramDefaultQ() {
    return this.formalSubprogramDefaultQ;
  }

  /**
   * Gets the value of the hasAbstractQ property.
   * 
   * @return possible object is {@link FormalFunctionDeclaration.HasAbstractQ }
   * 
   */
  public FormalFunctionDeclaration.HasAbstractQ getHasAbstractQ() {
    return this.hasAbstractQ;
  }

  /**
   * Gets the value of the isNotNullReturnQ property.
   * 
   * @return possible object is
   *         {@link FormalFunctionDeclaration.IsNotNullReturnQ }
   * 
   */
  public FormalFunctionDeclaration.IsNotNullReturnQ getIsNotNullReturnQ() {
    return this.isNotNullReturnQ;
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
   * Gets the value of the resultProfileQ property.
   * 
   * @return possible object is {@link ElementClass }
   * 
   */
  public ElementClass getResultProfileQ() {
    return this.resultProfileQ;
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
   * Sets the value of the formalSubprogramDefaultQ property.
   * 
   * @param value
   *          allowed object is {@link ExpressionClass }
   * 
   */
  public void setFormalSubprogramDefaultQ(final ExpressionClass value) {
    this.formalSubprogramDefaultQ = value;
  }

  /**
   * Sets the value of the hasAbstractQ property.
   * 
   * @param value
   *          allowed object is {@link FormalFunctionDeclaration.HasAbstractQ }
   * 
   */
  public void setHasAbstractQ(final FormalFunctionDeclaration.HasAbstractQ value) {
    this.hasAbstractQ = value;
  }

  /**
   * Sets the value of the isNotNullReturnQ property.
   * 
   * @param value
   *          allowed object is
   *          {@link FormalFunctionDeclaration.IsNotNullReturnQ }
   * 
   */
  public void setIsNotNullReturnQ(
      final FormalFunctionDeclaration.IsNotNullReturnQ value) {
    this.isNotNullReturnQ = value;
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
   * Sets the value of the resultProfileQ property.
   * 
   * @param value
   *          allowed object is {@link ElementClass }
   * 
   */
  public void setResultProfileQ(final ElementClass value) {
    this.resultProfileQ = value;
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
