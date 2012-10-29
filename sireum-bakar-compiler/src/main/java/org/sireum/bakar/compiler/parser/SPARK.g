/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 *                                                                            *
 * Contributors:                                                              *
 *     Robby - initial design and implementation                              *
 ******************************************************************************/

grammar SPARK;
options { backtrack=true; memoize=true; }

@header {
package org.sireum.bakar.compiler.parser;

import org.sireum.bakar.util.message.Message;
import org.sireum.bakar.util.message.ErrorMessage;
import org.sireum.bakar.util.message.MessageFactory;

import java.util.ArrayList;

import org.sireum.bakar.selection.SelectionUtil;
import org.sireum.bakar.selection.model.Caret;
import org.sireum.bakar.selection.model.RegionSelection;
import org.sireum.bakar.compiler.model.*;

import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.Triple;
/**
 * SPARK parser.
 * 
 * @author robby
 * @author hoag
 * @author belt
 */
}

@lexer::header {
package org.sireum.bakar.compiler.parser;

/**
 * SPARK lexer.
 * 
 * @author robby
 * @author hoag
 * @author belt
 */
}

@members {
public static final int SPARK_CHANNEL = Token.DEFAULT_CHANNEL + 1;
public static final int NEWLINE_CHANNEL = SPARKParser.SPARK_CHANNEL + 1;

private boolean parsingSubtype = false;

protected final ArrayList<Message> errors = new ArrayList<Message>();

private String source = null;

public final List<Message> popErrors() {
  List<Message> result = errors;
  return result;
}

public boolean lineStartsWithSparkAnnotation(){
  int currentIndex = this.input.index();
  Token temp = this.input.get(currentIndex);
  while(temp.getChannel() != SPARKParser.NEWLINE_CHANNEL){
    if(temp.getChannel() == SPARKParser.SPARK_CHANNEL){
      return true;
    }
    temp = this.input.get(--currentIndex);
  }
  return false;
}

public final void setSource(String s) {
  source = s;
}

private final Caret createCaret(Token a){
  return createCaret(a, true);
}

private final Caret createCaret(Token a, boolean useStartIndex){
  CommonToken ct = (CommonToken) a;
  int line = ct.getLine();
  int charPos = ct.getCharPositionInLine();
  int offset = ct.getStartIndex();
   
  if(!useStartIndex){
    offset = ct.getStopIndex();
    charPos += a.getText().length(); //ct.getStopIndex() - ct.getStartIndex();
  }
    
  return SelectionUtil.newCaret(line, charPos, offset);
}
    
private final RegionSelection createRS(Token a, Token b){
  return createRS(createCaret(a), createCaret(b, false));
}

private final RegionSelection createRS(Caret startCaret, Token b){
  return createRS(startCaret, createCaret(b, false));
}

private final RegionSelection createRS(Token a){
  return createRS(createCaret(a), createCaret(a, false));
}
        
private final RegionSelection createRS(Token a, Caret endCaret){
  return createRS(createCaret(a), endCaret);
}

private RegionSelection createRS(Token whenLoc, Node lastChoice) {
  assert lastChoice.getTheOptionalRegionSelection() != null;
  return createRS(whenLoc, lastChoice.getTheOptionalRegionSelection().getTheEndCaret());
}

private final RegionSelection createRS(Caret a, Caret b){
  return SelectionUtil.newRegionSelection(a, b, this.input.getSourceName());
}

private RegionSelection createRS(Node n) {
  return createRS(n.getTheOptionalRegionSelection()); 
}
        
private final RegionSelection createRS(RegionSelection rs){
  assert rs != null && !SelectionUtil.isEmpty(rs);
  return SelectionUtil.clone(rs);
}
    
private final RegionSelection createRS(RegionSelection rs1, RegionSelection rs2){
  return SelectionUtil.intersect(rs1, rs2);
}

private final RegionSelection createRS(Node r1, Node r2) {
  RegionSelection rs1 = r1.getTheOptionalRegionSelection();
  RegionSelection rs2 = r2.getTheOptionalRegionSelection();
  assert rs1 != null && rs2 != null;
  assert !SelectionUtil.isEmpty(rs1) && !SelectionUtil.isEmpty(rs2);
       
  RegionSelection ret = SelectionUtil.intersect(rs1, rs2);
  assert ret != null && !SelectionUtil.isEmpty(ret);
  return ret;
}
    
private final RegionSelection newRegionSelection(){
  return SelectionUtil.newRegionSelection(this.input.getSourceName());
}

private final Token getPrecedingToken(Token t){
  int currentIndex = t.getTokenIndex() - 1;
  Token temp = this.input.get(currentIndex);
  while(currentIndex >= 0){
    if(temp.getChannel() == Token.DEFAULT_CHANNEL){
      return temp;
    }
    temp = this.input.get(--currentIndex);
  }
  assert false;
  return null;
}

private final Token getPrecedingAdaToken(Token startToken, String endTokenName){
  int currentIndex = startToken.getTokenIndex() + 1;
  Token temp = this.input.get(currentIndex);
  while(currentIndex < input.size()){
    if(temp.getChannel() == SPARKParser.SPARK_CHANNEL || temp.getText().equals(endTokenName)){
      return getPrecedingToken(temp);
    }
    temp = this.input.get(++currentIndex);
  }
  assert false;
  return null;
}

@Override
public void displayRecognitionError(String[] tokenNames,
  RecognitionException re) {
  final ErrorMessage em = new ErrorMessage();
  em.setTheLineNumber(re.line);
  final String s = re.token.getText();
  final String[] ss = s.split("\n");
  final int len = ss.length;
  em.setTheColumnNumber(re.charPositionInLine + 1);
  em.setTheOptionalEndLineNumber(re.line + len - 1);
  em.setTheOptionalEndColumnNumber(re.charPositionInLine + ss[len - 1].length());
  em.setTheMessageText(getErrorMessage(re, tokenNames));
  em.setTheOptionalSource(this.source);
  this.errors.add(em);
}
}


compilationFile returns [ Compilation result = null ]
  : c=compilation EOF                      { result = $c.result; }
  ;

// SPARK95 2.8
pragma returns [ Pragma result = new Pragma() ]
@init {
ArrayList<PragmaArgumentAssociation> temp = new ArrayList<PragmaArgumentAssociation>();
}
  : 'pragma' id=IDENTIFIER                 { result.setTheIDString($id.text); }
    ( '(' paa=pragma_argument_association  { temp.add($paa.result); }
      ( ',' 
        paa=pragma_argument_association    { temp.add($paa.result); }
      )*
      ')'
    )? ';'                                 { if (!temp.isEmpty()) {
                                               result.setTheOptionalPragmaArgumentAssociations(temp);
                                             }
                                           }
  ;
  
pragma_argument_association returns [ PragmaArgumentAssociation result = null ]
  : ( paid=pragma_argument_identifier '=>'   
    )? 
    ( n=name                               { NamePragmaArgumentAssociation temp1 = new NamePragmaArgumentAssociation();
                                             temp1.setTheName($n.result); 
                                             result = temp1; }
    | e=expression                         { ExpPragmaArgumentAssociation temp2 = new ExpPragmaArgumentAssociation();
                                             temp2.setTheExp($e.result);
                                             result = temp2; }
    )                                      { if (paid != null) {
                                               result.setTheOptionalIDString(paid);
                                             }
                                           }
  ;

// added
pragma_argument_identifier returns [ String result = null ]
  : id=IDENTIFIER                          { result = $id.text; }
  ;


// SPARK95 3.1
basic_declaration returns [ BasicDeclaration result = null ]
  : 
    td=type_declaration                    { result = $td.result; }
  | sd=subtype_declaration                 { result = $sd.result; }
  | od=object_declaration                  { result = $od.result; }
  | nd=number_declaration                  { result = $nd.result; }
  ;

defining_identifier returns [ String result = null, 
                              RegionSelection rs = null ]
@after{
  $defining_identifier.rs = createRS($node_info);
}
  : node_info=IDENTIFIER { $defining_identifier.result = $node_info.text; }
  ;


// SPARK95 3.2.1
type_declaration returns [ TypeDeclaration result = null ]
  : ftd=full_type_declaration              { result = $ftd.result; }
  | ptd=private_type_declaration           { result = $ptd.result; }
  | ped=private_extension_declaration      { result = $ped.result; }
  ;

full_type_declaration returns [ FullTypeDeclaration result = new FullTypeDeclaration() ]
@after{ result.setTheOptionalRegionSelection(createRS($typeLoc, $semiLoc)); }
  : typeLoc='type' did=defining_identifier 'is'    { result.setTheIDString($did.result); } 
    td=type_definition semiLoc=';'                 { result.setTheTypeDefinition($td.result); }
  ;

type_definition returns [ TypeDefinition result = null ]
  : etd=enumeration_type_definition        { result = $etd.result; }
  | itd=integer_type_definition            { result = $itd.result; }
  | rtd=real_type_definition               { result = $rtd.result; }
  | atd=array_type_definition              { result = $atd.result; }
  | rectd=record_type_definition           { result = $rectd.result; }
  | mtd=modular_type_definition            { result = $mtd.result; }
  | rte=record_type_extension              { result = $rte.result; }
  ;

// Typo in SPARK95 Reference Manual?
// type_mark was used instead of
// subtype_mark

// also, SPARK95 indicates record_type_extendsion should end with a ';', but it is
// part of a full_type_declaration which already ends with a ';'
record_type_extension returns [ RecordTypeExtension result = new RecordTypeExtension() ]
  : 'new' n=subtype_mark                   { result.setTheSubtypeMark($n.result); } 
    'with' rd=record_definition            { result.setTheRecordDefinition($rd.result); }
  ;


// SPARK95 3.2.2
subtype_declaration returns [ SubTypeDeclaration result = new SubTypeDeclaration() ]
@after { result.setTheOptionalRegionSelection(createRS($subtypeLoc, $semiLoc)); }
  : subtypeLoc='subtype' did=defining_identifier 'is' si=subtype_indication semiLoc=';'
    { result.setTheIDString($did.result);
      result.setTheSubTypeIndication($si.result); 
    }
  ;

subtype_indication returns [ SubTypeIndication result = new SubTypeIndication() ]
@after{
  Node endNode = $c.result != null ? $c.result : $n.result;
  result.setTheOptionalRegionSelection(createRS($n.result, endNode)); 
}
  : n=subtype_mark                         { result.setTheName($n.result); } 
    ( c=constraint                         { result.setTheOptionalConstraint($c.result); }
    )?
  ;

subtype_mark returns [ Name result = null ]
  : sn=subtype_name                        { result = $sn.result; }
  ;

// added
subtype_name returns [ Name result = null ]
//@init{SPARKParser.this.parsingSubtype = true;}
///@after{SPARKParser.this.parsingSubtype = false;}
  : {SPARKParser.this.parsingSubtype=true;} 
    n=name                                 { result = $n.result; }
    {SPARKParser.this.parsingSubtype=false;}
  ;
  
constraint returns [ Constraint result = null ]
  : sc=scalar_constraint                   { result = $sc.result; }
  | cc=composite_constraint                { result = $cc.result; }
  ;

scalar_constraint returns [ RangeConstraint result = null ]
  : rc=range_constraint                    { result = $rc.result; }
  ;
  
composite_constraint returns [ IndexConstraint result = null ]
  : ic=index_constraint                    { result = $ic.result; }
  ;


// SPARK95 3.3.1
object_declaration returns [ ObjectDeclaration result = new ObjectDeclaration() ]
@after{
  result.setTheOptionalRegionSelection(createRS($dil.result.get(0).getTheOptionalRegionSelection().getTheStartCaret(), $semiLoc));
}
  : dil=defining_identifier_list ':' ( 'constant' { result.setTheConstantFlag(true); } )? n=subtype_mark
    ( ':=' e=expression { result.setTheOptionalInitializingExp($e.result); } )? 
    semiLoc=';'
    { result.setTheDefiningIdentifierList($dil.result);
      result.setTheSubtypeMark($n.result);
    }
  ;
  
defining_identifier_list returns [ArrayList <IDName> result = new ArrayList <IDName> () ]
@init  { IDName name = null; }
  : id0=defining_identifier
    { name = new IDName(); 
      name.setTheIDString($id0.result);
      name.setTheOptionalRegionSelection($id0.rs); 
      $defining_identifier_list.result.add(name); 
    }
    ( ',' id=defining_identifier 
          { name = new IDName();
            name.setTheIDString($id.result);
            name.setTheOptionalRegionSelection($id.rs);
            $defining_identifier_list.result . add ( name ) ;
          }
    )*
  ;


// SPARK95 3.3.2
number_declaration returns [ NumberDeclaration result = new NumberDeclaration() ]
@after{
  result.setTheOptionalRegionSelection(createRS($dil.result.get(0).getTheOptionalRegionSelection().getTheStartCaret(), $semiLoc));
}
  : dil=defining_identifier_list ':' 'constant' ':=' e=static_expression semiLoc=';'
    { result.setTheIDNames($dil.result);
      result.setTheExp($e.result); 
    }
  ;

// added
static_expression returns [ Exp result = null ]
  : e=expression { result = $e.result; }
  ;


// SPARK95 3.4


// SPARK95 3.5
range_constraint returns [ RangeConstraint result = new RangeConstraint() ]
  : rangeLoc='range' sr=static_range
    { result.setTheRange($sr.result); 
      result.setTheOptionalRegionSelection(createRS($rangeLoc, $sr.result.getTheOptionalRegionSelection().getTheEndCaret()));
    }
  ;

// added
static_range returns [ Range result = null ]
  : r=range                                { result = $r.result; }
  ;
  
range returns [ Range result = null ]
  : rar=range_attribute_reference          { result = $rar.result; }
  | se1=simple_expression '..' se2=simple_expression 
    { ExpRange temp = new ExpRange();
      temp.setTheLowRangeExp($se1.result);
      temp.setTheHighRangeExp($se2.result);
      result = temp;
      result.setTheOptionalRegionSelection(createRS($se1.result.getTheOptionalRegionSelection().getTheStartCaret(),
                                                    $se2.result.getTheOptionalRegionSelection().getTheEndCaret()));
    }
  ;


// SPARK95 3.5.1
enumeration_type_definition returns [ EnumerationTypeDefinition result = new EnumerationTypeDefinition() ]
@init {
ArrayList<IDName> temp = new ArrayList<IDName>();
}
  : '(' els=enumeration_literal_specification { temp.add($els.result); }
        ( ',' 
          els=enumeration_literal_specification { temp.add($els.result); }
      )* ')'                               { result.setTheIDNames(temp); }
  ;

enumeration_literal_specification returns [ IDName result = null ]
  : did=defining_identifier
    { result = new IDName(); 
      result.setTheIDString($did.result);
      result.setTheOptionalRegionSelection($did.rs);
    }
  ;


// SPARK95 3.5.4
integer_type_definition returns [ IntegerTypeDefinition result = null ]
  : sitd=signed_integer_type_definition    { result = $sitd.result; }
  ;

