package com.gp.calculator.model;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

import com.gp.calculator.exceptions.ExpressionException;

public class Expression {

	private String expression;

	private final String allowedChars = "[0-9()\\.\\-\\+\\*\\^\\/ ]*";
	private final String spañesBetwNumbers = ".*[0-9]+[ ]+[0-9]+.*";
	private final String nullDivision = ".*\\/ *0.*";

	public Expression(String expression) {
		this.expression = expression.replace(',', '.');
	}

	public boolean isValid() {
		if (!expression.matches(allowedChars)) {
			System.out.println("Incorrect expression.");
			return false;			
		}
		if (expression.matches(spañesBetwNumbers)) {
			System.out.println("Invalid number.");
			return false;
		}
		if (expression.matches(nullDivision)) {
			System.out.println("Devision by 0.");
			return false;					
		}
		if (expression.equals("")) {
			System.out.println("Incorrect expression.");
			return false;			
		}
		return true;
	}

	public List<String> parseToRPM() {
		List<String> rpm_expression = new ArrayList<String>();
		Deque<String> stackOper = new ArrayDeque<String>();
		StringTokenizer tokenizer = new StringTokenizer(expression.replaceAll(" ", ""), 
				Token.OPERATORS + "()", true);
		Token prev = new Token("");
		while (tokenizer.hasMoreTokens()) {
			Token token = new Token(tokenizer.nextToken());
			if (token.isNumber()) {
				rpm_expression.add(token.getValue());
			}			
			if (token.equals("(")) {
				stackOper.push(token.getValue());
			}
			if (token.equals(")")) {
				if (stackOper.isEmpty()) {
					throw new ExpressionException("Brackets not matched.");
				}
				else {
					while (!stackOper.peek().equals("(")) {
						rpm_expression.add(stackOper.pop());
						if (stackOper.isEmpty()) {
							throw new ExpressionException("Brackets not matched.");
						}
					}
				}
				stackOper.pop(); // push all operators and delete "("
			}
			if(token.isOperator()) {
				if (!tokenizer.hasMoreTokens()) {
					throw new ExpressionException("Invalid expression.");
				}
				if (token.equals("-") && (prev.equals("") || prev.isOperator() || prev.equals("("))) {
					token = new Token("u-");
				}
				while (!stackOper.isEmpty() && (token.priority() <= new Token(stackOper.peek()).priority())) {
					rpm_expression.add(stackOper.pop());
				}
				stackOper.push(token.getValue());
			}
			prev = token;
		}		
		while (!stackOper.isEmpty()) {
			if (!stackOper.peek().equals("(")) {
				rpm_expression.add(stackOper.pop());
			}
			else {
				throw new ExpressionException("Brackets not matched.");
			}
		}
		return rpm_expression;
	}
}
