package com.gp.calculator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

import com.gp.calculator.exceptions.ExpressionException;
import com.gp.calculator.model.Expression;

public class Calculator {

	public void perform() {

		Expression expression;
		try (Scanner sc = new Scanner(System.in)) {
			for (;;) {
				System.out.println("Enter expression:");
				expression = new Expression(sc.nextLine());
				if (expression.isValid()) {
					try {
						List<String> rpm_expression = expression.parseToRPM();
						System.out.println("Calculation result:");
						System.out.println(calculation(rpm_expression));
						break;
					}
					catch (ExpressionException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}			
		}
	}

	private Double calculation(List<String> rpm_expression) {
		Deque<Double> stack = new ArrayDeque<Double>();
		for (String token: rpm_expression) {
			if (token.equals("+")) {
				stack.push(stack.pop() + stack.pop());
			}
			else if (token.equals("-")) {
				Double b = stack.pop(), a = stack.pop();
				stack.push(a - b);
			}
			else if (token.equals("*")) {
				stack.push(stack.pop() * stack.pop());
			}
			else if (token.equals("/")) {
				Double b = stack.pop(), a = stack.pop();
				stack.push(a / b);
			}
			else if (token.equals("^")) {
				Double b = stack.pop(), a = stack.pop();
				stack.push(Math.pow(a, b));
			}
			else if (token.equals("u-")) {
				stack.push(-stack.pop());
			}
			else {
				stack.push(Double.valueOf(token));
			}
		}
		return stack.pop();
	}
}