signed_integer_type_definition returns [ SignedIntegerTypeDefinition result = new SignedIntegerTypeDefinition() ]
  : 'range' sse1=static_simple_expression  { result.setTheLowRangeExp($sse1.result); }
    '..' sse2=static_simple_expression     { result.setTheHighRangeExp($sse2.result); }
  ;

// added
static_simple_expression returns [ Exp result = null ]
  : se=simple_expression                   { result = $se.result; }
  ;

modular_type_definition returns [ ModularTypeDefinition result = new ModularTypeDefinition() ]
  : 'mod' sse=static_simple_expression     { result.setTheExp($sse.result); }
  ;


// SPARK95 3.5.6
real_type_definition returns [ RealTypeDefinition result = null ]
  : fpd1=floating_point_definition         { result = $fpd1.result; }
  | fpd2=fixed_point_definition            { result = $fpd2.result; }
  ;


// SPARK95 3.5.7
floating_point_definition returns [ FloatingPointDefinition result = new FloatingPointDefinition() ]
  : 'digits' 
    sse=static_simple_expression           { result.setTheExp($sse.result); }
    ( rrs=real_range_specification         { result.setTheOptionalLowRangeExp($rrs.lExp);
                                             result.setTheOptionalHighRangeExp($rrs.hExp); }
    )?
  ;

real_range_specification returns [ Exp lExp = null, Exp hExp = null ]
  : 'range' sse1=static_simple_expression  { $real_range_specification.lExp = $sse1.result; }
    '..' sse2=static_simple_expression   { $real_range_specification.hExp = $sse2.result; }
  ;


// SPARK95 3.5.9
fixed_point_definition returns [ FixedPointDefinition result = null ]
  : ofpd=ordinary_fixed_point_definition   { result = $ofpd.result; }
  ;

ordinary_fixed_point_definition returns [ OrdinaryFixedPointDefinition result = new OrdinaryFixedPointDefinition() ]
  : 'delta' sse=static_simple_expression   { result.setTheExp($sse.result); }
    rrs=real_range_specification           { result.setTheLowRangeExp($rrs.lExp);
                                             result.setTheLowRangeExp($rrs.hExp); }
  ;


// SPARK95 3.6
array_type_definition returns [ ArrayTypeDefinition result = null ]
  : uad=unconstrained_array_definition     { result = $uad.result; }
  | cad=constrained_array_definition       { result = $cad.result; }
  ;

unconstrained_array_definition returns [ UnconstrainedArrayDefinition result = new UnconstrainedArrayDefinition() ]
@init {
ArrayList<Name> temp = new ArrayList<Name>();
}
  : 'array'
    '(' isd=index_subtype_definition       { temp.add($isd.result); }
    ( ',' isd=index_subtype_definition     { temp.add($isd.result); } 
    )*
    ')' 'of' cd=component_definition       { result.setTheIndexSubTypeNames(temp);
                                             result.setTheComponentName($cd.result); }
  ;
  
index_subtype_definition returns [ Name result = null ]
  : sm=subtype_mark 'range' '<>'           { result = $sm.result; }
  ;

constrained_array_definition returns [ ConstrainedArrayDefinition result = new ConstrainedArrayDefinition() ]
@init {
ArrayList<Name> temp = new ArrayList<Name>();
}
  : 'array'
    '(' dsd=discrete_subtype_definition    { temp.add($dsd.result); }
    ( ',' dsd=discrete_subtype_definition  { temp.add($dsd.result); }
          )*
    ')' 'of' cd=component_definition       { result.setTheDiscreteSubTypeNames(temp);
                                             result.setTheComponentName($cd.result); }
  ;

discrete_subtype_definition returns [ Name result = null ]
  : dsm=discrete_subtype_mark              { result = $dsm.result; }
  ;

// added
discrete_subtype_mark returns [ Name result = null ]
  : sm=subtype_mark                        { result = $sm.result; }
  ;

component_definition returns [ Name result = null ]
  : sm=subtype_mark                        { result = $sm.result; }
  ;


// SPARK95 3.6.1
index_constraint returns [ IndexConstraint result = new IndexConstraint() ]
@init {
ArrayList<Name> temp = new ArrayList<Name>();
}
@after {
  result.setTheOptionalRegionSelection(createRS($firstSemiLoc, $lastSemiLoc));
}
  : firstSemiLoc='(' dsm=discrete_subtype_mark          { temp.add($dsm.result); }
        ( ',' 
          dsm=discrete_subtype_mark        { temp.add($dsm.result); }
        )*
     lastSemiLoc=')'                                   { result.setTheDiscreteSubTypeNames(temp); }
  ;

discrete_range returns [ Choice result = null ]
  :
     sr=static_range                        
     { RangeChoice temp2 = new RangeChoice();
       temp2.setTheRange($sr.result);
       temp2.setTheOptionalRegionSelection(createRS($sr.result));
       result = temp2;
     }
  |  dsi=discrete_subtype_indication
     { SubTypeChoice temp1 = new SubTypeChoice();
       temp1.setTheSubTypeIndication($dsi.result);
       temp1.setTheOptionalRegionSelection(createRS($dsi.result));
       result = temp1; 
     }
  ;

discrete_subtype_indication returns [ SubTypeIndication result = null ]
  : si=subtype_indication                  { result = $si.result; }
  ;


// SPARK95 3.7


// SPARK95 3.7.1


// SPARK95 3.8
record_type_definition returns [ RecordTypeDefinition result = new RecordTypeDefinition() ]
  : ( 'tagged'                             { result.setTheTaggedFlag(true); }
    )? 
    rd=record_definition                   { result.setTheRecordDefinition($rd.result); }
  ;

record_definition returns [ RecordDefinition result = new RecordDefinition() ]
  : recordLoc='record'
    cl=component_list
    { if ($cl.result != null) { 
        result.setTheOptionalComponentDeclarations($cl.result);
      }
    }
    'end' endRecordLoc='record'
    { result.setTheOptionalRegionSelection(createRS($recordLoc, $endRecordLoc));
    }
  | nullLoc='null' endRecordLoc='record'
    { result.setTheNullRecordFlag(true); 
      result.setTheOptionalRegionSelection(createRS($nullLoc, $recordLoc));
    }
  ;

component_list returns [ ArrayList<ComponentDeclaration> result = new ArrayList<ComponentDeclaration>() ]
  : ( ci=component_item                    { result.add($ci.result); }
    )+
  | 'null'                                 { return null; }
  ;

component_item returns [ ComponentDeclaration result = null ]
  : cd=component_declaration               { result = $cd.result; }
  ;
  
component_declaration returns [ ComponentDeclaration result = new ComponentDeclaration() ]
@after{
  Caret startCaret = $dil.result.get(0).getTheOptionalRegionSelection().getTheStartCaret();
  result.setTheOptionalRegionSelection(createRS(startCaret, $parenLoc));
}
  : dil=defining_identifier_list ':' cd=component_definition parenLoc=';' 
    { result.setTheIDNames($dil.result);
      result.setTheName($cd.result); 
    }
  ;


// SPARK95 3.8.1
discrete_choice_list returns [ ArrayList<Choice> result = new ArrayList<Choice>() ]
  : dc=discrete_choice                     { result.add($dc.result); }
    ( '|' dc=discrete_choice               { result.add($dc.result); }
    )*
  ;

discrete_choice returns [ Choice result = null ]
  : dr=discrete_range                      { result = $dr.result; }
  | sse=static_simple_expression           { ExpChoice temp = new ExpChoice();
                                             temp.setTheExp($sse.result);
                                             temp.setTheOptionalRegionSelection(createRS($sse.result));
                                             result = temp; }
  ;


// SPARK95 3.9.1


// SPARK95 3.10


// SPARK95 3.10.1


// SPARK95 3.11
declarative_part returns [ DeclarativePart result = new DeclarativePart() ]
@init {
  ArrayList<RenamingDeclaration> temp = new ArrayList<RenamingDeclaration>();
  ArrayList<DeclarativePartMember> temp2 = new ArrayList<DeclarativePartMember>();
  RegionSelection rs1= this.newRegionSelection();
  RegionSelection rs2 = this.newRegionSelection();
}
@after{
  result.setTheOptionalRegionSelection(createRS(rs1, rs2));
}
  : ( rd=renaming_declaration              { temp.add($rd.result); } 
    )*
    (    di=declarative_item                 { temp2.add($di.result); }
      | epd=embedded_package_declaration     { temp2.add($epd.result); }
      | esd=external_subprogram_declaration  { temp2.add($esd.result); }
    )*
    { 
      if (!temp.isEmpty()) { 
        result.setTheOptionalRenamingDeclarations(temp); 
        rs1.setTheStartCaret(temp.get(0).getTheOptionalRegionSelection().getTheStartCaret());
        rs1.setTheEndCaret(temp.get(temp.size() - 1).getTheOptionalRegionSelection().getTheEndCaret());
      }
      if (!temp2.isEmpty()) {
        result.setTheOptionalDeclarativePartMembers(temp2);
        rs2.setTheStartCaret(temp2.get(0).getTheOptionalRegionSelection().getTheStartCaret());
        rs2.setTheEndCaret(temp2.get(temp2.size() - 1).getTheOptionalRegionSelection().getTheEndCaret());
      }

    }
  ;

declarative_item returns [ DeclarativeItem result = null ]
  : bdi=basic_declarative_item             { result = $bdi.result; }
  | b=body                                 { result = $b.result; }
  | gi=generic_instantiation               { result = $gi.result; }
  ;

basic_declarative_item returns [ BasicDeclarativeItem result = null ]
 :
    bd=basic_declaration
    { BasicDeclarationBasicDeclarativeItem temp = new BasicDeclarationBasicDeclarativeItem();
      temp.setTheBasicDeclaration($bd.result);
      temp.setTheOptionalRegionSelection(createRS($bd.result.getTheOptionalRegionSelection()));
      result = temp; 
    }
  | rc=representation_clause
    { RepresentationClauseBasicDeclarativeItem temp2 = new RepresentationClauseBasicDeclarativeItem(); 
      temp2.setTheRepresentationClause($rc.result);
      temp2.setTheOptionalRegionSelection(createRS($rc.result.getTheOptionalRegionSelection()));
      result = temp2; 
    }
  | {lineStartsWithSparkAnnotation()}? => (
         pfs=proof_function_specification       { result = $pfs.result; }
       | bpd=basic_proof_declaration            { result = $bpd.result; }
     )
  ;

proof_function_specification returns [ProofFunctionDeclaration result = new ProofFunctionDeclaration() ]
@after{
  Caret startCaret = $fs.result.getTheOptionalRegionSelection().getTheStartCaret();
  result.setTheOptionalRegionSelection(createRS(startCaret ,$semiLoc));
}
    :  fs=function_specification semiLoc=';' {result.setTheFunctionSpecification($fs.result);}
    ;
    
basic_proof_declaration returns [ BasicProofDeclaration result = null ]
  :  
  ( typeLoc='type' id=IDENTIFIER 'is' 'abstract' semiLoc=';'
    { ProofTypeDeclaration ptd = new ProofTypeDeclaration();
      ptd.setTheDefiningIdentifier($id.text); 
      ptd.setTheOptionalRegionSelection(createRS($typeLoc, $semiLoc));
      result = ptd;
    }
  | assertLoc='assert' id=IDENTIFIER  CHARACTER_LITERAL_OR_QUOTE b=name 'is' sm=name semiLoc=';'
    { TypeAssertion ta = new TypeAssertion();
      ta.setTheIdentifier($id.text);
      ta.setTheBase($b.result);
      ta.setTheSubtypeMark($sm.result); 
      ta.setTheOptionalRegionSelection(createRS($assertLoc, $semiLoc));
      result = ta;
    }
  )
  ;
  
embedded_package_declaration returns [ EmbeddedPackageDeclaration result = new EmbeddedPackageDeclaration() ] 
@init {
ArrayList<EmbeddedPackageDeclarationMember> temp = new ArrayList<EmbeddedPackageDeclarationMember>();
}
@after{
  Caret startCaret = $pd.result.getTheOptionalRegionSelection().getTheStartCaret();
  Caret endCaret = null;
  if(!temp.isEmpty()){
    endCaret = temp.get(temp.size() - 1).getTheOptionalRegionSelection().getTheEndCaret();
  } else{ 
    endCaret = $pd.result.getTheOptionalRegionSelection().getTheEndCaret();
  }
  result.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
}
  : pd=package_declaration                 { result.setThePackageDeclaration($pd.result); } 
    (   rd=renaming_declaration
        { RenamingDeclarationEmbeddedPackageDeclarationMember rdepdm = new RenamingDeclarationEmbeddedPackageDeclarationMember();
          rdepdm.setTheRenamingDeclaration($rd.result); 
          temp.add(rdepdm); 
        }
      | utc=use_type_clause 
        { UseTypeClauseEmbeddedPackageDeclarationMember utcepdm = new UseTypeClauseEmbeddedPackageDeclarationMember();
          utcepdm.setTheUseTypeClause($utc.result); 
          temp.add(utcepdm); 
        }
    )*
    { if (!temp.isEmpty()) {
        result.setTheOptionalEmbeddedPackageDeclarationMembers(temp);
      }
    }
  ;

external_subprogram_declaration returns [ ExternalSubProgramDeclaration result = new ExternalSubProgramDeclaration() ]
@init {
Pragma pragma = new Pragma();
ArrayList<PragmaArgumentAssociation> temp = new ArrayList<PragmaArgumentAssociation>();
}
  : sd=subprogram_declaration              { result.setTheSubProgramDeclaration($sd.result); }
    'pragma' id=IMPORT                     { pragma.setTheIDString($id.text); }
    '(' paa=pragma_argument_association    { temp.add($paa.result); }
    ( ','  paa=pragma_argument_association { temp.add($paa.result); }
    )+
    ')' ';'                           { if (!temp.isEmpty()) {
                                               pragma.setTheOptionalPragmaArgumentAssociations(temp);
                                             }
                                             result.setThePragma(pragma);
                                           }
  ;

body returns [ Body result = null ]
  : pd=proper_body                         { result = $pd.result; }
  | bs=body_stub                           { result = $bs.result; }
  ;

proper_body returns [ ProperBody result = null ]
  : sb=subprogram_body                     { result = $sb.result; }
  | pb=package_body                        { result = $pb.result; }
  ;


// SPARK95 4.1
/* left-recursive!
name 
  :  direct_name
  | indexed_component
  | selected_component
  | attribute_reference
  | function_call
  ;
*/

