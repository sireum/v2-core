// $ANTLR 3.4 /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g 2012-04-17 16:51:32

package org.sireum.bakar.compiler.parser;

/**
 * SPARK lexer.
 * 
 * @author robby
 * @author hoag
 * @author belt
 */

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

@SuppressWarnings({ "all", "warnings", "unchecked" })
public class SPARKLexer extends Lexer {
  class DFA16 extends DFA {

    public DFA16(final BaseRecognizer recognizer) {
      this.recognizer = recognizer;
      this.decisionNumber = 16;
      this.eot = SPARKLexer.DFA16_eot;
      this.eof = SPARKLexer.DFA16_eof;
      this.min = SPARKLexer.DFA16_min;
      this.max = SPARKLexer.DFA16_max;
      this.accept = SPARKLexer.DFA16_accept;
      this.special = SPARKLexer.DFA16_special;
      this.transition = SPARKLexer.DFA16_transition;
    }

    public String getDescription() {
      return "1:1: Tokens : ( T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | IMPORT | IDENTIFIER | NUMERIC_LITERAL | CHARACTER_LITERAL_OR_QUOTE | STRING_LITERAL | SPARK_ANNOTATION | COMMENT | NEW_LINE | WS );";
    }

    public int specialStateTransition(int s, final IntStream _input)
        throws NoViableAltException {
      final IntStream input = _input;
      final int _s = s;
      switch (s) {
        case 0:
          final int LA16_127 = input.LA(1);

          final int index16_127 = input.index();
          input.rewind();

          s = -1;
          if ((((LA16_127 >= '\u0000') && (LA16_127 <= '\uFFFF')))
              && ((!((input.index() + 3) < input.size()) || ((char) input.LA(3) != '#')))) {
            s = 128;
          } else {
            s = 197;
          }

          input.seek(index16_127);

          if (s >= 0) {
            return s;
          }
          break;

        case 1:
          final int LA16_52 = input.LA(1);

          final int index16_52 = input.index();
          input.rewind();

          s = -1;
          if ((LA16_52 == '#')) {
            s = 127;
          }

          else if ((((LA16_52 >= '\u0000') && (LA16_52 <= '\"')) || ((LA16_52 >= '$') && (LA16_52 <= '\uFFFF')))
              && ((!((input.index() + 3) < input.size()) || ((char) input.LA(3) != '#')))) {
            s = 128;
          }

          input.seek(index16_52);

          if (s >= 0) {
            return s;
          }
          break;
      }
      if (SPARKLexer.this.state.backtracking > 0) {
        SPARKLexer.this.state.failed = true;
        return -1;
      }

      final NoViableAltException nvae = new NoViableAltException(
          getDescription(), 16, _s, input);
      error(nvae);
      throw nvae;
    }

  }

  class DFA3 extends DFA {

    public DFA3(final BaseRecognizer recognizer) {
      this.recognizer = recognizer;
      this.decisionNumber = 3;
      this.eot = SPARKLexer.DFA3_eot;
      this.eof = SPARKLexer.DFA3_eof;
      this.min = SPARKLexer.DFA3_min;
      this.max = SPARKLexer.DFA3_max;
      this.accept = SPARKLexer.DFA3_accept;
      this.special = SPARKLexer.DFA3_special;
      this.transition = SPARKLexer.DFA3_transition;
    }

    public String getDescription() {
      return "3149:1: NUMERIC_LITERAL : ( DECIMAL_LITERAL | BASED_LITERAL );";
    }
  }

  public static final int EOF = -1;
  public static final int T__25 = 25;
  public static final int T__26 = 26;
  public static final int T__27 = 27;
  public static final int T__28 = 28;
  public static final int T__29 = 29;
  public static final int T__30 = 30;
  public static final int T__31 = 31;
  public static final int T__32 = 32;
  public static final int T__33 = 33;
  public static final int T__34 = 34;
  public static final int T__35 = 35;
  public static final int T__36 = 36;
  public static final int T__37 = 37;
  public static final int T__38 = 38;
  public static final int T__39 = 39;
  public static final int T__40 = 40;
  public static final int T__41 = 41;
  public static final int T__42 = 42;
  public static final int T__43 = 43;
  public static final int T__44 = 44;
  public static final int T__45 = 45;
  public static final int T__46 = 46;
  public static final int T__47 = 47;
  public static final int T__48 = 48;
  public static final int T__49 = 49;
  public static final int T__50 = 50;
  public static final int T__51 = 51;
  public static final int T__52 = 52;
  public static final int T__53 = 53;
  public static final int T__54 = 54;
  public static final int T__55 = 55;
  public static final int T__56 = 56;
  public static final int T__57 = 57;
  public static final int T__58 = 58;
  public static final int T__59 = 59;
  public static final int T__60 = 60;
  public static final int T__61 = 61;
  public static final int T__62 = 62;
  public static final int T__63 = 63;
  public static final int T__64 = 64;
  public static final int T__65 = 65;
  public static final int T__66 = 66;
  public static final int T__67 = 67;
  public static final int T__68 = 68;
  public static final int T__69 = 69;
  public static final int T__70 = 70;
  public static final int T__71 = 71;
  public static final int T__72 = 72;
  public static final int T__73 = 73;
  public static final int T__74 = 74;
  public static final int T__75 = 75;
  public static final int T__76 = 76;
  public static final int T__77 = 77;
  public static final int T__78 = 78;
  public static final int T__79 = 79;
  public static final int T__80 = 80;
  public static final int T__81 = 81;
  public static final int T__82 = 82;
  public static final int T__83 = 83;
  public static final int T__84 = 84;
  public static final int T__85 = 85;
  public static final int T__86 = 86;
  public static final int T__87 = 87;
  public static final int T__88 = 88;
  public static final int T__89 = 89;
  public static final int T__90 = 90;
  public static final int T__91 = 91;
  public static final int T__92 = 92;
  public static final int T__93 = 93;
  public static final int T__94 = 94;
  public static final int T__95 = 95;
  public static final int T__96 = 96;
  public static final int T__97 = 97;
  public static final int T__98 = 98;
  public static final int T__99 = 99;
  public static final int T__100 = 100;
  public static final int T__101 = 101;
  public static final int T__102 = 102;
  public static final int T__103 = 103;
  public static final int T__104 = 104;
  public static final int T__105 = 105;
  public static final int T__106 = 106;
  public static final int T__107 = 107;
  public static final int T__108 = 108;
  public static final int T__109 = 109;
  public static final int T__110 = 110;
  public static final int T__111 = 111;
  public static final int T__112 = 112;
  public static final int T__113 = 113;
  public static final int T__114 = 114;
  public static final int T__115 = 115;
  public static final int T__116 = 116;
  public static final int T__117 = 117;
  public static final int T__118 = 118;
  public static final int T__119 = 119;
  public static final int T__120 = 120;
  public static final int T__121 = 121;
  public static final int T__122 = 122;
  public static final int T__123 = 123;
  public static final int BASE = 4;
  public static final int BASED_LITERAL = 5;
  public static final int BASED_NUMERAL = 6;
  public static final int CHARACTER_LITERAL_OR_QUOTE = 7;
  public static final int COMMENT = 8;
  public static final int DECIMAL_LITERAL = 9;
  public static final int DIGIT = 10;
  public static final int EXPONENT = 11;
  public static final int EXTENDED_DIGIT = 12;
  public static final int IDENTIFIER = 13;
  public static final int IDENTIFIER_LETTER = 14;
  public static final int IMPORT = 15;
  public static final int LETTER_OR_DIGIT = 16;
  public static final int LOWER_CASE_IDENTIFIER_LETTER = 17;
  public static final int NEW_LINE = 18;
  public static final int NUMERAL = 19;
  public static final int NUMERIC_LITERAL = 20;
  public static final int SPARK_ANNOTATION = 21;
  public static final int STRING_LITERAL = 22;

  public static final int UPPER_CASE_IDENTIFIER_LETTER = 23;

  public static final int WS = 24;
  protected DFA3 dfa3 = new DFA3(this);
  protected DFA16 dfa16 = new DFA16(this);
  static final String DFA3_eotS = "\1\uffff\1\2\2\uffff\1\2\1\uffff";

  static final String DFA3_eofS = "\6\uffff";

  static final String DFA3_minS = "\1\60\1\43\1\uffff\1\60\1\43\1\uffff";

  static final String DFA3_maxS = "\1\71\1\137\1\uffff\1\71\1\137\1\uffff";

  static final String DFA3_acceptS = "\2\uffff\1\1\2\uffff\1\2";

  static final String DFA3_specialS = "\6\uffff}>";

  static final String[] DFA3_transitionS = { "\12\1",
      "\1\5\14\uffff\12\4\45\uffff\1\3", "", "\12\4",
      "\1\5\14\uffff\12\4\45\uffff\1\3", "" };

  static final short[] DFA3_eot = DFA.unpackEncodedString(SPARKLexer.DFA3_eotS);

  static final short[] DFA3_eof = DFA.unpackEncodedString(SPARKLexer.DFA3_eofS);

  static final char[] DFA3_min = DFA
      .unpackEncodedStringToUnsignedChars(SPARKLexer.DFA3_minS);

  static final char[] DFA3_max = DFA
      .unpackEncodedStringToUnsignedChars(SPARKLexer.DFA3_maxS);

  static final short[] DFA3_accept = DFA
      .unpackEncodedString(SPARKLexer.DFA3_acceptS);

  static final short[] DFA3_special = DFA
      .unpackEncodedString(SPARKLexer.DFA3_specialS);

  static final short[][] DFA3_transition;

