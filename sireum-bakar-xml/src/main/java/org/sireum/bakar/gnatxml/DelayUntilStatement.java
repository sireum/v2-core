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
 * Java class for Delay_Until_Statement complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Delay_Until_Statement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="label_names_ql" type="{}Defining_Name_List"/>
 *         &lt;element name="delay_expression_q" type="{}Expression_Class"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Delay_Until_Statement", propOrder = { "sloc", "labelNamesQl",
    "delayExpressionQ" })
public class DelayUntilStatement {

  @XmlElement(required = true)
  protected SourceLocation sloc;
  @XmlElement(name = "label_names_ql", required = true)
  protected DefiningNameList labelNamesQl;
  @XmlElement(name = "delay_expression_q", required = true)
  protected ExpressionClass delayExpressionQ;

  /**
   * Gets the value of the delayExpressionQ property.
   * 
   * @return possible object is {@link ExpressionClass }
   * 
   */
  public ExpressionClass getDelayExpressionQ() {
    return this.delayExpressionQ;
  }

  /**
   * Gets the value of the labelNamesQl property.
   * 
   * @return possible object is {@link DefiningNameList }
   * 
   */
  public DefiningNameList getLabelNamesQl() {
    return this.labelNamesQl;
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
   * Sets the value of the delayExpressionQ property.
   * 
   * @param value
   *          allowed object is {@link ExpressionClass }
   * 
   */
  public void setDelayExpressionQ(final ExpressionClass value) {
    this.delayExpressionQ = value;
  }

  /**
   * Sets the value of the labelNamesQl property.
   * 
   * @param value
   *          allowed object is {@link DefiningNameList }
   * 
   */
  public void setLabelNamesQl(final DefiningNameList value) {
    this.labelNamesQl = value;
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
