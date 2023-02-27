package craft;
import java.util.ArrayList;

import java.util.List;

import static craft.TokenType.*;

public class Scan {
    private final String source;
    private final List<Token> tokens = new ArrayList<Token>();

    private int current; //point to the current lexemes
    private int start;   //points to the start of the current lexemes
    private int line;   // current line of the text;

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
                addToken(match('=') ? GREATER_EQUAL:GREATER);break;
            default:
                if(isDigit()){

                } else if(isAlpha()){

                }
                Lox.error(line,"Unexpect charactor");
        }
    }
     private boolean isDigit(){
        return false;
     }
     private boolean isAlpha(){
        return false;
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
}
