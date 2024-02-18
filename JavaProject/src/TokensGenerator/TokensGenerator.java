import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class TokensGenerator {
    public static void main(String[] args) {
        String code = "";
        try {
              var reader = new BufferedReader(new FileReader("C:\\Users\\HP\\OneDrive\\Desktop\\java\\JavaProject\\src\\code\\MyCode.txt"));
              
              if (reader != null) {
              int counter;
              boolean isSpace = false; 

              while ((counter = reader.read()) != -1) {
                char c = (char) counter;
                if (Character.isWhitespace(counter)) {
                  if (!isSpace) {
                    code += " "; 
                    isSpace = true;
                   }
                } else if (c == ';') {
                code += c + "\n"; 
                isSpace = false;
                } else {
                code += c;
                isSpace = false;
            }
        }
        

        reader.close();
    }
        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
        
        System.out.println(code);
        
        List<Token> tokens = GetTokens(code);
        for (Token token : tokens) {
            System.out.println(token);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();

        System.out.println("Name:-Taha Ahmed Radwan, Stu_ID:- 2201805468");
        System.out.println("Created at:"+currentDateTime);
    }
    
    public static List<Token> GetTokens(String code) {

        List<Token> tokensList = new ArrayList<>();
        String variablepattern = "\\b(?!int\\b|float\\b|printf\\b|scanf\\b|char\\b|if\\b|goto\\b|\\\"[^\\\"]*\\\")[a-zA-Z_][a-zA-Z0-9_]*\\b";       
        Map<String, Integer> varMap = new HashMap<>();
        int IdCounter = 1;
    
        Pattern variableRegex = Pattern.compile(variablepattern);
        Matcher variableMatcher = variableRegex.matcher(code);
    
        while (variableMatcher.find()) {
            String variable = variableMatcher.group();
            String tokenType = "id";

            if (!varMap.containsKey(variable)) {
                varMap.put(variable, IdCounter);
                tokensList.add(new Token(tokenType, "" + IdCounter + ""));
                IdCounter++;
            } else {
                int id = varMap.get(variable);
                tokensList.add(new Token(tokenType, "" + id + ""));
            }
        }
    
        String[] operatorTokens = {"+", "-", "*", "/", "%", "=", ">", "<", ">=", "<=", "==", "!=", "!"};
        String[] keywordTokens = {"int", "float", "char", "if", "goto", "scanf", "printf"};
        String[] symbols = {"{", "}", "(", ")", ",", ";"};
    
        for (String operator : operatorTokens) {
            Pattern operatorPattern = Pattern.compile(Pattern.quote(operator));
            Matcher operatorMatcher = operatorPattern.matcher(code);
    
            while (operatorMatcher.find()) {
                String op = operatorMatcher.group();
                String tokenType = "OP";
                tokensList.add(new Token(tokenType, op));
            }
        }
    
        for (String keyword : keywordTokens) {
            Pattern operatorPattern = Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b");
            Matcher operatorMatcher = operatorPattern.matcher(code);
    
            while (operatorMatcher.find()) {
                String key = operatorMatcher.group();
                String tokenType = "keyword";
                tokensList.add(new Token(tokenType, key));
            }
        }
    
        for (String s : symbols) {
            Pattern operatorPattern = Pattern.compile(Pattern.quote(s));
            Matcher operatorMatcher = operatorPattern.matcher(code);
    
            while (operatorMatcher.find()) {
                String symb = operatorMatcher.group();
                String tokenType = "Symbols";
                tokensList.add(new Token(tokenType, symb));
            }
        }
        return tokensList;
    }
}

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