  static {
    final int numStates = SPARKLexer.DFA3_transitionS.length;
    DFA3_transition = new short[numStates][];
    for (int i = 0; i < numStates; i++) {
      SPARKLexer.DFA3_transition[i] = DFA
          .unpackEncodedString(SPARKLexer.DFA3_transitionS[i]);
    }
  }

  static final String DFA16_eotS = "\4\uffff\1\62\2\uffff\1\65\1\67\1\71\1\73\1\uffff\1\100\1\102\1"
      + "\105\4\53\2\uffff\23\53\2\uffff\1\53\33\uffff\13\53\1\u008c\16\53"
      + "\1\u009c\1\u009f\1\u00a0\7\53\1\u00a8\1\u00a9\23\53\1\u00c5\1\uffff"
      + "\5\53\1\u00cc\1\53\1\u00ce\1\u00cf\2\53\1\uffff\11\53\1\u00dc\1"
      + "\53\1\u00de\3\53\1\uffff\2\53\2\uffff\3\53\1\u00e7\1\u00e8\1\u00e9"
      + "\1\53\2\uffff\1\53\1\u00ec\1\u00ed\3\53\1\u00f1\4\53\1\u00f6\11"
      + "\53\1\u0100\3\53\1\u0104\1\53\1\uffff\6\53\1\uffff\1\53\2\uffff"
      + "\3\53\1\u0110\1\u0111\5\53\1\u0117\1\53\1\uffff\1\u0119\1\uffff"
      + "\1\u011a\5\53\1\u0120\1\53\3\uffff\1\u0122\1\53\2\uffff\1\53\1\u0125"
      + "\1\53\1\uffff\4\53\1\uffff\4\53\1\u012f\2\53\1\u0132\1\u0133\1\uffff"
      + "\1\u0134\1\53\1\u0136\1\uffff\1\53\1\u0138\1\53\1\uffff\1\u013b"
      + "\3\53\1\u013f\1\53\1\u0141\2\uffff\1\u0142\1\53\1\u0144\2\53\1\uffff"
      + "\1\u0147\2\uffff\5\53\3\uffff\2\53\1\uffff\3\53\1\u0153\5\53\1\uffff"
      + "\2\53\3\uffff\1\u015b\1\uffff\1\53\1\uffff\1\u015d\1\53\1\uffff"
      + "\2\53\1\u0161\1\uffff\1\u0162\2\uffff\1\53\1\uffff\1\53\1\u0165"
      + "\1\uffff\1\53\1\u0167\4\53\1\u016c\1\53\1\u016e\2\53\1\uffff\1\u0171"
      + "\1\53\1\u0173\3\53\1\u0177\1\uffff\1\u0178\1\uffff\3\53\2\uffff"
      + "\1\53\1\u017d\1\uffff\1\53\1\uffff\1\u017f\1\53\1\u0181\1\53\1\uffff"
      + "\1\u0183\1\uffff\1\u0184\1\53\1\uffff\1\u0186\1\uffff\1\u0187\1"
      + "\53\1\u0189\2\uffff\1\53\1\uffff\1\u018c\1\u018d\1\uffff\1\u018e"
      + "\1\uffff\1\53\1\uffff\1\53\2\uffff\1\53\2\uffff\1\u0192\1\uffff"
      + "\2\53\3\uffff\2\53\1\u0197\1\uffff\4\53\1\uffff\2\53\1\u019e\1\53"
      + "\1\u01a0\1\53\1\uffff\1\u01a2\1\uffff\1\53\1\uffff\1\53\1\u01a5"
      + "\1\uffff";

  static final String DFA16_eofS = "\u01a6\uffff";

  static final String DFA16_minS = "\1\11\3\uffff\1\52\2\uffff\1\55\1\56\2\75\1\uffff\1\55\1\76\1\75"
      + "\1\145\1\154\2\141\2\uffff\1\142\1\145\1\141\1\145\1\154\1\157\1"
      + "\154\1\146\1\151\1\141\1\145\1\146\2\141\1\145\1\141\1\163\1\150"
      + "\1\157\2\uffff\1\155\11\uffff\1\0\21\uffff\1\154\1\147\1\157\1\156"
      + "\1\162\1\163\1\143\1\154\1\144\1\162\1\163\1\60\1\147\1\144\1\163"
      + "\1\145\1\156\1\154\1\147\1\163\1\144\1\151\1\162\1\157\1\156\1\157"
      + "\3\60\1\155\1\157\1\151\1\144\1\167\1\164\1\154\2\60\1\150\1\164"
      + "\1\156\1\143\1\163\1\141\1\156\1\143\1\160\1\155\1\142\1\147\1\145"
      + "\1\160\2\145\1\164\1\162\1\160\1\0\1\uffff\1\164\1\151\1\167\1\147"
      + "\1\156\1\60\1\145\2\60\1\141\1\145\1\uffff\1\151\1\171\1\145\1\143"
      + "\1\163\1\164\2\151\1\145\1\60\1\164\1\60\1\155\1\143\1\142\1\uffff"
      + "\1\145\1\164\2\uffff\1\151\1\160\1\156\3\60\1\154\2\uffff\1\145"
      + "\2\60\1\153\1\164\1\147\1\60\1\166\1\143\1\147\1\157\1\60\1\141"
      + "\1\165\1\145\1\141\1\145\1\164\1\147\1\156\1\145\1\60\1\156\1\154"
      + "\1\150\1\60\1\157\1\uffff\1\141\1\164\1\137\1\145\1\151\1\162\1"
      + "\uffff\1\160\2\uffff\1\171\1\162\1\156\2\60\1\153\1\164\1\141\1"
      + "\166\1\164\1\60\1\146\1\uffff\1\60\1\uffff\1\60\1\164\1\141\1\162"
      + "\1\151\1\164\1\60\1\137\3\uffff\1\60\1\162\2\uffff\1\141\1\60\1"
      + "\155\1\uffff\1\141\2\145\1\162\1\uffff\1\155\3\162\1\60\1\171\1"
      + "\145\2\60\1\uffff\1\60\1\145\1\60\1\uffff\1\162\1\60\1\163\2\60"
      + "\1\156\1\141\1\164\1\60\1\164\1\60\2\uffff\1\60\1\141\1\60\1\145"
      + "\1\163\1\uffff\1\60\2\uffff\1\151\1\154\1\151\1\141\1\145\1\uffff"
      + "\1\60\1\uffff\1\163\1\147\1\uffff\1\141\1\164\1\144\1\60\1\144\1"
      + "\145\1\156\1\163\1\141\1\uffff\1\160\1\144\3\uffff\1\60\1\uffff"
      + "\1\164\1\uffff\1\60\1\145\1\uffff\1\147\1\143\1\60\1\uffff\1\60"
      + "\2\uffff\1\156\1\uffff\1\163\1\60\1\uffff\1\157\1\60\1\164\1\154"
      + "\1\144\1\162\1\60\1\145\1\60\1\145\1\165\1\uffff\1\60\1\163\1\60"
      + "\1\145\1\164\1\145\1\60\1\uffff\1\60\1\uffff\1\163\1\137\1\164\2"
      + "\uffff\1\164\1\60\1\uffff\1\156\1\uffff\1\60\1\151\1\60\1\157\1"
      + "\uffff\1\60\1\uffff\1\60\1\162\1\uffff\1\60\1\uffff\1\60\1\145\1"
      + "\60\2\uffff\1\163\3\60\1\uffff\1\60\1\uffff\1\172\1\uffff\1\147"
      + "\2\uffff\1\145\2\uffff\1\60\1\uffff\1\141\1\145\3\uffff\1\145\1"
      + "\162\1\60\1\uffff\1\147\2\163\1\141\1\uffff\1\145\1\163\1\60\1\155"
      + "\1\60\1\141\1\uffff\1\60\1\uffff\1\147\1\uffff\1\145\1\60\1\uffff";

