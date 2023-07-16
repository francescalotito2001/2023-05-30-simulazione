package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	
	private List<Retailers> vertici;
	private List<Arco> archi;
	private Graph<Retailers, DefaultWeightedEdge> grafo;	//grafo pesato 
	
	private GOsalesDAO dao;
	
	public Model() {
		dao= new GOsalesDAO();
	}
	
	
	//CREO GRAFO
	public void creaGrafo(String country, int anno, int min) {
		this.vertici = dao.getVertici(country);
		this.archi = new ArrayList<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);		//edge non orientati
		
		//aggiungo vertici al grafo
		Graphs.addAllVertices(this.grafo, vertici);
		
		//aggiungo gli archi
		for(Retailers v1:vertici) {
			for(Retailers v2:vertici) {
				if(!v1.equals(v2)) {
					Arco  arco = dao.getArco(v1, v2, anno, min);
					if(arco != null) {
						Graphs.addEdgeWithVertices(this.grafo, v1, v2, arco.getPeso());
						if(!archi.contains(arco)) {
							archi.add(arco);
						}
					}
				}
			}
		}
		Collections.sort(this.archi);
		
	}
	
	//CERCO LA COMPONENTE CONNESSA
	private int nConnessi;
	private int pesoConnessa;
	public void componenteConnessa(Retailers v) {
		ConnectivityInspector<Retailers, DefaultWeightedEdge> ci =
				new ConnectivityInspector<>(this.grafo) ;
		Set<Retailers> connessi = ci.connectedSetOf(v);
		this.nConnessi = connessi.size();
		
		this.pesoConnessa = 0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(connessi.contains(this.grafo.getEdgeSource(e)) 
					&& connessi.contains(this.grafo.getEdgeTarget(e))){
				pesoConnessa += (int)this.grafo.getEdgeWeight(e);
			}
		}
		
	}

	//METODI DI SUPPORTO
	public List<String> getCountry (){
		return dao.getCountry();
	}
	public String stampaVertici() {
		String result = "";
		for(Retailers a: vertici) {
			result += a.toString() + "\n";
		}
		return result;
	}
	public String stampaArchi() {
		String result = "";
		for(Arco a: archi) {
			result += a.toString() + "\n";
		}
		return result;
		
	}
	
	//GETTER E SETTER
	public Graph<Retailers, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	public void setGrafo(Graph<Retailers, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}
	public List<Retailers> getVertici() {
		return vertici;
	}

	
	public int getnConnessi() {
		return nConnessi;
	}
	public int getPesoConnessa() {
		return pesoConnessa;
	}
	
	

	
	
	
	
	
}
