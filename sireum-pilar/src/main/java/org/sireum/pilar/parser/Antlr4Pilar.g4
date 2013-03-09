  grammar Antlr4Pilar;

modelFile : model EOF;

annotationFile : annotation EOF;

packageDeclarationFile : packageDeclaration EOF;

packageElementFile : packageElement EOF;

locationFile : location EOF;

transformationFile : transformation EOF;

actionFile : action EOF;

jumpFile : jump EOF;

expFile : exp EOF;

typeFile : type EOF;

model
  : annotation*
    packageElement*
    packageDeclaration*
  ;

annotation
  : '@' Identifier annotationParams?
  ;

annotationParams
  : '(' annotationParam ( ',' annotationParam )* ')'        #AnnotationParamsA
  | exp ( ',' exp )*                                        #AnnotationParamsE
  ;

annotationParam
  : Identifier '=' annotation                               #AnnotationParamIA
  | Identifier '=' exp                                      #AnnotationParamIE
  | annotation                                              #AnnotationParamA
  | exp                                                     #AnnotationParamE
  ;

angleGroupedAnnotations
  : '<' annotation* '>'
  ;

parenGroupedAnnotations
  : '(' annotation* ')'
  ;

packageDeclaration
  : 'package' name annotation* ';'
    packageElement*
  ; 

packageElement
  : constDeclaration
  | enumDeclaration
  | recordDeclaration
  | globalVarsDeclaration
  | procedureDeclaration
  | funDeclaration
  | extDeclaration
  ;

constDeclaration
  : 'const' Identifier annotation* '{' constElement* '}'
  ;

constElement
  : Identifier '=' exp annotation* ';'
  ;

enumDeclaration
  : 'enum' Identifier annotation* '{' ( enumElement ( ',' enumElement )* )? '}'
  ;

enumElement
  : Identifier annotation*
  ;

typealiasDeclaration
  : 'typealias' type Identifier annotation* ';'
  ;

recordDeclaration
  : 'record' Identifier typeVarTuple? annotation* extendClauses? '{' field* '}'
  ;

typeVarTuple
  : '<' typeVar ( ',' typeVar )* '>'
  ;

typeVar
  : TypeVariableIdentifier annotation*
  ;

extendClauses
  : 'extends' extendClause ( ',' extendClause )*
  ;

extendClause
  : name typeTuple? annotation*
  ;

typeTuple
  : '<' type ( ',' type )* '>'
  ;

field
  : type? fieldFragment ( ',' fieldFragment )* ';'
  ;

fieldFragment
  : Identifier annotation*
  ;

globalVarsDeclaration
  : 'global' globalVarDeclaration+
  ;

globalVarDeclaration
  : type? globalVarFragment ( ',' globalVarFragment )*
  ;

globalVarFragment
  : GlobalVariableIdentifier annotation*
  ;

procedureDeclaration
  : 'procedure' typeVarTuple? type? Identifier
    ( '(' paramVar ( ',' paramVar )* ')' )?
    annotation*
    body
  ;

paramVar
  : type? Identifier annotation*
  ;

funDeclaration
  : 'fun' typeVarTuple? type? Identifier
    ( '(' paramVar ( ',' paramVar )* ')' )?
    annotation* '=' exp ';'
  ;

extDeclaration
  : 'extension' typeVarTuple? Identifier annotation* '{' extElement* '}'
  ;

extElement
  : 'typedef'   typeVarTuple?       Identifier annotation*  extendClauses? ';'            #TypeExtension
  | 'actiondef' typeVarTuple?       Identifier ( '(' extParams ')' )? annotation* ';'     #ActionExtension
  | 'expdef'    typeVarTuple? type? Identifier ( '(' extParams ')' )? annotation* ';'     #ExpExtension
  | 'procdef'   typeVarTuple? type? Identifier ( '(' extParams ')' )? annotation* ';'     #ProcedureExtension
  ;

extParams
  : ( extParam ( ',' extParam )* )? extParamVariable?
  ;

extParam
  : type? Identifier? annotation*
  ;

extParamVariable
  : type? Identifier? '...' annotation*
  ;

body
  : '{'
    localVarsDeclaration?
    location*
    catchClause*
    '}'                                                     #ImplementedBody
  | ';'                                                     #EmptyBody
  ;

localVarsDeclaration
  : 'local' localVarDeclaration+
  ;

localVarDeclaration
  : type? localVarFragment ( ',' localVarFragment )* ';'
  ;

localVarFragment
  : Identifier annotation*
  ;

location
  : LocationIdentifier angleGroupedAnnotations?
    ( transformation ( '|' transformation )* )?
  ;

transformation
  : parenGroupedAnnotations? guard action* jump?
  ;