  static final String DFA16_maxS = "\1\176\3\uffff\1\52\2\uffff\1\76\1\56\2\75\1\uffff\3\76\1\151\1"
      + "\154\2\141\2\uffff\1\164\2\157\1\151\1\170\1\165\1\154\1\163\2\157"
      + "\1\165\1\167\1\162\1\145\1\165\1\171\1\163\1\151\1\157\2\uffff\1"
      + "\155\11\uffff\1\uffff\21\uffff\1\154\1\147\1\157\1\156\1\162\1\163"
      + "\1\143\1\154\1\144\1\162\1\163\1\172\1\147\1\144\1\163\1\145\1\156"
      + "\1\162\1\147\1\163\1\144\1\151\1\162\1\157\1\156\1\157\3\172\1\155"
      + "\1\157\1\151\1\144\1\167\1\164\1\154\2\172\1\150\1\164\1\156\1\143"
      + "\1\163\1\157\1\156\1\166\1\160\1\155\1\142\1\147\1\145\1\160\1\145"
      + "\1\151\1\164\1\162\1\160\1\uffff\1\uffff\1\164\1\151\1\167\1\147"
      + "\1\156\1\172\1\145\2\172\1\141\1\145\1\uffff\1\151\1\171\1\145\1"
      + "\143\1\163\1\164\3\151\1\172\1\164\1\172\1\155\1\143\1\142\1\uffff"
      + "\1\145\1\164\2\uffff\1\151\1\160\1\156\3\172\1\154\2\uffff\1\145"
      + "\2\172\1\153\1\164\1\147\1\172\1\166\1\143\1\147\1\157\1\172\1\141"
      + "\1\165\1\145\1\141\1\145\1\164\1\147\1\156\1\145\1\172\1\156\1\154"
      + "\1\150\1\172\1\157\1\uffff\1\141\1\164\1\137\1\145\1\151\1\162\1"
      + "\uffff\1\160\2\uffff\1\171\1\162\1\156\2\172\1\153\1\164\1\141\1"
      + "\166\1\164\1\172\1\146\1\uffff\1\172\1\uffff\1\172\1\164\1\141\1"
      + "\162\1\151\1\164\1\172\1\137\3\uffff\1\172\1\162\2\uffff\1\141\1"
      + "\172\1\155\1\uffff\1\141\2\145\1\162\1\uffff\1\155\3\162\1\172\1"
      + "\171\1\145\2\172\1\uffff\1\172\1\145\1\172\1\uffff\1\162\1\172\1"
      + "\163\2\172\1\156\1\141\1\164\1\172\1\164\1\172\2\uffff\1\172\1\141"
      + "\1\172\1\145\1\163\1\uffff\1\172\2\uffff\1\151\1\154\1\151\1\141"
      + "\1\145\1\uffff\1\172\1\uffff\1\163\1\147\1\uffff\1\141\1\164\1\144"
      + "\1\172\1\144\1\145\1\156\1\163\1\141\1\uffff\1\160\1\144\3\uffff"
      + "\1\172\1\uffff\1\164\1\uffff\1\172\1\145\1\uffff\1\147\1\143\1\172"
      + "\1\uffff\1\172\2\uffff\1\156\1\uffff\1\163\1\172\1\uffff\1\157\1"
      + "\172\1\164\1\154\1\144\1\162\1\172\1\145\1\172\1\145\1\165\1\uffff"
      + "\1\172\1\163\1\172\1\145\1\164\1\145\1\172\1\uffff\1\172\1\uffff"
      + "\1\163\1\137\1\164\2\uffff\1\164\1\172\1\uffff\1\156\1\uffff\1\172"
      + "\1\151\1\172\1\157\1\uffff\1\172\1\uffff\1\172\1\162\1\uffff\1\172"
      + "\1\uffff\1\172\1\145\1\172\2\uffff\1\163\3\172\1\uffff\1\172\1\uffff"
      + "\1\172\1\uffff\1\147\2\uffff\1\145\2\uffff\1\172\1\uffff\1\141\1"
      + "\145\3\uffff\1\145\1\162\1\172\1\uffff\1\147\2\163\1\141\1\uffff"
      + "\1\145\1\163\1\172\1\155\1\172\1\141\1\uffff\1\172\1\uffff\1\147"
      + "\1\uffff\1\145\1\172\1\uffff";

  static final String DFA16_acceptS = "\1\uffff\1\1\1\2\1\3\1\uffff\1\6\1\7\4\uffff\1\20\7\uffff\1\40\1"
      + "\41\23\uffff\1\142\1\143\1\uffff\1\145\1\146\1\147\1\150\1\153\1"
      + "\154\1\5\1\4\1\11\1\uffff\1\10\1\13\1\12\1\15\1\14\1\17\1\16\1\22"
      + "\1\23\1\24\1\25\1\21\1\27\1\26\1\31\1\32\1\30\72\uffff\1\152\13"
      + "\uffff\1\51\17\uffff\1\72\2\uffff\1\73\1\76\7\uffff\1\106\1\107"
      + "\33\uffff\1\151\6\uffff\1\42\1\uffff\1\45\1\46\14\uffff\1\64\1\uffff"
      + "\1\66\10\uffff\1\102\1\103\1\104\2\uffff\1\111\1\112\3\uffff\1\116"
      + "\4\uffff\1\123\11\uffff\1\135\3\uffff\1\141\13\uffff\1\53\1\54\5"
      + "\uffff\1\62\1\uffff\1\65\1\67\5\uffff\1\100\1\uffff\1\105\2\uffff"
      + "\1\114\11\uffff\1\130\2\uffff\1\133\1\134\1\136\1\uffff\1\140\1"
      + "\uffff\1\33\2\uffff\1\36\3\uffff\1\47\1\uffff\1\52\1\55\1\uffff"
      + "\1\57\2\uffff\1\63\13\uffff\1\121\7\uffff\1\137\1\uffff\1\34\3\uffff"
      + "\1\44\1\50\2\uffff\1\61\1\uffff\1\71\4\uffff\1\110\1\uffff\1\115"
      + "\2\uffff\1\122\1\uffff\1\125\3\uffff\1\132\1\144\4\uffff\1\60\1"
      + "\uffff\1\74\1\uffff\1\77\1\uffff\1\113\1\117\1\uffff\1\124\1\126"
      + "\1\uffff\1\131\2\uffff\1\43\1\56\1\70\3\uffff\1\127\4\uffff\1\120"
      + "\6\uffff\1\75\1\uffff\1\35\1\uffff\1\101\2\uffff\1\37";

  static final String DFA16_specialS = "\64\uffff\1\1\112\uffff\1\0\u0126\uffff}>";

  static final String[] DFA16_transitionS = {
      "\1\60\1\57\1\uffff\1\60\1\57\22\uffff\1\60\1\uffff\1\56\3\uffff"
          + "\1\1\1\55\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\12\54\1\12\1\13"
          + "\1\14\1\15\1\16\2\uffff\3\53\1\17\1\53\1\20\2\53\1\52\10\53"
          + "\1\21\4\53\1\22\3\53\1\23\1\uffff\1\24\3\uffff\1\25\1\26\1\27"
          + "\1\30\1\31\1\32\1\33\1\53\1\34\2\53\1\35\1\36\1\37\1\40\1\41"
          + "\1\53\1\42\1\43\1\44\1\45\1\53\1\46\1\47\2\53\1\uffff\1\50\1"
          + "\uffff\1\51",
      "",
      "",
      "",
      "\1\61",
      "",
      "",
      "\1\64\20\uffff\1\63",
      "\1\66",
      "\1\70",
      "\1\72",
      "",
      "\1\74\16\uffff\1\75\1\76\1\77",
      "\1\101",
      "\1\103\1\104",
      "\1\106\3\uffff\1\107",
      "\1\110",
      "\1\111",
      "\1\112",
      "",
      "",
      "\1\113\1\114\10\uffff\1\115\1\uffff\1\116\3\uffff\1\117\1\120"
          + "\1\121",
      "\1\122\11\uffff\1\123",
      "\1\124\6\uffff\1\125\6\uffff\1\126",
      "\1\127\3\uffff\1\130",
      "\1\131\1\uffff\1\132\11\uffff\1\133",
      "\1\134\2\uffff\1\135\2\uffff\1\136",
      "\1\137",
      "\1\140\7\uffff\1\141\4\uffff\1\142",
      "\1\143\5\uffff\1\144",
      "\1\145\15\uffff\1\146",
      "\1\147\11\uffff\1\150\5\uffff\1\151",
      "\1\152\13\uffff\1\153\1\uffff\1\154\1\155\1\uffff\1\156",
      "\1\157\15\uffff\1\160\2\uffff\1\161",
      "\1\162\3\uffff\1\163",
      "\1\164\11\uffff\1\165\5\uffff\1\166",
      "\1\167\6\uffff\1\170\20\uffff\1\171",
      "\1\172",
      "\1\173\1\174",
      "\1\175",
      "",
      "",
      "\1\176",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "\43\u0080\1\177\uffdc\u0080",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "",
      "\1\u0081",
      "\1\u0082",
      "\1\u0083",
      "\1\u0084",
      "\1\u0085",
      "\1\u0086",
      "\1\u0087",
      "\1\u0088",
      "\1\u0089",
      "\1\u008a",
      "\1\u008b",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\1\u008d",
      "\1\u008e",
      "\1\u008f",
      "\1\u0090",
      "\1\u0091",
      "\1\u0092\5\uffff\1\u0093",
      "\1\u0094",
      "\1\u0095",
      "\1\u0096",
      "\1\u0097",
      "\1\u0098",
      "\1\u0099",
      "\1\u009a",
      "\1\u009b",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\7\53\1\u009d\1\u009e"
          + "\21\53", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\1\u00a1", "\1\u00a2", "\1\u00a3", "\1\u00a4", "\1\u00a5", "\1\u00a6",
      "\1\u00a7", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00aa",
      "\1\u00ab", "\1\u00ac", "\1\u00ad", "\1\u00ae",
      "\1\u00af\3\uffff\1\u00b0\3\uffff\1\u00b1\5\uffff\1\u00b2", "\1\u00b3",
      "\1\u00b4\11\uffff\1\u00b5\1\u00b6\5\uffff\1\u00b7\1\uffff\1" + "\u00b8",
      "\1\u00b9", "\1\u00ba", "\1\u00bb", "\1\u00bc", "\1\u00bd", "\1\u00be",
      "\1\u00bf", "\1\u00c0\3\uffff\1\u00c1", "\1\u00c2", "\1\u00c3",
      "\1\u00c4", "\0\u0080", "", "\1\u00c6", "\1\u00c7", "\1\u00c8",
      "\1\u00c9", "\1\u00ca",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\23\53\1\u00cb\6\53",
      "\1\u00cd", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00d0",
      "\1\u00d1", "", "\1\u00d2", "\1\u00d3", "\1\u00d4", "\1\u00d5",
      "\1\u00d6", "\1\u00d7", "\1\u00d8", "\1\u00d9",
      "\1\u00da\3\uffff\1\u00db",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00dd",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00df",
      "\1\u00e0", "\1\u00e1", "", "\1\u00e2", "\1\u00e3", "", "", "\1\u00e4",
      "\1\u00e5", "\1\u00e6",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00ea", "", "",
      "\1\u00eb", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00ee",
      "\1\u00ef", "\1\u00f0",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00f2",
      "\1\u00f3", "\1\u00f4", "\1\u00f5",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u00f7",
      "\1\u00f8", "\1\u00f9", "\1\u00fa", "\1\u00fb", "\1\u00fc", "\1\u00fd",
      "\1\u00fe", "\1\u00ff",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0101",
      "\1\u0102", "\1\u0103",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0105", "",
      "\1\u0106", "\1\u0107", "\1\u0108", "\1\u0109", "\1\u010a", "\1\u010b",
      "", "\1\u010c", "", "", "\1\u010d", "\1\u010e", "\1\u010f",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0112",
      "\1\u0113", "\1\u0114", "\1\u0115", "\1\u0116",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0118", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u011b",
      "\1\u011c", "\1\u011d", "\1\u011e", "\1\u011f",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0121", "", "",
      "", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0123", "",
      "", "\1\u0124", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\1\u0126", "", "\1\u0127", "\1\u0128", "\1\u0129", "\1\u012a", "",
      "\1\u012b", "\1\u012c", "\1\u012d", "\1\u012e",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0130",
      "\1\u0131", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0135",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u0137",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0139",
      "\12\53\7\uffff\14\53\1\u013a\15\53\6\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u013c",
      "\1\u013d", "\1\u013e",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0140",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0143",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0145",
      "\1\u0146", "", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "", "\1\u0148", "\1\u0149", "\1\u014a", "\1\u014b", "\1\u014c", "",
      "\12\53\7\uffff\32\53\6\uffff\17\53\1\u014d\12\53", "", "\1\u014e",
      "\1\u014f", "", "\1\u0150", "\1\u0151", "\1\u0152",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0154",
      "\1\u0155", "\1\u0156", "\1\u0157", "\1\u0158", "", "\1\u0159",
      "\1\u015a", "", "", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u015c", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u015e", "",
      "\1\u015f", "\1\u0160",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "", "\1\u0163",
      "", "\1\u0164", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\1\u0166", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\1\u0168", "\1\u0169", "\1\u016a", "\1\u016b",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u016d",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u016f",
      "\1\u0170", "", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\1\u0172", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\1\u0174", "\1\u0175", "\1\u0176",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u0179",
      "\1\u017a", "\1\u017b", "", "", "\1\u017c",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u017e", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0180",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0182", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0185", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u0188",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "", "\1\u018a",
      "\12\53\7\uffff\14\53\1\u018b\15\53\6\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u018f", "",
      "\1\u0190", "", "", "\1\u0191", "", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u0193",
      "\1\u0194", "", "", "", "\1\u0195", "\1\u0196",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u0198",
      "\1\u0199", "\1\u019a", "\1\u019b", "", "\1\u019c", "\1\u019d",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u019f",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "\1\u01a1", "",
      "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "", "\1\u01a3", "",
      "\1\u01a4", "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53", "" };

