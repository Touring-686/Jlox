package craft;

// Single-character tokens.
public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;
    Token(TokenType type, String lexeme, Object literal, int line){
        this.type = type;
        this.line = line;
        this.literal = literal;
        this.lexeme = lexeme;
    }
    public String toString(){
        return type + " " + lexeme + " " + literal + " " + line;
    }
}