guard
  : exp annotation* '+>'                                    # ExpGuard
  | 'else' annotation*                                      # ElseGuard
  ;

action
  : 'assert' exp annotation* ';'                                       #Assert
  | 'assume' exp annotation* ';'                                       #Assume
  | 'throw'  exp annotation* ';'                                       #Throw
  | lhss ( ':=' | ActionExtOperator ) rhs ( ',' rhs )* annotation* ';' #Assign
  | 'start' name ( '[' exp ']' )? exp? annotation* ';'                 #Start
  | exp annotation* ';'                                                #ActionExtCall
  ;

lhss
  : lhs ( ',' lhs )*
  ;

lhs
  : exp annotation*
  ;

rhs
  : annotation* exp
  ;

jump
  : gotoj                                                            #GotoJump
  | 'return'  exp? annotation* ';'                                   #ReturnJump
  | ifThenJump ( 'else' ifThenJump )* ifElseJump? annotation* ';'    #IfJump
  | 'switch' exp switchCaseJump* switchDefaultJump? annotation* ';'  #SwitchJump
  | 'call' ( lhs ':=' )? exp annotation* ';' gotoj?                  #CallJump
  ;

gotoj
  : 'goto' Identifier annotation* ';'
  ;

ifThenJump
  : 'if' exp 'then' annotation* 'goto' Identifier
  ;

ifElseJump
  : 'else' annotation* 'goto' Identifier
  ;

switchCaseJump
  : '|' exp '=>' annotation* 'goto' Identifier
  ;

switchDefaultJump
  : '|' '=>' annotation* 'goto' Identifier
  ;

catchClause
  : 'catch' annotation* type?
    var=Identifier? '@' '[' from=Identifier '..' to=Identifier ']' gotoj
  ;

exp
  : primary primarySuffix*                                  #PrimaryExp
  | '(' type ')' exp                                        #CastExp
  | op=( UnaryOperator | AdditiveOperator
       | MultiplicativeOperator | '*' )
    exp                                                     #UnaryExp
  | exp op=( MultiplicativeOperator | '*' ) exp             #BinaryExp
  | exp op=AdditiveOperator exp                             #BinaryExp
  | exp op=ShiftOperator exp                                #BinaryExp
  | exp op=( RelationalOperator | '<' | '>' ) exp           #BinaryExp
  | exp op=ColonOperator exp                                #BinaryExp
  | exp op=EqualityOperator exp                             #BinaryExp
  | exp op=AndOperator exp                                  #BinaryExp
  | exp op=XorOperator exp                                  #BinaryExp
  | exp op=OrOperator exp                                   #BinaryExp
  | exp op=ConditionalAndOperator exp                       #BinaryExp
  | exp op=ConditionalOrOperator exp                        #BinaryExp
  | exp op=( '==>' | '<==' ) exp                            #BinaryExp
  | ifThenExp ( 'else' ifThenExp )* ifElseExp?              #IfExp
  | 'switch' exp switchCaseExp* switchDefaultExp?           #SwitchExp
  ;

ifThenExp
  : 'if' cond=exp 'then' annotation* exp
  ;

ifElseExp
  : 'else' annotation* exp
  ;

switchCaseExp
  : '|' cond=exp '=>' annotation* exp
  ;

switchDefaultExp
  : '|' '=>' annotation* exp
  ;

primarySuffix
  : '[' exp ( ',' exp )* ']'                                #IndexingSuffix
  | '.' Identifier                                          #AccessSuffix
  ;

primary
  : 'true'                                                  #TrueLit
  | 'false'                                                 #FalseLit
  | 'null'                                                  #NullLit
  | CharacterLiteral                                        #CharLit
  | HexLiteral                                              #HexLit
  | OctalLiteral                                            #OctLit
  | DecimalLiteral                                          #DecLit
  | BinaryLiteral                                           #BinLit
  | FloatingPointLiteral                                    #FloatLit
  | SymbolLiteral                                           #SymbolLit
  | StringLiteral                                           #StringLit
  | MultilineStringLiteral                                  #MultilineStringLit
  | '`' type                                                #TypeLit
  | '(' ( annExp ( ',' annExp )* )? ')'                     #TupleExp
  | name                                                    #NameExp
  | globalName                                              #GlobalNameExp
  | newK '[' exp '..' exp ']'                               #RangedListExp
  | newK '[' ( exp ( ',' exp )* )? ']'                      #ListExp
  | newK '{' ( exp ( ',' exp )* )? '}'                      #SetExp
  | newK '{' ( '->' | mapping ( ',' mapping )* ) '}'        #MapExp
  | newK '['
    ( newMultiArrayFragment
      ( ',' newMultiArrayFragment )* )? ']'                 #MultiArrayExp
  | newK '{' matching ( '|' matching )* '}'                 #ClosureExp
  | newK name typeTuple?
    '{' ( fieldInit ( ',' fieldInit )* )? '}'               #RecordExp
  | newK baseType newMultiArrayTypeFragment* typeFragment*  #ArrayExp
  | 'let' binding ( ',' binding )* 'in' exp                 #LetExp
  ;