  static final short[] DFA16_eot = DFA
      .unpackEncodedString(SPARKLexer.DFA16_eotS);

  static final short[] DFA16_eof = DFA
      .unpackEncodedString(SPARKLexer.DFA16_eofS);

  static final char[] DFA16_min = DFA
      .unpackEncodedStringToUnsignedChars(SPARKLexer.DFA16_minS);

  static final char[] DFA16_max = DFA
      .unpackEncodedStringToUnsignedChars(SPARKLexer.DFA16_maxS);

  static final short[] DFA16_accept = DFA
      .unpackEncodedString(SPARKLexer.DFA16_acceptS);

  static final short[] DFA16_special = DFA
      .unpackEncodedString(SPARKLexer.DFA16_specialS);

  static final short[][] DFA16_transition;

  static {
    final int numStates = SPARKLexer.DFA16_transitionS.length;
    DFA16_transition = new short[numStates][];
    for (int i = 0; i < numStates; i++) {
      SPARKLexer.DFA16_transition[i] = DFA
          .unpackEncodedString(SPARKLexer.DFA16_transitionS[i]);
    }
  }

  public SPARKLexer() {
  }

  public SPARKLexer(final CharStream input) {
    this(input, new RecognizerSharedState());
  }

  public SPARKLexer(final CharStream input, final RecognizerSharedState state) {
    super(input, state);
  }

  // delegates
  // delegators
  public Lexer[] getDelegates() {
    return new Lexer[] {};
  }

  public String getGrammarFileName() {
    return "/Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g";
  }

