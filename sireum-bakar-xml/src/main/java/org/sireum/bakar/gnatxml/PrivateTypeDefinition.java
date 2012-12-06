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
 * Java class for Private_Type_Definition complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Private_Type_Definition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
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
 *         &lt;element name="has_limited_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="limited" type="{}Limited"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Private_Type_Definition", propOrder = { "sloc",
    "hasAbstractQ", "hasLimitedQ" })
public class PrivateTypeDefinition {

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
   *         &lt;element name="limited" type="{}Limited"/>
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
  @XmlType(name = "", propOrder = { "limited", "notAnElement" })
  public static class HasLimitedQ {

    protected Limited limited;
    @XmlElement(name = "not_an_element")
    protected NotAnElement notAnElement;

    /**
     * Gets the value of the limited property.
     * 
     * @return possible object is {@link Limited }
     * 
     */
    public Limited getLimited() {
      return this.limited;
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
     * Sets the value of the limited property.
     * 
     * @param value
     *          allowed object is {@link Limited }
     * 
     */
    public void setLimited(final Limited value) {
      this.limited = value;
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

  @XmlElement(required = true)
  protected SourceLocation sloc;

  @XmlElement(name = "has_abstract_q", required = true)
  protected PrivateTypeDefinition.HasAbstractQ hasAbstractQ;

  @XmlElement(name = "has_limited_q", required = true)
  protected PrivateTypeDefinition.HasLimitedQ hasLimitedQ;

  /**
   * Gets the value of the hasAbstractQ property.
   * 
   * @return possible object is {@link PrivateTypeDefinition.HasAbstractQ }
   * 
   */
  public PrivateTypeDefinition.HasAbstractQ getHasAbstractQ() {
    return this.hasAbstractQ;
  }

  /**
   * Gets the value of the hasLimitedQ property.
   * 
   * @return possible object is {@link PrivateTypeDefinition.HasLimitedQ }
   * 
   */
  public PrivateTypeDefinition.HasLimitedQ getHasLimitedQ() {
    return this.hasLimitedQ;
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
   * Sets the value of the hasAbstractQ property.
   * 
   * @param value
   *          allowed object is {@link PrivateTypeDefinition.HasAbstractQ }
   * 
   */
  public void setHasAbstractQ(final PrivateTypeDefinition.HasAbstractQ value) {
    this.hasAbstractQ = value;
  }

  /**
   * Sets the value of the hasLimitedQ property.
   * 
   * @param value
   *          allowed object is {@link PrivateTypeDefinition.HasLimitedQ }
   * 
   */
  public void setHasLimitedQ(final PrivateTypeDefinition.HasLimitedQ value) {
    this.hasLimitedQ = value;
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