// refactored left-recursive version
// refactored left-recursive version
name returns [Name result = null ]
@init   { Name prefix = null ; 
          ArrayList < Exp > exp_list = null ;
          ArrayList<Pair<ArrayList<Exp>, Exp>> array_update_list = null;
          ArrayList<Pair<String, Exp>> record_update_list = null;
        }
  : node_info=direct_name { prefix = $direct_name.result ; }
    ({lineStartsWithSparkAnnotation()}?=> '~' {((IDName) prefix).setTheDecoratedFlag(true);})?

    (   // indexed_component suffix
        {!SPARKParser.this.parsingSubtype}?=> // subtypes cannot contain parens '(' ')' 
        indexed_node_info='(' exp0=expression  { exp_list = new ArrayList < Exp > () ; 
                                                 exp_list . add ( $exp0.result ) ; 
                                               }
                              ( ',' exp=expression { exp_list . add ( $exp.result ) ; } )* 
                                lastParen=')'
        { IndexedComponent next = new IndexedComponent () ;
          next.setTheName ( prefix ) ; 
          next.setTheExps ( exp_list ) ;

          Caret startCaret = prefix.getTheOptionalRegionSelection().getTheStartCaret();
          Caret endCaret = createCaret($lastParen);
          next.setTheOptionalRegionSelection(createRS(startCaret, endCaret));

          prefix = next ; 
        }
      | // selected_component suffix
        selected_node_info='.' sn=selector_name 
        ( {lineStartsWithSparkAnnotation()}?=> sc_tilde='~' )?
        { SelectedComponent next = new SelectedComponent();
          next.setTheName(prefix); 
          next.setTheIDString(($sn.result).getTheIDString());
          next.setTheDecoratedFlag(sc_tilde != null);
             
          Caret startCaret = prefix.getTheOptionalRegionSelection().getTheStartCaret();
          Caret endCaret = sc_tilde != null ? createCaret($sc_tilde) : $sn.result.getTheOptionalRegionSelection().getTheEndCaret();
          next.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
                            
          prefix = next ; 
        }
      | // attribute_reference suffix
        attr_node_info=CHARACTER_LITERAL_OR_QUOTE attribute_designator
        { AttributeReference next = new AttributeReference() ;
          next.setTheName ( prefix ) ; 
          next.setTheAttributeDesignator ( $attribute_designator.result ) ; 
              
          Caret startCaret = prefix.getTheOptionalRegionSelection().getTheStartCaret();
          Caret endCaret = $attribute_designator.result.getTheOptionalRegionSelection().getTheEndCaret();
          next.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
                            
          prefix = next ; 
        }
      | // function_call suffix
        {!SPARKParser.this.parsingSubtype}?=> // subtype names cannot be a function call
          
        actual_parameter_part
        { FunctionCall next = new FunctionCall() ;
          next.setTheName( prefix ) ; 
          next.setTheOptionalParameterAssociationList( $actual_parameter_part.result ) ; 
              
            // we'll never actually get here since a function call looks just like an
            // indexed component
          assert false;
          prefix = next ; 
        }
      | // record update suffix
        {lineStartsWithSparkAnnotation()}?=>
        '[' il1=index_list '=>' exp1=expression { array_update_list = new ArrayList<Pair<ArrayList<Exp>, Exp>>();
                                                  array_update_list.add(new Pair<ArrayList<Exp>, Exp>(il1, exp1)); }
        (';' il2=index_list '=>' exp2=expression { array_update_list.add(new Pair<ArrayList<Exp>, Exp>(il2, exp2)); } )* lastBrace=']'
        { ArrayUpdate temp = new ArrayUpdate();
          temp.setTheName(prefix);
          temp.setTheIndexListToExpressionList(array_update_list);
              
          Caret startCaret = prefix.getTheOptionalRegionSelection().getTheStartCaret();
          Caret endCaret = createCaret($lastBrace);
          temp.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
              
          prefix = temp;
        }
      | // array update suffix  
        {lineStartsWithSparkAnnotation()}?=>  
        '[' sn2=selector_name '=>' exp1=expression { record_update_list = new ArrayList<Pair<String, Exp>>(); 
                                                     record_update_list.add(new Pair<String, Exp>(($sn2.result).getTheIDString(), exp1)); }
        (';' sn3=selector_name '=>' exp2=expression { record_update_list.add(new Pair<String,Exp>(($sn3.result).getTheIDString(), exp2)); })* lastBrace=']'
        { RecordUpdate temp = new RecordUpdate();
          temp.setTheName(prefix);
          temp.setTheSelectorToExpressionList(record_update_list);
                            
          Caret startCaret = prefix.getTheOptionalRegionSelection().getTheStartCaret();
          Caret endCaret = createCaret($lastBrace);
          temp.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
              
          prefix = temp;
        }
    )*
    { result = prefix ; }
  ;

index_list returns [ArrayList<Exp> result = new ArrayList() ]
  : exp1=expression {result.add(exp1);} (',' exp2=expression { result.add(exp2);} )*
  ;
  
direct_name returns [IDName result = new IDName (), Integer line = null, Integer pos = null ]
@after  { 
  $direct_name.line = $node_info.line ;
  $direct_name.pos  = $node_info.pos  ; 
  
  String ret = $direct_name.result.getTheIDString();
  if(ret != null){
    $direct_name.result.setTheOptionalRegionSelection(createRS($node_info));
  }
}
  : node_info=IDENTIFIER { $direct_name.result.setTheIDString ( $IDENTIFIER.text ) ; }
  ;

prefix returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;


// SPARK95 4.1.1
/* left-refactored in name
indexed_component
  : prefix '(' expression
    ( ',' expression )* ')'
  ;
*/

// SPARK95 4.1.2


// SPARK95 4.1.3
/* left-refactored in name
selected_component
  : prefix '.' selector_name
  ;
*/

selector_name returns [IDName result = new IDName () ]
@after { 
  result.setTheOptionalRegionSelection(createRS($node_info));
}
    : node_info=IDENTIFIER { result . setTheIDString ( $IDENTIFIER.text ) ; }
    ;


// SPARK95 4.1.4
/* left-refactored in name
attribute_reference
  : prefix '\'' attribute_designator
  ;
*/

attribute_designator returns [AttributeDesignator result = null ]
@init   { ArrayList < Exp > list = new ArrayList < Exp > () ; }
  : node_info1=IDENTIFIER
    (
      '('       
                e1=expression {list.add($e1.result);}
          ( ',' e2=expression {list.add($e2.result);} )?
      lastParen=')'
    )?
    { 
      result = new IDAttributeDesignator () ;
      ((IDAttributeDesignator) result).setTheIDString($IDENTIFIER.text); 
      
      if (!list.isEmpty() ){
         ((IDAttributeDesignator) result).setTheOptionalExps(list);
         result.setTheOptionalRegionSelection(createRS($node_info1, $lastParen));
      } else {
        result.setTheOptionalRegionSelection(createRS($node_info1));
      }

    }
  | delta='Delta'  {
      result = new DeltaAttributeDesignator () ;; 
      result.setTheOptionalRegionSelection(createRS($delta));
    }
  | digits='Digits' { 
      result = new DigitsAttributeDesignator () ; 
      result.setTheOptionalRegionSelection(createRS($digits));
    }
  ;

range_attribute_reference returns [RangeAttributeReference result = new RangeAttributeReference () ]
  : node_prefix=prefix CHARACTER_LITERAL_OR_QUOTE rad=range_attribute_designator
    { result.setTheName($node_prefix.result); 
      result.setTheDesignator($rad.result);
      result.setTheOptionalRegionSelection(createRS($node_prefix.result.getTheOptionalRegionSelection().getTheStartCaret(), 
                                                    $rad.result.getTheOptionalRegionSelection().getTheEndCaret()));
    }
  ;

range_attribute_designator returns [RangeAttributeDesignator result = new RangeAttributeDesignator() ]
@after{
  Caret endCaret = result.getTheOptionalExp() != null ? 
                     createCaret($lastParenLoc) :
                     createCaret($rangeLoc, false);
  result.setTheOptionalRegionSelection(createRS($rangeLoc, endCaret));
}
  : rangeLoc='Range' 
    ( '(' static_expression lastParenLoc=')' 
          { result.setTheOptionalExp($static_expression.result); }
    )?
  ;

// ADA95 4.3
aggregate returns [ Aggregate result = null ]
  : ra=record_aggregate                    { result = $ra.result; }
  | ea=extension_aggregate                 { result = $ea.result; }
  | aa=array_aggregate                     { result = $aa.result; }
  ;

// SPARK95 4.3.1
record_aggregate returns [RecordAggregate result ]
  : positional_record_aggregate { result = $positional_record_aggregate.result ; }
  | named_record_aggregate      { result = $named_record_aggregate.result ; }
  ;

positional_record_aggregate returns [PositionalRecordAggregate result = new PositionalRecordAggregate () ]
@init   { ArrayList < Exp > list = new ArrayList < Exp > () ; }
@after { 
  result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen)); 
}
  : firstParen='(' 
           e0=expression{list.add($e0.result);}
      ( ',' e=expression{list.add($e.result);})* 
    lastParen=')'
    { result.setTheExps(list); }
  ;

named_record_aggregate returns [NamedRecordAggregate result = new NamedRecordAggregate () ]
@init   { ArrayList < RecordComponentAssociation > list = new ArrayList < RecordComponentAssociation > () ; }
@after { 
  result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen)); 
}
  : firstParen='(' 
           assoc0=record_component_association { list . add ( $assoc0.result ) ; }
      ( ',' assoc=record_component_association { list . add ( $assoc.result ) ; } )*
    lastParen=')'
    { result . setTheRecordComponentAssociations ( list ) ; }
  ;

record_component_association returns [RecordComponentAssociation result = new RecordComponentAssociation () ]
  : csn=component_selector_name '=>' e=expression
    { result.setTheIDString($csn.result); 
      result.setTheExp($e.result);
      result.setTheOptionalRegionSelection(createRS($csn.rs.getTheStartCaret(), $e.result.getTheOptionalRegionSelection().getTheEndCaret()));
    }
  ;

component_selector_name returns [String result = null, 
                                 RegionSelection rs = null ]
  : node_info=selector_name
    { IDName id = $selector_name.result;
      $component_selector_name.result = id.getTheIDString();
      $component_selector_name.rs = id.getTheOptionalRegionSelection();
    }
  ;


// SPARK95 4.3.2
extension_aggregate returns [ ExtensionAggregate result = null ]
  : firstParen='(' 
      ap=ancestor_part 'with' rcal=record_component_association_list 
    lastParen=')'
    { result = $rcal.result;
      result.setTheExp($ap.result);
      result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen)); 
    }
  | firstParen='(' ap=ancestor_part 'with' 'null' 'record' 
    lastParen=')'
    { NullRecordExtensionAggregate temp2 = new NullRecordExtensionAggregate();
      temp2.setTheExp($ap.result);
      result = temp2; 
      result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen));
    }
  ;

ancestor_part returns [ Exp result = null ]
  : e=expression { result = $e.result; }
  ;

record_component_association_list returns [ RecordExtensionAggregate result = null  ]
  : nrca=named_record_component_association { result = $nrca.result; }
  | prca=positional_record_component_association { result = $prca.result; }
  ;

positional_record_component_association returns [ PositionalRecordExtensionAggregate result = new PositionalRecordExtensionAggregate() ]
@init {
ArrayList<Exp> temp = new ArrayList<Exp>();
}
  : e=expression                           { temp.add($e.result); }
    ( ',' e=expression                     { temp.add($e.result); }
    )*                                     { result.setTheExps(temp); }
  ;

named_record_component_association returns [ NamedRecordExtensionAggregate result = new NamedRecordExtensionAggregate() ]
@init {
ArrayList<RecordComponentAssociation> temp = new ArrayList<RecordComponentAssociation>();
}
  : rca=record_component_association       { temp.add($rca.result); }
    ( ',' rca=record_component_association { temp.add($rca.result); }
    )*                                     { result.setTheRecordComponentAssociations(temp); }
  ;


// SPARK95 4.3.3
array_aggregate returns [ ArrayAggregate result = null ]
  : paa=positional_array_aggregate         { result = $paa.result; }
  | naa=named_array_aggregate              { result = $naa.result; }
  ;

positional_array_aggregate returns [ PositionalArrayAggregate result = new PositionalArrayAggregate() ]
@init {
ArrayList<AggregateItem> temp = new ArrayList<AggregateItem>();
}
  : firstParen='('
            ai=aggregate_item { temp.add($ai.result); } 
      ( ',' ai=aggregate_item { temp.add($ai.result); }
    )+ 
    lastParen=')'
    { result.setTheAggregateItems(temp); 
      result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen));
    }
  | firstParen='(' 
            ai=aggregate_item { temp.add($ai.result); }
      ( ',' ai=aggregate_item { temp.add($ai.result); }
      )* ','
      'others' '=>' ai=aggregate_item
    lastParen=')' 
    { result.setTheOthersFlag(true);
      result.setTheOptionalOthersAggregateItem($ai.result);
      result.setTheAggregateItems(temp);
      result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen));
    }
  ;

named_array_aggregate returns [ NamedArrayAggregate result = new NamedArrayAggregate() ]
@init {
ArrayList<ArrayComponentAssociation> temp = new ArrayList<ArrayComponentAssociation>();
}
  : firstParen='(' 
            aca=array_component_association { temp.add($aca.result); }
      ( ',' aca=array_component_association { temp.add($aca.result); }
      )*                                    { result.setTheOptionalArrayComponentAssociations(temp); }
      ( ',' 'others' '=>' ai=aggregate_item { result.setTheOptionalOthersAggregateItem($ai.result); 
                                              result.setTheOthersFlag(true); 
                                            }
      )?
    lastParen=')'
    { result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen));
    }
  | firstParen='(' 'others' '=>' ai=aggregate_item lastParen=')'
    { result.setTheOptionalOthersAggregateItem($ai.result); 
      result.setTheOthersFlag(true); 
      result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen));
    }
  ;

array_component_association returns [ ArrayComponentAssociation result = new ArrayComponentAssociation() ]
  : dcl=discrete_choice_list '=>'          { result.setTheChoices($dcl.result); }
    ai=aggregate_item                      { result.setTheAggregateItem($ai.result); }
  ;

aggregate_item returns [ AggregateItem result = null ]
  : e=expression                           { ExpAggregateItem temp = new ExpAggregateItem();
                                             temp.setTheExp($e.result);
                                             result = temp; }
  | aa=array_aggregate                     { ArrayAggregateItem temp2 = new ArrayAggregateItem();
                                             temp2.setTheArrayAggregate($aa.result);
                                             result = temp2; }
  ;


