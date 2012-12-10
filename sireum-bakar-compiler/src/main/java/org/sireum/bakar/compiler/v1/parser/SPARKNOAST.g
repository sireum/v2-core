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

grammar SPARKNOAST;
options { backtrack=true; memoize=true; }

tokens
{
  OPTION;
  LIST;
}

@header {
package org.sireum.profile.spark.parser;

import org.sireum.module.message.Message;
import org.sireum.module.message.ErrorMessage;

/**
 * SPARK parser.
 * 
 * @author robby
 */
}

@lexer::header {
package org.sireum.profile.spark.parser;

/**
 * SPARK lexer.
 * 
 * @author robby
 */
}

@members {
protected ArrayList<Message> errors = new ArrayList<Message>();

public List<Message> popErrors() {
  List<Message> result = errors;
  errors = new ArrayList<Message>();
  return result;
}
    
@Override
public void reportError(RecognitionException re) {
  // if we've already reported an error and have not matched a token
  // yet successfully, don't report any errors.
  if ( errorRecovery ) {
    //System.err.print("[SPURIOUS] ");
    return;
  }
  errorRecovery = true;
      
  ErrorMessage em = new ErrorMessage();
  em.setTheLineNumber(re.line);
  em.setTheColumnNumber(re.charPositionInLine + 1);
  em.setTheMessageText(getErrorMessage(re, this.getTokenNames()));
  errors.add(em);
}
}


compilationFile returns [Compilation result = null ]
	: c=compilation EOF                   { result = $c.result; }
	;

// SPARK95 2.8
pragma returns [ Pragma result = new Pragma() ]
	: 'pragma' IDENTIFIER
	  ( '(' pragma_argument_association
	    ( ',' pragma_argument_association )*
	    ')'
	  )? ';'
	;
	
pragma_argument_association
	: ( pragma_argument_identifier '=>' )? 
	  ( name | expression )
	;

// added
pragma_argument_identifier
	: IDENTIFIER
	;


// SPARK95 3.1
basic_declaration 
	: type_declaration
	| subtype_declaration
	| object_declaration
	| number_declaration
	;

defining_identifier
	: IDENTIFIER
	;


// SPARK95 3.2.1
type_declaration
	: full_type_declaration
	| private_type_declaration
	| private_extension_declaration
	;

full_type_declaration
	: 'type' defining_identifier 'is' 
	  type_definition ';'
	;

type_definition
	: enumeration_type_definition
	| integer_type_definition
	| real_type_definition
	| array_type_definition
	| record_type_definition
	| modular_type_definition
	| record_type_extension
	;

// Typo in SPARK95 Reference Manual?
// type_mark was used instead of
// subtype_mark
record_type_extension
	: 'new' subtype_mark 
	  'with' record_definition ';'
	;


// SPARK95 3.2.2
subtype_declaration
	: 'subtype' defining_identifier
	  'is' subtype_indication ';'
	;

subtype_indication
	: subtype_mark constraint?
	;
	
subtype_mark
	: subtype_name
	;

// added
subtype_name
	: name
	;
	
constraint
	: scalar_constraint
	| composite_constraint
	;

scalar_constraint
	: range_constraint
	;
	
composite_constraint
	: index_constraint
	;


// SPARK95 3.3.1
object_declaration
	: defining_identifier_list
	  ':' 'constant'?
	  subtype_mark
	  ( ':=' expression )? ';'
	;
	
defining_identifier_list
	: defining_identifier
	  ( ',' defining_identifier )*
	;


// SPARK95 3.3.2
number_declaration
	: defining_identifier_list
	  ':' 'constant' ':='
	  static_expression ';'
	;

// added
static_expression
	: expression
	;


// SPARK95 3.4


// SPARK95 3.5
range_constraint
	: 'range' static_range
	;

// added
static_range
	: range
	;
	
range
	: range_attribute_reference
	| simple_expression 
	  '..' simple_expression
	;


// SPARK95 3.5.1
enumeration_type_definition
	: '(' enumeration_literal_specification
	      ( ',' enumeration_literal_specification )* ')'
	;

enumeration_literal_specification
	: defining_identifier
	;


// SPARK95 3.5.4
integer_type_definition
	: signed_integer_type_definition
	;

signed_integer_type_definition
	: 'range' static_simple_expression
	  '..' static_simple_expression
	;

