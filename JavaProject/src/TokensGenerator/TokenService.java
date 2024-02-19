import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenService {
 
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

    public static void WriteToOutputFile(List<Token> tokens,String pureCode) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\HP\\OneDrive\\Desktop\\java\\JavaProject\\src\\TokensGenerator\\output.txt"))) {
            writer.write(pureCode);
            for (Token element : tokens) {
                writer.write(element.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