// SPARK95 4.4
expression returns [ Exp result = null ]
  :
    qe=quantified_expression { result = $qe.result; }
  | r=relation { result = $r.result; }
     ( '->' r=relation
       { 
         BinaryExp temp = new BinaryExp();
         temp.setTheLeftExp(result);
         temp.setTheRightExp($r.result);
         temp.setTheBinaryOp(BinaryOp.IMPLIES);
         temp.setTheOptionalRegionSelection(createRS(result, $r.result));
         result = temp; 
       }
      |
        '<->' r=relation
        { 
          BinaryExp temp = new BinaryExp();
          temp.setTheLeftExp(result);
          temp.setTheRightExp($r.result);
          temp.setTheBinaryOp(BinaryOp.IS_EQUIVALENT_TO);
          temp.setTheOptionalRegionSelection(createRS(result, $r.result));
          result = temp; 
        }
      |
        ( 'and' r=relation 
          { BinaryExp temp = new BinaryExp();
            temp.setTheLeftExp(result);
            temp.setTheRightExp($r.result);
            temp.setTheBinaryOp(BinaryOp.AND);
            temp.setTheOptionalRegionSelection(createRS(result, $r.result));
            result = temp; 
          } 
         | 'and' 'then' r=relation 
           { BinaryExp temp = new BinaryExp();
             temp.setTheLeftExp(result);
             temp.setTheRightExp($r.result);
             temp.setTheBinaryOp(BinaryOp.AND_THEN);
             temp.setTheOptionalRegionSelection(createRS(result, $r.result));
             result = temp; 
           } 
         | 'or' r=relation
           { BinaryExp temp = new BinaryExp();
             temp.setTheLeftExp(result);
             temp.setTheRightExp($r.result);
             temp.setTheBinaryOp(BinaryOp.OR);
             temp.setTheOptionalRegionSelection(createRS(result, $r.result));             
             result = temp; 
           } 
         | 'or' 'else' r=relation
           { BinaryExp temp = new BinaryExp();
             temp.setTheLeftExp(result);
             temp.setTheRightExp($r.result);
             temp.setTheBinaryOp(BinaryOp.OR_ELSE);
             temp.setTheOptionalRegionSelection(createRS(result, $r.result));             
             result = temp; 
           }
         | 'xor' r=relation
           { BinaryExp temp = new BinaryExp();
             temp.setTheLeftExp(result);
             temp.setTheRightExp($r.result);
             temp.setTheBinaryOp(BinaryOp.XOR); 
             temp.setTheOptionalRegionSelection(createRS(result, $r.result));             
             result = temp; 
           }
       )*    
     )
  ;

// bug fix : Barnes p392 indicates quant exp end with a ';'.  However, quant exp
// are part of assert/check/pre/postreturn expressions, all of which end with a ';'
quantified_expression returns [ QuantifiedExp result = null ]
@init{
QuantifierKind qk = null;
ArrayList<Predicate> predList = new ArrayList<Predicate>();
}
@after{ result.setTheOptionalRegionSelection(createRS($forLoc, $lastParenLoc)); }
  :  forLoc='for' 
    (   'all' { qk = QuantifierKind.FOR_ALL; } 
      | 'some' { qk = QuantifierKind.FOR_SOME; }
    )
    id=defining_identifier 'in' sm=subtype_mark ('range' r=range)? '=>' '(' p=predicate lastParenLoc=')' 
    { result = new QuantifiedExp();
      result.setTheKind(qk);
      result.setTheIdentifier($id.result);
      result.setTheSubTypeMark(sm);
      result.setTheOptionalRange(r);
      result.setThePredicate(p);
    }
  ;
  
predicate returns [ Predicate result = new Predicate() ]
  :  e=expression {result.setTheExp($e.result); }
  ;
  
relation returns [ Exp result = null ]
@init {
boolean isNegated = false;
}
  : se3=simple_expression { result = $se3.result; }
    ('not' { isNegated = true; } )? 'in' r=range
    { InRangeExp temp2 = new InRangeExp();
      temp2.setTheExp(result);
      temp2.setTheRange($r.result);
      result = temp2;
      if (isNegated) {
        UnaryExp temp3 = new UnaryExp();
        temp3.setTheUnaryOp(UnaryOp.NOT);
        temp3.setTheExp(temp2);
        result = temp3;
      }
      result.setTheOptionalRegionSelection(createRS($se3.result.getTheOptionalRegionSelection().getTheStartCaret(), 
                                                    $r.result.getTheOptionalRegionSelection().getTheEndCaret()));
    }
  | se4=simple_expression { result = $se4.result; }
    ('not' { isNegated = true; } )? 'in' sm=subtype_mark
    { NameRangeExp temp4 = new NameRangeExp();
      temp4.setTheExp(result);
      temp4.setTheName($sm.result);
      result = temp4;
      if (isNegated) {
        UnaryExp temp5 = new UnaryExp();
        temp5.setTheUnaryOp(UnaryOp.NOT);
        temp5.setTheExp(temp4);
        result = temp5;
      }
      result.setTheOptionalRegionSelection(createRS($se4.result.getTheOptionalRegionSelection().getTheStartCaret(),
                                                    $sm.result.getTheOptionalRegionSelection().getTheEndCaret()));
    }
    |  se1=simple_expression { result = $se1.result; }
    ( ro=relational_operator se2=simple_expression
      { BinaryExp temp = new BinaryExp();
        temp.setTheLeftExp(result);
        temp.setTheRightExp($se2.result);
        temp.setTheBinaryOp($ro.result); 
        temp.setTheOptionalRegionSelection(createRS(result.getTheOptionalRegionSelection()));
        temp.getTheOptionalRegionSelection().setTheEndCaret($se2.result.getTheOptionalRegionSelection().getTheEndCaret());
        result = temp; 
      }
    )?
  ;

simple_expression returns [ Exp result = null ]
@init {
UnaryOp unaryOp = null;
boolean hasUnaryOp = false;
RegionSelection rs = this.newRegionSelection();
}
@after { result.setTheOptionalRegionSelection(rs); }
  : ( uao=unary_adding_operator
      { unaryOp = $uao.result;
        hasUnaryOp = true; 
      }
    )? 
    t=term 
    { if (hasUnaryOp) {
        UnaryExp temp = new UnaryExp();
        temp.setTheUnaryOp(unaryOp);
        temp.setTheExp($t.result);
        result = temp;
        rs.setTheStartCaret($uao.rs.getTheStartCaret());
      } else {
        result = $t.result;
        rs.setTheStartCaret($t.result.getTheOptionalRegionSelection().getTheStartCaret());
      }
      rs.setTheEndCaret($t.result.getTheOptionalRegionSelection().getTheEndCaret());
    }
    ( bao=binary_adding_operator           
      t=term
      { BinaryExp temp2 = new BinaryExp();
        temp2.setTheBinaryOp($bao.result);
        temp2.setTheLeftExp(result);
        temp2.setTheRightExp($t.result);
        result = temp2; 
        rs.setTheEndCaret($t.result.getTheOptionalRegionSelection().getTheEndCaret());
      }
    )*
  ;

term returns [ Exp result = null ]
@init{ RegionSelection rs = this.newRegionSelection(); }
@after{ result.setTheOptionalRegionSelection(rs); }
  : f=factor 
    { result = $f.result; 
      rs = createRS($f.result.getTheOptionalRegionSelection());
    }
    ( mo=multiplying_operator f=factor
      { BinaryExp temp = new BinaryExp();
        temp.setTheBinaryOp($mo.result);
        temp.setTheLeftExp(result);
        temp.setTheRightExp($f.result);
        result = temp; 
        rs.setTheEndCaret($f.result.getTheOptionalRegionSelection().getTheEndCaret());
      }
    )*
  ;

factor returns [ Exp result = null ]
  : p=primary 
    { result = $p.result; 
      result.setTheOptionalRegionSelection(createRS($p.result.getTheOptionalRegionSelection()));
    }
    ( '**' p2=primary
           { 
             BinaryExp temp = new BinaryExp();
             temp.setTheBinaryOp(BinaryOp.POW);
             temp.setTheLeftExp(result);
             temp.setTheRightExp($p2.result);
             temp.setTheOptionalRegionSelection(createRS(result.getTheOptionalRegionSelection()));
              temp.getTheOptionalRegionSelection().setTheEndCaret($p2.result.getTheOptionalRegionSelection().getTheEndCaret());
             result = temp;
           }
    )?
  | absLoc='abs' p3=primary
    { 
      UnaryExp temp = new UnaryExp();
      temp.setTheUnaryOp(UnaryOp.ABS);
      temp.setTheExp($p3.result);
      temp.setTheOptionalRegionSelection(createRS($absLoc, $p3.result.getTheOptionalRegionSelection().getTheEndCaret()));      
      result = temp;
    }
  | notLoc='not' p4=primary
    { 
      UnaryExp temp = new UnaryExp();
      temp.setTheUnaryOp(UnaryOp.NOT);
      temp.setTheExp($p4.result);
      temp.setTheOptionalRegionSelection(createRS($notLoc, $p4.result.getTheOptionalRegionSelection().getTheEndCaret()));      
      result = temp; 
    }
  ;

primary returns [ Exp result = null ]
  : nl=NUMERIC_LITERAL
    { NumericLiteral temp = new NumericLiteral();
      RegionSelection rs = createRS($nl);
      temp.setTheNumberString($nl.text);
      temp.setTheOptionalRegionSelection(rs);
      LiteralExp temp2 = new LiteralExp();
      temp2.setTheOptionalRegionSelection(rs);
      temp2.setTheLiteral(temp);
      result = temp2;
    }
  | cl=CHARACTER_LITERAL_OR_QUOTE
    { CharacterLiteral temp3 = new CharacterLiteral();
      RegionSelection rs = createRS($cl);
      if ($cl.text.length() > 1) { 
        temp3.setTheCharacter($cl.text.charAt(1));
      } else{
        assert false;
      }
      temp3.setTheOptionalRegionSelection(rs);
      
      LiteralExp temp4 = new LiteralExp();
      temp4.setTheLiteral(temp3);
      temp4.setTheOptionalRegionSelection(rs);
      result = temp4;
    }
  | sl=STRING_LITERAL
    { StringLiteral temp5 = new StringLiteral();
      RegionSelection rs = createRS($sl);
      temp5.setTheString($sl.text);
      temp5.setTheOptionalRegionSelection(rs);
       
      LiteralExp temp6 = new LiteralExp();
      temp6.setTheLiteral(temp5);
      temp6.setTheOptionalRegionSelection(rs);
      result = temp6; 
    }
  | n=name
    { NameExp temp7 = new NameExp();
      temp7.setTheName($n.result);
      temp7.setTheOptionalRegionSelection(createRS($n.result.getTheOptionalRegionSelection()));
      result = temp7; 
    }
    ( // ANTLR LL(*) HACK 
      // (refactored common name)
      // qualified_expression_suffix
      CHARACTER_LITERAL_OR_QUOTE '(' e=expression lastParen=')'
      { ExpQualifiedExp tempA = new ExpQualifiedExp();
        tempA.setTheName($n.result);
        tempA.setTheExp($e.result);
        tempA.setTheOptionalRegionSelection(createRS($n.result.getTheOptionalRegionSelection().getTheStartCaret(), $lastParen));
        result = tempA;
      }
    | CHARACTER_LITERAL_OR_QUOTE a=aggregate
      { AggregateQualifiedExp tempB = new AggregateQualifiedExp();
        tempB.setTheName($n.result);
        tempB.setTheAggregate($a.result);
        tempB.setTheOptionalRegionSelection(createRS($n.result.getTheOptionalRegionSelection().getTheStartCaret(), 
                                                     $a.result.getTheOptionalRegionSelection().getTheEndCaret()));
        result = tempB; 
      }
    )?
  | tc=type_conversion                      { result = $tc.result; }
  | firstParen='(' e=expression lastParen=')'
    { ParenExp temp8 = new ParenExp();
      temp8.setTheExp($e.result);
      temp8.setTheOptionalRegionSelection(createRS($firstParen, $lastParen));
      result = temp8; 
    }
  ;


// SPARK95 4.5
relational_operator returns [ BinaryOp result ]
  : '='                                    { result = BinaryOp.EQUAL; }
  | '/='                                   { result = BinaryOp.NOT_EQUAL; }
  | '<'                                    { result = BinaryOp.LESS; }
  | '<='                                   { result = BinaryOp.LESS_EQUAL; }
  | '>'                                    { result = BinaryOp.GREATER; }
  | '>='                                   { result = BinaryOp.GREATER_EQUAL; }
  ;

binary_adding_operator returns [ BinaryOp result ]
  : '+'                                    { result = BinaryOp.ADD; }
  | '-'                                    { result = BinaryOp.SUB; }
  | '&'                                    { result = BinaryOp.AMP; }
  ;

unary_adding_operator returns [ UnaryOp result = null,
                                RegionSelection rs = null ]
@after{ 
  $unary_adding_operator.rs = createRS($loc); 
}
  : loc='+' { $unary_adding_operator.result = UnaryOp.POS;}
  | loc='-' { $unary_adding_operator.result = UnaryOp.NEG; }
  ;

multiplying_operator returns [ BinaryOp result ]
  : '*'                                    { result = BinaryOp.MUL; }
  | '/'                                    { result = BinaryOp.DIV; }
  | 'mod'                                  { result = BinaryOp.MOD; }
  | 'rem'                                  { result = BinaryOp.REM; }
  ;


// SPARK95 4.6
type_conversion returns [ TypeConversion result = new TypeConversion() ]
  : sm=subtype_mark '(' e=expression ')'   { result.setTheName($sm.result);
                                             result.setTheExp($e.result); }
  ;


// SPARK95 4.7
qualified_expression returns [ QualifiedExp result = null ]
  : sm=subtype_mark 
    CHARACTER_LITERAL_OR_QUOTE '('
    e=expression ')'                       { ExpQualifiedExp temp = new ExpQualifiedExp();
                                             temp.setTheName($sm.result);
                                             temp.setTheExp($e.result);
                                             result = temp; }
  | sm=subtype_mark 
    CHARACTER_LITERAL_OR_QUOTE
    a=aggregate                            { AggregateQualifiedExp temp2 = new AggregateQualifiedExp();
                                             temp2.setTheName($sm.result);
                                             temp2.setTheAggregate($a.result);
                                             result = temp2; }
  ;


// SPARK95 4.8


// SPARK95 5.1
sequence_of_statements returns [ StatementList result = new StatementList() ]
@init {
ArrayList<Statement> temp = new ArrayList<Statement>();
}
  : ( s=statement                          { temp.add($s.result); }
    )+                                     { result.setTheStatements(temp); }
  ;


statement returns [ Statement result = null ]
  : label_list=sequence_of_statement_labels ss=simple_statement
    { result = $ss.result; 
      result.setTheOptionalLabelList($label_list.result);
    }
  | label_list=sequence_of_statement_labels cs=compound_statement                  
    { result = $cs.result; 
      result.setTheOptionalLabelList($label_list.result);
    }
  | {lineStartsWithSparkAnnotation()}? => (
      ps=proof_statement                     { result = $ps.result; }
    )
  ;

sequence_of_statement_labels returns [ArrayList<String> result = null]
  : ('<<' id=IDENTIFIER '>>'  {if(result == null) { result = new ArrayList<String>(); } result.add($id.text); } )*
  ;

simple_statement returns [ SimpleStatement result = null ]
  : ns=null_statement                      { result = $ns.result; }
  | as=assignment_statement                { result = $as.result; }
  | pcs=procedure_call_statement           { result = $pcs.result; }
  | es=exit_statement                      { result = $es.result; }
  | rs=return_statement                    { result = $rs.result; }
  ;

compound_statement returns [ CompoundStatement result = null ]
  : is=if_statement                        { result = $is.result; }
  | cs=case_statement                      { result = $cs.result; }
  | ls=loop_statement                      { result = $ls.result; }
  ;

proof_statement returns [ ProofStatement result = null ]
  : as=assert_statement                    { result = $as.result; }
  | cs=check_statement                     { result = $cs.result; }
  | js=justification_statement             { result = $js.result; }
  | jse=justification_statement_end        { result = $jse.result; }  
  ;
  
