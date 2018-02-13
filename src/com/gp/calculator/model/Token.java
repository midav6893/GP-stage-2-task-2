package com.gp.calculator.model;

public class Token {

	private String token;
	public static final String OPERATORS = "+-*/^";

	public Token(String token) {
		this.token = token;
	}

	public String getValue() {
		return token;
	}

	public boolean isNumber() {
		if (token.matches("[0-9]|[0-9]+\\.?[0-9]+")) return true;
		return false;
	}

	public boolean isOperator() {
		if (token.equals("u-")) return true;
		if (token.matches("[\\-\\+\\*\\^\\/]")) return true;
		return false;
	}

	public int priority() {
		if (token.equals("(")) return 1;
		if (token.equals("+") || token.equals("-")) return 2;
		if (token.equals("*") || token.equals("/")) return 3;
		if (token.equals("^")) return 4;
		return 5;
	}

	@Override
	public boolean equals(Object o) {
		return token.equals(o);
	}

	@Override
	public int hashCode() {
		return token.hashCode();
	}
}
