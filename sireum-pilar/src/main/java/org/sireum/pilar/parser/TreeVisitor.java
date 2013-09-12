/*
Copyright (c) 2011 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser;

import org.antlr.runtime.tree.Tree;

public class TreeVisitor<G> {

  G context;

  public TreeVisitor(final G context) {
    this.context = context;
  }

  protected boolean defaultCase(final Tree t) {
    return true;
  }

  public void visit(final Tree t) {
    switch (t.getType()) {
      case 108:
        if (visitANNOTATED_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 6:
        if (visitMODEL(t)) {
          visitChildren(t);
        }
        return;
      case 151:
        if (visitTX_Exponent(t)) {
          visitChildren(t);
        }
        return;
      case 83:
        if (visitFLOAT(t)) {
          visitChildren(t);
        }
        return;
      case 34:
        if (visitEXT_PARAM_VARIABLE(t)) {
          visitChildren(t);
        }
        return;
      case 40:
        if (visitELSE_GUARD(t)) {
          visitChildren(t);
        }
        return;
      case 59:
        if (visitCATCH_CLAUSE(t)) {
          visitChildren(t);
        }
        return;
      case 26:
        if (visitEXTENSION(t)) {
          visitChildren(t);
        }
        return;
      case 71:
        if (visitCALL(t)) {
          visitChildren(t);
        }
        return;
      case 148:
        if (visitTX_BASICID(t)) {
          visitChildren(t);
        }
        return;
      case 145:
        if (visitBIN_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 76:
        if (visitDEC_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 113:
        if (visitLET_BINDING(t)) {
          visitChildren(t);
        }
        return;
      case 61:
        if (visitIF_THEN_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 36:
        if (visitLOCAL(t)) {
          visitChildren(t);
        }
        return;
      case 152:
        if (visitTX_FloatTypeSuffix(t)) {
          visitChildren(t);
        }
        return;
      case 134:
        if (visitEQ_OP(t)) {
          visitChildren(t);
        }
        return;
      case 125:
        if (visitTYPEVARID(t)) {
          visitChildren(t);
        }
        return;
      case 15:
        if (visitRECORD(t)) {
          visitChildren(t);
        }
        return;
      case 17:
        if (visitATTRIBUTE(t)) {
          visitChildren(t);
        }
        return;
      case 75:
        if (visitHEX_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 39:
        if (visitEXP_GUARD(t)) {
          visitChildren(t);
        }
        return;
      case 56:
        if (visitSWITCH_CASE_JUMP(t)) {
          visitChildren(t);
        }
        return;
      case 136:
        if (visitREL_OP(t)) {
          visitChildren(t);
        }
        return;
      case 18:
        if (visitATTRIBUTE_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 50:
        if (visitGOTO(t)) {
          visitChildren(t);
        }
        return;
      case 12:
        if (visitENUM(t)) {
          visitChildren(t);
        }
        return;
      case 150:
        if (visitTX_IntTypeSuffix(t)) {
          visitChildren(t);
        }
        return;
      case 98:
        if (visitMAPPING(t)) {
          visitChildren(t);
        }
        return;
      case 13:
        if (visitENUM_ELEMENT(t)) {
          visitChildren(t);
        }
        return;
      case 60:
        if (visitIF_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 144:
        if (visitRAW_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 132:
        if (visitXOR_OP(t)) {
          visitChildren(t);
        }
        return;
      case 161:
        if (visitTX_OctalEscape(t)) {
          visitChildren(t);
        }
        return;
      case 163:
        if (visitCOMMENT(t)) {
          visitChildren(t);
        }
        return;
      case 95:
        if (visitNFUNCTION(t)) {
          visitChildren(t);
        }
        return;
      case 115:
        if (visitNEW_MULTI_ARRAY_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 44:
        if (visitRHS(t)) {
          visitChildren(t);
        }
        return;
      case 4:
        if (visitOPTION(t)) {
          visitChildren(t);
        }
        return;
      case 27:
        if (visitTYPEVAR(t)) {
          visitChildren(t);
        }
        return;
      case 84:
        if (visitRATIONAL(t)) {
          visitChildren(t);
        }
        return;
      case 116:
        if (visitTYPE(t)) {
          visitChildren(t);
        }
        return;
      case 117:
        if (visitARRAY_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 88:
        if (visitNLIST_RANGE(t)) {
          visitChildren(t);
        }
        return;
      case 43:
        if (visitLHS(t)) {
          visitChildren(t);
        }
        return;
      case 42:
        if (visitASSIGN(t)) {
          visitChildren(t);
        }
        return;
      case 130:
        if (visitCOND_AND_OP(t)) {
          visitChildren(t);
        }
        return;
      case 97:
        if (visitFUN(t)) {
          visitChildren(t);
        }
        return;
      case 74:
        if (visitNULL(t)) {
          visitChildren(t);
        }
        return;
      case 109:
        if (visitPROCEDURE_TYPE_PARAM_VARIABLE(t)) {
          visitChildren(t);
        }
        return;
      case 129:
        if (visitCOND_OR_OP(t)) {
          visitChildren(t);
        }
        return;
      case 20:
        if (visitGLOBAL_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 101:
        if (visitINT(t)) {
          visitChildren(t);
        }
        return;
      case 7:
        if (visitANNOTATION(t)) {
          visitChildren(t);
        }
        return;
      case 123:
        if (visitANN_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 22:
        if (visitPARAM(t)) {
          visitChildren(t);
        }
        return;
      case 91:
        if (visitNMULTI_ARRAY_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 9:
        if (visitPACKAGE(t)) {
          visitChildren(t);
        }
        return;
      case 35:
        if (visitBODY(t)) {
          visitChildren(t);
        }
        return;
      case 139:
        if (visitMUL_OP(t)) {
          visitChildren(t);
        }
        return;
      case 128:
        if (visitACTION_EXT_OP(t)) {
          visitChildren(t);
        }
        return;
      case 30:
        if (visitACTION_EXT(t)) {
          visitChildren(t);
        }
        return;
      case 70:
        if (visitACCESS(t)) {
          visitChildren(t);
        }
        return;
      case 5:
        if (visitLIST(t)) {
          visitChildren(t);
        }
        return;
      case 89:
        if (visitNLIST(t)) {
          visitChildren(t);
        }
        return;
      case 158:
        if (visitTX_Letter(t)) {
          visitChildren(t);
        }
        return;
      case 85:
        if (visitCHAR(t)) {
          visitChildren(t);
        }
        return;
      case 90:
        if (visitNMULTI_ARRAY(t)) {
          visitChildren(t);
        }
        return;
      case 138:
        if (visitADD_OP(t)) {
          visitChildren(t);
        }
        return;
      case 72:
        if (visitTRUE(t)) {
          visitChildren(t);
        }
        return;
      case 92:
        if (visitNRECORD(t)) {
          visitChildren(t);
        }
        return;
      case 45:
        if (visitASSERT(t)) {
          visitChildren(t);
        }
        return;
      case 81:
        if (visitNAME(t)) {
          visitChildren(t);
        }
        return;
      case 99:
        if (visitMATCHING(t)) {
          visitChildren(t);
        }
        return;
      case 66:
        if (visitBINARY(t)) {
          visitChildren(t);
        }
        return;
      case 96:
        if (visitFUN_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 21:
        if (visitPROCEDURE(t)) {
          visitChildren(t);
        }
        return;
      case 16:
        if (visitEXTENDCLAUSE_ELEMENT(t)) {
          visitChildren(t);
        }
        return;
      case 38:
        if (visitLOCATION(t)) {
          visitChildren(t);
        }
        return;
      case 65:
        if (visitSWITCH_DEFAULT_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 23:
        if (visitPARAM_VARIABLE(t)) {
          visitChildren(t);
        }
        return;
      case 142:
        if (visitSTRING_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 68:
        if (visitCAST(t)) {
          visitChildren(t);
        }
        return;
      case 127:
        if (visitLOCID(t)) {
          visitChildren(t);
        }
        return;
      case 156:
        if (visitTX_OPID_CHARMLT(t)) {
          visitChildren(t);
        }
        return;
      case 80:
        if (visitNAME_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 102:
        if (visitNAME_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 112:
        if (visitLET_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 28:
        if (visitTYPE_EXT(t)) {
          visitChildren(t);
        }
        return;
      case 120:
        if (visitSET_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 87:
        if (visitTYPE_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 153:
        if (visitTX_EscapeSequence(t)) {
          visitChildren(t);
        }
        return;
      case 131:
        if (visitOR_OP(t)) {
          visitChildren(t);
        }
        return;
      case 47:
        if (visitTHROW(t)) {
          visitChildren(t);
        }
        return;
      case 133:
        if (visitAND_OP(t)) {
          visitChildren(t);
        }
        return;
      case 24:
        if (visitVSET(t)) {
          visitChildren(t);
        }
        return;
      case 11:
        if (visitCONST_ELEMENT(t)) {
          visitChildren(t);
        }
        return;
      case 10:
        if (visitCONST(t)) {
          visitChildren(t);
        }
        return;
      case 77:
        if (visitOCT_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 104:
        if (visitTUPLE_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 135:
        if (visitCOLON_OP(t)) {
          visitChildren(t);
        }
        return;
      case 154:
        if (visitTX_OPID_SUFFIX(t)) {
          visitChildren(t);
        }
        return;
      case 143:
        if (visitSYMBOL_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 114:
        if (visitNEW_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 107:
        if (visitTYPE_PARAM(t)) {
          visitChildren(t);
        }
        return;
      case 62:
        if (visitIF_ELSE_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 137:
        if (visitSHIFT_OP(t)) {
          visitChildren(t);
        }
        return;
      case 14:
        if (visitTYPEALIAS(t)) {
          visitChildren(t);
        }
        return;
      case 55:
        if (visitSWITCH_JUMP(t)) {
          visitChildren(t);
        }
        return;
      case 54:
        if (visitIF_ELSE_JUMP(t)) {
          visitChildren(t);
        }
        return;
      case 105:
        if (visitFUN_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 79:
        if (visitTUPLE(t)) {
          visitChildren(t);
        }
        return;
      case 126:
        if (visitGLOBALID(t)) {
          visitChildren(t);
        }
        return;
      case 69:
        if (visitINDEXING(t)) {
          visitChildren(t);
        }
        return;
      case 46:
        if (visitASSUME(t)) {
          visitChildren(t);
        }
        return;
      case 49:
        if (visitACTION_EXT_CALL(t)) {
          visitChildren(t);
        }
        return;
      case 31:
        if (visitEXP_EXT(t)) {
          visitChildren(t);
        }
        return;
      case 146:
        if (visitFLOAT_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 199:
        if (visitT__199(t)) {
          visitChildren(t);
        }
        return;
      case 63:
        if (visitSWITCH_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 198:
        if (visitT__198(t)) {
          visitChildren(t);
        }
        return;
      case 197:
        if (visitT__197(t)) {
          visitChildren(t);
        }
        return;
      case 196:
        if (visitT__196(t)) {
          visitChildren(t);
        }
        return;
      case 195:
        if (visitT__195(t)) {
          visitChildren(t);
        }
        return;
      case 194:
        if (visitT__194(t)) {
          visitChildren(t);
        }
        return;
      case 193:
        if (visitT__193(t)) {
          visitChildren(t);
        }
        return;
      case 192:
        if (visitT__192(t)) {
          visitChildren(t);
        }
        return;
      case 191:
        if (visitT__191(t)) {
          visitChildren(t);
        }
        return;
      case 190:
        if (visitT__190(t)) {
          visitChildren(t);
        }
        return;
      case 51:
        if (visitRETURN(t)) {
          visitChildren(t);
        }
        return;
      case 162:
        if (visitWS(t)) {
          visitChildren(t);
        }
        return;
      case 164:
        if (visitLINE_COMMENT(t)) {
          visitChildren(t);
        }
        return;
      case 73:
        if (visitFALSE(t)) {
          visitChildren(t);
        }
        return;
      case 122:
        if (visitRAW(t)) {
          visitChildren(t);
        }
        return;
      case 86:
        if (visitSYMBOL(t)) {
          visitChildren(t);
        }
        return;
      case 121:
        if (visitTYPEVAR_TUPLE(t)) {
          visitChildren(t);
        }
        return;
      case 118:
        if (visitLIST_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 111:
        if (visitRELATION_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 78:
        if (visitSTRING(t)) {
          visitChildren(t);
        }
        return;
      case 224:
        if (visitT__224(t)) {
          visitChildren(t);
        }
        return;
      case 223:
        if (visitT__223(t)) {
          visitChildren(t);
        }
        return;
      case 222:
        if (visitT__222(t)) {
          visitChildren(t);
        }
        return;
      case 221:
        if (visitT__221(t)) {
          visitChildren(t);
        }
        return;
      case 189:
        if (visitT__189(t)) {
          visitChildren(t);
        }
        return;
      case 220:
        if (visitT__220(t)) {
          visitChildren(t);
        }
        return;
      case 188:
        if (visitT__188(t)) {
          visitChildren(t);
        }
        return;
      case 187:
        if (visitT__187(t)) {
          visitChildren(t);
        }
        return;
      case 186:
        if (visitT__186(t)) {
          visitChildren(t);
        }
        return;
      case 185:
        if (visitT__185(t)) {
          visitChildren(t);
        }
        return;
      case 110:
        if (visitFUNCTION_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 184:
        if (visitT__184(t)) {
          visitChildren(t);
        }
        return;
      case 183:
        if (visitT__183(t)) {
          visitChildren(t);
        }
        return;
      case 41:
        if (visitTRANSFORMATION(t)) {
          visitChildren(t);
        }
        return;
      case 147:
        if (visitRATIONAL_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 119:
        if (visitMULTIARRAY_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 100:
        if (visitANN_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 182:
        if (visitT__182(t)) {
          visitChildren(t);
        }
        return;
      case 181:
        if (visitT__181(t)) {
          visitChildren(t);
        }
        return;
      case 149:
        if (visitTX_HexDigit(t)) {
          visitChildren(t);
        }
        return;
      case 180:
        if (visitT__180(t)) {
          visitChildren(t);
        }
        return;
      case 58:
        if (visitCALL_JUMP(t)) {
          visitChildren(t);
        }
        return;
      case 29:
        if (visitTYPE_EXT_ATTRIBUTE_BINDING(t)) {
          visitChildren(t);
        }
        return;
      case 159:
        if (visitTX_JavaIDDigit(t)) {
          visitChildren(t);
        }
        return;
      case 37:
        if (visitLOCAL_FRAGMENT(t)) {
          visitChildren(t);
        }
        return;
      case 94:
        if (visitNSET(t)) {
          visitChildren(t);
        }
        return;
      case 32:
        if (visitPROC_EXT(t)) {
          visitChildren(t);
        }
        return;
      case 155:
        if (visitTX_OPID_CHAR(t)) {
          visitChildren(t);
        }
        return;
      case 48:
        if (visitSTART(t)) {
          visitChildren(t);
        }
        return;
      case 33:
        if (visitEXT_PARAM(t)) {
          visitChildren(t);
        }
        return;
      case 219:
        if (visitT__219(t)) {
          visitChildren(t);
        }
        return;
      case 218:
        if (visitT__218(t)) {
          visitChildren(t);
        }
        return;
      case 217:
        if (visitT__217(t)) {
          visitChildren(t);
        }
        return;
      case 216:
        if (visitT__216(t)) {
          visitChildren(t);
        }
        return;
      case 215:
        if (visitT__215(t)) {
          visitChildren(t);
        }
        return;
      case 93:
        if (visitATTR_INIT(t)) {
          visitChildren(t);
        }
        return;
      case 214:
        if (visitT__214(t)) {
          visitChildren(t);
        }
        return;
      case 213:
        if (visitT__213(t)) {
          visitChildren(t);
        }
        return;
      case 212:
        if (visitT__212(t)) {
          visitChildren(t);
        }
        return;
      case 211:
        if (visitT__211(t)) {
          visitChildren(t);
        }
        return;
      case 179:
        if (visitT__179(t)) {
          visitChildren(t);
        }
        return;
      case 210:
        if (visitT__210(t)) {
          visitChildren(t);
        }
        return;
      case 178:
        if (visitT__178(t)) {
          visitChildren(t);
        }
        return;
      case 177:
        if (visitT__177(t)) {
          visitChildren(t);
        }
        return;
      case 176:
        if (visitT__176(t)) {
          visitChildren(t);
        }
        return;
      case 175:
        if (visitT__175(t)) {
          visitChildren(t);
        }
        return;
      case 174:
        if (visitT__174(t)) {
          visitChildren(t);
        }
        return;
      case 173:
        if (visitT__173(t)) {
          visitChildren(t);
        }
        return;
      case 172:
        if (visitT__172(t)) {
          visitChildren(t);
        }
        return;
      case 171:
        if (visitT__171(t)) {
          visitChildren(t);
        }
        return;
      case 157:
        if (visitTX_OPID_CHARMGT(t)) {
          visitChildren(t);
        }
        return;
      case 141:
        if (visitCHAR_LIT(t)) {
          visitChildren(t);
        }
        return;
      case 106:
        if (visitPROCEDURE_TYPE(t)) {
          visitChildren(t);
        }
        return;
      case 170:
        if (visitT__170(t)) {
          visitChildren(t);
        }
        return;
      case 57:
        if (visitSWITCH_DEFAULT_JUMP(t)) {
          visitChildren(t);
        }
        return;
      case 52:
        if (visitIF_JUMP(t)) {
          visitChildren(t);
        }
        return;
      case 124:
        if (visitID(t)) {
          visitChildren(t);
        }
        return;
      case 53:
        if (visitIF_THEN_JUMP(t)) {
          visitChildren(t);
        }
        return;
      case 209:
        if (visitT__209(t)) {
          visitChildren(t);
        }
        return;
      case 208:
        if (visitT__208(t)) {
          visitChildren(t);
        }
        return;
      case 64:
        if (visitSWITCH_CASE_EXP(t)) {
          visitChildren(t);
        }
        return;
      case 207:
        if (visitT__207(t)) {
          visitChildren(t);
        }
        return;
      case 25:
        if (visitVSET_ELEMENT(t)) {
          visitChildren(t);
        }
        return;
      case 206:
        if (visitT__206(t)) {
          visitChildren(t);
        }
        return;
      case 205:
        if (visitT__205(t)) {
          visitChildren(t);
        }
        return;
      case 204:
        if (visitT__204(t)) {
          visitChildren(t);
        }
        return;
      case 203:
        if (visitT__203(t)) {
          visitChildren(t);
        }
        return;
      case 202:
        if (visitT__202(t)) {
          visitChildren(t);
        }
        return;
      case 201:
        if (visitT__201(t)) {
          visitChildren(t);
        }
        return;
      case 169:
        if (visitT__169(t)) {
          visitChildren(t);
        }
        return;
      case 200:
        if (visitT__200(t)) {
          visitChildren(t);
        }
        return;
      case 168:
        if (visitT__168(t)) {
          visitChildren(t);
        }
        return;
      case 140:
        if (visitUN_OP(t)) {
          visitChildren(t);
        }
        return;
      case 167:
        if (visitT__167(t)) {
          visitChildren(t);
        }
        return;
      case 166:
        if (visitT__166(t)) {
          visitChildren(t);
        }
        return;
      case 165:
        if (visitT__165(t)) {
          visitChildren(t);
        }
        return;
      case 103:
        if (visitTYPE_TUPLE(t)) {
          visitChildren(t);
        }
        return;
      case 67:
        if (visitUNARY(t)) {
          visitChildren(t);
        }
        return;
      case 8:
        if (visitANNOTATION_PARAM_IDED(t)) {
          visitChildren(t);
        }
        return;
      case 19:
        if (visitGLOBAL(t)) {
          visitChildren(t);
        }
        return;
      case 160:
        if (visitTX_UnicodeEscape(t)) {
          visitChildren(t);
        }
        return;
      case 82:
        if (visitTYPEVARID_TYPE(t)) {
          visitChildren(t);
        }
        return;
      default:
        defaultCase(t);
    }
  }

  protected boolean visitACCESS(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitACTION_EXT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitACTION_EXT_CALL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitACTION_EXT_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitADD_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitAND_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitANN_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitANN_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitANNOTATED_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitANNOTATION(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitANNOTATION_PARAM_IDED(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitARRAY_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitASSERT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitASSIGN(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitASSUME(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitATTR_INIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitATTRIBUTE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitATTRIBUTE_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitBIN_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitBINARY(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitBODY(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCALL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCALL_JUMP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCAST(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCATCH_CLAUSE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCHAR(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCHAR_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected void visitChildren(final Tree t) {
    final int count = t.getChildCount();
    for (int i = 0; i < count; i++) {
      visit(t.getChild(i));
    }
  }

  protected boolean visitCOLON_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCOMMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCOND_AND_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCOND_OR_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCONST(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitCONST_ELEMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitDEC_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitELSE_GUARD(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitENUM(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitENUM_ELEMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitEQ_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitEXP_EXT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitEXP_GUARD(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitEXT_PARAM(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitEXT_PARAM_VARIABLE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitEXTENDCLAUSE_ELEMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitEXTENSION(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitFALSE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitFLOAT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitFLOAT_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitFUN(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitFUN_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitFUN_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitFUNCTION_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitGLOBAL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitGLOBAL_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitGLOBALID(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitGOTO(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitHEX_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitID(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitIF_ELSE_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitIF_ELSE_JUMP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitIF_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitIF_JUMP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitIF_THEN_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitIF_THEN_JUMP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitINDEXING(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitINT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLET_BINDING(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLET_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLHS(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLINE_COMMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLIST(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLIST_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLOCAL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLOCAL_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLOCATION(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitLOCID(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitMAPPING(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitMATCHING(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitMODEL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitMUL_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitMULTIARRAY_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNAME(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNAME_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNAME_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNEW_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNEW_MULTI_ARRAY_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNFUNCTION(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNLIST(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNLIST_RANGE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNMULTI_ARRAY(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNMULTI_ARRAY_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNRECORD(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNSET(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitNULL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitOCT_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitOPTION(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitOR_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitPACKAGE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitPARAM(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitPARAM_VARIABLE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitPROC_EXT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitPROCEDURE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitPROCEDURE_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitPROCEDURE_TYPE_PARAM_VARIABLE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRATIONAL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRATIONAL_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRAW(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRAW_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRECORD(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitREL_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRELATION_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRETURN(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitRHS(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSET_FRAGMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSHIFT_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSTART(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSTRING(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSTRING_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSWITCH_CASE_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSWITCH_CASE_JUMP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSWITCH_DEFAULT_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSWITCH_DEFAULT_JUMP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSWITCH_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSWITCH_JUMP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSYMBOL(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitSYMBOL_LIT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__165(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__166(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__167(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__168(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__169(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__170(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__171(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__172(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__173(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__174(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__175(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__176(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__177(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__178(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__179(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__180(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__181(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__182(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__183(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__184(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__185(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__186(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__187(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__188(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__189(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__190(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__191(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__192(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__193(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__194(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__195(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__196(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__197(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__198(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__199(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__200(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__201(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__202(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__203(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__204(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__205(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__206(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__207(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__208(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__209(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__210(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__211(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__212(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__213(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__214(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__215(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__216(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__217(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__218(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__219(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__220(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__221(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__222(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__223(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitT__224(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTHROW(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTRANSFORMATION(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTRUE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTUPLE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTUPLE_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_BASICID(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_EscapeSequence(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_Exponent(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_FloatTypeSuffix(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_HexDigit(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_IntTypeSuffix(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_JavaIDDigit(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_Letter(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_OctalEscape(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_OPID_CHAR(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_OPID_CHARMGT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_OPID_CHARMLT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_OPID_SUFFIX(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTX_UnicodeEscape(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPE_EXP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPE_EXT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPE_EXT_ATTRIBUTE_BINDING(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPE_PARAM(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPE_TUPLE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPEALIAS(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPEVAR(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPEVAR_TUPLE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPEVARID(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitTYPEVARID_TYPE(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitUN_OP(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitUNARY(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitVSET(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitVSET_ELEMENT(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitWS(final Tree t) {
    return defaultCase(t);
  }

  protected boolean visitXOR_OP(final Tree t) {
    return defaultCase(t);
  }
}