assert_statement returns [ AssertStatement result = new AssertStatement() ]
@after{
  result.setTheOptionalRegionSelection(createRS($assertLoc, $semiLoc));
}
  :  assertLoc='assert' p=predicate semiLoc=';'
    { result.setThePredicate($p.result); }
  ;
  
check_statement returns [ CheckStatement result = new CheckStatement() ]
@after{
  result.setTheOptionalRegionSelection(createRS($checkLoc, $semiLoc));
}
  :  checkLoc='check' c=predicate semiLoc=';'
    { result.setThePredicate($c.result); }
  ;
  
justification_statement_end returns [JustificationStatementEnd result = new JustificationStatementEnd() ]
@after{
  result.setTheOptionalRegionSelection(createRS($endLoc, $semiLoc));
}
  : endLoc='end' 'accept' semiLoc=';'
  ;
  
justification_statement returns [ JustificationStatement result = new JustificationStatement() ]
@init{
ArrayList<JustificationClause> list = new ArrayList<JustificationClause>();
result.setTheClauses(list);
}
@after{
  result.setTheOptionalRegionSelection(createRS($acceptLoc, $semiLoc));
}
  : acceptLoc='accept' jc=justification_clause {list.add($jc.result);} ( '&' jc=justification_clause {list.add($jc.result);} )*  semiLoc=';'
  ;
  
justification_clause returns [ JustificationClause result = new JustificationClause() ] 
@init{
ArrayList<Name> theList = null;
}
  : mk=message_kind ',' 
    il=NUMERIC_LITERAL ',' 
    ( n=name 
      { theList = new ArrayList<Name>(); 
        result.setTheOptionalNames(theList);
        theList.add($n.result);
      } 
      (',' n2=name {theList.add($n2.result); } )* ',')?  
    sl=STRING_LITERAL
    {
      result.setTheKind($mk.result);
      result.setTheMessageID($il.text); 
      String theMessage = $sl.text;
      result.setTheMessage(theMessage); 
    }
  ;

message_kind returns [ MessageKind result = null; ]
  : 
    'Flow_Message'{result=MessageKind.FLOW_MESSAGE;}
  | 'Warning_Message'{result=MessageKind.WARNING_MESSAGE;}
  ;
  
null_statement returns [ NullStatement result = new NullStatement() ]
@after{
  RegionSelection rs = createRS($nullLoc, $semiloc);
  result.setTheOptionalRegionSelection(rs);
}
  : nullLoc='null' semiloc=';'
  ;

statement_identifier returns [ IDName result = null ]
  : dn=direct_name { result = $dn.result; }
  ;


// SPARK95 5.2
assignment_statement returns [ AssignmentStatement result = new AssignmentStatement() ]
@after{
  RegionSelection rs = createRS($vn.result.getTheOptionalRegionSelection().getTheStartCaret(), $semiLoc);
  result.setTheOptionalRegionSelection(rs);
}
  : vn=variable_name ':=' e=expression semiLoc=';' { result.setTheName($vn.result);
                                                     result.setTheExp($e.result); }  
  ;

// added
variable_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;


// SPARK95 5.3
if_statement returns [ IfStatement result = new IfStatement() ]
@init {
ArrayList<Triple<Exp, StatementList, RegionSelection>> temp = new ArrayList<Triple<Exp, StatementList, RegionSelection>>();
}
@after{
  result.setTheOptionalRegionSelection(createRS($ifLoc, $semiLoc));
}
  : ifLoc='if' c=condition thenLoc1='then' sos=sequence_of_statements
    { result.setTheExp($c.result);
      result.setTheThen($sos.result); 
      result.setTheIfRegionSelection(createRS($ifLoc, getPrecedingToken($thenLoc1)));
    }
    ( elsifLoc='elsif' c=condition thenLoc2='then' sos=sequence_of_statements 
      { RegionSelection rs = createRS($elsifLoc, getPrecedingToken($thenLoc2));
        Triple<Exp, StatementList, RegionSelection> temp2 = new Triple<Exp, StatementList, RegionSelection>($c.result, $sos.result, rs); 
        temp.add(temp2);
      }
    )*
    { if (!temp.isEmpty()) {
        result.setTheOptionalElseIfs(temp);
      }
    }
    ( elseLoc='else' sos=sequence_of_statements 
      { result.setTheOptionalElse($sos.result); } 
    )?
    'end' 'if' semiLoc=';'
    { result.setTheOptionalRegionSelection(createRS($ifLoc, $semiLoc)); }
  ;

condition returns [ Exp result = null ]
  : be=boolean_expression { result = $be.result; }
  ;

// added
boolean_expression returns [ Exp result = null ]
  : e=expression { result = $e.result; }
  ;


// SPARK95 5.4
case_statement returns [ CaseStatement result = new CaseStatement() ]
@init {
ArrayList<CaseStatementAlternative> temp = new ArrayList<CaseStatementAlternative>();
}
@after{
  result.setTheOptionalRegionSelection(createRS($caseLoc, $semiLoc));
}
  : caseLoc='case' e=expression 'is'               { result.setTheExp($e.result); }
    ( csa=case_statement_alternative       { temp.add($csa.result); }
    )+                                     { result.setTheCaseStatementAlternatives(temp); }
    ( whenLoc='when' othersLoc='others' '=>'
      sos=sequence_of_statements
      { result.setTheOptionalOthers($sos.result); 
        int size = sos.getTheStatements().size();
        Statement firstStatement = sos.getTheStatements().get(0);
        Statement lastStatement = sos.getTheStatements().get(size - 1);
        result.setTheOptionalOthersBodySelection(createRS(firstStatement, lastStatement));
        result.setTheOptionalOthersSelection(createRS($whenLoc, $othersLoc));        
      }
    )?
    'end' 'case' semiLoc=';'
  ;

case_statement_alternative returns [ CaseStatementAlternative result = new CaseStatementAlternative() ]
@after{
  Choice lastChoice = dcl.get(dcl.size() - 1);
  int size = sos.getTheStatements().size();
  Statement firstStatement = sos.getTheStatements().get(0);
  Statement lastStatement = sos.getTheStatements().get(size - 1);
  
  result.setTheOptionalRegionSelection(createRS($whenLoc, lastStatement));
  result.setTheChoiceListSelection(createRS($whenLoc, lastChoice));
  result.setTheBodySelection(createRS(firstStatement, lastStatement));
}
  : whenLoc='when' dcl=discrete_choice_list        { result.setTheChoices($dcl.result); }
    '=>' sos=sequence_of_statements        { result.setTheStatementList($sos.result); }
  ;


// SPARK95 5.5
loop_statement returns [ LoopStatement result = null ]
@after{
  Caret theStartCaret = result.getTheLoopKeywordRegionSelection().getTheStartCaret();
  if(result.getTheOptionalIDRegionSelection() != null){
    theStartCaret = result.getTheOptionalIDRegionSelection().getTheStartCaret();
  } else if(result instanceof IterationSchemeLoopStatement){
    IterationSchemeLoopStatement isls = (IterationSchemeLoopStatement) result;
    theStartCaret = isls.getTheIterationSchemeRegionSelection().getTheStartCaret();
  }
  result.setTheOptionalRegionSelection(createRS(theStartCaret, $semiLoc));
}
  : ( lsi=loop_statement_identifier colonLoc=':' 
    )?
    ( is=iteration_scheme ( linv=loop_invariant )?
      { result = $is.result; 
        result.setTheOptionalLoopInvariant(linv); 
      }
    )?
    { if (result == null) {
        result = new DefaultLoopStatement();
      }
    }
    { if ($lsi.result != null ){
        result.setTheOptionalIDString($lsi.result.getTheIDString());
        result.setTheOptionalIDRegionSelection(createRS($lsi.result.getTheOptionalRegionSelection().getTheStartCaret(), $colonLoc));
      }
    }
    loopLoc='loop' { result.setTheLoopKeywordRegionSelection(createRS($loopLoc)); }
    sos=sequence_of_statements { result.setTheStatementList($sos.result); }
    'end' 'loop' 
    ( li=loop_identifier )? // TODO: would probably be a good idea to ensure the id's match
    semiLoc=';'
  ;

loop_invariant returns [ AssertStatement result = null ]
  : as=assert_statement {result = $as.result;}
  ;
  
// added
loop_statement_identifier returns [ IDName result = null ]
  : si=statement_identifier { result = $si.result; }
  ;

loop_identifier returns [ String result = null ]
  : id=IDENTIFIER
    { result = $id.text; }
  ;

iteration_scheme returns [ LoopStatement result = null ]
  : whileLoc='while' c=condition
    { WhileLoopStatement temp = new WhileLoopStatement();
      temp.setTheExp($c.result); 
      //temp.setTheOptionalRegionSelection(createRS($whileLoc$, c.result.getTheOptionalRegionSelection().getTheEndCaret()));
      temp.setTheIterationSchemeRegionSelection(createRS($whileLoc, getPrecedingAdaToken($whileLoc, "loop"))); 
      result = temp; 
    }
  | forLoc='for' lps=loop_parameter_specification
    { ForLoopStatement temp = $lps.result; 
      temp.setTheIterationSchemeRegionSelection(createRS($forLoc, getPrecedingAdaToken($forLoc, "loop")));
      //result.setTheOptionalRegionSelection(createRS($forLoc, $lps.result.getTheOptionalRegionSelection().getTheEndCaret()));
      result = temp;
    }
  ;

loop_parameter_specification returns [ ForLoopStatement result = new ForLoopStatement() ]
@after{
  Caret endCaret = null;
  if(result.getTheOptionalRange() != null){
    endCaret = $r.result.getTheOptionalRegionSelection().getTheEndCaret();
  } else {
    endCaret = $dsm.result.getTheOptionalRegionSelection().getTheEndCaret();
  }
  result.setTheOptionalRegionSelection(createRS($did.rs.getTheStartCaret(), endCaret));
}
  : did=defining_identifier 'in'           { result.setTheIDString($did.result); }
    ( 'reverse'                            { result.setTheReverseFlag(true); }
    )? 
    dsm=discrete_subtype_mark              { result.setTheName($dsm.result); }
    ( 'range' r=range                      { result.setTheOptionalRange($r.result); }
    )?
  ;


// SPARK95 5.6


// SPARK95 5.7
exit_statement returns [ ExitStatement result = new ExitStatement() ]
@after{
  RegionSelection rs = createRS($exitLoc, $semiLoc);
  result.setTheOptionalRegionSelection(rs);
}
  : exitLoc='exit' ( sn=simple_name                { result.setTheOptionalName($sn.result); }
           )?
    ( 'when' c=condition                   { result.setTheOptionalExp($c.result); }
    )? semiLoc=';' 
  ;

// added
simple_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;
  

// SPARK95 5.8


// SPARK95 6.1
subprogram_declaration returns [ SubProgramDeclaration result = null ]
  : ps=procedure_specification semiLoc1=';'
    pa=procedure_annotation               
    {
      ProcedureSubProgramDeclaration temp = new ProcedureSubProgramDeclaration();
      temp.setTheProcedureSpecification($ps.result);
      temp.setTheProcedureAnnotation($pa.result);
      result = temp;
      result.setTheOptionalRegionSelection(createRS($ps.result.getTheOptionalRegionSelection().getTheStartCaret(), $semiLoc1));
    }
  | fs=function_specification semiLoc2=';'
    fa=function_annotation
    { 
      FunctionSubProgramDeclaration temp2 = new FunctionSubProgramDeclaration();
      temp2.setTheFunctionSpecification($fs.result);
      temp2.setTheFunctionAnnotation($fa.result); 
      result = temp2;
       result.setTheOptionalRegionSelection(createRS($fs.result.getTheOptionalRegionSelection().getTheStartCaret(), $semiLoc2));
    }
  ;

procedure_specification returns [ ProcedureSpecification result = new ProcedureSpecification() ]
@after{
  Caret endCaret = $did.rs.getTheEndCaret();
  if($pp.result != null) {
    endCaret = $pp.rs.getTheEndCaret();
  }
  result.setTheOptionalRegionSelection(createRS($procedureLoc, endCaret)); //$did.rs.getTheEndCaret()));
  result.setTheMethodNameSelection($did.rs);
}
  : procedureLoc='procedure' did=defining_identifier { result.setTheIDString($did.result); }
    pp=parameter_profile { result.setTheOptionalParameterSpecification($pp.result); }
  ;

function_specification returns [ FunctionSpecification result = new FunctionSpecification() ]
@after{
  result.setTheOptionalRegionSelection(createRS($functionLoc, result.getTheName().getTheOptionalRegionSelection().getTheEndCaret()));
  result.setTheMethodNameSelection($dd.rs);
}
  : functionLoc='function' dd=defining_designator
    { 
      result.setTheIDString($dd.result); 
    }
    prp=parameter_and_result_profile      
    {
      result.setTheName($prp.name); 
      if ($prp.formalPart != null) {
        result.setTheOptionalParameterSpecification($prp.formalPart);
      }
    }
  ;

designator returns [ String result = null,
                     RegionSelection rs = null ]
@after  { $designator.rs = createRS($id); }
  : id=IDENTIFIER  { $designator.result = $id.text; }
  ;

defining_designator returns [ String result = null, 
                              RegionSelection rs = null ]
  : did=defining_identifier 
    { $defining_designator.result = $did.result;
      $defining_designator.rs = $did.rs;
    }
  ;

// modifieddeclarative_item
defining_program_unit_name returns [ Name result = null ]
  : n=name                                       { result = $n.result; }
//    ( parent_unit_name '.' )?
//    defining_identifier  
  ;

operator_symbol returns [ StringLiteral result = new StringLiteral() ]
  : sl=STRING_LITERAL 
    { result.setTheString($sl.text);
      result.setTheOptionalRegionSelection(createRS($sl));      
    }
  ;

defining_operator_symbol returns [ StringLiteral result = null ]
  : os=operator_symbol                     { result = $os.result; }
  ;

parameter_profile returns [ ArrayList<ParameterSpecification> result = null,
                            RegionSelection rs = null ]
  : ( fp=formal_part 
      { $parameter_profile.result = $fp.result; 
        $parameter_profile.rs = $fp.rs;
      } 
     )?
  ;

parameter_and_result_profile returns [ ArrayList<ParameterSpecification> formalPart = null, Name name = null ]
  : ( fp=formal_part                       { $parameter_and_result_profile.formalPart = $fp.result; }
    )?
    'return' sm=subtype_mark               { $parameter_and_result_profile.name = $sm.result; }
  ;

formal_part returns [ ArrayList<ParameterSpecification> result = new ArrayList<ParameterSpecification>(), 
                      RegionSelection rs = null ]
@after{
 $formal_part.rs = createRS($startParen, $endParen);
}
  : startParen='(' 
            ps=parameter_specification { $formal_part.result.add($ps.result); }
      ( ';' ps=parameter_specification { $formal_part.result.add($ps.result); } )* 
    endParen=')'
  ;

