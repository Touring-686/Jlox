package lox;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lox.TokenType.*;

public class Scan {
    private final String source;
    private final List<Token> tokens = new ArrayList<Token>();

    private int current; //point to the current lexemes
    private int start;   //points to the start of the current lexemes
    private int line;   // current line of the text;

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    AND);
        keywords.put("class",  CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("fun",    FUN);
        keywords.put("if",     IF);
        keywords.put("nil",    NIL);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
    }

    Scan(String source){
        this.source = source;
    }
    Scan(String source, int line, int st ,int cur){
        this.source = source;
        this.line = line;
        this.start = st;
        this.current = cur;
    }

    /**
     * check whether reach the end of the code*/
    private boolean isEnd(){
        return this.current >= this.source.length();
    }
    public List<Token> scanTokens(){
        while(!isEnd()){
            start = current; // update the start pointer;
            scanToken();
        }

        //remember to add the null to the end of the tokens;
        tokens.add(new Token(EOF,"",null,line));
        return tokens;
    }
    private void scanToken(){
        char c = advance(); // get the current char and update the pointer
        switch(c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            // check operators has 2 chars
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL:GREATER);
                break;
            case '/':
                if(match('/')){
                    // the sequential contents are comment
                    while(peek() != '\n' && !isEnd()){
                        advance();
                    }
                } else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n': line++;break;
            case '"':  checkString();break;
            //为每一个数字添加情况很麻烦
            default:
                // all the digit in the lox is float
                if(isDigit(c)){
                    checkNumber();
                }
                else if (isAlpha(c)) {
                    checkIdentifier();
                }
                Lox.error(line,"Unexpect charactor");
        }
    }
     private boolean isDigit(char c){
        if(c >= '0' && c <= '9') return true;
        return false;
     }
     private boolean isAlpha(char c){
         return (c >= 'a' && c <= 'z') ||
                 (c >= 'A' && c <= 'Z') ||
                 c == '_';
     }
     private boolean isAlphaNumeric(char c){
        return isDigit(c) || isAlpha(c);
     }

     /**
      * terminlogy : lookahead go ahead but not consume the char
      * only lookahead the next char
      * (don't update the current pointer)*/
     private char peek(){
         if(isEnd())    return '\0';// reach the end of the file;
         return source.charAt(current);
     }

     /*scanner looks ahead at most two characters.*/
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    /**
     * add token to the token list*/
    private void addToken(TokenType type){
        addToken(type,null);
    }
    /**
     * helper function : add token to the arraylist*/
    private void addToken(TokenType type,Object literal){
        String text = source.substring(start,current);
        tokens.add(new Token(type,text,literal,line));
    }
    private char advance(){
        assert(current < source.length());
        return this.source.charAt(current++);
    }


    // check whether the chars are operators like >= ..
    private boolean match(char expect){
        if(isEnd()) return false;
        if(expect != source.charAt(current))   return false;
        // else update the current pointer skip the next '='
        current++;
        return true;
    }

    /**
     * private method to match string
     */
    private void checkString(){
        // lookahead to check whether hit the right '"'
        while(peek() != '"' && !isEnd()){
            //lox support multi-line strings;
            if(peek() == '\n') line++;
            advance();
        }
        // if reach the end of the file but without reach '"' report error
        if(isEnd()){
            Lox.error(line,"Unterminated string.");
            return;
        }
        // skip the right '"'
        advance();
        String value = source.substring(start+1,current-1);
        addToken(STRING,value);
    }

    private void checkIdentifier(){
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }
    private void checkNumber(){
        /*123.1
        * 123
        * */
        /*.132*/
        if(!isDigit(peek()))
            Lox.error(line,"invalid number");
        while(isDigit(peek())) {
            advance();
        }
        if(peek() == '.' && isDigit(peekNext())){
            advance();
            while(isDigit(peek())){
                advance();
            }
        }

        addToken(NUMBER,Double.parseDouble(source.substring(start,current)));
    }

    //=============================TEST HELPER======================
    /**
     * helper function for debugging
     * print the tokens of the source
     * */
    public String tokenType(Token token){
        return token.type.name();
    }
    public Object tokenValue(Token token){
        return token.literal;
    }
    public List<Token> tokenArray(){
        return this.tokens;
    }
    public void printToken(){
        for(Token token:tokens){
            System.out.println(token.lexeme + " " + token.type.name());
        }
    }
}
