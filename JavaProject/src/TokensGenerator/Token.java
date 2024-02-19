class Token{
    
    public String TokenType;
    public String Value;

    public Token(String tokenType,String value) {
        TokenType = tokenType;
        Value = value;
    }
    @Override
    public String toString() {
        return "<"+TokenType+","+Value +">";
    }
}