parameter_specification returns [ ParameterSpecification result = new ParameterSpecification(); ]
  : dil=defining_identifier_list ':' m=mode sm=subtype_mark
    { result.setTheParameterNames($dil.result);
      result.setTheMode($m.result); 
      result.setTheSubtypeMark($sm.result);
      result.setTheOptionalRegionSelection(createRS($dil.result.get(0).getTheOptionalRegionSelection().getTheStartCaret(), $sm.result.getTheOptionalRegionSelection().getTheEndCaret()));
    }
  ;

mode returns [Mode result = Mode.NONE, RegionSelection rs = null ]
  : ( in1='in'
      { $mode.result = Mode.IN; 
        $mode.rs = createRS($in1);
      }
    )?
  | in2='in' out1='out'
    { $mode.result = Mode.IN_OUT; 
      $mode.rs = createRS($in2);
    }
  | out2='out'
    { $mode.result = Mode.OUT; 
      $mode.rs = createRS($out2);
    }
  ;


// SPARK95 6.1.1
procedure_annotation returns [ ProcedureAnnotation result = new ProcedureAnnotation() ]
  : ( gd=global_definition                 { result.setTheOptionalGlobalDefinition($gd.result); }
    )?
    ( dr=dependency_relation               { result.setTheOptionalDependency($dr.result); }
    )?
    ( prec=precondition                    { result.setTheOptionalPrecondition($prec.result); }
    )?
    ( postc=postcondition                  { result.setTheOptionalPostcondition($postc.result); }
    )?
  ;

function_annotation returns [ FunctionAnnotation result = new FunctionAnnotation() ]
  : ( gd=global_definition                 { result.setTheOptionalGlobalDefinition($gd.result); }
    )?
    ( prec=precondition                    { result.setTheOptionalPrecondition($prec.result); }
    )?
    ( ret=return_annotation                { result.setTheOptionalReturnAnnotation($ret.result); }
    )?
  ;

precondition returns [ Precondition result = new Precondition() ]
@after{ result.setTheOptionalRegionSelection(createRS($preLoc, $semiLoc));}
  :  preLoc='pre' p=predicate semiLoc=';'       { result.setThePredicate($p.result); }
  ;

postcondition returns [ Postcondition result = new Postcondition() ]
@after{result.setTheOptionalRegionSelection(createRS($postLoc, $semiLoc));}
  :   postLoc='post' p=predicate semiLoc=';'      { result.setThePredicate($p.result); }
  ;
  
return_annotation returns [ ReturnAnnotation result = null; ]
  :  retLoc='return' e=expression semiLoc=';'    
    { RegionSelection rs = createRS($retLoc, $semiLoc);
    
      IDName theId = new IDName();
      theId.setTheIDString(org.sireum.bakar.SparkUtil.IMPLICIT_RESULT_ID);
      theId.setTheOptionalRegionSelection(rs);
      
      NameExp ne = new NameExp();
      ne.setTheName(theId);
      ne.setTheOptionalRegionSelection(rs);
      
      BinaryExp be = new BinaryExp();
      be.setTheBinaryOp(BinaryOp.EQUAL);
      be.setTheLeftExp(ne);
      be.setTheRightExp($e.result);
      be.setTheOptionalRegionSelection(rs);
      
      Predicate pred = new Predicate();
      pred.setTheExp(be);
      pred.setTheOptionalRegionSelection(rs);
      
      ReturnAnnotationPred fakerap = new ReturnAnnotationPred();
      fakerap.setTheID(org.sireum.bakar.SparkUtil.IMPLICIT_RESULT_ID);
      fakerap.setThePredicate(pred);
      fakerap.setTheOptionalRegionSelection(rs);
      result = fakerap;
      /*
      ReturnAnnotationExp rae = new ReturnAnnotationExp();
      rae.setTheExp($e.result); 
      rae.setTheOptionalRegionSelection(createRS($retLoc, $semiLoc));
      result = rae;
      */
    }
  | retLoc='return' id=IDENTIFIER '=>' p=predicate semiLoc=';'
    { ReturnAnnotationPred rap = new ReturnAnnotationPred();
      rap.setTheID($id.text); 
      rap.setThePredicate($p.result); 
      rap.setTheOptionalRegionSelection(createRS($retLoc, $semiLoc));
      result = rap;
    }
  ;
  
// SPARK95 6.1.2
global_definition returns [ GlobalDefinition result = null ]
@init{
  ArrayList<GlobalDeclaration> l = new ArrayList<GlobalDeclaration>();
}
@after{
  result = new GlobalDefinition();
  result.setTheGlobalDeclarations(l);
  
  // get the position of the last semicolon
  Caret endCaret = l.get(l.size() - 1).getTheOptionalRegionSelection().getTheEndCaret();
  result.setTheOptionalRegionSelection(createRS($globalLoc, endCaret));
}                            
  : 
    globalLoc='global' ( gm=global_mode gvl=global_variable_list semiLoc=';'
    { GlobalDeclaration temp = new GlobalDeclaration(); 
      temp.setTheMode($gm.result);
      
      Caret startCaret = $gm.result != GlobalMode.NONE ? $gm.rs.getTheStartCaret() : $gvl.result.get(0).getTheOptionalRegionSelection().getTheStartCaret();
      temp.setTheOptionalRegionSelection(createRS(startCaret, $semiLoc));
      
      temp.setTheNames($gvl.result); 
      l.add(temp); 
    }
    )+
  ;

global_mode returns [ GlobalMode result = GlobalMode.NONE,
                      RegionSelection rs = null ]
  : 
  (
      in1='in'
      { $global_mode.result = GlobalMode.IN; 
        $global_mode.rs = createRS($in1);
      }
    | in2='in' out1='out'
      { $global_mode.result = GlobalMode.IN_OUT;
        $global_mode.rs = createRS($in2);
      }
    | out2='out'
      { $global_mode.result = GlobalMode.OUT; 
        $global_mode.rs = createRS($out2);
      }
  )?
  ;

global_variable_list returns [ ArrayList<Name> result = new ArrayList<Name>() ]
  : gv=global_variable                     { result.add($gv.result); }
    ( ',' gv=global_variable               { result.add($gv.result); }
    )*
  ;

global_variable returns [ Name result = null ]
  : ev=entire_variable                     { result = $ev.result; }
  ;

// modified
entire_variable returns [ Name result = null ]
  : n=name                                 { result = $n.result; } 
  // ( package_name '.' )? direct_name
  ;

// added
package_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;

dependency_relation returns [ DependencyRelation result = new DependencyRelation() ]
@init {
ArrayList<DependencyClause> temp = new ArrayList<DependencyClause>();
}
  : 
    derives='derives'
    ( dc=dependency_clause                 { temp.add($dc.result); }
        ( '&' dc=dependency_clause         { temp.add($dc.result); }
        )*                                 { result.setTheOptionalDependencyClauses(temp); }
        ( '&' ndc=null_dependency_clause   { result.setTheOptionalNullDependencyVariables($ndc.result); }
        )?
    )? semiloc=';'
    {
      result.setTheOptionalRegionSelection(createRS($derives, $semiloc));
    }
  | 
    derives='derives' 
    ndc=null_dependency_clause semiloc=';'         
    { 
      result.setTheOptionalNullDependencyVariables($ndc.result);
      result.setTheOptionalRegionSelection(createRS($derives, $semiloc));
    }

  ;

dependency_clause returns [ DependencyClause result = new DependencyClause() ]
@after{
  Caret startCaret = $evl.result.get(0).getTheOptionalRegionSelection().getTheStartCaret();

  Caret endCaret = null;
  if($ivl.importedVariables != null && !$ivl.importedVariables.isEmpty()) {
    endCaret = $ivl.importedVariables.get($ivl.importedVariables.size() - 1).getTheOptionalRegionSelection().getTheEndCaret();
  } else{
    endCaret = createCaret($from, false);
  }
  
  result.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
}
  : evl=exported_variable_list             { result.setTheExportedVariables($evl.result); }
    from='from'
    ( ivl=imported_variable_list           { result.setTheImportStarFlag($ivl.starFlag);
                                             if ($ivl.importedVariables != null ) {
                                               result.setTheOptionalImportedVariables($ivl.importedVariables); 
                                             }
                                           }
    )?
  ;

exported_variable_list returns [ ArrayList<Name> result = new ArrayList<Name>() ]
  : ev=exported_variable                   { result.add($ev.result); }
    ( ',' ev=exported_variable             { result.add($ev.result); }
    )*
  ;

exported_variable returns [ Name result = null ]
  : ev=entire_variable                     { result = $ev.result; }
  ;

imported_variable_list returns [ ArrayList<Name> importedVariables = null, boolean starFlag = false,
                                 RegionSelection rs = null ]
@after{
  Caret startCaret = null;
  Caret endCaret = null;
  
  if($imported_variable_list.starFlag){
    startCaret = createCaret($ast);
  } else if($imported_variable_list.importedVariables != null) {
    Name first = $imported_variable_list.importedVariables.get(0);
    startCaret = first.getTheOptionalRegionSelection().getTheStartCaret();
  }
  
  if($imported_variable_list.importedVariables != null){
    Name last = $imported_variable_list.importedVariables.get($imported_variable_list.importedVariables.size() - 1);
    endCaret = last.getTheOptionalRegionSelection().getTheEndCaret();
  } else {
    endCaret = createCaret($ast, false);
  }
  
  $imported_variable_list.rs = createRS(startCaret, endCaret);
}
  : ast='*'                                { $imported_variable_list.starFlag = true; }
  | ( ast='*' ','                              { $imported_variable_list.starFlag = true; }
    )?
    iv=imported_variable {
      $imported_variable_list.importedVariables = new ArrayList<Name>(); 
      $imported_variable_list.importedVariables.add($iv.result); 
    }
    ( ',' iv=imported_variable {
            $imported_variable_list.importedVariables.add($iv.result); 
          }
    )*
  ;

imported_variable returns [ Name result = null ]
  : ev=entire_variable                     { result = $ev.result; }
  ;

null_dependency_clause returns [ ArrayList<Name> result = new ArrayList<Name>() ]
  : 'null' 'from'
    iv=imported_variable                   { result.add($iv.result); }
    ( ',' imported_variable                { result.add($iv.result); }
    )*
  ;


// SPARK95 6.3
subprogram_body returns [ SubProgramBody result = null ]
@init {
ProcedureSubProgramBody temp = null;
FunctionSubProgramBody temp2 = null;
}
  : ps=procedure_specification
    { 
      temp = new ProcedureSubProgramBody();
      temp.setTheProcedureSpecification($ps.result); 
    }
    ( pa=procedure_annotation              { temp.setTheOptionalProcedureAnnotation($pa.result); }
    )?
    is='is'
    si=subprogram_implementation
    { 
      temp.setTheSubProgramImplementation($si.result); 
      result = temp;
      
      RegionSelection rs = createRS($ps.result.getTheOptionalRegionSelection().getTheStartCaret(),
                                    $si.result.getTheOptionalRegionSelection().getTheEndCaret());
      result.setTheOptionalRegionSelection(rs);
    }
  | fs=function_specification
    { 
      temp2 = new FunctionSubProgramBody();
      temp2.setTheFunctionSpecification($fs.result);
    }
    ( fa=function_annotation               { temp2.setTheOptionalFunctionAnnotation($fa.result); }
    )?
    is='is'
    si=subprogram_implementation
    { 
      temp2.setTheSubProgramImplementation($si.result); 
      result = temp2;
      
      RegionSelection rs = createRS($fs.result.getTheOptionalRegionSelection().getTheStartCaret(), 
                                    $si.result.getTheOptionalRegionSelection().getTheEndCaret());
      result.setTheOptionalRegionSelection(rs);
    }
  ;

subprogram_implementation returns [ SubProgramImplementation result = null ]
@init {
StatementSubProgramImplementation temp;
CodeSubProgramImplementation temp2;
}
  : dp=declarative_part
    beginLoc='begin' sos=sequence_of_statements
    'end' d=designator semiLoc=';'
    { 
      temp = new StatementSubProgramImplementation();
      temp.setTheDeclarativePart($dp.result);
      temp.setTheStatementList($sos.result);
      temp.setTheIDString($d.result); 
      result = temp;

      result.setTheOptionalRegionSelection(createRS($dp.result.getTheOptionalRegionSelection().getTheStartCaret(), $semiLoc));
    }
  | beginLoc='begin' ci=code_insertion
    'end' d=designator semiLoc=';'
    {
      temp2 = new CodeSubProgramImplementation();
      temp2.setTheQualifiedExps($ci.result);
      temp2.setTheIDString($d.result);
      result = temp2; 
      
      this.errors.add(MessageFactory.newWarningMessage(input.getSourceName(), $beginLoc.line, $beginLoc.pos, "subprogram implementation selection does not include the declarative part"));
      result.setTheOptionalRegionSelection(createRS($beginLoc, $semiLoc));
    }
  ;

code_insertion returns [ ArrayList<QualifiedExp> result = new ArrayList<QualifiedExp>() ]
  : ( cs=code_statement                    { result.add($cs.result); }
    )+
  ;


// SPARK95 6.4
procedure_call_statement returns [ ProcedureCallStatement result = new ProcedureCallStatement() ]
@after{
  RegionSelection rs = createRS($pn.result.getTheOptionalRegionSelection().getTheStartCaret(), $semiLoc);
  result.setTheOptionalRegionSelection(rs);
}
  : pn=procedure_name                      { result.setTheName($pn.result); }
// ambiguous (name eats parameter as indexed component)
//    ( app=actual_parameter_part            { result.setTheOptionalParameterAssociationList($app.result); }
//    )?
    semiLoc=';'
  ;

// added
procedure_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;

/* left-refactored in name
function_call
  : function_name
    actual_parameter_part?
  ;
*/

// added
function_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;

actual_parameter_part returns [ParameterAssociationList result = null ]
@after{result.setTheOptionalRegionSelection(createRS($firstParen, $lastParen));}
  : firstParen='(' parameter_association_list lastParen=')' { result = $parameter_association_list.result ; }
  ;

parameter_association_list returns [ ParameterAssociationList result = null ]
  : npal=named_parameter_association_list  { result = $npal.result; }
  | ppal=positional_parameter_association_list { result = $ppal.result; }
  ;

named_parameter_association_list returns [ NamedParameterAssociationList result = new NamedParameterAssociationList() ]
@init {
ArrayList<NamedParameterAssociation> temp = new ArrayList<NamedParameterAssociation>();
}
  : fpsn=formal_parameter_selector_name
    '=>' eap=explicit_actual_parameter     { NamedParameterAssociation temp2 = new NamedParameterAssociation(); temp2.setTheIDString($fpsn.result);
            temp2.setTheExp($eap.result); temp.add(temp2); }
    ( ',' 
      fpsn=formal_parameter_selector_name
      '=>' eap=explicit_actual_parameter   { NamedParameterAssociation temp3 = new NamedParameterAssociation(); temp3.setTheIDString($fpsn.result);
            temp3.setTheExp($eap.result); temp.add(temp3); }
    )*                                     { result.setTheParameterAssociations(temp); }
  ;