annExp
  : exp annotation*
  ;

newK
  : '`' | 'new'
  ;

newMultiArrayFragment
  : '[' (     ( newMultiArrayFragment | exp )
        ( ',' ( newMultiArrayFragment | exp ) )* )? ']'
  ;


newMultiArrayTypeFragment
  : '[' exp ( ',' exp )* ']'
  ;

fieldInit
  : Identifier '=' exp
  ;

mapping
  : exp '->' exp
  ;

matching
  : ( paramVar ( ',' paramVar )* )? '=>' exp
  ;

binding
  : Identifier ( ',' Identifier )? '=' exp
  ;

name
  : ( Identifier '::' )* Identifier
  ;

globalName
  : ( Identifier '::' )* GlobalVariableIdentifier
  ;

type
  : baseType typeFragment*
  ;

typeFragment
  : '*'                                                     #StarTypeFragment
  | '[' ']'                                                 #ListFragment
  | '[' ','+ ']'                                            #MultiArrayFragment
  | '{' '}'                                                 #SetFragment
  ;

baseType
  : TypeVariableIdentifier                                  #TypeVariable
  | name ( '.' Identifier )? typeTuple?                     #NamedType
  | '(' procTypeParam? '->' annotatedType? ')'              #ClosureType
	| '(' procTypeParam? '-!>' annotatedType? ')'             #ProcedureType
  | '(' typeParam ( ',' typeParam)* ')'                     #TupleType
  | '{' typeParam ( ',' typeParam )* '->' annotatedType '}' #MapType
  | '{' typeParam ( ',' typeParam )+ '}'                    #RelationType
  ;

procTypeParam
  : procedureTypeParamVariable
	| typeParam ( ',' typeParam )* ( ',' procedureTypeParamVariable )?
  ;

typeParam
	: type Identifier? annotation*
	;

annotatedType
	: type annotation*
	;

procedureTypeParamVariable
	: type Identifier? '...' annotation*
	;

Identifier
	: '^'? IdentifierFragment '\''*
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

GlobalVariableIdentifier
	: '@@' Identifier
	;

TypeVariableIdentifier
	: '\'' Identifier
	;

LocationIdentifier	: '#' ( Identifier '.'? )? ;

SymbolLiteral
	: ':' Identifier
	;

// Lexer adapted from ANTLR Java 1.5 grammar v1.0 by T. Parr
MultilineStringLiteral
  : '"""' .*? '"""'
  ;

ConditionalAndOperator
	: '&' OperatorSuffix
	;

ConditionalOrOperator
	: '||' OperatorSuffix
	;

AndOperator	: '^&' OperatorSuffix
	;

XorOperator	: '^~' OperatorSuffix
	;

OrOperator	: '^|' OperatorSuffix
	;

EqualityOperator	: ( '==' | '!=' ) OperatorSuffix
	;

ColonOperator
	: OperatorChar OperatorSuffix ':'
	;

RelationalOperator
	: '<' OperatorCharMLT OperatorSuffix
	| '>' OperatorCharMGT OperatorSuffix
	;

ShiftOperator
	: '^<' | '^>>' OperatorSuffix
	| '^>' ( OperatorCharMGT OperatorSuffix )?
	;

AdditiveOperator
	: ( '+' | '-' ) OperatorSuffix
	;

MultiplicativeOperator
	: ( '/' | '%' ) OperatorSuffix
	| '*' OperatorChar OperatorSuffix
	;

UnaryOperator
	: ( '!' | '~' ) OperatorSuffix
	;

ActionExtOperator
  :	':' OperatorChar+ '='
  ;

fragment
OperatorSuffix
	: OperatorChar* ( '_' IdentifierFragment )?
	;

fragment
OperatorChar
	: ( '+' | '-' | '/' | '\\' | '*' | '%' | '&' | '|' | '?' | '>' | '<' | '=' | '~' )
	;

fragment
OperatorCharMGT
	: ( '+' | '-' | '/' | '\\' | '*' | '%' | '&' | '|' | '?' | '<' | '=' | '~' )
	;

fragment
OperatorCharMLT
	: ( '+' | '-' | '/' | '\\' | '*' | '%' | '&' | '|' | '?' | '>' | '=' | '~' )
	;

fragment
IdentifierFragment
	: Letter ( Letter | JavaIDDigit )*
	;

HexLiteral
  // underscores may be freely inserted after first hex digit and before last
  : '0' ('x'|'X')
    HexDigits
    IntegerTypeSuffix?
  ;

