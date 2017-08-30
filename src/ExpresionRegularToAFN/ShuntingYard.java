package ExpresionRegularToAFN;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by criscastro on 28/08/17.
 */
public class ShuntingYard {

    private enum Operador
    {
        CERRADURAKLEENE(3), CERRADURAPOSITIVA(3), CONCAT(2), UNION(1),;
        final int precedencia;
        Operador(int p) { precedencia = p; }
    }

    private static Map<String, Operador> operadores = new HashMap<String, Operador>() {{
        put("*", Operador.CERRADURAKLEENE);
        put("+", Operador.CERRADURAPOSITIVA);
        put(".", Operador.CONCAT);
        put("|", Operador.UNION);
    }};

    private static boolean isMayorPrecedencia(String op, String sub)
    {
        return (operadores.containsKey(sub) && operadores.get(sub).precedencia >= operadores.get(op).precedencia);
    }

    public static String postfix(String infix)
    {
        StringBuilder output = new StringBuilder();
        Deque<String> stack  = new LinkedList<>();

        for (String token : infix.split("")) { // recorre letra a letra
            // operator
            if (operadores.containsKey(token)) {
                while ( ! stack.isEmpty() && isMayorPrecedencia(token, stack.peek()))
                    output.append(stack.pop());
                stack.push(token);
                // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);
                // right parenthesis
            } else if (token.equals(")")) {
                while ( ! stack.peek().equals("("))
                    output.append(stack.pop());
                stack.pop();
                // digit
            } else {
//si es token normal y tiene ago ala derecha appen un punto
               output.append(token);

            }
        }
        while ( ! stack.isEmpty())
            output.append(stack.pop());
        return output.toString();
    }

}