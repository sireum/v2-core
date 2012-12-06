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
 * Java class for Object_Renaming_Declaration complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Object_Renaming_Declaration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="names_ql" type="{}Defining_Name_List"/>
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
 *         &lt;element name="object_declaration_view_q" type="{}Definition_Class"/>
 *         &lt;element name="renamed_entity_q" type="{}Expression_Class"/>
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
@XmlType(name = "Object_Renaming_Declaration", propOrder = { "sloc", "namesQl",
    "hasNullExclusionQ", "objectDeclarationViewQ", "renamedEntityQ",
    "aspectSpecificationsQl" })
public class ObjectRenamingDeclaration {

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
  @XmlElement(name = "names_ql", required = true)
  protected DefiningNameList namesQl;
  @XmlElement(name = "has_null_exclusion_q", required = true)
  protected ObjectRenamingDeclaration.HasNullExclusionQ hasNullExclusionQ;
  @XmlElement(name = "object_declaration_view_q", required = true)
  protected DefinitionClass objectDeclarationViewQ;
  @XmlElement(name = "renamed_entity_q", required = true)
  protected ExpressionClass renamedEntityQ;

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
   * Gets the value of the hasNullExclusionQ property.
   * 
   * @return possible object is
   *         {@link ObjectRenamingDeclaration.HasNullExclusionQ }
   * 
   */
  public ObjectRenamingDeclaration.HasNullExclusionQ getHasNullExclusionQ() {
    return this.hasNullExclusionQ;
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
   * Gets the value of the objectDeclarationViewQ property.
   * 
   * @return possible object is {@link DefinitionClass }
   * 
   */
  public DefinitionClass getObjectDeclarationViewQ() {
    return this.objectDeclarationViewQ;
  }

  /**
   * Gets the value of the renamedEntityQ property.
   * 
   * @return possible object is {@link ExpressionClass }
   * 
   */
  public ExpressionClass getRenamedEntityQ() {
    return this.renamedEntityQ;
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
   * Sets the value of the hasNullExclusionQ property.
   * 
   * @param value
   *          allowed object is
   *          {@link ObjectRenamingDeclaration.HasNullExclusionQ }
   * 
   */
  public void setHasNullExclusionQ(
      final ObjectRenamingDeclaration.HasNullExclusionQ value) {
    this.hasNullExclusionQ = value;
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
   * Sets the value of the objectDeclarationViewQ property.
   * 
   * @param value
   *          allowed object is {@link DefinitionClass }
   * 
   */
  public void setObjectDeclarationViewQ(final DefinitionClass value) {
    this.objectDeclarationViewQ = value;
  }

  /**
   * Sets the value of the renamedEntityQ property.
   * 
   * @param value
   *          allowed object is {@link ExpressionClass }
   * 
   */
  public void setRenamedEntityQ(final ExpressionClass value) {
    this.renamedEntityQ = value;
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