DecimalLiteral
  // Only a single zero digit may begin with a zero
  // Underscores may be freely inserted after first digit and before last
  : ( '0' | '1'..'9' ('_'* Digit)* ) IntegerTypeSuffix?
  ;

OctalLiteral
  // Underscores may be freely inserted before the last digit.
  // Don't know why underscores here are different from others -
  // Maybe the leading 0 is considered a digit as well as a marker
  // indicating that the following is a base 8 number
  : '0' ('_'* '0'..'7')+ IntegerTypeSuffix?
  ;

BinaryLiteral
  // underscores may be freely inserted after first digit and before last
  : '0' ('b'|'B')
    BinaryDigit ('_'* BinaryDigit)*
    IntegerTypeSuffix?
  ;

fragment
BinaryDigit : ('0'|'1') ;

fragment
HexDigits : HexDigit ('_'* HexDigit)* ;

fragment
HexDigit : (Digit|'a'..'f'|'A'..'F') ;

fragment
Digits : Digit ('_'* Digit)* ;

fragment
Digit : '0'..'9' ;

fragment
IntegerTypeSuffix : ('l'|'L'|'ii'|'II'|'i'|'I') ;

FloatingPointLiteral
  : Digits '.' Digits? Exponent? FloatTypeSuffix?
  | '.' Digits Exponent? FloatTypeSuffix?
  | Digits Exponent FloatTypeSuffix?
  | Digits FloatTypeSuffix

    // Hex float literal
  | ('0x' | '0X')
    HexDigits? ('.' HexDigits?)?
    ( 'p' | 'P' ) ( '+' | '-' )? Digits // note decimal exponent
    FloatTypeSuffix?
  ;

fragment
Exponent : ('e'|'E') ('+'|'-')? Digits ;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

CharacterLiteral
  : '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
  ;

StringLiteral
  :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
  ;

fragment
EscapeSequence
  : '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
  | UnicodeEscape
  | OctalEscape
  ;

fragment
OctalEscape
  : '\\' ('0'..'3') ('0'..'7') ('0'..'7')
  | '\\' ('0'..'7') ('0'..'7')
  | '\\' ('0'..'7')
  ;

fragment
UnicodeEscape
  : '\\' 'u' HexDigit HexDigit HexDigit HexDigit
  ;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
  : '\u0024'                 // $
  | '\u0041'..'\u005a'       // A-Z
  | '\u005f'                 // _
  | '\u0061'..'\u007a'       // a-z
  | '\u00c0'..'\u00d6'       // Latin Capital Letter A with grave - Latin Capital letter O with diaeresis
  | '\u00d8'..'\u00f6'       // Latin Capital letter O with stroke - Latin Small Letter O with diaeresis
  | '\u00f8'..'\u00ff'       // Latin Small Letter O with stroke - Latin Small Letter Y with diaeresis
  | '\u0100'..'\u1fff'       // Latin Capital Letter A with macron - Latin Small Letter O with stroke and acute
  | '\u3040'..'\u318f'       // Hiragana
  | '\u3300'..'\u337f'       // CJK compatibility
  | '\u3400'..'\u3d2d'       // CJK compatibility
  | '\u4e00'..'\u9fff'       // CJK compatibility
  | '\uf900'..'\ufaff'       // CJK compatibility
  ;

fragment
JavaIDDigit
  : '\u0030'..'\u0039'       // 0-9
  | '\u0660'..'\u0669'       // Arabic-Indic Digit 0-9
  | '\u06f0'..'\u06f9'       // Extended Arabic-Indic Digit 0-9
  | '\u0966'..'\u096f'       // Devanagari 0-9
  | '\u09e6'..'\u09ef'       // Bengali 0-9
  | '\u0a66'..'\u0a6f'       // Gurmukhi 0-9
  | '\u0ae6'..'\u0aef'       // Gujarati 0-9
  | '\u0b66'..'\u0b6f'       // Oriya 0-9
  | '\u0be7'..'\u0bef'       // Tami 0-9
  | '\u0c66'..'\u0c6f'       // Telugu 0-9
  | '\u0ce6'..'\u0cef'       // Kannada 0-9
  | '\u0d66'..'\u0d6f'       // Malayala 0-9
  | '\u0e50'..'\u0e59'       // Thai 0-9
  | '\u0ed0'..'\u0ed9'       // Lao 0-9
  | '\u1040'..'\u1049'       // Myanmar 0-9?
  ;

WS
  : [ \r\t\u000C\n]+ -> channel(HIDDEN)
  ;

COMMENT
  : '/*' .*? '*/'    -> channel(HIDDEN)
  ;

LINE_COMMENT
  : '//' ~[\r\n]*    -> channel(HIDDEN)
  ;