package ru.fbtw.cipher_pro.core;

import java.util.Comparator;
import java.util.HashMap;

public class Symbol implements Comparable<Symbol> {
	private double frec;
	private String name;

	private static int nameCount = 0;
	private static HashMap<Character,Character> nameDic;

	static {
		nameDic = new HashMap<>();
		int j = 0;
		for(char i = 'A'; i <= 'Z'; j+=1, i++){
			nameDic.put(Integer.toString(j,26).charAt(0),i);
		}
	}

	static String getNewName(){
		StringBuilder namBuilder = new StringBuilder();
		for (char ch : Integer.toString(nameCount,26).toCharArray()) {
			namBuilder.append(nameDic.get(ch));
		}
		nameCount++;
		return namBuilder.toString();
	}

	public static void restNames(){
		nameCount = 0;
	}

	public Symbol(double frec, String name) {
		this.frec = frec;
		this.name = name;
	}

	public Symbol(double frec) {
		this.frec = frec;
		this.name = getNewName();
	}

	public void setFrec(double frec) {
		this.frec = frec;
	}



	public double getFrec() {
		return frec;
	}

	public String getName() {
		return name;
	}


	@Override
	public int compareTo(Symbol o) {
		return Double.compare(frec,o.frec);
	}

	@Override
	public String toString() {
		return "p("+ name+")= "+frec;
	}
}
