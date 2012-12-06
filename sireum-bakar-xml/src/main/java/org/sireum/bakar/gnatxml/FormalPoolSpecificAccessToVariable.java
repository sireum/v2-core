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
 * Java class for Formal_Pool_Specific_Access_To_Variable complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Formal_Pool_Specific_Access_To_Variable">
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
 *         &lt;element name="access_to_object_definition_q" type="{}Element_Class"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Formal_Pool_Specific_Access_To_Variable", propOrder = {
    "sloc", "hasNullExclusionQ", "accessToObjectDefinitionQ" })
public class FormalPoolSpecificAccessToVariable {

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
  @XmlElement(name = "has_null_exclusion_q", required = true)
  protected FormalPoolSpecificAccessToVariable.HasNullExclusionQ hasNullExclusionQ;

  @XmlElement(name = "access_to_object_definition_q", required = true)
  protected ElementClass accessToObjectDefinitionQ;

  /**
   * Gets the value of the accessToObjectDefinitionQ property.
   * 
   * @return possible object is {@link ElementClass }
   * 
   */
  public ElementClass getAccessToObjectDefinitionQ() {
    return this.accessToObjectDefinitionQ;
  }

  /**
   * Gets the value of the hasNullExclusionQ property.
   * 
   * @return possible object is
   *         {@link FormalPoolSpecificAccessToVariable.HasNullExclusionQ }
   * 
   */
  public FormalPoolSpecificAccessToVariable.HasNullExclusionQ getHasNullExclusionQ() {
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
   * Sets the value of the accessToObjectDefinitionQ property.
   * 
   * @param value
   *          allowed object is {@link ElementClass }
   * 
   */
  public void setAccessToObjectDefinitionQ(final ElementClass value) {
    this.accessToObjectDefinitionQ = value;
  }

  /**
   * Sets the value of the hasNullExclusionQ property.
   * 
   * @param value
   *          allowed object is
   *          {@link FormalPoolSpecificAccessToVariable.HasNullExclusionQ }
   * 
   */
  public void setHasNullExclusionQ(
      final FormalPoolSpecificAccessToVariable.HasNullExclusionQ value) {
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

}