// added
static_simple_expression
	: simple_expression
	;

modular_type_definition
	: 'mod' static_simple_expression
	;


// SPARK95 3.5.6
real_type_definition
	: floating_point_definition
	| fixed_point_definition
	;


// SPARK95 3.5.7
floating_point_definition 
	: 'digits' 
	  static_simple_expression
	  real_range_specification?
	;

real_range_specification
	: 'range' static_simple_expression
    '..' static_simple_expression	
	;


// SPARK95 3.5.9
fixed_point_definition 
	: ordinary_fixed_point_definition
	;

ordinary_fixed_point_definition
	: 'delta' static_simple_expression
	  real_range_specification
	;


// SPARK95 3.6
array_type_definition
	: unconstrained_array_definition
	| constrained_array_definition
	;

unconstrained_array_definition
	: 'array'
	  '(' index_subtype_definition
	  ( ',' index_subtype_definition )*
	  ')' 'of' component_definition
	;
	
index_subtype_definition
	: subtype_mark 'range' '<>'
	;

constrained_array_definition
	: 'array'
	  '(' discrete_subtype_definition
	  ( ',' discrete_subtype_definition )*
	  ')' 'of' component_definition
	;

discrete_subtype_definition
	: discrete_subtype_mark
	;

// added
discrete_subtype_mark
	: subtype_mark
	;

component_definition
	: subtype_mark
	;


// SPARK95 3.6.1
index_constraint
	: '(' ( discrete_subtype_mark
	        ',' discrete_subtype_mark )* ')'
	;

discrete_range
	: discrete_subtype_indication
	| static_range
	;

discrete_subtype_indication
	: subtype_indication
	;


// SPARK95 3.7


// SPARK95 3.7.1


// SPARK95 3.8
record_type_definition
	: 'tagged'? 
	  record_definition
	;

record_definition
	: 'record'
	  component_list
	  'end' 'record'
	| 'null' 'record'
	;

component_list
	: component_item+
	| 'null'
	;

component_item
	: component_declaration
	;
	
component_declaration
  : defining_identifier_list
    ':' component_definition ';'	
  ;


// SPARK95 3.8.1
discrete_choice_list 
	: discrete_choice 
	  ( '|' discrete_choice )*
	;

discrete_choice
	: static_simple_expression
	| discrete_range
	;


// SPARK95 3.9.1


// SPARK95 3.10


// SPARK95 3.10.1


// SPARK95 3.11
declarative_part
	: renaming_declaration*
	  ( declarative_item
	  | embedded_package_declaration
	  | external_subprogram_declaration )*	
	;

declarative_item
	: basic_declarative_item
	| body
	| generic_instantiation
	;

basic_declarative_item
	: basic_declaration
	| representation_clause
	;

embedded_package_declaration
  : package_declaration
    ( renaming_declaration
    | use_type_clause )*	
  ;

external_subprogram_declaration
  : subprogram_declaration
    'pragma' IMPORT
    '(' pragma_argument_association
        ( ','  pragma_argument_association )+
    ')' ';'	
  ;

body
	: proper_body
	| body_stub
	;

proper_body
	: subprogram_body
	| package_body
	;


// SPARK95 4.1
/* left-recursive!
name 
	:	direct_name
	| indexed_component
	| selected_component
	| attribute_reference
	| function_call
	;
*/

// refactored left-recursive version
name
	: direct_name
	  ( // indexed_component suffix
	    '(' expression
	    ( ',' expression )* ')'
	  | // selected_component suffix
	    '.' selector_name
	  | // attribute_reference suffix
	    CHARACTER_LITERAL_OR_QUOTE attribute_designator
	  | // function_call suffix
	    actual_parameter_part
	  )*
	;

direct_name
	: IDENTIFIER
	;

prefix
	: name
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

selector_name
  : IDENTIFIER	
  ;


// SPARK95 4.1.4
/* left-refactored in name
attribute_reference
	: prefix '\'' attribute_designator
	;
*/

attribute_designator
	:	IDENTIFIER (
	    '(' expression
	    ( ',' expression )?
	    ')'
	  )?
	| 'Delta'
	| 'Digits'
	;

range_attribute_reference
	: prefix CHARACTER_LITERAL_OR_QUOTE range_attribute_designator
	;

range_attribute_designator
	: 'Range' ( '(' static_expression ')' )?
	;