// added
formal_parameter_selector_name returns [ String result = null ]
  : sn=selector_name                       { IDName id = $sn.result; result = id.getTheIDString(); }
  ;

positional_parameter_association_list returns [ PositionalParameterAssociationList result = new PositionalParameterAssociationList() ]
@init {
ArrayList<Exp> temp = new ArrayList<Exp>();
}
  : eap=explicit_actual_parameter          { temp.add($eap.result); }
    ( ',' eap=explicit_actual_parameter    { temp.add($eap.result); }
    )*                                     { result.setTheParameterAssociations(temp); }
  ;

// modified
explicit_actual_parameter returns [ Exp result = null ]
  : e=expression                           { result = $e.result; }
  // ambiguous | variable_name 
  ;


// SPARK95 6.5
return_statement returns [ ReturnStatement result = new ReturnStatement() ]
@after{
  result.setTheOptionalRegionSelection(createRS($returnLoc, $semiLoc));
}
  : returnLoc='return' e=expression semiLoc=';'              { result.setTheExp($e.result); }
  ;
  

// SPARK95 7.1
package_declaration returns [ PackageDeclaration result = new PackageDeclaration() ]
@after{
  Caret startCaret = null;
  if($ic.result != null){
    startCaret = $ic.result.get(0).getTheOptionalRegionSelection().getTheStartCaret();
  } else{
    startCaret = $ps.result.getTheOptionalRegionSelection().getTheStartCaret();
  }
  result.setTheOptionalRegionSelection(createRS(startCaret, $semiLoc));
}
  : ( ic=inherit_clause                    { result.setTheOptionalInheritClauses($ic.result); }
    )?
    ps=package_specification               { result.setThePackageSpecification($ps.result); }
    semiLoc=';'
  ;

private_package_declaration returns [ PackageDeclaration result = new PackageDeclaration() ]
  : ( ic=inherit_clause                    { result.setTheOptionalInheritClauses($ic.result); }
    )?
    'private' ps=package_specification     { result.setThePackageSpecification($ps.result); 
                                             result.setThePrivateFlag(true); }
    ';'  
  ;

// modified
package_specification returns [ PackageSpecification result = new PackageSpecification() ]
@after{
  result.setTheOptionalRegionSelection(createRS($packageLoc, $endName.result.getTheOptionalRegionSelection().getTheEndCaret()));
}
  : packageLoc='package' 
    dpun=defining_program_unit_name        { result.setTheName($dpun.result); }
    pa=package_annotation                  { result.setThePackageAnnotation($pa.result); }
    'is'
    vp=visible_part                        { if (!$vp.declaration.isEmpty()) {
                                               result.setTheOptionalVisiblePartDeclaration($vp.declaration); 
                                             }
                                             if (!$vp.item.isEmpty()) {
                                               result.setTheOptionalVisiblePartDeclarativePartMember($vp.item); 
                                             }
                                           }
    ( 'private' pp=private_part            { if (!$pp.declaration.isEmpty()) {
                                               result.setTheOptionalPrivatePartDeclaration($pp.declaration); 
                                             }
                                             if (!$pp.item.isEmpty()) {
                                               result.setTheOptionalPrivatePartDeclarativePartMember($pp.item); 
                                             }
                                           }
    )?
    'end' endName=name 
    //( parent_unit_name '.' )?
    //IDENTIFIER
  ;

visible_part returns [ ArrayList<RenamingDeclaration> declaration = new ArrayList<RenamingDeclaration>(),
                 ArrayList<DeclarativePartMember> item = new ArrayList<DeclarativePartMember>() ]
  : ( rd=renaming_declaration              { $visible_part.declaration.add($rd.result); }
    )*
    ( pdi=package_declarative_item         { $visible_part.item.add($pdi.result); }
    )*
  ;

private_part returns [ ArrayList<RenamingDeclaration> declaration = new ArrayList<RenamingDeclaration>(),
                 ArrayList<DeclarativePartMember> item = new ArrayList<DeclarativePartMember>() ]
  : ( rd=renaming_declaration              { $private_part.declaration.add($rd.result); }
    )*
    ( pdi=package_declarative_item         { $private_part.item.add($pdi.result); }
    )*
  ;

package_declarative_item returns [ DeclarativePartMember result = null ]
  : bdi=basic_declarative_item             { result = $bdi.result; }
  | sd=subprogram_declaration              { result = $sd.result; }
  | esd=external_subprogram_declaration    { result = $esd.result; }
  ;


// SPARK95 7.1.1
inherit_clause returns [ ArrayList<Name> result = new ArrayList<Name>() ]
  : 
    'inherit'
    pn=package_name                        { result.add($pn.result); }
    ( ',' pn=package_name                  { result.add($pn.result); }
    )*
    ';'
  ;

// SPARK95 7.1.2
package_annotation returns [ PackageAnnotation result = new PackageAnnotation() ]

  : ( ovc=own_variable_clause              { 
                                             result.setTheOptionalOwnVariables($ovc.result);
                                             result.setTheOptionalOwnVariablesRegionSelection($ovc.rs);
                                           }
      ( is=initialization_specification    { result.setTheOptionalInitializeVariables($is.result); }
      )? 
     )?
  ;

// SPARK95 7.1.3
// own_variable_clause ::= --# own_variable_specification {own_variable_specification}
own_variable_clause returns [ ArrayList<OwnVariableSpecification> result = null, 
                              RegionSelection rs = null  ]
@after{
  if($own_variable_clause.result != null){
    OwnVariableSpecification last = $own_variable_clause.result.get($own_variable_clause.result.size() - 1);
    Caret lastCaret = last.getTheOptionalRegionSelection().getTheEndCaret();
    Caret startCaret = createCaret($own);
    $own_variable_clause.rs=createRS(startCaret, lastCaret);
  }
}
  : 
    own='own' ovs1=own_variable_specification { $own_variable_clause.result = new ArrayList<OwnVariableSpecification>(); 
                                            $own_variable_clause.result.add($ovs1.result);
                                          }
         (ovs2=own_variable_specification { $own_variable_clause.result.add($ovs2.result);
                                          })* 
  ;

own_variable_specification returns [ OwnVariableSpecification result = null]
@after{
  if($own_variable_specification.result != null){
    Caret startCaret = $ovl.result.get(0).getTheOptionalRegionSelection().getTheStartCaret();
    result.setTheOptionalRegionSelection(createRS(startCaret, createCaret($endloc)));
  }
}
  : ovl=own_variable_list (':' n=name)? endloc=';' { $own_variable_specification.result = new OwnVariableSpecification(); 
                                                     $own_variable_specification.result.setTheOptionalSubtypeMark($n.result); 
                                                     $own_variable_specification.result.setTheOwnVariables(ovl); }
  ;
  
own_variable_list returns [ ArrayList<OwnVariable> result = new ArrayList<OwnVariable>() ] 
  : m=own_mode ov=own_variable                 { OwnVariable temp = new OwnVariable();
                                                 temp.setTheMode($m.result);
                                                 temp.setTheIDString($ov.result.getTheIDString());
                                                 Caret startCaret = $m.result != Mode.NONE ? $m.theCaret
                                                                                           : $ov.result.getTheOptionalRegionSelection().getTheStartCaret();
                                                 Caret endCaret = $ov.result.getTheOptionalRegionSelection().getTheEndCaret();
                                                 temp.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
                                                 $own_variable_list.result.add(temp); 
                                               }
    ( ',' m2=own_mode ov2=own_variable         { OwnVariable temp = new OwnVariable();  
                                                 temp.setTheMode($m2.result); 
                                                 temp.setTheIDString($ov2.result.getTheIDString()); 
                                                 Caret startCaret = $m2.result != Mode.NONE ? $m2.theCaret
                                                                                            : $ov2.result.getTheOptionalRegionSelection().getTheStartCaret();
                                                 Caret endCaret = $ov2.result.getTheOptionalRegionSelection().getTheEndCaret();
                                                 temp.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
                                                 $own_variable_list.result.add(temp); 
                                               }
    )*
  ;

own_mode returns [ Mode result = Mode.NONE,
                   Caret theCaret = null ]
  : (
         in='in'  {$own_mode.result = Mode.IN; $own_mode.theCaret = createCaret($in);} 
      | out='out' {$own_mode.result=Mode.OUT; $own_mode.theCaret = createCaret($out);}
     )?
  ;

own_variable returns [ IDName result = null ]
  : dn=direct_name                         { result = $dn.result; }
  ;


// SPARK95 7.1.4
initialization_specification returns [ ArrayList result = null ]
  : 
    'initializes'
    ovl=own_variable_list ';'              { result = $ovl.result; }
  ;

// SPARK95 7.2 (modified)
package_body returns [ PackageBody result = new PackageBody() ]
@init{ result.setTheRefinementClauses(new ArrayList<RefinementClause>()); }
@after{ result.setTheOptionalRegionSelection(createRS($packageLoc, $semiLoc)); }
  : packageLoc='package' 'body'
    dpun=defining_program_unit_name        { result.setTheName($dpun.result); }
    ( rd=refinement_definition             { result.setTheRefinementClauses($rd.result);
                                             result.setTheOptionalRefinementClausesRegionSelection($rd.rs);
                                           }
    )?
    'is'
    pi=package_implementation              { result.setThePackageImplementation($pi.result); }
    'end' n=name
    //( parent_unit_name '.' )?
    //IDENTIFIER 
    semiLoc=';'
  ;

package_implementation returns [PackageImplementation result = new PackageImplementation() ]
  : dp=declarative_part                    { result.setTheDeclarativePart($dp.result); }
    ( 'begin' 
      pi=package_initialization            { result.setTheOptionalStatementList($pi.result); }
    )?
  ;

package_initialization returns [ StatementList result = null ]
  : sos=sequence_of_statements             { result = $sos.result; }
  ;


// SPARK95 7.2.1
refinement_definition returns [ ArrayList<RefinementClause> result = new ArrayList<RefinementClause>(),
                                RegionSelection rs = null ]
@after{
  if(!$refinement_definition.result.isEmpty()){
    $refinement_definition.rs = createRS($own, $semiloc);
  }
}
  : 
    own='own' rc=refinement_clause             { $refinement_definition.result.add($rc.result); }             
        ( '&' rc=refinement_clause             { $refinement_definition.result.add($rc.result); }
    )* semiloc=';'
  ;

refinement_clause returns [ RefinementClause result = new RefinementClause() ]
  : s=subject 'is' cl=constituent_list
    { 
      result.setTheSubject($s.result);

      Caret startCaret = $s.result.getTheOptionalRegionSelection().getTheStartCaret();
      Caret endCaret =  $cl.result.get($cl.result.size() - 1).getTheOptionalRegionSelection().getTheEndCaret();
      result.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
      
      result.setTheConstituents($cl.result);
    }
  ;


subject returns [ IDName result = null ]
  : dn=direct_name { 
      result=$dn.result;
    }
  ;

constituent_list returns [ ArrayList<Constituent> result = new ArrayList<Constituent>() ]
  : m=own_mode c=constituent
    { 
      Constituent temp = new Constituent(); 
      temp.setTheMode($m.result);
      temp.setTheName($c.result);

      Caret startCaret = $m.result != Mode.NONE ? $m.theCaret : $c.result.getTheOptionalRegionSelection().getTheStartCaret();
      Caret endCaret = $c.result.getTheOptionalRegionSelection().getTheEndCaret();
      temp.setTheOptionalRegionSelection(createRS(startCaret, endCaret));

      result.add(temp); 
                                   }
    ( ',' m=own_mode c=constituent 
          { 
            Constituent temp = new Constituent(); 
            temp.setTheMode($m.result);
            temp.setTheName($c.result);

            Caret startCaret = $m.result != Mode.NONE ? $m.theCaret : $c.result.getTheOptionalRegionSelection().getTheStartCaret();
            Caret endCaret = $c.result.getTheOptionalRegionSelection().getTheEndCaret();
            temp.setTheOptionalRegionSelection(createRS(startCaret, endCaret));

            result.add(temp);
          }
    )*
  ;

// modified
constituent returns [ Name result = null ]
  : n=name { result = $n.result; }
  // ( package_name '.' )? direct_name
  ;


// SPARK95 7.3
private_type_declaration returns [ PrivateTypeDeclaration result = new PrivateTypeDeclaration() ]
@after{ result.setTheOptionalRegionSelection(createRS($typeLoc, $semiLoc));}
  : typeLoc='type' did=defining_identifier         { result.setTheIDString($did.result); }
    'is' 
    ( 'tagged'                             { result.setTheTaggedFlag(true); }
    )? 
    ( 'limited'                            { result.setTheLimitedFlag(true); }
    )?
    'private' 
    semiLoc=';'
  ;

private_extension_declaration returns [ PrivateExtensionDeclaration result = new PrivateExtensionDeclaration() ] 
  : 'type' did=defining_identifier         { result.setTheIDString($did.result); }
    'is' 'new'
    asi=ancestor_subtype_indication        { result.setTheSubTypeIndication($asi.result); }
    'with' 'private' ';'
  ;

ancestor_subtype_indication returns [ SubTypeIndication result = null ]
  : si=subtype_indication                  { result = $si.result; }
  ;


// SPARK95 8.4
use_type_clause returns [ UseTypeClause result = new UseTypeClause() ]
@init {
ArrayList<Name> temp = new ArrayList<Name>();
}
  : 'use' 'type' sm=subtype_mark           { temp.add($sm.result); }
    ( ',' sm=subtype_mark                  { temp.add($sm.result); }
    )* ';'                                 { result.setTheNames(temp); }
  ;


// SPARK95 8.5
renaming_declaration returns [ RenamingDeclaration result = null ]
  : prd=package_renaming_declaration       { result = $prd.result; }
  | srd=subprogram_renaming_declaration    { result = $srd.result; }
  ;


// SPARK95 8.5.1 - 8.5.2


// SPARK95 8.5.3 (modified)
package_renaming_declaration returns [ PackageRenamingDeclaration result = new PackageRenamingDeclaration() ]
  : 'package' 
    dpun=defining_program_unit_name        { result.setThePackageName($dpun.result); }
    'renames' n=name                       { result.setTheRenamedName($n.result); }
    //parent_unit_name
    //'.' package_direct_name 
    ';'
  ;

// added
package_direct_name returns [ String result = null ]
  : dn=direct_name                         { result = $dn.result.getTheIDString(); }
  ;


// SPARK95 8.5.4 (modified)
subprogram_renaming_declaration returns [ SubProgramRenamingDeclaration result = null ]
  : 'function' dos=defining_operator_symbol
    fp=formal_part 'return' sm=subtype_mark
    'renames' pn=package_name '.' 
    os=operator_symbol ';'                 { FunctionRenamingDeclaration temp = new FunctionRenamingDeclaration();
                                             temp.setTheDefiningStringLiteral($dos.result);
                                             temp.setTheParameterSpecifications($fp.result);
                                             temp.setTheReturnName($sm.result);
                                             temp.setThePackageName($pn.result);
                                             temp.setTheStringLiteral($os.result);
                                             result = temp; }
  | fs=function_specification
    'renames' n=name                       { FunctionSpecificationRenamingDeclaration temp2 = new FunctionSpecificationRenamingDeclaration();
                                             temp2.setTheFunctionSpecification($fs.result);
                                             temp2.setTheName($n.result);
                                             result = temp2; }
    //package_name '.'
    //function_direct_name 
    ';'
  | ps=procedure_specification             
    'renames' n=name                       { ProcedureSpecificationRenamingDeclaration temp3 = new ProcedureSpecificationRenamingDeclaration();
                                             temp3.setTheProcedureSpecification($ps.result);
                                             temp3.setTheName($n.result); 
                                             result = temp3; } 
    //package_name '.'
    //procedure_direct_name 
    ';'
  ;

