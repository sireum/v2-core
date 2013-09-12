/*
Copyright (c) 2011 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

grammar AntlrPilar;
options { backtrack=true; memoize=true; output=AST; }

tokens
{
  OPTION;
  LIST;
  MODEL;
  ANNOTATION;
  ANNOTATION_PARAM_IDED;
  PACKAGE;
  CONST;
  CONST_ELEMENT;
  ENUM;
  ENUM_ELEMENT;
  TYPEALIAS;
  RECORD;
  EXTENDCLAUSE_ELEMENT;
  ATTRIBUTE;
  ATTRIBUTE_FRAGMENT;
  GLOBAL;
  GLOBAL_FRAGMENT;
  PROCEDURE;
  PARAM;
  PARAM_VARIABLE;
  VSET;
  VSET_ELEMENT;
  EXTENSION;
  TYPEVAR;
  TYPE_EXT;
  TYPE_EXT_ATTRIBUTE_BINDING;
  ACTION_EXT;
  EXP_EXT;
  PROC_EXT;
  EXT_PARAM;
  EXT_PARAM_VARIABLE;
  BODY;
  LOCAL;
  LOCAL_FRAGMENT;
  LOCATION;
  EXP_GUARD;
  ELSE_GUARD;
  TRANSFORMATION;
  ASSIGN;
  LHS;
  RHS;
  ASSERT;
  ASSUME;
  THROW;
  START;
  ACTION_EXT_CALL;
  GOTO;
  RETURN;
  IF_JUMP;
  IF_THEN_JUMP;
  IF_ELSE_JUMP;
  SWITCH_JUMP;
  SWITCH_CASE_JUMP;
  SWITCH_DEFAULT_JUMP;
  CALL_JUMP;
  CATCH_CLAUSE;
  IF_EXP;
  IF_THEN_EXP;
  IF_ELSE_EXP;
  SWITCH_EXP;
  SWITCH_CASE_EXP;
  SWITCH_DEFAULT_EXP;
  BINARY;
  UNARY;
  CAST;
  INDEXING;
  ACCESS;
  CALL;
  TRUE;
  FALSE;
  NULL;
  HEX_LIT;
  DEC_LIT;
  OCT_LIT;
  STRING;
  TUPLE;
  NAME_EXP;
  NAME;
  TYPEVARID_TYPE;
  FLOAT;
  RATIONAL;
  CHAR;
  STRING;
  SYMBOL;
  TYPE_EXP;
  NLIST_RANGE;
  NLIST;
  NMULTI_ARRAY;
  NMULTI_ARRAY_FRAGMENT;
  NRECORD;
  ATTR_INIT;
  NSET;
  NFUNCTION;
  FUN_EXP;
  FUN;
  MAPPING;
  MATCHING;
  ANN_EXP;
  INT;
  NAME_TYPE;
  TYPE_TUPLE;
  TUPLE_TYPE;
  FUN_TYPE;
  PROCEDURE_TYPE;
  TYPE_PARAM;
  ANNOTATED_TYPE;
  PROCEDURE_TYPE_PARAM_VARIABLE;
  FUNCTION_TYPE;
  RELATION_TYPE;
  LET_EXP;
  LET_BINDING;
  NEW_EXP;
  NEW_MULTI_ARRAY_FRAGMENT;
  TYPE;
  ARRAY_FRAGMENT;
  LIST_FRAGMENT;
  MULTIARRAY_FRAGMENT;
  SET_FRAGMENT;
  TYPEVAR_TUPLE;
  RAW;
  ANN_TYPE;
}

@header {
/*
Copyright (c) 2011 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser;

}

@lexer::header {
/*
Copyright (c) 2011 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser;

}

modelFile
	: model EOF                            -> model
	;

annotationFile
	: annotation EOF                       -> annotation
	;
    
packageDeclarationFile
	: packageDeclaration EOF               -> packageDeclaration
	;

packageElementFile
	: packageElement EOF                   -> packageElement
	;

locationFile
	: location EOF                         -> location
	;

transformationFile
	: transformation EOF                   -> transformation
	;

actionFile
	: action EOF                           -> action
	;

jumpFile
	: jump EOF                             -> jump
	;

expFile
	: exp EOF                              -> exp
	;

typeFile
	: type EOF                             -> type
	;

model
	: annotationList
	  packageElement*
	  packageDeclaration*                  
	  EOF                                  -> ^(MODEL annotationList ^(LIST packageElement*) ^(LIST packageDeclaration*))
	;

annotationList
	:                                      -> ^(LIST)
	| annotation+                          -> ^(LIST annotation+)
	;

angleGroupedAnnotationList
	:                                      -> ^(LIST)
	| '<' annotation+ '>'                  -> ^(LIST annotation+)
	;

parenGroupedAnnotationList
	:                                      -> ^(LIST)
	| '(' annotation+ ')'                  -> ^(LIST annotation+)
	;

annotation
	: t='@' ID annotationParams?           -> ^(ANNOTATION[$t] ID ^(OPTION annotationParams?))
	;
    
annotationParams
	: t='(' annotationParam
	      ( ',' annotationParam )* ')'     -> ^(LIST[$t] annotationParam+)
	| exp ( ',' exp )*                     -> ^(LIST exp+)
	;
	
annotationParam
	: t=ID '=' ( annotation | exp )        -> ^(ANNOTATION_PARAM_IDED[$t] ^(OPTION ID?) annotation? exp?)
	| annotation                           -> ^(ANNOTATION_PARAM_IDED ^(OPTION) annotation)
	| exp                                  -> ^(ANNOTATION_PARAM_IDED ^(OPTION) exp)
	;

packageDeclaration
	: t='package' name annotationList ';'
	  packageElement*                      -> ^(PACKAGE[$t] name annotationList ^(LIST packageElement*))
	;
	
packageElement
	: constDeclaration
	| enumDeclaration
	| typealiasDeclaration
	| recordDeclaration
	| globalVarsDeclaration
	| procedureDeclaration
	| virtualSetDeclaration
	| funexpDeclaration
	| extDeclaration
	;

constDeclaration
	: t='const' ID annotationList
	  '{' constElement+ '}'                -> ^(CONST[$t] ID annotationList ^(LIST constElement+))
	;

constElement
	: t=ID '='
	  exp annotationList ';'               -> ^(CONST_ELEMENT[$t] ID exp annotationList)
	;
	
enumDeclaration
	: t='enum' ID annotationList
	  '{' enumElement
	  ( ',' enumElement )* '}'             -> ^(ENUM[$t] ID annotationList ^(LIST enumElement+))
	;

enumElement
	: t=ID annotationList                  -> ^(ENUM_ELEMENT[$t] ID annotationList)
	;

typealiasDeclaration
	: t='typealias' type ID  
	   annotationList ';'                  -> ^(TYPEALIAS[$t] type ID annotationList)
	;

recordDeclaration
	: t='record'
	  ID typeVarTuple? annotationList
	  extendClause?
	  '{' attribute* '}'                   -> ^(RECORD[$t] ID ^(OPTION typeVarTuple?) annotationList ^(OPTION extendClause?) ^(LIST attribute*))
	;
	
typeVarTuple
	: t='<' typeVar ( ',' typeVar )* '>'   -> ^(TYPEVAR_TUPLE[$t] ^(LIST typeVar+))
	;

typeVar
	: t=TYPEVARID annotationList           -> ^(TYPEVAR[$t] TYPEVARID annotationList)
	;

extendClause
	: t='extends' extendClauseElement
	  ( ',' extendClauseElement )*         -> ^(LIST[$t] extendClauseElement+)
	;

extendClauseElement
	: name typeTuple? annotationList       -> ^(EXTENDCLAUSE_ELEMENT name ^(OPTION typeTuple?) annotationList)
	;
	
typeTuple
	: t='<' type ( ',' type )* '>'         -> ^(TYPE_TUPLE[$t] ^(LIST type+))
	;

attribute
	: type? attributeFragment
	  ( ',' attributeFragment )* ';'       -> ^(ATTRIBUTE ^(OPTION type?) ^(LIST attributeFragment+))
	;

attributeFragment
	: t=ID annotationList ( '=' name )?    -> ^(ATTRIBUTE_FRAGMENT[$t] ID annotationList ^(OPTION name?))
	;

globalVarsDeclaration
	: 'global' globalVarDeclaration+       -> globalVarDeclaration+
	;

globalVarDeclaration
	: type? globalVarFragment
	  ( ',' globalVarFragment )* ';'       -> ^(GLOBAL ^(OPTION type?) ^(LIST globalVarFragment+))
	;

globalVarFragment
	: t=GLOBALID annotationList            -> ^(GLOBAL_FRAGMENT[$t] GLOBALID annotationList)
	;
	
procedureDeclaration
	: t='procedure' typeVarTuple?
	  type? 
	  p=ID
	  ( '(' 
	    ( paramVariable
	    | ( paramList 
	          ( ',' paramVariable )?)?
	    ) 
	    ')' 
	  )? annotationList
	  body                                 -> ^(PROCEDURE[$t] ^(OPTION typeVarTuple?) ^(OPTION type?) $p ^(LIST paramList? paramVariable?) annotationList body)
	;
	
paramList
	: param ( ',' param )*                 -> param+
	;

param
	: type? ID annotationList              -> ^(PARAM ^(OPTION type?) ID annotationList)
	;

paramVariable
	: type? ID '...' annotationList        -> ^(PARAM_VARIABLE ^(OPTION type?) ID annotationList)
	;
	
virtualSetDeclaration
	: t='vset' ID annotationList
	  '{'
	  vsetElement ( ',' vsetElement )*
	  '}'                                  -> ^(VSET[$t] ID annotationList ^(LIST vsetElement+))
	;

vsetElement
	: name annotationList                  -> ^(VSET_ELEMENT name annotationList)
	;

funexpDeclaration
	: t='fun' ID annotationList '='
	  funExp                               -> ^(FUN[$t] ID annotationList funExp)
	;

extDeclaration
	: t='extension' typeVarTuple?
	  ID annotationList
	  '{' extElement+ '}'                  -> ^(EXTENSION[$t] ^(OPTION typeVarTuple?) ID annotationList ^(LIST extElement+))
	;
	
extElement
	: typeExtension 
	| actionExtension 
	| expExtension
	| procExtension
	;

typeExtension
	: t='typedef' typeVarTuple?
	  ID annotationList
	  ( ';'
	  | extendClause?
	    '{' typeExtensionMember* '}' )     -> ^(TYPE_EXT[$t] ^(OPTION typeVarTuple?) ID annotationList ^(OPTION extendClause?)  ^(LIST typeExtensionMember*))
	;
	
typeExtensionMember
	: typeExtensionAttributeBinding
	| actionExtension
	| expExtension
	| procExtension
	;

typeExtensionAttributeBinding
	: id1=ID '=' id2=ID
	  annotationList ';'                   -> ^(TYPE_EXT_ATTRIBUTE_BINDING[$id1] $id1 $id2 annotationList)
	;

actionExtension
	: t='actiondef' typeVarTuple? ID
	  ( '(' extParamList ')' )?
	  annotationList ';'                   -> ^(ACTION_EXT[$t] ^(OPTION typeVarTuple?) ID ^(OPTION extParamList?) annotationList)
	;

expExtension
	: t='expdef' typeVarTuple? type? ID 
	  ( '(' extParamList ')' )?
	  annotationList ';'                   -> ^(EXP_EXT[$t] ^(OPTION typeVarTuple?) ^(OPTION type?) ID ^(OPTION extParamList?) annotationList)
	;

procExtension
	: t='procdef' typeVarTuple? type? ID
	  ( '(' extParamList ')' )?
	  annotationList ';'                   -> ^(PROC_EXT[$t] ^(OPTION typeVarTuple?) ^(OPTION type?) ID ^(OPTION extParamList?) annotationList)
	;
	
extParamList
	: extParamVariable                     -> ^(LIST extParamVariable?)
	| extParam ( ',' extParam )*
	  extParamVariable?                    -> ^(LIST extParam+ extParamVariable?)
	;

extParam
	: type? ID? annotationList             -> ^(EXT_PARAM ^(OPTION type?) ^(OPTION ID?) annotationList)
	;

extParamVariable
	: type? ID? 
	  '...' annotationList                 -> ^(EXT_PARAM_VARIABLE ^(OPTION type?) ^(OPTION ID?) annotationList)
	;
	
body
	: t='{' localVarsDeclaration? 
	  location+ 
	  catchClause* '}'                     -> ^(BODY[$t] ^(OPTION localVarsDeclaration?) ^(LIST location+) ^(LIST catchClause*))
	;

localVarsDeclaration
	: t='local' localVarDeclaration+       -> ^(LIST[$t] localVarDeclaration+)
	;

localVarDeclaration
	: type? localVarFragment 
	  ( ',' localVarFragment )* ';'        -> ^(LOCAL ^(OPTION type?) ^(LIST localVarFragment+))
	;

localVarFragment
	: t=ID annotationList                  -> ^(LOCAL_FRAGMENT[$t] ID annotationList)
	;
        	
location
	: t=LOCID angleGroupedAnnotationList 
	  ( transformation 
	    ( '|' transformation )* )?          
	                                       -> ^(LOCATION[$t] LOCID angleGroupedAnnotationList ^(LIST transformation*))
	;

transformation
	: parenGroupedAnnotationList guard? 
	  seqTransformation                    -> ^(TRANSFORMATION parenGroupedAnnotationList ^(OPTION guard?) seqTransformation)
	;
	
guard
	: exp annotationList '+>'              -> ^(EXP_GUARD exp annotationList)
	| t='else' annotationList              -> ^(ELSE_GUARD[$t] annotationList)
	;

lhsList
	: lhs ( ',' lhs )*                     -> ^(LIST lhs+)
	;

lhs
	: exp annotationList                   -> ^(LHS exp annotationList)
	;
	
seqTransformation
	: action* jump?                        ->  ^(LIST action*) ^(OPTION jump?)
	;

action
	: t='assert' exp annotationList ';'    -> ^(ASSERT[$t] exp annotationList)
	| t='assume' exp annotationList ';'    -> ^(ASSUME[$t] exp annotationList)
	| t='throw' exp annotationList ';'     -> ^(THROW[$t] exp annotationList)
	| assignment 
	| startThread
	| extCall
	;

assignment
	: lhsList 
	  ( t=':=' 
	  | t=ACTION_EXT_OP)
	  rhs ( ',' rhs )*
	  annotationList ';'                   -> ^(ASSIGN lhsList $t ^(LIST rhs+) annotationList)
	;

rhs
	: annotationList exp                   -> ^(RHS annotationList exp)
	;

startThread
	: t='start' name
	  ( '[' num=exp ']' )?
	  (arg=exp)?
	   annotationList ';'                  -> ^(START[$t] name ^(OPTION $num?) ^(OPTION $arg?) annotationList) 
	;

extCall 
	: exp annotationList ';'               -> ^(ACTION_EXT_CALL exp annotationList)
	;

jump
	: gotoJump
	| returnJump
	| ifThenJump
	  ( 'else' ifThenJump )*
	  ifElseJump? 
	  annotationList ';'                   -> ^(IF_JUMP ^(LIST ifThenJump+) ^(OPTION ifElseJump?) annotationList)
	| t='switch' exp switchCaseJump+      
	  switchDefaultJump?
	  annotationList ';'                   -> ^(SWITCH_JUMP[$t] exp ^(LIST switchCaseJump+) ^(OPTION switchDefaultJump?) annotationList)
	| t='call'
	  ( nameExp ( ',' nameExp )* ':=' )?
	  exp annotationList ';'
	  ( gotoJump )?                        -> ^(CALL_JUMP[$t] ^(LIST nameExp*) exp ^(OPTION gotoJump?) annotationList)
	;

gotoJump 
	: t='goto' ID annotationList ';'       -> ^(GOTO[$t] ID annotationList)
	;

returnJump
	: t='return' exp? annotationList ';'   -> ^(RETURN[$t] ^(OPTION exp?) annotationList)
	;

ifThenJump
	: t='if' exp 'then' annotationList 
	  'goto' ID                            -> ^(IF_THEN_JUMP[$t] exp annotationList ID)
	;

ifElseJump
	: t='else' annotationList 'goto' ID    -> ^(IF_ELSE_JUMP[$t] annotationList ID)
	;

switchCaseJump
	: t='|' exp  
	  '=>' annotationList 'goto' ID        -> ^(SWITCH_CASE_JUMP[$t] exp annotationList ID)
	;

switchDefaultJump
	: t='|' '=>' annotationList 'goto' ID  -> ^(SWITCH_DEFAULT_JUMP[$t] annotationList ID)
	;

catchClause
	: t='catch' annotationList
	  type? (local=ID)?
	  '@' '[' from=ID '..'
	  to=ID ']'
	  jump                                 -> ^(CATCH_CLAUSE[$t] annotationList ^(OPTION type?) ^(OPTION $local?) $from $to jump?)
	;
	
exp
	: conditionalExp
	;
		
conditionalExp
	: conditionalImplyExp
	| ifThenExp
	  ('else' ifThenExp)*
	  ifElseExp                            -> ^(IF_EXP ^(LIST ifThenExp+) ifElseExp)
	| 'switch' exp switchCaseExp*          
	  switchDefaultExp?                    -> ^(SWITCH_EXP exp ^(LIST switchCaseExp*) ^(OPTION switchDefaultExp?))
	;

ifThenExp
	: t='if' e1=exp 'then' annotationList 
	     e2=exp                            -> ^(IF_THEN_EXP[$t] $e1 annotationList $e2)
	;

ifElseExp
	: t='else' annotationList exp          -> ^(IF_ELSE_EXP[$t] annotationList exp) 
	;

switchCaseExp
	: t='|' e1=exp '=>'
	  annotationList e2=exp                -> ^(SWITCH_CASE_EXP[$t] $e1 annotationList $e2)
	;

switchDefaultExp
	: t='|' '=>' annotationList exp        -> ^(SWITCH_DEFAULT_EXP[$t] annotationList exp)
	;

conditionalImplyExp
	: ( c1=conditionalOrExp                -> $c1
	  ) ( t='==>' c2=conditionalOrExp      -> ^(BINARY $t $conditionalImplyExp $c2)
	    | t='<==' c2=conditionalOrExp      -> ^(BINARY $t $conditionalImplyExp $c2)
	    )*
	;
	
conditionalOrExp
	: ( c1=conditionalAndExp               -> $c1 
	  ) ( t=COND_OR_OP 
	      c2=conditionalAndExp             -> ^(BINARY $t $conditionalOrExp $c2)
	    )*
	;

conditionalAndExp
	: ( i1=inclusiveOrExp                  -> $i1
	  ) ( t=COND_AND_OP i2=inclusiveOrExp  -> ^(BINARY $t $conditionalAndExp $i2)
	    )*
	;

inclusiveOrExp
	: ( e1=exclusiveOrExp                  -> $e1
	  ) ( t=OR_OP e2=exclusiveOrExp        -> ^(BINARY $t $inclusiveOrExp $e2)
	    )*
	;


exclusiveOrExp
	: ( a1=andExp                          -> $a1
	  ) ( t=XOR_OP a2=andExp               -> ^(BINARY $t $exclusiveOrExp $a2)
	    )*
	;

andExp
	: ( e1=equalityExp                     -> $e1
	  ) ( t=AND_OP e2=equalityExp          -> ^(BINARY $t $andExp $e2)
	    )*
	;

equalityExp
	: ( t1=typeTestExp                     -> $t1
	  ) ( t=EQ_OP t2=typeTestExp           -> ^(BINARY $t $equalityExp $t2) 
	    )*
	;

typeTestExp 
	: ( r1=relationalExp                   -> $r1
	  ) ( t=COLON_OP r2=relationalExp      -> ^(BINARY $t $typeTestExp $r2)
	    )*
	;
	
relationalExp
	: ( s1=shiftExp                        -> $s1
	  ) ( t=REL_OP s2=shiftExp             -> ^(BINARY $t $relationalExp $s2)
	    | t='<' s2=shiftExp                -> ^(BINARY $t $relationalExp $s2)
	    | t='>' s2=shiftExp                -> ^(BINARY $t $relationalExp $s2)
	    )*
	;

shiftExp
	: ( a1=additiveExp                     -> $a1
	  ) ( t=SHIFT_OP a2=additiveExp        -> ^(BINARY $t $shiftExp $a2)
	    )*
	;

additiveExp
	: ( m1=multiplicativeExp               -> $m1
	  ) ( t=ADD_OP m2=multiplicativeExp    -> ^(BINARY $t $additiveExp $m2)
	    )*
	;
	
multiplicativeExp
	: ( u1=unaryExp                        -> $u1 
	  ) ( t=MUL_OP u2=unaryExp             -> ^(BINARY $t $multiplicativeExp $u2)
	    | t='*' u2=unaryExp                -> ^(BINARY $t $multiplicativeExp $u2)
            )*
	;
	
unaryExp
	: t=UN_OP unaryExp                     -> ^(UNARY $t unaryExp)
	| t=ADD_OP unaryExp                    -> ^(UNARY $t unaryExp)
	| t=MUL_OP unaryExp                    -> ^(UNARY $t unaryExp)
	| t='*' unaryExp                       -> ^(UNARY $t unaryExp)
	| castExp
	;

castExp
	: t='(' type ')' primaryExp            -> ^(CAST[$t] type primaryExp)
	| primaryExp
	;
	
primaryExp
	: ( p=primary                          -> primary
	  ) ( t='[' expList ']'                -> ^(INDEXING[$t] $primaryExp expList)
	    | t='.' ID                         -> ^(ACCESS[$t] $primaryExp ID)
	    | callArg                          -> ^(CALL $primaryExp callArg)
	    )*
	;

expList
	: exp ( ',' exp)*                      -> ^(LIST exp+)
	;

callArg
	: ( p=primary                          -> primary
	  ) ( t='[' expList ']'                -> ^(INDEXING[$t] $callArg expList)
	    | t='.' ID                         -> ^(ACCESS[$t] $callArg ID)
	    )*
	;
		
primary
	: literal
	| tuple
	| nameExp
	| newListRanged
	| newList
	| newMultiArray
	| newRecord
	| newSet
	| newFunction 
	| t='`' type                           -> ^(TYPE_EXP[$t] type)
	| newExp
	| funExp
	| letExp
	;

nameExp
	: name                                 -> ^(NAME_EXP name)
	| globalName                           -> ^(NAME_EXP globalName)
	;
		
literal
	: t='true'                             -> ^(TRUE[$t])
	| t='false'                            -> ^(FALSE[$t])
	| t='null'                             -> ^(NULL[$t])
	| c=CHAR_LIT                           -> ^(CHAR[$c] $c)
	| intLiteral
	| realLiteral
	| s=STRING_LIT                         -> ^(STRING[$s] $s)
	| s=SYMBOL_LIT                         -> ^(SYMBOL[$s] $s)
	| r=RAW_LIT                            -> ^(RAW[$r] $r)
	;

intLiteral
	: h=HEX_LIT                            -> ^(INT[$h] $h) 
	| o=OCT_LIT                            -> ^(INT[$o] $o)
	| d=DEC_LIT                            -> ^(INT[$d] $d)
	| b=BIN_LIT                            -> ^(INT[$b] $b)
	;

realLiteral
	: f=FLOAT_LIT                          -> ^(FLOAT[$f] $f)
	| r=RATIONAL_LIT                       -> ^(RATIONAL[$r] $r)
	;
	
tuple
	: t='(' ( annotatedExp
	      ( ',' annotatedExp )* )? ')'     -> ^(TUPLE[$t] ^(LIST annotatedExp*))
	;
		
annotatedExp
	: exp annotationList                   -> ^(ANN_EXP exp annotationList)
	;

newExp
	: t='new' baseType
	  newMultiArrayTypeFragment*
	  typeFragment*                        -> ^(NEW_EXP[$t] baseType ^(LIST newMultiArrayTypeFragment*) ^(LIST typeFragment*))
	;
    
newMultiArrayTypeFragment
	: t='[' exp ( ',' exp )* ']'           -> ^(NEW_MULTI_ARRAY_FRAGMENT[$t] ^(LIST exp+))
	;

newListRanged
	: t='`' '[' exp '..' exp ']'           -> ^(NLIST_RANGE[$t] exp+)
	;

newMultiArray
	: t='`' '[' 
	  ( newMultiArrayFragment 
	    ( ',' newMultiArrayFragment )* 
	  )? ']'                               -> ^(NMULTI_ARRAY[$t] ^(LIST newMultiArrayFragment*))
	;

newMultiArrayFragment
	: t='[' ( newMultiArrayFragment 
	        ( ',' newMultiArrayFragment )* -> ^(NMULTI_ARRAY_FRAGMENT[$t] ^(LIST newMultiArrayFragment*))
	        )? ']'
	| exp
	;

newList
	: t='`' '[' ( exp ( ',' exp )* )? ']'  -> ^(NLIST[$t] ^(LIST exp*))
	;

newSet
	: t='`' '{' ( exp ( ',' exp )* )? '}'  -> ^(NSET[$t] ^(LIST exp*))
	;

newRecord
	: t='`' name typeTuple?
	  '{' ( attrInit ( ',' attrInit )* )?
	  '}'                                  -> ^(NRECORD[$t] ^(NAME_TYPE name ^(OPTION) ^(OPTION typeTuple?)) ^(LIST attrInit*))
	;

attrInit
	: t=ID '=' exp                         -> ^(ATTR_INIT[$t] ID exp)
	;

newFunction
	: t='`' '{' ( '->'                     -> ^(NFUNCTION[$t] ^(OPTION))
	      | mapping ( ',' mapping )*       -> ^(NFUNCTION[$t] ^(OPTION ^(LIST mapping+)))
	      ) '}'
	;

mapping
	: e1=exp '->' e2=exp                   -> ^(MAPPING $e1 $e2)
	;
	
funExp
	: t='{' matching
	      ( '|' matching )* '}'            -> ^(FUN_EXP[$t] ^(LIST matching+))
	;

matching
	: t='=>' exp                           -> ^(MATCHING[$t] ^(OPTION) exp)
	| paramList '=>' exp                   -> ^(MATCHING ^(OPTION ^(LIST paramList)) exp)
	;

letExp
	: t='let' binding
	       ( ',' binding )* 
	       'in' exp                        -> ^(LET_EXP[$t] ^(LIST binding+) exp)
	;

binding
	: t=ID ( ',' ID )* '=' exp             -> ^(LET_BINDING[$t] ^(LIST ID+) exp)
	;

name
	: (ids+=ID '::' )* id=ID               -> ^(NAME[$id] ^(LIST $ids*) $id)
	;

globalName
	: (  ID '::' )* t=GLOBALID             -> ^(NAME[$t] ^(LIST ID*) GLOBALID)
	;
    
type 
	: baseType typeFragment*               -> ^(TYPE baseType ^(LIST typeFragment*))
	;
	
typeFragment
	: t='*'                                -> ^(ARRAY_FRAGMENT[$t])
	| t='[' ']'                            -> ^(LIST_FRAGMENT[$t])
	| t='[' ','+ ']'                       -> ^(MULTIARRAY_FRAGMENT[$t] ^(LIST ','+))
	| t='{' '}'                            -> ^(SET_FRAGMENT[$t])
	;

baseType
	: t=TYPEVARID                          -> ^(TYPEVARID_TYPE[$t] $t)
	| name ( '.' ID)? typeTuple?           -> ^(NAME_TYPE name ^(OPTION ID?) ^(OPTION typeTuple?))
	| procedureType
	| tupleType
	| functionType
	| relationType
	;

procedureType
	: t='(' 
	  (  ( procedureTypeParamVariable
	     | typeParam 
	       ( ',' typeParam )* 
	       ( ',' procedureTypeParamVariable 
	       )?
	     ) 
	  )?
	  ( '->' annotatedType? ')'            -> ^(FUN_TYPE[$t] ^(LIST typeParam* procedureTypeParamVariable?) ^(OPTION annotatedType?))
	  | '-!>' annotatedType? ')'           -> ^(PROCEDURE_TYPE[$t] ^(LIST typeParam* procedureTypeParamVariable?) ^(OPTION annotatedType?))
	  )
	;

typeParam
	: type ID? annotationList              -> ^(TYPE_PARAM type ^(OPTION ID?) annotationList)
	;

annotatedType
	: type annotationList                  -> ^(ANNOTATED_TYPE type annotationList)
	;
	
procedureTypeParamVariable
	: type ID? '...' annotationList        -> ^(PROCEDURE_TYPE_PARAM_VARIABLE type ^(OPTION ID?) annotationList)
	;

tupleType
	: t='(' typeParam 
	      ( ',' typeParam)* ')'            -> ^(TUPLE_TYPE[$t] ^(LIST typeParam+))
	;

functionType
	: t='{' i+=typeParam 
	      ( ',' i+=typeParam )* '->' 
	      o=annotatedType '}'              -> ^(FUNCTION_TYPE[$t] ^(LIST $i+) $o) 
	;
        
relationType
	: t='{' typeParam 
	      ( ',' typeParam )+ '}'           -> ^(RELATION_TYPE[$t] ^(LIST typeParam+)) 
	;

// Lexer
ID 
	: '^'? TX_BASICID '\''*
	| '{|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( '}' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|}'
	| '(|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( ')' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|)'
	| '[|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( ']' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|]'
	| '<|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( '>' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|>'
	| '+|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( '+' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|+'
	| '-|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( '-' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|-'
	| '*|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( '*' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|*'
	| '.|' ( ~( '|' | '\n' | '\r' | '\t' ) 
	       | ( '|' ~( '}' |'\r'|'\t'|'\u000C'|'\n') ) )* 
	   '|.'
	;

GLOBALID
	: '@@' ID
	;

TYPEVARID
	: '\'' ID
	;

LOCID	: '#' ( ID '.'? )? ;

SYMBOL_LIT	
	: ':' ID
	;

// Lexer adapted from ANTLR Java 1.5 grammar v1.0 by T. Parr
HEX_LIT
	: '0' ('x'|'X') TX_HexDigit+ TX_IntTypeSuffix?
	;

DEC_LIT	
	: ('0' | '-'? '1'..'9' '0'..'9'*) TX_IntTypeSuffix?
	;

OCT_LIT
	: '0' ('0'..'7')+ TX_IntTypeSuffix?
	;

BIN_LIT
	: '0' ('b' | 'B') ('0' | '1')+ TX_IntTypeSuffix?
	;

FLOAT_LIT
	: '-'? ('0'..'9')+ '.' ('0'..'9')* TX_Exponent? TX_FloatTypeSuffix?
	| '-'? '.' ('0'..'9')+ TX_Exponent? TX_FloatTypeSuffix?
	| '-'? ('0'..'9')+ TX_Exponent TX_FloatTypeSuffix?
	| '-'? ('0'..'9')+ TX_FloatTypeSuffix
	;

RATIONAL_LIT
	: '-'? ('0' | '1'..'9' '0'..'9'*) '/' ( '1'..'9' '0'..'9'* )
	;

CHAR_LIT
	: '\'' ( TX_EscapeSequence | ~('\''|'\\') ) '\''
	;

STRING_LIT
	: '"' ( TX_EscapeSequence | ~('\\'|'"'|'\n') )* '"'
	;
	
COND_AND_OP 
	: '&' TX_OPID_SUFFIX
	;

COND_OR_OP 
	: '||' TX_OPID_SUFFIX
	;
	
AND_OP	: '^&' TX_OPID_SUFFIX
	;

XOR_OP	: '^~' TX_OPID_SUFFIX
	;
		
OR_OP	: '^|' TX_OPID_SUFFIX
	;
	
EQ_OP	: ( '==' | '!=' ) TX_OPID_SUFFIX
	;

COLON_OP
	: TX_OPID_CHAR TX_OPID_SUFFIX ':'
	;

REL_OP
	: '<' TX_OPID_CHARMLT TX_OPID_SUFFIX
	| '>' TX_OPID_CHARMGT TX_OPID_SUFFIX
	;

SHIFT_OP
	: '^<' | '^>>' TX_OPID_SUFFIX
	| '^>' ( TX_OPID_CHARMGT TX_OPID_SUFFIX )?
	;

ADD_OP
	: ( '+' | '-' ) TX_OPID_SUFFIX
	;
	
MUL_OP
	: ( '/' | '%' ) TX_OPID_SUFFIX
	| '*' TX_OPID_CHAR TX_OPID_SUFFIX
	;
	
UN_OP
	: ( '!' | '~' ) TX_OPID_SUFFIX
	;

ACTION_EXT_OP
  :	':' TX_OPID_CHAR+ '='
  ;

fragment
TX_OPID_SUFFIX
	: TX_OPID_CHAR* ( '_' TX_BASICID )?
	;

fragment
TX_OPID_CHAR
	: ( '+' | '-' | '/' | '\\' | '*' | '%' | '&' | '|' | '?' | '>' | '<' | '=' | '~' )
	;
	
fragment
TX_OPID_CHARMGT
	: ( '+' | '-' | '/' | '\\' | '*' | '%' | '&' | '|' | '?' | '<' | '=' | '~' )
	;

fragment
TX_OPID_CHARMLT
	: ( '+' | '-' | '/' | '\\' | '*' | '%' | '&' | '|' | '?' | '>' | '=' | '~' )
	;

fragment
TX_BASICID
	: TX_Letter ( TX_Letter | TX_JavaIDDigit )*
	;

fragment
TX_HexDigit
	: ('0'..'9'|'a'..'f'|'A'..'F')
	;


fragment
TX_Exponent
	: ('e'|'E') ('+'|'-')? ('0'..'9')+
	;

fragment
TX_FloatTypeSuffix
	: ('f'|'F'|'d'|'D')
	;

fragment
TX_IntTypeSuffix
	: ('l'|'L'|'ii'|'II'|'i'|'I')
	;

fragment
TX_EscapeSequence
	: '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
	| TX_UnicodeEscape
	| TX_OctalEscape
	;

fragment
TX_OctalEscape
	: '\\' ('0'..'3') ('0'..'7') ('0'..'7')
	| '\\' ('0'..'7') ('0'..'7')
	| '\\' ('0'..'7')
	;

fragment
TX_UnicodeEscape
	: '\\' 'u' TX_HexDigit TX_HexDigit TX_HexDigit TX_HexDigit
	;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
TX_Letter
	: '\u0024'
	| '\u0041'..'\u005a'
	| '\u005f'
	| '\u0061'..'\u007a'
	| '\u00c0'..'\u00d6'
	| '\u00d8'..'\u00f6'
	| '\u00f8'..'\u00ff'
	| '\u0100'..'\u1fff'
	| '\u3040'..'\u318f'
	| '\u3300'..'\u337f'
	| '\u3400'..'\u3d2d'
	| '\u4e00'..'\u9fff'
	| '\uf900'..'\ufaff'
	;
	
fragment
TX_JavaIDDigit
	: '\u0030'..'\u0039'
	| '\u0660'..'\u0669'
	| '\u06f0'..'\u06f9'
	| '\u0966'..'\u096f'
	| '\u09e6'..'\u09ef'
	| '\u0a66'..'\u0a6f'
	| '\u0ae6'..'\u0aef'
	| '\u0b66'..'\u0b6f'
	| '\u0be7'..'\u0bef'
	| '\u0c66'..'\u0c6f'
	| '\u0ce6'..'\u0cef'
	| '\u0d66'..'\u0d6f'
	| '\u0e50'..'\u0e59'
	| '\u0ed0'..'\u0ed9'
	| '\u1040'..'\u1049'
	;

WS	: (' '|'\r'|'\t'|'\u000C'|'\n') { $channel=HIDDEN; } ;
    
RAW_LIT : '"""' ( options {greedy=false;} : . )* '"""' ;

COMMENT : '/*' ( options {greedy=false;} : . )* '*/' { $channel=HIDDEN; } ;

LINE_COMMENT
	: '//' ~('\n'|'\r')* '\r'? '\n' { $channel=HIDDEN; }
	;