// ADA95 4.3
aggregate
	: record_aggregate
	| extension_aggregate
	| array_aggregate
	;

// SPARK95 4.3.1
record_aggregate 
	:	positional_record_aggregate
	| named_record_aggregate
	;

positional_record_aggregate
	: '(' expression
	  ( ',' expression )* ')'
	;

named_record_aggregate
	:	'(' record_component_association
	  ( ',' record_component_association )* ')'
	;

record_component_association
	: component_selector_name '=>' expression
	;

component_selector_name
	: selector_name
	;


// SPARK95 4.3.2
extension_aggregate
	: '(' ancestor_part 'with'
	  record_component_association_list ')'
	| '(' ancestor_part 'with'
	  'null' 'record' ')'
	;

ancestor_part
	: expression
	;

record_component_association_list
	: named_record_component_association
	| positional_record_component_association
	;

positional_record_component_association
	: expression ( ',' expression )*
	;

named_record_component_association
	: record_component_association
	  ( ',' record_component_association )*
	;


// SPARK95 4.3.3
array_aggregate
	: positional_array_aggregate
	| named_array_aggregate
	;

positional_array_aggregate
	: '(' aggregate_item 
	  ( ',' aggregate_item )+ ')'
	| '(' aggregate_item
	  ( ',' aggregate_item )* ','
	  'others' '=>' aggregate_item ')'
	;

named_array_aggregate
	: '(' array_component_association
	  ( ',' array_component_association )*
	  ( ',' 'others' '=>' aggregate_item )?
	  ')'
	| '(' 'others' '=>' aggregate_item ')'
	;

array_component_association
	: discrete_choice_list '=>'
	  aggregate_item
	;

aggregate_item
	: expression
	| array_aggregate
	;


// SPARK95 4.4
expression
	: relation ( 'and' relation )*
	| relation ( 'and' 'then' relation )*
	| relation ( 'or' relation )*
	| relation ( 'or' 'else' relation )*
	| relation ( 'xor' relation )*
	;

relation
	: simple_expression
	  ( relational_operator 
	    simple_expression )?
	| simple_expression 'not'? 'in'
	  range
	| simple_expression 'not'? 'in'
	  subtype_mark
	;

simple_expression
	: unary_adding_operator? term
	  ( binary_adding_operator term )*
	;

term
	: factor ( multiplying_operator factor )*
	;

factor
	: primary ( '**' primary )?
	| 'abs' primary
	| 'not' primary
	;

primary
	: NUMERIC_LITERAL
	| CHARACTER_LITERAL_OR_QUOTE
	| STRING_LITERAL
	| name
	| type_conversion
	| qualified_expression
	| '(' expression ')'
	;


// SPARK95 4.5
relational_operator
	: '='
	| '/='
	| '<'
	| '<='
	| '>'
	| '>='
	;

binary_adding_operator
	:	'+'
	| '-'
	| '&'
	;

unary_adding_operator
	: '+'
	| '-'
	;

multiplying_operator
	: '*'
	| '/'
	| 'mod'
	| 'rem'
	;


// SPARK95 4.6
type_conversion
	: subtype_mark '(' expression ')'
	;


// SPARK95 4.7
qualified_expression
	: subtype_mark CHARACTER_LITERAL_OR_QUOTE '('
	  expression ')'
	| subtype_mark CHARACTER_LITERAL_OR_QUOTE
	  aggregate
	;


// SPARK95 4.8


// SPARK95 5.1
sequence_of_statements
	: statement+
	;

statement
	: simple_statement
	| compound_statement
	;

simple_statement
	: null_statement
	| assignment_statement
	| procedure_call_statement
	| exit_statement
	| return_statement
	;

compound_statement
	: if_statement
	| case_statement
	| loop_statement
	;

null_statement
	: 'null' ';'
	;

statement_identifier
	: direct_name
	;


// SPARK95 5.2
assignment_statement 
	: variable_name ':=' expression ';'	
	;

// added
variable_name
	: name
	;


// SPARK95 5.3
if_statement
	: 'if' condition 'then'
	  sequence_of_statements
	  ( 'elsif' condition 'then'
	    sequence_of_statements )*
	  ( 'else' sequence_of_statements )?
	  'end' 'if' ';'
	;

condition
	: boolean_expression
	;

// added
boolean_expression
	: expression
	;


