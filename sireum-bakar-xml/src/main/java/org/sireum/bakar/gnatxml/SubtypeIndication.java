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
 * Java class for Subtype_Indication complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Subtype_Indication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="has_aliased_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="aliased" type="{}Aliased"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
 *         &lt;element name="subtype_mark_q" type="{}Expression_Class"/>
 *         &lt;element name="subtype_constraint_q" type="{}Constraint_Class"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Subtype_Indication", propOrder = { "sloc", "hasAliasedQ",
    "hasNullExclusionQ", "subtypeMarkQ", "subtypeConstraintQ" })
public class SubtypeIndication {

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
   *         &lt;element name="aliased" type="{}Aliased"/>
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
  @XmlType(name = "", propOrder = { "aliased", "notAnElement" })
  public static class HasAliasedQ {

    protected Aliased aliased;
    @XmlElement(name = "not_an_element")
    protected NotAnElement notAnElement;

    /**
     * Gets the value of the aliased property.
     * 
     * @return possible object is {@link Aliased }
     * 
     */
    public Aliased getAliased() {
      return this.aliased;
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
     * Sets the value of the aliased property.
     * 
     * @param value
     *          allowed object is {@link Aliased }
     * 
     */
    public void setAliased(final Aliased value) {
      this.aliased = value;
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

  @XmlElement(required = true)
  protected SourceLocation sloc;
  @XmlElement(name = "has_aliased_q", required = true)
  protected SubtypeIndication.HasAliasedQ hasAliasedQ;
  @XmlElement(name = "has_null_exclusion_q", required = true)
  protected SubtypeIndication.HasNullExclusionQ hasNullExclusionQ;

  @XmlElement(name = "subtype_mark_q", required = true)
  protected ExpressionClass subtypeMarkQ;

  @XmlElement(name = "subtype_constraint_q", required = true)
  protected ConstraintClass subtypeConstraintQ;

  /**
   * Gets the value of the hasAliasedQ property.
   * 
   * @return possible object is {@link SubtypeIndication.HasAliasedQ }
   * 
   */
  public SubtypeIndication.HasAliasedQ getHasAliasedQ() {
    return this.hasAliasedQ;
  }

  /**
   * Gets the value of the hasNullExclusionQ property.
   * 
   * @return possible object is {@link SubtypeIndication.HasNullExclusionQ }
   * 
   */
  public SubtypeIndication.HasNullExclusionQ getHasNullExclusionQ() {
    return this.hasNullExclusionQ;
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
   * Gets the value of the subtypeConstraintQ property.
   * 
   * @return possible object is {@link ConstraintClass }
   * 
   */
  public ConstraintClass getSubtypeConstraintQ() {
    return this.subtypeConstraintQ;
  }

  /**
   * Gets the value of the subtypeMarkQ property.
   * 
   * @return possible object is {@link ExpressionClass }
   * 
   */
  public ExpressionClass getSubtypeMarkQ() {
    return this.subtypeMarkQ;
  }

  /**
   * Sets the value of the hasAliasedQ property.
   * 
   * @param value
   *          allowed object is {@link SubtypeIndication.HasAliasedQ }
   * 
   */
  public void setHasAliasedQ(final SubtypeIndication.HasAliasedQ value) {
    this.hasAliasedQ = value;
  }

  /**
   * Sets the value of the hasNullExclusionQ property.
   * 
   * @param value
   *          allowed object is {@link SubtypeIndication.HasNullExclusionQ }
   * 
   */
  public void setHasNullExclusionQ(
      final SubtypeIndication.HasNullExclusionQ value) {
    this.hasNullExclusionQ = value;
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
   * Sets the value of the subtypeConstraintQ property.
   * 
   * @param value
   *          allowed object is {@link ConstraintClass }
   * 
   */
  public void setSubtypeConstraintQ(final ConstraintClass value) {
    this.subtypeConstraintQ = value;
  }

  /**
   * Sets the value of the subtypeMarkQ property.
   * 
   * @param value
   *          allowed object is {@link ExpressionClass }
   * 
   */
  public void setSubtypeMarkQ(final ExpressionClass value) {
    this.subtypeMarkQ = value;
  }

}
