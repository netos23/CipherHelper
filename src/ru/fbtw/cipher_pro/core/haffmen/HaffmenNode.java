package ru.fbtw.cipher_pro.core.haffmen;

import ru.fbtw.cipher_pro.core.Symbol;

public class HaffmenNode  implements Comparable<HaffmenNode>{
	private String name;
	private boolean isLeaf;
	private HaffmenNode left;
	private HaffmenNode rigth;

	private String code;
	private double freq;

	private double x,y;

	public HaffmenNode(Symbol symbol) {
		this.name = symbol.getName();
		this.freq = symbol.getFrec();
		isLeaf = true;
	}

	public HaffmenNode(HaffmenNode left, HaffmenNode rigth) {
		this.left = left;
		this.rigth = rigth;
		freq = left.freq + rigth.freq;
		isLeaf = false;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return isLeaf ? name + "\n" + freq : "" + freq;
	}

	public String getName() {
		return name;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public HaffmenNode getLeft() {
		return left;
	}

	public HaffmenNode getRigth() {
		return rigth;
	}

	public double getFreq() {
		return freq;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int compareTo(HaffmenNode o) {
		return Double.compare(freq,o.freq);
	}
}