// SPARK95 5.4
case_statement
	: 'case' expression 'is'
	  case_statement_alternative+
	  ( 'when' 'others' '=>'
	    sequence_of_statements
	  )?
	  'end' 'case' ';'
	;

case_statement_alternative
	: 'when' discrete_choice_list
	  '=>' sequence_of_statements
	;


// SPARK95 5.5
loop_statement
	: ( loop_statement_identifier ':' )?
	  ( iteration_scheme )?
	  'loop' sequence_of_statements
	  'end' 'loop'
	  loop_identifier? ';'
	;

// added
loop_statement_identifier
	: statement_identifier
	;

loop_identifier
	: IDENTIFIER
	;

iteration_scheme
	: 'while' condition
	| 'for' loop_parameter_specification
	;

loop_parameter_specification
	: defining_identifier 'in'
	  'reverse'? discrete_subtype_mark
	  ( 'range' range )?
	;


// SPARK95 5.6


// SPARK95 5.7
exit_statement
	: 'exit' simple_name?
	  ( 'when' condition )? ';' 
	;

// added
simple_name
	: name
	;
	

// SPARK95 5.8


// SPARK95 6.1
subprogram_declaration
	: procedure_specification ';'
	  procedure_annotation
	| function_specification ';'
	  function_annotation
	;

procedure_specification
	: 'procedure' defining_identifier
	  parameter_profile
	;

function_specification
	: 'function' defining_designator
	  parameter_and_result_profile
	;

designator
	: IDENTIFIER
	;

defining_designator
	: defining_identifier
	;

// modified
defining_program_unit_name
  : name
//    ( parent_unit_name '.' )?
//    defining_identifier	
  ;

operator_symbol
	: STRING_LITERAL
	;

defining_operator_symbol
	: operator_symbol
	;

parameter_profile
	: formal_part?
	;

parameter_and_result_profile
	: formal_part?
	  'return' subtype_mark
	;

formal_part
	: '(' parameter_specification
	  ( ';' parameter_specification )* ')'
	;

parameter_specification
	: defining_identifier_list ':'
	  mode subtype_mark
	;

mode
	: 'in'?
	| 'in' 'out'
	| 'out'
	;


// SPARK95 6.1.1
procedure_annotation
	: global_definition?
	  dependency_relation?
	;

function_annotation
	: global_definition?
	;


// SPARK95 6.1.2
global_definition
	: //ANNOTATION+
	  'global'
	  ( global_mode? global_variable_list ';' )+
	;

global_mode
	: 'in' 
	| 'in' 'out'
	| 'out'
	;

global_variable_list
	: global_variable 
	  ( ',' global_variable )*
	;

global_variable
	: entire_variable
	;

// modified
entire_variable
	: name 
	// ( package_name '.' )? direct_name
	;

// added
package_name
	: name
	;

dependency_relation
	: //ANNOTATION+
	  'derives'
  	( dependency_clause
	      ( '&' dependency_clause )*
	      ( '&' null_dependency_clause )?
	  )? ';'
	| 'derives' 
	  null_dependency_clause ';'
	;

dependency_clause
	: exported_variable_list
	  'from'
	  imported_variable_list?
	;

exported_variable_list
	: exported_variable
	  ( ',' exported_variable )*
	;

exported_variable
	: entire_variable
	;

imported_variable_list
	: '*'
	| ( '*' ',' )?
	  imported_variable
	  ( ',' imported_variable )*
	;

imported_variable
	: entire_variable
	;

null_dependency_clause
	: 'null' 'from'
	  imported_variable
	  ( ',' imported_variable )*
	;


// SPARK95 6.3
subprogram_body
	: procedure_specification
	  procedure_annotation?
	  'is'
	  subprogram_implementation
	| function_specification
	  function_annotation?
	  'is'
	  subprogram_implementation
	;

subprogram_implementation
	: declarative_part
	  'begin' sequence_of_statements
	  'end' designator ';'
	| 'begin' code_insertion
	  'end' designator ';'
	;

code_insertion
	: code_statement+
	;


// SPARK95 6.4
procedure_call_statement
	: procedure_name 
	  actual_parameter_part?
	  ';'
	;

// added
procedure_name
	: name
	;

/* left-refactored in name
function_call
	: function_name
	  actual_parameter_part?
	;
*/

// added
function_name
	: name
	;

