import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Sergey Solomevich on 11.02.2018.
 */
public class Task2 {
    public static void main(String[] args) throws Exception {
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
        String sIn;
        try {
            sIn = d.readLine();
            // для разделения целой и дробной части чисел во вводимой строке как  точкой, так и запятой
            sIn = opn(sIn.replace( ',', '.' ));
            BigDecimal result = calculate(sIn);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//     Преобразовать строку в обратную польскую нотацию
//     @param sIn Входная строка
//     @return Выходная строка в обратной польской нотации
    private static String opn(String sIn) throws Exception {
        StringBuilder sbStack = new StringBuilder(""), sbOut = new StringBuilder("");
        char cIn, cTmp;

        for (int i = 0; i < sIn.length(); i++) {
            cIn = sIn.charAt(i);

//            для обработки отрицательной степени
            if (isOp(cIn)) {
                if ((cIn == '-' && i >1 && sIn.charAt(i - 1) == '^') ||
                        (cIn == '-' && i >1 && sIn.charAt(i - 1) == ' ' && sIn.charAt(i -2) == '^')) {
                    sbOut.append(cIn);
                                   } else {
                    while (sbStack.length() > 0) {
                        cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
                        if (isOp(cTmp) && (opPrior(cIn) <= opPrior(cTmp))) {
                            sbOut.append(" ").append(cTmp).append(" ");
                            sbStack.setLength(sbStack.length() - 1);
                        } else {
                            sbOut.append(" ");
                            break;
                        }
                    }
                    sbOut.append(" ");
                    sbStack.append(cIn);
                }
            }else if ('(' == cIn) {
                sbStack.append(cIn);
            } else if (')' == cIn) {
                cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                while ('(' != cTmp) {
                    if (sbStack.length() < 1) {
                        throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                    }
                    sbOut.append(" ").append(cTmp);
                    sbStack.setLength(sbStack.length()-1);
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                }
                sbStack.setLength(sbStack.length()-1);
            } else {
                // Если символ не оператор - добавляем в выходную последовательность
                sbOut.append(cIn);
            }
        }

        // Если в стеке остались операторы, добавляем их в входную строку
        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length()-1));
            sbStack.setLength(sbStack.length()-1);
        }
//        System.out.println(sbOut.toString());
        return  sbOut.toString();
    }

//     Проверка, является ли текущий символ оператором
    private static boolean isOp(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }

//      Возвращает приоритет операции
    private static byte opPrior(char op) {
        switch (op) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
        }
        return 1; // Тут остается + и -
    }

//      Считает выражение, записанное в обратной польской нотации
//      @param sIn
//      @return BigDecimal result
    private static BigDecimal calculate(String sIn) throws Exception {
        BigDecimal dA = new BigDecimal(0), dB = new BigDecimal(0);
        String sTmp;
        Deque<BigDecimal> stack = new ArrayDeque<BigDecimal>();
        StringTokenizer st = new StringTokenizer(sIn);
        while(st.hasMoreTokens()) {
            try {
                sTmp = st.nextToken().trim();
                if (1 == sTmp.length() && isOp(sTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Невалидные данные");
                    }
                    dB = stack.pop();
                    dA = stack.pop();
                    switch (sTmp.charAt(0)) {
                        case '+':
                            dA = dA.add(dB);
                            break;
                        case '-':
                            dA =dA.subtract(dB);
                            break;
                        case '/':
                            dA = dA.divide(dB,new MathContext(4, RoundingMode.HALF_UP));
                            break;
                        case '*':
                            dA =dA.multiply(dB);
                            break;
                        case '^':
                            dA=dA.pow(dB.intValue(),new MathContext(4, RoundingMode.HALF_UP));
                            break;
                        default:
                            throw new Exception("Недопустимая операция " + sTmp);
                    }
                    stack.push(dA);
                } else {
                        dA = new BigDecimal(sTmp);
                        stack.push(dA);
                }
            } catch (Exception e) {
                throw new Exception("Невалидные данные");
            }
        }
        if (stack.size() > 1) {
            throw new Exception("Невалидные данные");
        }
        return stack.pop();
    }
}
