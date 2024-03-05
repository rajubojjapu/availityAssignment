package com.availity.domain;

import java.util.Stack;

public class ValidParenthesis {

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' && !stack.isEmpty() && stack.peek() == '(') {
                stack.pop();
            } else if (c == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.pop();
            } else if (c == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop();
            } else {
                return false; // Unmatched closing parenthesis or empty stack
            }
        }
        return stack.isEmpty(); // Check if stack is empty after iterating through all characters
    }

    public static void main(String[] args) {
        String str = "({})";
        System.out.println(ValidParenthesis.isValid(str));
    }
}