actual_parameter_part
	: '(' parameter_association_list ')'
	;

parameter_association_list
	: named_parameter_association_list
	| positional_parameter_association_list
	;

named_parameter_association_list
	: formal_parameter_selector_name
		'=>' explicit_actual_parameter
		( ',' formal_parameter_selector_name
		  '=>' explicit_actual_parameter )*
	;

// added
formal_parameter_selector_name
	: selector_name
	;

positional_parameter_association_list
	: explicit_actual_parameter
	  ( ',' explicit_actual_parameter )*
	;

// modified
explicit_actual_parameter
	: expression
	// ambiguous | variable_name 
	;


// SPARK95 6.5
return_statement
	: 'return' expression ';'
	;
	

// SPARK95 7.1
package_declaration
	: inherit_clause?
	  package_specification 
	  ';'
	;

private_package_declaration 
	: inherit_clause?
	  'private' package_specification
	  ';'	
	;

// modified
package_specification
	: 'package' defining_program_unit_name
	  package_annotation 
	  'is'
	  visible_part
	  ( 'private' private_part )?
	  'end' name 
	  //( parent_unit_name '.' )?
	  //IDENTIFIER
	;

visible_part
	: renaming_declaration*
	  package_declarative_item*
	;

private_part
	: renaming_declaration*
	  package_declarative_item*
	;

package_declarative_item
	: basic_declarative_item
	| subprogram_declaration
	| external_subprogram_declaration
	;


// SPARK95 7.1.1
inherit_clause
	: //ANNOTATION+ 
	  'inherit'
	  package_name
	  ( ',' package_name )*
	  ';'
	;

// SPARK95 7.1.2
package_annotation
	: ( own_variable_clause
	    initialization_specification? )?
	;


// SPARK95 7.1.3
own_variable_clause
	: //ANNOTATION+
	  'own' own_variable_list ';'
	;

own_variable_list
	: mode own_variable
	  ( ',' mode own_variable )*
	;

own_variable
	: direct_name
	;


// SPARK95 7.1.4
initialization_specification
	: //ANNOTATION+
	  'initializes'
	  own_variable_list ';'
	;

// SPARK95 7.2 (modified)
package_body
	: 'package' 'body'
	  defining_program_unit_name
	  refinement_definition?
	  'is'
	  package_implementation
	  'end' name
	  //( parent_unit_name '.' )?
	  //IDENTIFIER 
	  ';'
	;

package_implementation
	: declarative_part
	  ( 'begin' package_initialization )?
	;

package_initialization
	: sequence_of_statements
	;


// SPARK95 7.2.1
refinement_definition
	: //ANNOTATION+
	  'own' refinement_clause
	  ( '&' refinement_clause )* ';'
	;

refinement_clause
	: subject 'is' constituent_list
	;

subject
	: direct_name
	;

constituent_list
	: mode constituent
	  ( ',' mode constituent )*
	;

// modified
constituent
	: name
	// ( package_name '.' )? direct_name
	;


// SPARK95 7.3
private_type_declaration
	: 'type' defining_identifier
	  'is' 'tagged'? 'limited'?
	  'private'? ';'
	;

private_extension_declaration
	: 'type' defining_identifier
	  'is' 'new'
	  ancestor_subtype_indication
	  'with' 'private' ';'
	;

ancestor_subtype_indication
	: subtype_indication
	;


// SPARK95 8.4
use_type_clause
	: 'use' 'type' subtype_mark
	  ( ',' subtype_mark )* ';'
	;


// SPARK95 8.5
renaming_declaration
	: package_renaming_declaration
	| subprogram_renaming_declaration
	;


// SPARK95 8.5.1 - 8.5.2


// SPARK95 8.5.3 (modified)
package_renaming_declaration
	:	'package' defining_program_unit_name
	  'renames' name 
	  //parent_unit_name
	  //'.' package_direct_name 
	  ';'
	;

// added
package_direct_name
	: direct_name
	;


// SPARK95 8.5.4 (modified)
subprogram_renaming_declaration
	: 'function' defining_operator_symbol
	  formal_part 'return' subtype_mark
	  'renames' package_name '.' 
	  operator_symbol ';'
	| function_specification
	  'renames' name
	  //package_name '.'
	  //function_direct_name 
	  ';'
	| procedure_specification
		'renames' name
		//package_name '.'
		//procedure_direct_name 
		';'
	;

