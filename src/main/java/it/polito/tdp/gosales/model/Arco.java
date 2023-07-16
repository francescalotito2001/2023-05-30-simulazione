package it.polito.tdp.gosales.model;

public class Arco implements Comparable<Arco> {

	private Retailers v1;
	private Retailers v2;
	private int peso;
	public Arco(Retailers v1, Retailers v2, int peso) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}
	public Retailers getV1() {
		return v1;
	}
	public Retailers getV2() {
		return v2;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return peso - o.peso;
	}
	
	public String toString() {
		return peso + " --> " + v1.getName() + " - " + v2.getName();
	}
	
}