// added
function_direct_name returns [ String result = null ]
  : dn=direct_name                         { result = $dn.result.getTheIDString(); }
  ;

// added
procedure_direct_name returns [ String result = null ]
  : dn=direct_name                         { result = $dn.result.getTheIDString(); }
  ;


// SPARK95 8.5.5


// SPARK95 9.1


// SPARK95 10.1.1
compilation returns [ Compilation result = new Compilation() ]
@init {
ArrayList<CompilationUnit> temp = new ArrayList<CompilationUnit>();
}
  : ( cu=compilation_unit                  { temp.add($cu.result); }
    )*                                     { if (!temp.isEmpty()) { 
                                               result.setTheOptionalCompilationUnits(temp);
                                             }
                                           }
  ;

compilation_unit returns [ CompilationUnit result = null ]
  : cc=context_clause li=library_item      { LibraryCompilationUnit temp = new LibraryCompilationUnit();
                                             temp.setTheContextClause($cc.result);
                                             temp.setTheLibraryItem($li.result);
                                             result = temp; }
  | cc=context_clause s=subunit            { SubUnitCompilationUnit temp = new SubUnitCompilationUnit();
                                             temp.setTheContextClause($cc.result);
                                             temp.setTheName($s.name);
                                             temp.setTheProperBody($s.body);
                                             result = temp; }
  ;

library_item returns [ LibraryItem result = null ]
  : lud=library_unit_declaration           { result = $lud.result; }
  | lub=library_unit_body                  { result = $lub.result; }
  ;

library_unit_declaration returns [ LibraryUnitDeclaration result = null ]
  : pd=package_declaration                 { result = $pd.result; }
  | ppd=private_package_declaration        { result = $ppd.result; }
  | mp=main_program                        { result = $mp.result; }
  ;

library_unit_body returns [ LibraryUnitBody result = new LibraryUnitBody() ]
  : pb=package_body                        { result.setThePackageBody($pb.result); }
  ;

parent_unit_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;

main_program returns [ MainProgram result = new MainProgram() ]
@after{
  Caret startCaret = null;
  if(result.getTheOptionalInheritClauses() != null){
    startCaret = result.getTheOptionalInheritClauses().get(0).getTheOptionalRegionSelection().getTheStartCaret();
  } else{
    startCaret = $mpaLoc.rs.getTheStartCaret();
  }
  Caret endCaret = $sb.result.getTheOptionalRegionSelection().getTheEndCaret();
  result.setTheOptionalRegionSelection(createRS(startCaret, endCaret));
}
  : ( ic=inherit_clause                    { result.setTheOptionalInheritClauses($ic.result); }
    )?
    mpaLoc=main_program_annotation
    sb=subprogram_body                     { result.setTheSubProgramBody($sb.result); }
  ;

main_program_annotation returns [ RegionSelection rs = null ]
@after{ rs = createRS($mpLoc, $semiLoc); }
  : 
    mpLoc='main_program' semiLoc=';'
  ;


// SPARK95 10.1.2
context_clause returns [ ContextClause result = new ContextClause() ]
@init {
ArrayList<ContextItem> temp = new ArrayList<ContextItem>();
}
  : ( ci=context_item                      { temp.add($ci.result); }
    )*                                     { if (!temp.isEmpty()) {
                                               result.setTheOptionalContextItems(temp); 
                                             }
                                           }
  ;

context_item returns [ ContextItem result = null ]
  : wc=with_clause                         { result = $wc.result; } 
  | utc=use_type_clause                    { result = $utc.result; }
  ;

with_clause returns [ WithClause result = new WithClause() ]
@init {
ArrayList<Name> temp = new ArrayList<Name>();
}
  : 'with' lpn=library_package_name        { temp.add($lpn.result); }
    ( ',' lpn=library_package_name         { temp.add($lpn.result); }
    )*
    ';'                                    { result.setTheNames(temp); }
  ;

// added
library_package_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;
  

// SPARK95 10.1.3
body_stub returns [ BodyStub result = null ]
  : sbs=subprogram_body_stub               { result = $sbs.result; }
  | pbs=package_body_stub                  { result = $pbs.result; }
  ;

subprogram_body_stub returns [ SubProgramBodyStub result = null ]
@init {
ProcedureBodyStub temp = null;
FunctionBodyStub temp2 = null;
}
  : ps=procedure_specification             { temp = new ProcedureBodyStub();
                                                   temp.setTheProcedureSpecification($ps.result); }
    ( pa=procedure_annotation              { temp.setTheOptionalProcedureAnnotation($pa.result); }
    )?
    'is' 'separate' ';'                    { result = temp; }
  | fs=function_specification              { temp2 = new FunctionBodyStub();
                                                   temp2.setTheFunctionSpecification($fs.result); }
    ( fa=function_annotation               { temp2.setTheOptionalFunctionAnnotation($fa.result); }
    )?
    'is' 'separate' ';'                    { result = temp2; }
  ;

package_body_stub returns [ PackageBodyStub result = new PackageBodyStub() ]
  : 'package' 'body'
    did=defining_identifier                { result.setTheIDString($did.result); }
    'is' 'separate' ';'
  ;

subunit returns [ Name name = null, ProperBody body = null ] 
  : 'separate' '('
    pun=parent_unit_name                   { $subunit.name = $pun.result; }
    ')' pb=proper_body                     { $subunit.body = $pb.result; }
  ;


// SPARK95 11.1 - 12.2


// SPARK95 12.3
generic_instantiation returns [ GenericInstantiation result = new GenericInstantiation() ]
  : 'function' did=defining_designator     { result.setTheIDString($did.result); }
    'is' 'new'
    gfn=generic_function_name              { result.setTheName($gfn.result); }
    ( gap=generic_actual_part              { result.setTheOptionalGenericAssociations($gap.result); }
    )?
  ;

// added
generic_function_name returns [ Name result = null ]
  : n=name                                 { result = $n.result; }
  ;

generic_actual_part returns [ ArrayList<GenericAssociation> result = new ArrayList<GenericAssociation>() ]
  : '(' ga=generic_association             { result.add($ga.result); }
    ( ',' ga=generic_association           { result.add($ga.result); }
    )* ')'
  ;

generic_association returns [ GenericAssociation result = new GenericAssociation() ]
  : ( gfpsn=generic_formal_parameter_selector_name { result.setTheOptionalIDString($gfpsn.result); }
      '=>' )?
    egap=explicit_generic_actual_parameter { result.setTheName($egap.result); }
  ;

// added
generic_formal_parameter_selector_name returns [ String result = null ]
  : sn=selector_name                       { IDName id = $sn.result; result = id.getTheIDString(); }
  ;

explicit_generic_actual_parameter returns [ Name result = null ]
  : sm=subtype_mark                        { result = $sm.result; }
  ;


// SPARK95 12.4 - 12.7

/* Representation clauses may appear in SPARK texts. The SPARK Examiner 
 * checks their syntax, which must conform to the syntax rules given in 
 * Chapter 13 of the Ada LRM, but it ignores their semantics. A warning 
 * message to this effect is given whenever the SPARK Examiner encounters
 * a representation clause.
 */
 
// SPARK95 13.1
  
/* Note that the term representation_clause was replace by aspect_clause
 * in the Ada 2000 Corrigendum, however, the SPARK BNF still refers to
 * it as representation_clause
 */
representation_clause returns [ RepresentationClause result = null ]
  : 
    adc=attribute_definition_clause        { result = $adc.result; }
  | erc=enumeration_representation_clause  { result = $erc.result; }
  | rrc=record_representation_clause       { result = $rrc.result; }
  | ac=at_clause                           { result = $ac.result; }
  ;

// modified
local_name returns [ Name result = null ]
  : /* ambiguous
    direct_name
  | direct_name CHARACTER_LITERAL_OR_QUOTE attribute_designator
  | library_unit_name */
    n=name { result = $n.result; }
  ;

// added
//library_unit_name returns [ Name result = null ]
//  : n=name                                 { result = $n.result; }
//  ;


// SPARK95 13.3 (modified)
attribute_definition_clause returns [ AttributeDefinitionClause result = new AttributeDefinitionClause() ]
@after{ result.setTheOptionalRegionSelection(createRS($forLoc, $semiLoc)); }
  : forLoc='for' n=name                           { result.setTheName($n.result); } 
          /* ambiguous local_name CHARACTER_LITERAL_OR_QUOTE
    attribute_designator */
    'use' se=simple_expression semiLoc=';'         { result.setTheExp($se.result); }
  ;


// SPARK95 13.4
enumeration_representation_clause returns [ EnumerationRepresentationClause result = new EnumerationRepresentationClause() ]
@after{result.setTheOptionalRegionSelection(createRS($forLoc, $semiLoc));}
  : forLoc='for' fsln=first_subtype_local_name    { result.setTheName($fsln.result); }
    'use' ea=enumeration_aggregate semiLoc=';'     { result.setTheArrayAggregate($ea.result); }
  ;

// addded
first_subtype_local_name returns [ Name result = null ]
  : ln=local_name                          { result = $ln.result; }
  ;

enumeration_aggregate returns [ ArrayAggregate result = null ]
  : aa=array_aggregate                  { result = $aa.result; }
  ;


// SPARK95 13.5
at_clause returns [ AtClause result = new AtClause() ]
@after{result.setTheOptionalRegionSelection(createRS($forLoc, $semiLoc));}
  : forLoc='for' sn=simple_name                   { result.setTheName($sn.result); }
    'use' 'at' se=simple_expression        { result.setTheExp($se.result); }
    semiLoc=';'
  ;


// SPARK95 13.5.1
record_representation_clause returns [ RecordRepresentationClause result = new RecordRepresentationClause() ]
@init {
ArrayList<ComponentClause> temp = new ArrayList<ComponentClause>();
}
@after{result.setTheOptionalRegionSelection(createRS($forLoc, $semiLoc));}
  : forLoc='for' fsln=first_subtype_local_name    { result.setTheName($fsln.result); }
    'use' 'record' 
    ( mc=mod_clause                        { result.setTheOptionalExp($mc.result); }
    )?
    ( cc=component_clause                  { temp.add($cc.result); }
    )*
    'end' 'record' semiLoc=';'                     { if (!temp.isEmpty()) {
                                               result.setTheOptionalComponentClauses(temp);
                                             } 
                                           }
  ;

component_clause returns [ ComponentClause result = new ComponentClause() ]
  : cln=component_local_name 'at'
    p=position 'range'
    fb=first_bit '..' lb=last_bit ';'      { result.setTheName($cln.result); 
                                             result.setThePositionExp($p.result); 
                                             result.setTheFirstBitExp($fb.result);
                                             result.setTheLastBitExp($lb.result); }
  ;

// added
component_local_name returns [ Name result = null ]
  : ln=local_name                          { result = $ln.result; }
  ;

position returns [ Exp result = null ]
  : sse=static_simple_expression           { result = $sse.result; }
  ;

first_bit returns [ Exp result = null ]
  : sse=static_simple_expression           { result = $sse.result; }
  ;

last_bit returns [ Exp result = null ]
  : sse=static_simple_expression           { result = $sse.result; }
  ;

mod_clause returns [ Exp result = null ]
  : 'at' 'mod' se=simple_expression ';'    { result = $se.result; }
  ;


// SPARK95 13.8
code_statement returns [ QualifiedExp result = null ]
  : qe=qualified_expression ';'            { result = $qe.result; }
  ;



// Special Tokens

IMPORT
  : 'Import'                            { $type=IDENTIFIER; }
  ;

// ADA95 2.1
fragment
IDENTIFIER_LETTER
  : UPPER_CASE_IDENTIFIER_LETTER
  | LOWER_CASE_IDENTIFIER_LETTER
  ;

fragment
UPPER_CASE_IDENTIFIER_LETTER
  : 'A' .. 'Z'
  ;

fragment
LOWER_CASE_IDENTIFIER_LETTER
  : 'a' .. 'z'
  ;
  
fragment
DIGIT
  : '0' .. '9'
  ;
  

// SPARK95 2.3
IDENTIFIER
  : IDENTIFIER_LETTER 
    ( '_'? LETTER_OR_DIGIT )*
  ;


fragment
LETTER_OR_DIGIT
  : IDENTIFIER_LETTER
  | DIGIT
  ;


// SPARK95 2.4  
NUMERIC_LITERAL
  : DECIMAL_LITERAL
  | BASED_LITERAL
  ;


// SPARK95 2.4.1
fragment
DECIMAL_LITERAL
  : NUMERAL ( ( '.' DIGIT ) => '.' NUMERAL )? EXPONENT? 
  ;

fragment
NUMERAL
  : DIGIT ( '_'? DIGIT )*
  ;

fragment
EXPONENT
  : 'E' ( '+' | '-' )? NUMERAL
  ;


// SPARK95 2.4.2
fragment
BASED_LITERAL
  : BASE '#' BASED_NUMERAL '#' EXPONENT?
  ;

fragment
BASE
  : NUMERAL
  ;

fragment
BASED_NUMERAL
  : EXTENDED_DIGIT ( '_'? EXTENDED_DIGIT )*
  ;
  
fragment
EXTENDED_DIGIT
  : DIGIT | 'A' .. 'F'
  ;

// SPARK95 2.5 (modified)
CHARACTER_LITERAL_OR_QUOTE
  : ('\'' . '\'') => '\'' . '\''
  | '\''
  ;

// SPARK95 2.6 (modified)
STRING_LITERAL
  : '"' ( '""' | ~('"') )* '"'
  ;
  
SPARK_ANNOTATION
  : '--#'                               { $channel=SPARKParser.SPARK_CHANNEL; }
  ;

// SPARK95 2.7 (modified)
COMMENT
  : // an ugly guard to get around whitespace issues such as '-- #'.  The guard should
    // be read as
    //   "having 3 or more characters left implies that the 3rd character is not #"
    {!(input.index() + 3 < input.size()) || ((char) input.LA(3) != '#') }?=> 
    '--' 
    (
      ~('\r' | '\n')* // match any sequence of chars with the exception of carriage returns or new lines
      '\r'? '\n' // this is a little too strong as the file could end with " ... end procP; -- "
    )
    { $channel=HIDDEN; }
  ;


NEW_LINE 
  : ('\r' | '\n')                      { $channel=SPARKParser.NEWLINE_CHANNEL; }
  ;  
  
WS
  :  ( ' ' | '\t' 
     | '\u000C' )                { $channel=HIDDEN; }
  ;