// added
function_direct_name
	: direct_name
	;

// added
procedure_direct_name
	: direct_name
	;


// SPARK95 8.5.5


// SPARK95 9.1


// SPARK95 10.1.1
compilation returns [ Compilation result = new Compilation() ]
	: compilation_unit*
	;

compilation_unit
	: context_clause library_item
	| context_clause subunit 
	;

library_item
	: library_unit_declaration
	| library_unit_body
	;

library_unit_declaration
	: package_declaration
	| private_package_declaration
	| main_program
	;

library_unit_body
	: package_body
	;

parent_unit_name
	: name
	;

main_program
	: inherit_clause?
	  main_program_annotation
	  subprogram_body
	;

main_program_annotation
	: //ANNOTATION+
	  'main_program' ';'
	;


// SPARK95 10.1.2
context_clause
	: context_item*
	;

context_item
	: with_clause
	| use_type_clause
	;

with_clause
	: 'with' library_package_name
	  ( ',' library_package_name )*
	  ';'
	;

// added
library_package_name
	: name
	;
	

// SPARK95 10.1.3
body_stub
	: subprogram_body_stub
	| package_body_stub
	;

subprogram_body_stub
	: procedure_specification
	  procedure_annotation?
	  'is' 'separate' ';'
	| function_specification
	  function_annotation?
	  'is' 'separate' ';'
	;

package_body_stub
	: 'package' 'body'
	  defining_identifier
	  'is' 'separate' ';'
	;

subunit
	: 'separate' '('
	  parent_unit_name
	  ')' proper_body
	;


// SPARK95 11.1 - 12.2


// SPARK95 12.3
generic_instantiation
	: 'function' defining_designator
	  'is' 'new'
	  generic_function_name
	  generic_actual_part?
	;

// added
generic_function_name
	: name
	;

generic_actual_part
	: '(' generic_association
	  ( ',' generic_association )* ')'
	;

generic_association
	: ( generic_formal_parameter_selector_name
	    '=>' )?
	  explicit_generic_actual_parameter
	;

// added
generic_formal_parameter_selector_name
	: selector_name
	;

explicit_generic_actual_parameter
	: subtype_mark
	;


// SPARK95 12.4 - 12.7


// SPARK95 13.1
representation_clause
	: attribute_definition_clause
	| enumeration_representation_clause
	| record_representation_clause
	| at_clause
	;

// modified
local_name
	: /* ambiguous
	  direct_name
	| direct_name CHARACTER_LITERAL_OR_QUOTE attribute_designator
	| library_unit_name */
          name
  ;

// added
library_unit_name
	: name
	;


// SPARK95 13.3 (modified)
attribute_definition_clause
	: 'for' name /* ambiguous local_name CHARACTER_LITERAL_OR_QUOTE
	  attribute_designator */
	  'use' simple_expression ';'
	;


// SPARK95 13.4
enumeration_representation_clause
	: 'for' first_subtype_local_name
	  'use' enumeration_aggregate ';'
	;

// addded
first_subtype_local_name
	: local_name
	;

enumeration_aggregate
	: array_aggregate ';'
	;


// SPARK95 13.5
at_clause
	: 'for' simple_name
	  'use' 'at' simple_expression
	  ';'
	;


// SPARK95 13.5.1
record_representation_clause
	: 'for' first_subtype_local_name
	  'use' 'record' mod_clause?
	  component_clause*
	  'end' 'record' ';'
	;

component_clause
	: component_local_name 'at'
	  position 'range'
	  first_bit '..' last_bit ';'
	;

// added
component_local_name
	: local_name
	;

position
	: static_simple_expression
	;

first_bit
	: static_simple_expression
	;

last_bit
	: static_simple_expression
	;

mod_clause
	: 'at' 'mod' simple_expression ';'
	;


// SPARK95 13.8
code_statement
	: qualified_expression ';'
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
	
	
// SPARK95 2.7 (modified)
COMMENT
	: '--' ~( '\n' | '\r' | '#' ) 
	  ~( '\n' | '\r' )* 
	  '\r'? '\n'                          { $channel=HIDDEN; }
	;

ANNOTATION
	: '--#'                               { $channel=HIDDEN; }
	;

WS
	:  ( ' ' | '\r' | '\t' 
	   | '\u000C' | '\n' )                { $channel=HIDDEN; }
	;