  // $ANTLR start "BASE"
  public final void mBASE() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3181:3: ( NUMERAL )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3181:5: NUMERAL
      {
        mNUMERAL();
        if (this.state.failed) {
          return;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "BASE"

  // $ANTLR start "BASED_LITERAL"
  public final void mBASED_LITERAL() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3176:3: ( BASE '#' BASED_NUMERAL '#' ( EXPONENT )? )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3176:5: BASE '#' BASED_NUMERAL '#' ( EXPONENT )?
      {
        mBASE();
        if (this.state.failed) {
          return;
        }

        match('#');
        if (this.state.failed) {
          return;
        }

        mBASED_NUMERAL();
        if (this.state.failed) {
          return;
        }

        match('#');
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3176:32: ( EXPONENT )?
        int alt9 = 2;
        final int LA9_0 = this.input.LA(1);

        if ((LA9_0 == 'E')) {
          alt9 = 1;
        }
        switch (alt9) {
          case 1:
          // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3176:32: EXPONENT
          {
            mEXPONENT();
            if (this.state.failed) {
              return;
            }

          }
            break;

        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "BASED_LITERAL"

  // $ANTLR start "BASED_NUMERAL"
  public final void mBASED_NUMERAL() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3186:3: ( EXTENDED_DIGIT ( ( '_' )? EXTENDED_DIGIT )* )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3186:5: EXTENDED_DIGIT ( ( '_' )? EXTENDED_DIGIT )*
      {
        mEXTENDED_DIGIT();
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3186:20: ( ( '_' )? EXTENDED_DIGIT )*
        loop11: do {
          int alt11 = 2;
          final int LA11_0 = this.input.LA(1);

          if ((((LA11_0 >= '0') && (LA11_0 <= '9'))
              || ((LA11_0 >= 'A') && (LA11_0 <= 'F')) || (LA11_0 == '_'))) {
            alt11 = 1;
          }

          switch (alt11) {
            case 1:
            // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3186:22: ( '_' )? EXTENDED_DIGIT
            {
              // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3186:22: ( '_' )?
              int alt10 = 2;
              final int LA10_0 = this.input.LA(1);

              if ((LA10_0 == '_')) {
                alt10 = 1;
              }
              switch (alt10) {
                case 1:
                // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3186:22: '_'
                {
                  match('_');
                  if (this.state.failed) {
                    return;
                  }

                }
                  break;

              }

              mEXTENDED_DIGIT();
              if (this.state.failed) {
                return;
              }

            }
              break;

            default:
              break loop11;
          }
        } while (true);

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "BASED_NUMERAL"

  // $ANTLR start "CHARACTER_LITERAL_OR_QUOTE"
  public final void mCHARACTER_LITERAL_OR_QUOTE() throws RecognitionException {
    try {
      final int _type = SPARKLexer.CHARACTER_LITERAL_OR_QUOTE;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3195:3: ( ( '\\'' . '\\'' )=> '\\'' . '\\'' | '\\'' )
      int alt12 = 2;
      final int LA12_0 = this.input.LA(1);

      if ((LA12_0 == '\'')) {
        final int LA12_1 = this.input.LA(2);

        if ((((LA12_1 >= '\u0000') && (LA12_1 <= '\uFFFF')))
            && (synpred2_SPARK())) {
          alt12 = 1;
        } else {
          alt12 = 2;
        }
      } else {
        if (this.state.backtracking > 0) {
          this.state.failed = true;
          return;
        }
        final NoViableAltException nvae = new NoViableAltException("", 12, 0,
            this.input);

        throw nvae;

      }
      switch (alt12) {
        case 1:
        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3195:5: ( '\\'' . '\\'' )=> '\\'' . '\\''
        {
          match('\'');
          if (this.state.failed) {
            return;
          }

          matchAny();
          if (this.state.failed) {
            return;
          }

          match('\'');
          if (this.state.failed) {
            return;
          }

        }
          break;
        case 2:
        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3196:5: '\\''
        {
          match('\'');
          if (this.state.failed) {
            return;
          }

        }
          break;

      }
      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "CHARACTER_LITERAL_OR_QUOTE"

  // $ANTLR start "COMMENT"
  public final void mCOMMENT() throws RecognitionException {
    try {
      final int _type = SPARKLexer.COMMENT;
      int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3210:3: ({...}? => '--' ( (~ ( '\\r' | '\\n' ) )* ( '\\r' )? '\\n' ) )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3213:5: {...}? => '--' ( (~ ( '\\r' | '\\n' ) )* ( '\\r' )? '\\n' )
      {
        if (!((!((this.input.index() + 3) < this.input.size()) || ((char) this.input
            .LA(3) != '#')))) {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          throw new FailedPredicateException(this.input, "COMMENT",
              "!(input.index() + 3 < input.size()) || ((char) input.LA(3) != '#') ");
        }

        match("--");
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3215:5: ( (~ ( '\\r' | '\\n' ) )* ( '\\r' )? '\\n' )
        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3216:7: (~ ( '\\r' | '\\n' ) )* ( '\\r' )? '\\n'
        {
          // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3216:7: (~ ( '\\r' | '\\n' ) )*
          loop14: do {
            int alt14 = 2;
            final int LA14_0 = this.input.LA(1);

            if ((((LA14_0 >= '\u0000') && (LA14_0 <= '\t'))
                || ((LA14_0 >= '\u000B') && (LA14_0 <= '\f')) || ((LA14_0 >= '\u000E') && (LA14_0 <= '\uFFFF')))) {
              alt14 = 1;
            }

            switch (alt14) {
              case 1:
              // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
              {
                if (((this.input.LA(1) >= '\u0000') && (this.input.LA(1) <= '\t'))
                    || ((this.input.LA(1) >= '\u000B') && (this.input.LA(1) <= '\f'))
                    || ((this.input.LA(1) >= '\u000E') && (this.input.LA(1) <= '\uFFFF'))) {
                  this.input.consume();
                  this.state.failed = false;
                } else {
                  if (this.state.backtracking > 0) {
                    this.state.failed = true;
                    return;
                  }
                  final MismatchedSetException mse = new MismatchedSetException(
                      null, this.input);
                  recover(mse);
                  throw mse;
                }

              }
                break;

              default:
                break loop14;
            }
          } while (true);

          // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3217:7: ( '\\r' )?
          int alt15 = 2;
          final int LA15_0 = this.input.LA(1);

          if ((LA15_0 == '\r')) {
            alt15 = 1;
          }
          switch (alt15) {
            case 1:
            // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3217:7: '\\r'
            {
              match('\r');
              if (this.state.failed) {
                return;
              }

            }
              break;

          }

          match('\n');
          if (this.state.failed) {
            return;
          }

        }

        if (this.state.backtracking == 0) {
          _channel = BaseRecognizer.HIDDEN;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "COMMENT"

  // $ANTLR start "DECIMAL_LITERAL"
  public final void mDECIMAL_LITERAL() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:3: ( NUMERAL ( ( '.' DIGIT )=> '.' NUMERAL )? ( EXPONENT )? )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:5: NUMERAL ( ( '.' DIGIT )=> '.' NUMERAL )? ( EXPONENT )?
      {
        mNUMERAL();
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:13: ( ( '.' DIGIT )=> '.' NUMERAL )?
        int alt4 = 2;
        final int LA4_0 = this.input.LA(1);

        if ((LA4_0 == '.') && (synpred1_SPARK())) {
          alt4 = 1;
        }
        switch (alt4) {
          case 1:
          // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:15: ( '.' DIGIT )=> '.' NUMERAL
          {
            match('.');
            if (this.state.failed) {
              return;
            }

            mNUMERAL();
            if (this.state.failed) {
              return;
            }

          }
            break;

        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:47: ( EXPONENT )?
        int alt5 = 2;
        final int LA5_0 = this.input.LA(1);

        if ((LA5_0 == 'E')) {
          alt5 = 1;
        }
        switch (alt5) {
          case 1:
          // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:47: EXPONENT
          {
            mEXPONENT();
            if (this.state.failed) {
              return;
            }

          }
            break;

        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "DECIMAL_LITERAL"

  // $ANTLR start "DIGIT"
  public final void mDIGIT() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3131:3: ( '0' .. '9' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
      {
        if (((this.input.LA(1) >= '0') && (this.input.LA(1) <= '9'))) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "DIGIT"

  // $ANTLR start "EXPONENT"
  public final void mEXPONENT() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3169:3: ( 'E' ( '+' | '-' )? NUMERAL )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3169:5: 'E' ( '+' | '-' )? NUMERAL
      {
        match('E');
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3169:9: ( '+' | '-' )?
        int alt8 = 2;
        final int LA8_0 = this.input.LA(1);

        if (((LA8_0 == '+') || (LA8_0 == '-'))) {
          alt8 = 1;
        }
        switch (alt8) {
          case 1:
          // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
          {
            if ((this.input.LA(1) == '+') || (this.input.LA(1) == '-')) {
              this.input.consume();
              this.state.failed = false;
            } else {
              if (this.state.backtracking > 0) {
                this.state.failed = true;
                return;
              }
              final MismatchedSetException mse = new MismatchedSetException(
                  null, this.input);
              recover(mse);
              throw mse;
            }

          }
            break;

        }

        mNUMERAL();
        if (this.state.failed) {
          return;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "EXPONENT"

  // $ANTLR start "EXTENDED_DIGIT"
  public final void mEXTENDED_DIGIT() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3191:3: ( DIGIT | 'A' .. 'F' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
      {
        if (((this.input.LA(1) >= '0') && (this.input.LA(1) <= '9'))
            || ((this.input.LA(1) >= 'A') && (this.input.LA(1) <= 'F'))) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "EXTENDED_DIGIT"

  // $ANTLR start "IDENTIFIER"
  public final void mIDENTIFIER() throws RecognitionException {
    try {
      final int _type = SPARKLexer.IDENTIFIER;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3136:3: ( IDENTIFIER_LETTER ( ( '_' )? LETTER_OR_DIGIT )* )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3136:5: IDENTIFIER_LETTER ( ( '_' )? LETTER_OR_DIGIT )*
      {
        mIDENTIFIER_LETTER();
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3137:5: ( ( '_' )? LETTER_OR_DIGIT )*
        loop2: do {
          int alt2 = 2;
          final int LA2_0 = this.input.LA(1);

          if ((((LA2_0 >= '0') && (LA2_0 <= '9'))
              || ((LA2_0 >= 'A') && (LA2_0 <= 'Z')) || (LA2_0 == '_') || ((LA2_0 >= 'a') && (LA2_0 <= 'z')))) {
            alt2 = 1;
          }

          switch (alt2) {
            case 1:
            // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3137:7: ( '_' )? LETTER_OR_DIGIT
            {
              // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3137:7: ( '_' )?
              int alt1 = 2;
              final int LA1_0 = this.input.LA(1);

              if ((LA1_0 == '_')) {
                alt1 = 1;
              }
              switch (alt1) {
                case 1:
                // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3137:7: '_'
                {
                  match('_');
                  if (this.state.failed) {
                    return;
                  }

                }
                  break;

              }

              mLETTER_OR_DIGIT();
              if (this.state.failed) {
                return;
              }

            }
              break;

            default:
              break loop2;
          }
        } while (true);

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "IDENTIFIER"

  // $ANTLR start "IDENTIFIER_LETTER"
  public final void mIDENTIFIER_LETTER() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3115:3: ( UPPER_CASE_IDENTIFIER_LETTER | LOWER_CASE_IDENTIFIER_LETTER )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
      {
        if (((this.input.LA(1) >= 'A') && (this.input.LA(1) <= 'Z'))
            || ((this.input.LA(1) >= 'a') && (this.input.LA(1) <= 'z'))) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "IDENTIFIER_LETTER"

  // $ANTLR start "IMPORT"
  public final void mIMPORT() throws RecognitionException {
    try {
      int _type = SPARKLexer.IMPORT;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3108:3: ( 'Import' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3108:5: 'Import'
      {
        match("Import");
        if (this.state.failed) {
          return;
        }

        if (this.state.backtracking == 0) {
          _type = SPARKLexer.IDENTIFIER;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "IMPORT"

  // $ANTLR start "LETTER_OR_DIGIT"
  public final void mLETTER_OR_DIGIT() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3144:3: ( IDENTIFIER_LETTER | DIGIT )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
      {
        if (((this.input.LA(1) >= '0') && (this.input.LA(1) <= '9'))
            || ((this.input.LA(1) >= 'A') && (this.input.LA(1) <= 'Z'))
            || ((this.input.LA(1) >= 'a') && (this.input.LA(1) <= 'z'))) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "LETTER_OR_DIGIT"

  // $ANTLR start "LOWER_CASE_IDENTIFIER_LETTER"
  public final void mLOWER_CASE_IDENTIFIER_LETTER() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3126:3: ( 'a' .. 'z' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
      {
        if (((this.input.LA(1) >= 'a') && (this.input.LA(1) <= 'z'))) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "LOWER_CASE_IDENTIFIER_LETTER"

  // $ANTLR start "NEW_LINE"
  public final void mNEW_LINE() throws RecognitionException {
    try {
      final int _type = SPARKLexer.NEW_LINE;
      int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3224:3: ( ( '\\r' | '\\n' ) )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3224:5: ( '\\r' | '\\n' )
      {
        if ((this.input.LA(1) == '\n') || (this.input.LA(1) == '\r')) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

        if (this.state.backtracking == 0) {
          _channel = SPARKParser.NEWLINE_CHANNEL;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "NEW_LINE"

  // $ANTLR start "NUMERAL"
  public final void mNUMERAL() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3164:3: ( DIGIT ( ( '_' )? DIGIT )* )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3164:5: DIGIT ( ( '_' )? DIGIT )*
      {
        mDIGIT();
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3164:11: ( ( '_' )? DIGIT )*
        loop7: do {
          int alt7 = 2;
          final int LA7_0 = this.input.LA(1);

          if ((((LA7_0 >= '0') && (LA7_0 <= '9')) || (LA7_0 == '_'))) {
            alt7 = 1;
          }

          switch (alt7) {
            case 1:
            // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3164:13: ( '_' )? DIGIT
            {
              // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3164:13: ( '_' )?
              int alt6 = 2;
              final int LA6_0 = this.input.LA(1);

              if ((LA6_0 == '_')) {
                alt6 = 1;
              }
              switch (alt6) {
                case 1:
                // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3164:13: '_'
                {
                  match('_');
                  if (this.state.failed) {
                    return;
                  }

                }
                  break;

              }

              mDIGIT();
              if (this.state.failed) {
                return;
              }

            }
              break;

            default:
              break loop7;
          }
        } while (true);

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "NUMERAL"

  // $ANTLR start "NUMERIC_LITERAL"
  public final void mNUMERIC_LITERAL() throws RecognitionException {
    try {
      final int _type = SPARKLexer.NUMERIC_LITERAL;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3150:3: ( DECIMAL_LITERAL | BASED_LITERAL )
      int alt3 = 2;
      alt3 = this.dfa3.predict(this.input);
      switch (alt3) {
        case 1:
        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3150:5: DECIMAL_LITERAL
        {
          mDECIMAL_LITERAL();
          if (this.state.failed) {
            return;
          }

        }
          break;
        case 2:
        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3151:5: BASED_LITERAL
        {
          mBASED_LITERAL();
          if (this.state.failed) {
            return;
          }

        }
          break;

      }
      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "NUMERIC_LITERAL"

  // $ANTLR start "SPARK_ANNOTATION"
  public final void mSPARK_ANNOTATION() throws RecognitionException {
    try {
      final int _type = SPARKLexer.SPARK_ANNOTATION;
      int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3205:3: ( '--#' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3205:5: '--#'
      {
        match("--#");
        if (this.state.failed) {
          return;
        }

        if (this.state.backtracking == 0) {
          _channel = SPARKParser.SPARK_CHANNEL;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "SPARK_ANNOTATION"

  // $ANTLR start "STRING_LITERAL"
  public final void mSTRING_LITERAL() throws RecognitionException {
    try {
      final int _type = SPARKLexer.STRING_LITERAL;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3201:3: ( '\"' ( '\"\"' |~ ( '\"' ) )* '\"' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3201:5: '\"' ( '\"\"' |~ ( '\"' ) )* '\"'
      {
        match('\"');
        if (this.state.failed) {
          return;
        }

        // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3201:9: ( '\"\"' |~ ( '\"' ) )*
        loop13: do {
          int alt13 = 3;
          final int LA13_0 = this.input.LA(1);

          if ((LA13_0 == '\"')) {
            final int LA13_1 = this.input.LA(2);

            if ((LA13_1 == '\"')) {
              alt13 = 1;
            }

          } else if ((((LA13_0 >= '\u0000') && (LA13_0 <= '!')) || ((LA13_0 >= '#') && (LA13_0 <= '\uFFFF')))) {
            alt13 = 2;
          }

          switch (alt13) {
            case 1:
            // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3201:11: '\"\"'
            {
              match("\"\"");
              if (this.state.failed) {
                return;
              }

            }
              break;
            case 2:
            // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3201:18: ~ ( '\"' )
            {
              if (((this.input.LA(1) >= '\u0000') && (this.input.LA(1) <= '!'))
                  || ((this.input.LA(1) >= '#') && (this.input.LA(1) <= '\uFFFF'))) {
                this.input.consume();
                this.state.failed = false;
              } else {
                if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
                }
                final MismatchedSetException mse = new MismatchedSetException(
                    null, this.input);
                recover(mse);
                throw mse;
              }

            }
              break;

            default:
              break loop13;
          }
        } while (true);

        match('\"');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "STRING_LITERAL"

  // $ANTLR start "T__100"
  public final void mT__100() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__100;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:89:8: ( 'post' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:89:10: 'post'
      {
        match("post");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__100"

  // $ANTLR start "T__101"
  public final void mT__101() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__101;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:90:8: ( 'pragma' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:90:10: 'pragma'
      {
        match("pragma");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__101"

  // $ANTLR start "T__102"
  public final void mT__102() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__102;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:91:8: ( 'pre' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:91:10: 'pre'
      {
        match("pre");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__102"

  // $ANTLR start "T__103"
  public final void mT__103() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__103;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:92:8: ( 'private' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:92:10: 'private'
      {
        match("private");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__103"

  // $ANTLR start "T__104"
  public final void mT__104() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__104;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:93:8: ( 'procedure' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:93:10: 'procedure'
      {
        match("procedure");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__104"

  // $ANTLR start "T__105"
  public final void mT__105() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__105;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:94:8: ( 'range' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:94:10: 'range'
      {
        match("range");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__105"

  // $ANTLR start "T__106"
  public final void mT__106() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__106;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:95:8: ( 'record' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:95:10: 'record'
      {
        match("record");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__106"

  // $ANTLR start "T__107"
  public final void mT__107() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__107;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:96:8: ( 'rem' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:96:10: 'rem'
      {
        match("rem");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__107"

  // $ANTLR start "T__108"
  public final void mT__108() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__108;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:97:8: ( 'renames' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:97:10: 'renames'
      {
        match("renames");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__108"

  // $ANTLR start "T__109"
  public final void mT__109() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__109;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:98:8: ( 'return' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:98:10: 'return'
      {
        match("return");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__109"

  // $ANTLR start "T__110"
  public final void mT__110() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__110;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:99:8: ( 'reverse' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:99:10: 'reverse'
      {
        match("reverse");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__110"

  // $ANTLR start "T__111"
  public final void mT__111() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__111;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:100:8: ( 'separate' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:100:10: 'separate'
      {
        match("separate");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__111"

  // $ANTLR start "T__112"
  public final void mT__112() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__112;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:101:8: ( 'some' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:101:10: 'some'
      {
        match("some");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__112"

  // $ANTLR start "T__113"
  public final void mT__113() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__113;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:102:8: ( 'subtype' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:102:10: 'subtype'
      {
        match("subtype");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__113"

  // $ANTLR start "T__114"
  public final void mT__114() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__114;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:103:8: ( 'tagged' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:103:10: 'tagged'
      {
        match("tagged");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__114"

  // $ANTLR start "T__115"
  public final void mT__115() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__115;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:104:8: ( 'then' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:104:10: 'then'
      {
        match("then");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__115"

  // $ANTLR start "T__116"
  public final void mT__116() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__116;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:105:8: ( 'type' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:105:10: 'type'
      {
        match("type");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__116"

  // $ANTLR start "T__117"
  public final void mT__117() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__117;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:106:8: ( 'use' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:106:10: 'use'
      {
        match("use");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__117"

  // $ANTLR start "T__118"
  public final void mT__118() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__118;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:107:8: ( 'when' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:107:10: 'when'
      {
        match("when");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__118"

  // $ANTLR start "T__119"
  public final void mT__119() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__119;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:108:8: ( 'while' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:108:10: 'while'
      {
        match("while");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__119"

  // $ANTLR start "T__120"
  public final void mT__120() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__120;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:109:8: ( 'with' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:109:10: 'with'
      {
        match("with");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__120"

  // $ANTLR start "T__121"
  public final void mT__121() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__121;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:110:8: ( 'xor' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:110:10: 'xor'
      {
        match("xor");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__121"

  // $ANTLR start "T__122"
  public final void mT__122() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__122;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:111:8: ( '|' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:111:10: '|'
      {
        match('|');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__122"

  // $ANTLR start "T__123"
  public final void mT__123() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__123;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:112:8: ( '~' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:112:10: '~'
      {
        match('~');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__123"

  // $ANTLR start "T__25"
  public final void mT__25() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__25;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:14:7: ( '&' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:14:9: '&'
      {
        match('&');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__25"

  // $ANTLR start "T__26"
  public final void mT__26() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__26;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:15:7: ( '(' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:15:9: '('
      {
        match('(');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__26"

  // $ANTLR start "T__27"
  public final void mT__27() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__27;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:16:7: ( ')' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:16:9: ')'
      {
        match(')');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__27"

  // $ANTLR start "T__28"
  public final void mT__28() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__28;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:17:7: ( '*' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:17:9: '*'
      {
        match('*');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__28"

  // $ANTLR start "T__29"
  public final void mT__29() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__29;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:18:7: ( '**' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:18:9: '**'
      {
        match("**");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__29"

  // $ANTLR start "T__30"
  public final void mT__30() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__30;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:19:7: ( '+' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:19:9: '+'
      {
        match('+');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__30"

  // $ANTLR start "T__31"
  public final void mT__31() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__31;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:20:7: ( ',' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:20:9: ','
      {
        match(',');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__31"

  // $ANTLR start "T__32"
  public final void mT__32() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__32;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:21:7: ( '-' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:21:9: '-'
      {
        match('-');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__32"

  // $ANTLR start "T__33"
  public final void mT__33() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__33;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:22:7: ( '->' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:22:9: '->'
      {
        match("->");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__33"

  // $ANTLR start "T__34"
  public final void mT__34() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__34;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:23:7: ( '.' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:23:9: '.'
      {
        match('.');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__34"

  // $ANTLR start "T__35"
  public final void mT__35() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__35;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:24:7: ( '..' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:24:9: '..'
      {
        match("..");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__35"

  // $ANTLR start "T__36"
  public final void mT__36() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__36;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:25:7: ( '/' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:25:9: '/'
      {
        match('/');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__36"

  // $ANTLR start "T__37"
  public final void mT__37() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__37;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:26:7: ( '/=' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:26:9: '/='
      {
        match("/=");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__37"

  // $ANTLR start "T__38"
  public final void mT__38() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__38;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:27:7: ( ':' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:27:9: ':'
      {
        match(':');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__38"

  // $ANTLR start "T__39"
  public final void mT__39() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__39;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:28:7: ( ':=' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:28:9: ':='
      {
        match(":=");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__39"

  // $ANTLR start "T__40"
  public final void mT__40() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__40;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:29:7: ( ';' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:29:9: ';'
      {
        match(';');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__40"

  // $ANTLR start "T__41"
  public final void mT__41() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__41;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:30:7: ( '<' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:30:9: '<'
      {
        match('<');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__41"

  // $ANTLR start "T__42"
  public final void mT__42() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__42;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:31:7: ( '<->' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:31:9: '<->'
      {
        match("<->");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__42"

  // $ANTLR start "T__43"
  public final void mT__43() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__43;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:32:7: ( '<<' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:32:9: '<<'
      {
        match("<<");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__43"

  // $ANTLR start "T__44"
  public final void mT__44() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__44;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:33:7: ( '<=' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:33:9: '<='
      {
        match("<=");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__44"

  // $ANTLR start "T__45"
  public final void mT__45() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__45;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:34:7: ( '<>' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:34:9: '<>'
      {
        match("<>");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__45"

  // $ANTLR start "T__46"
  public final void mT__46() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__46;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:35:7: ( '=' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:35:9: '='
      {
        match('=');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__46"

  // $ANTLR start "T__47"
  public final void mT__47() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__47;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:36:7: ( '=>' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:36:9: '=>'
      {
        match("=>");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__47"

  // $ANTLR start "T__48"
  public final void mT__48() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__48;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:37:7: ( '>' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:37:9: '>'
      {
        match('>');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__48"

  // $ANTLR start "T__49"
  public final void mT__49() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__49;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:38:7: ( '>=' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:38:9: '>='
      {
        match(">=");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__49"

  // $ANTLR start "T__50"
  public final void mT__50() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__50;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:39:7: ( '>>' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:39:9: '>>'
      {
        match(">>");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__50"

  // $ANTLR start "T__51"
  public final void mT__51() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__51;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:40:7: ( 'Delta' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:40:9: 'Delta'
      {
        match("Delta");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__51"

  // $ANTLR start "T__52"
  public final void mT__52() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__52;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:41:7: ( 'Digits' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:41:9: 'Digits'
      {
        match("Digits");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__52"

  // $ANTLR start "T__53"
  public final void mT__53() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__53;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:42:7: ( 'Flow_Message' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:42:9: 'Flow_Message'
      {
        match("Flow_Message");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__53"

  // $ANTLR start "T__54"
  public final void mT__54() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__54;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:43:7: ( 'Range' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:43:9: 'Range'
      {
        match("Range");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__54"

  // $ANTLR start "T__55"
  public final void mT__55() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__55;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:44:7: ( 'Warning_Message' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:44:9: 'Warning_Message'
      {
        match("Warning_Message");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__55"

  // $ANTLR start "T__56"
  public final void mT__56() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__56;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:45:7: ( '[' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:45:9: '['
      {
        match('[');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__56"

  // $ANTLR start "T__57"
  public final void mT__57() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__57;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:46:7: ( ']' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:46:9: ']'
      {
        match(']');
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__57"

  // $ANTLR start "T__58"
  public final void mT__58() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__58;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:47:7: ( 'abs' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:47:9: 'abs'
      {
        match("abs");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__58"

  // $ANTLR start "T__59"
  public final void mT__59() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__59;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:48:7: ( 'abstract' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:48:9: 'abstract'
      {
        match("abstract");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__59"

  // $ANTLR start "T__60"
  public final void mT__60() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__60;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:49:7: ( 'accept' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:49:9: 'accept'
      {
        match("accept");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__60"

  // $ANTLR start "T__61"
  public final void mT__61() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__61;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:50:7: ( 'all' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:50:9: 'all'
      {
        match("all");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__61"

  // $ANTLR start "T__62"
  public final void mT__62() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__62;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:51:7: ( 'and' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:51:9: 'and'
      {
        match("and");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__62"

  // $ANTLR start "T__63"
  public final void mT__63() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__63;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:52:7: ( 'array' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:52:9: 'array'
      {
        match("array");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__63"

  // $ANTLR start "T__64"
  public final void mT__64() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__64;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:53:7: ( 'assert' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:53:9: 'assert'
      {
        match("assert");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__64"

  // $ANTLR start "T__65"
  public final void mT__65() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__65;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:54:7: ( 'at' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:54:9: 'at'
      {
        match("at");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__65"

  // $ANTLR start "T__66"
  public final void mT__66() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__66;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:55:7: ( 'begin' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:55:9: 'begin'
      {
        match("begin");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__66"

  // $ANTLR start "T__67"
  public final void mT__67() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__67;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:56:7: ( 'body' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:56:9: 'body'
      {
        match("body");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__67"

  // $ANTLR start "T__68"
  public final void mT__68() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__68;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:57:7: ( 'case' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:57:9: 'case'
      {
        match("case");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__68"

  // $ANTLR start "T__69"
  public final void mT__69() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__69;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:58:7: ( 'check' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:58:9: 'check'
      {
        match("check");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__69"

  // $ANTLR start "T__70"
  public final void mT__70() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__70;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:59:7: ( 'constant' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:59:9: 'constant'
      {
        match("constant");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__70"

  // $ANTLR start "T__71"
  public final void mT__71() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__71;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:60:7: ( 'delta' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:60:9: 'delta'
      {
        match("delta");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__71"
  // $ANTLR start "T__72"
  public final void mT__72() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__72;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:61:7: ( 'derives' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:61:9: 'derives'
      {
        match("derives");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__72"

  // $ANTLR start "T__73"
  public final void mT__73() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__73;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:62:7: ( 'digits' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:62:9: 'digits'
      {
        match("digits");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__73"
  // $ANTLR start "T__74"
  public final void mT__74() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__74;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:63:7: ( 'else' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:63:9: 'else'
      {
        match("else");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__74"
  // $ANTLR start "T__75"
  public final void mT__75() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__75;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:64:7: ( 'elsif' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:64:9: 'elsif'
      {
        match("elsif");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__75"
  // $ANTLR start "T__76"
  public final void mT__76() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__76;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:65:7: ( 'end' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:65:9: 'end'
      {
        match("end");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__76"
  // $ANTLR start "T__77"
  public final void mT__77() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__77;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:66:7: ( 'exit' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:66:9: 'exit'
      {
        match("exit");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__77"
  // $ANTLR start "T__78"
  public final void mT__78() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__78;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:67:7: ( 'for' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:67:9: 'for'
      {
        match("for");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__78"
  // $ANTLR start "T__79"
  public final void mT__79() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__79;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:68:7: ( 'from' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:68:9: 'from'
      {
        match("from");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__79"
  // $ANTLR start "T__80"
  public final void mT__80() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__80;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:69:7: ( 'function' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:69:9: 'function'
      {
        match("function");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__80"
  // $ANTLR start "T__81"
  public final void mT__81() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__81;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:70:7: ( 'global' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:70:9: 'global'
      {
        match("global");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__81"

  // $ANTLR start "T__82"
  public final void mT__82() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__82;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:71:7: ( 'if' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:71:9: 'if'
      {
        match("if");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__82"
  // $ANTLR start "T__83"
  public final void mT__83() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__83;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:72:7: ( 'in' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:72:9: 'in'
      {
        match("in");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__83"
  // $ANTLR start "T__84"
  public final void mT__84() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__84;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:73:7: ( 'inherit' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:73:9: 'inherit'
      {
        match("inherit");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__84"
  // $ANTLR start "T__85"
  public final void mT__85() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__85;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:74:7: ( 'initializes' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:74:9: 'initializes'
      {
        match("initializes");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__85"
  // $ANTLR start "T__86"
  public final void mT__86() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__86;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:75:7: ( 'is' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:75:9: 'is'
      {
        match("is");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__86"
  // $ANTLR start "T__87"
  public final void mT__87() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__87;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:76:7: ( 'limited' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:76:9: 'limited'
      {
        match("limited");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__87"
  // $ANTLR start "T__88"
  public final void mT__88() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__88;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:77:7: ( 'loop' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:77:9: 'loop'
      {
        match("loop");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__88"

  // $ANTLR start "T__89"
  public final void mT__89() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__89;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:78:7: ( 'main_program' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:78:9: 'main_program'
      {
        match("main_program");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__89"

  // $ANTLR start "T__90"
  public final void mT__90() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__90;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:79:7: ( 'mod' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:79:9: 'mod'
      {
        match("mod");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__90"
  // $ANTLR start "T__91"
  public final void mT__91() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__91;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:80:7: ( 'new' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:80:9: 'new'
      {
        match("new");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__91"
  // $ANTLR start "T__92"
  public final void mT__92() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__92;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:81:7: ( 'not' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:81:9: 'not'
      {
        match("not");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__92"
  // $ANTLR start "T__93"
  public final void mT__93() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__93;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:82:7: ( 'null' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:82:9: 'null'
      {
        match("null");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__93"
  // $ANTLR start "T__94"
  public final void mT__94() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__94;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:83:7: ( 'of' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:83:9: 'of'
      {
        match("of");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__94"
  // $ANTLR start "T__95"
  public final void mT__95() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__95;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:84:7: ( 'or' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:84:9: 'or'
      {
        match("or");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__95"
  // $ANTLR start "T__96"
  public final void mT__96() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__96;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:85:7: ( 'others' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:85:9: 'others'
      {
        match("others");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__96"
  // $ANTLR start "T__97"
  public final void mT__97() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__97;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:86:7: ( 'out' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:86:9: 'out'
      {
        match("out");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__97"

  // $ANTLR start "T__98"
  public final void mT__98() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__98;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:87:7: ( 'own' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:87:9: 'own'
      {
        match("own");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__98"
  // $ANTLR start "T__99"
  public final void mT__99() throws RecognitionException {
    try {
      final int _type = SPARKLexer.T__99;
      final int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:88:7: ( 'package' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:88:9: 'package'
      {
        match("package");
        if (this.state.failed) {
          return;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "T__99"
  public void mTokens() throws RecognitionException {
    // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:8: ( T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | IMPORT | IDENTIFIER | NUMERIC_LITERAL | CHARACTER_LITERAL_OR_QUOTE | STRING_LITERAL | SPARK_ANNOTATION | COMMENT | NEW_LINE | WS )
    int alt16 = 108;
    alt16 = this.dfa16.predict(this.input);
    switch (alt16) {
      case 1:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:10: T__25
      {
        mT__25();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 2:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:16: T__26
      {
        mT__26();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 3:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:22: T__27
      {
        mT__27();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 4:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:28: T__28
      {
        mT__28();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 5:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:34: T__29
      {
        mT__29();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 6:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:40: T__30
      {
        mT__30();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 7:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:46: T__31
      {
        mT__31();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 8:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:52: T__32
      {
        mT__32();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 9:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:58: T__33
      {
        mT__33();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 10:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:64: T__34
      {
        mT__34();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 11:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:70: T__35
      {
        mT__35();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 12:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:76: T__36
      {
        mT__36();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 13:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:82: T__37
      {
        mT__37();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 14:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:88: T__38
      {
        mT__38();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 15:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:94: T__39
      {
        mT__39();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 16:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:100: T__40
      {
        mT__40();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 17:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:106: T__41
      {
        mT__41();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 18:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:112: T__42
      {
        mT__42();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 19:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:118: T__43
      {
        mT__43();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 20:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:124: T__44
      {
        mT__44();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 21:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:130: T__45
      {
        mT__45();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 22:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:136: T__46
      {
        mT__46();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 23:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:142: T__47
      {
        mT__47();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 24:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:148: T__48
      {
        mT__48();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 25:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:154: T__49
      {
        mT__49();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 26:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:160: T__50
      {
        mT__50();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 27:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:166: T__51
      {
        mT__51();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 28:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:172: T__52
      {
        mT__52();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 29:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:178: T__53
      {
        mT__53();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 30:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:184: T__54
      {
        mT__54();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 31:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:190: T__55
      {
        mT__55();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 32:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:196: T__56
      {
        mT__56();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 33:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:202: T__57
      {
        mT__57();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 34:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:208: T__58
      {
        mT__58();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 35:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:214: T__59
      {
        mT__59();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 36:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:220: T__60
      {
        mT__60();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 37:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:226: T__61
      {
        mT__61();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 38:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:232: T__62
      {
        mT__62();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 39:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:238: T__63
      {
        mT__63();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 40:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:244: T__64
      {
        mT__64();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 41:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:250: T__65
      {
        mT__65();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 42:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:256: T__66
      {
        mT__66();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 43:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:262: T__67
      {
        mT__67();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 44:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:268: T__68
      {
        mT__68();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 45:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:274: T__69
      {
        mT__69();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 46:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:280: T__70
      {
        mT__70();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 47:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:286: T__71
      {
        mT__71();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 48:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:292: T__72
      {
        mT__72();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 49:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:298: T__73
      {
        mT__73();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 50:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:304: T__74
      {
        mT__74();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 51:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:310: T__75
      {
        mT__75();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 52:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:316: T__76
      {
        mT__76();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 53:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:322: T__77
      {
        mT__77();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 54:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:328: T__78
      {
        mT__78();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 55:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:334: T__79
      {
        mT__79();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 56:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:340: T__80
      {
        mT__80();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 57:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:346: T__81
      {
        mT__81();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 58:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:352: T__82
      {
        mT__82();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 59:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:358: T__83
      {
        mT__83();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 60:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:364: T__84
      {
        mT__84();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 61:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:370: T__85
      {
        mT__85();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 62:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:376: T__86
      {
        mT__86();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 63:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:382: T__87
      {
        mT__87();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 64:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:388: T__88
      {
        mT__88();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 65:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:394: T__89
      {
        mT__89();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 66:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:400: T__90
      {
        mT__90();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 67:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:406: T__91
      {
        mT__91();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 68:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:412: T__92
      {
        mT__92();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 69:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:418: T__93
      {
        mT__93();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 70:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:424: T__94
      {
        mT__94();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 71:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:430: T__95
      {
        mT__95();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 72:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:436: T__96
      {
        mT__96();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 73:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:442: T__97
      {
        mT__97();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 74:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:448: T__98
      {
        mT__98();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 75:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:454: T__99
      {
        mT__99();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 76:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:460: T__100
      {
        mT__100();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 77:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:467: T__101
      {
        mT__101();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 78:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:474: T__102
      {
        mT__102();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 79:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:481: T__103
      {
        mT__103();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 80:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:488: T__104
      {
        mT__104();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 81:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:495: T__105
      {
        mT__105();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 82:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:502: T__106
      {
        mT__106();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 83:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:509: T__107
      {
        mT__107();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 84:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:516: T__108
      {
        mT__108();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 85:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:523: T__109
      {
        mT__109();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 86:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:530: T__110
      {
        mT__110();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 87:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:537: T__111
      {
        mT__111();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 88:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:544: T__112
      {
        mT__112();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 89:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:551: T__113
      {
        mT__113();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 90:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:558: T__114
      {
        mT__114();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 91:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:565: T__115
      {
        mT__115();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 92:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:572: T__116
      {
        mT__116();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 93:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:579: T__117
      {
        mT__117();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 94:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:586: T__118
      {
        mT__118();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 95:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:593: T__119
      {
        mT__119();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 96:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:600: T__120
      {
        mT__120();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 97:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:607: T__121
      {
        mT__121();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 98:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:614: T__122
      {
        mT__122();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 99:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:621: T__123
      {
        mT__123();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 100:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:628: IMPORT
      {
        mIMPORT();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 101:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:635: IDENTIFIER
      {
        mIDENTIFIER();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 102:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:646: NUMERIC_LITERAL
      {
        mNUMERIC_LITERAL();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 103:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:662: CHARACTER_LITERAL_OR_QUOTE
      {
        mCHARACTER_LITERAL_OR_QUOTE();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 104:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:689: STRING_LITERAL
      {
        mSTRING_LITERAL();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 105:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:704: SPARK_ANNOTATION
      {
        mSPARK_ANNOTATION();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 106:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:721: COMMENT
      {
        mCOMMENT();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 107:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:729: NEW_LINE
      {
        mNEW_LINE();
        if (this.state.failed) {
          return;
        }

      }
        break;
      case 108:
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:1:738: WS
      {
        mWS();
        if (this.state.failed) {
          return;
        }

      }
        break;

    }

  }

  // $ANTLR start "UPPER_CASE_IDENTIFIER_LETTER"
  public final void mUPPER_CASE_IDENTIFIER_LETTER() throws RecognitionException {
    try {
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3121:3: ( 'A' .. 'Z' )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:
      {
        if (((this.input.LA(1) >= 'A') && (this.input.LA(1) <= 'Z'))) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

      }

    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "UPPER_CASE_IDENTIFIER_LETTER"
  // $ANTLR start "WS"
  public final void mWS() throws RecognitionException {
    try {
      final int _type = SPARKLexer.WS;
      int _channel = BaseRecognizer.DEFAULT_TOKEN_CHANNEL;
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3228:3: ( ( ' ' | '\\t' | '\\u000C' ) )
      // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3228:6: ( ' ' | '\\t' | '\\u000C' )
      {
        if ((this.input.LA(1) == '\t') || (this.input.LA(1) == '\f')
            || (this.input.LA(1) == ' ')) {
          this.input.consume();
          this.state.failed = false;
        } else {
          if (this.state.backtracking > 0) {
            this.state.failed = true;
            return;
          }
          final MismatchedSetException mse = new MismatchedSetException(null,
              this.input);
          recover(mse);
          throw mse;
        }

        if (this.state.backtracking == 0) {
          _channel = BaseRecognizer.HIDDEN;
        }

      }

      this.state.type = _type;
      this.state.channel = _channel;
    } finally {
      // do for sure before leaving
    }
  }

  // $ANTLR end "WS"
  public final boolean synpred1_SPARK() {
    this.state.backtracking++;
    final int start = this.input.mark();
    try {
      synpred1_SPARK_fragment(); // can never throw exception
    } catch (final RecognitionException re) {
      System.err.println("impossible: " + re);
    }
    final boolean success = !this.state.failed;
    this.input.rewind(start);
    this.state.backtracking--;
    this.state.failed = false;
    return success;
  }

  // $ANTLR start synpred1_SPARK
  public final void synpred1_SPARK_fragment() throws RecognitionException {
    // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:15: ( '.' DIGIT )
    // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3159:17: '.' DIGIT
    {
      match('.');
      if (this.state.failed) {
        return;
      }

      mDIGIT();
      if (this.state.failed) {
        return;
      }

    }

  }

  // $ANTLR end synpred1_SPARK

  public final boolean synpred2_SPARK() {
    this.state.backtracking++;
    final int start = this.input.mark();
    try {
      synpred2_SPARK_fragment(); // can never throw exception
    } catch (final RecognitionException re) {
      System.err.println("impossible: " + re);
    }
    final boolean success = !this.state.failed;
    this.input.rewind(start);
    this.state.backtracking--;
    this.state.failed = false;
    return success;
  }

  // $ANTLR start synpred2_SPARK
  public final void synpred2_SPARK_fragment() throws RecognitionException {
    // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3195:5: ( '\\'' . '\\'' )
    // /Volumes/ssd2/Users/belt/Documents/workspace-sireum-3.7.2/sireum-bakar-compiler/src/main/java/org/sireum/bakar/compiler/parser/SPARK.g:3195:6: '\\'' . '\\''
    {
      match('\'');
      if (this.state.failed) {
        return;
      }

      matchAny();
      if (this.state.failed) {
        return;
      }

      match('\'');
      if (this.state.failed) {
        return;
      }

    }

  }
  // $ANTLR end synpred2_SPARK

}
