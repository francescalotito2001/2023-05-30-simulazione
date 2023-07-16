package it.polito.tdp.gosales;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Retailers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private ComboBox<Retailers> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	Retailers r = this.cmbRivenditore.getValue();
    	if(r == null) {
    		this.txtResult.setText("Seleziona un rivenditore dal menu");
    		return;
    	}
    	
    	model.componenteConnessa(r);
    	this.txtResult.appendText("\nLa componente connessa di " + r + " è: " + model.getnConnessi());
    	this.txtResult.appendText("\nIl peso della componente connessa è: " + model.getPesoConnessa());
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	//controllo anno
    	Integer anno = this.cmbAnno.getValue();
    	if(anno == null) {
    		this.txtResult.setText("Seleziona un anno dal menu");
    		return;
    	}
    	//controllo nazione
    	String country = this.cmbNazione.getValue();
    	if(country == null) {
    		this.txtResult.setText("Seleziona una nazione dal menu");
    		return;
    	}
    	//controllo inserimento M
    	String input = this.txtNProdotti.getText();
    	int minimo = 0;
       	try {
    		minimo =Integer.parseInt(input);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un valore accettabile");
    		return;
    	}
    	
    	this.txtResult.setText("Creo grafo....");
    	
    	
    	//grazione grafo
    	model.creaGrafo(country, anno, minimo);
    	
    	//stampa grafo
    	this.txtResult.setText("Il grafo è stato creato e contiene; ");
    	this.txtResult.appendText("\nVertici: " + model.getGrafo().vertexSet().size());
    	this.txtResult.appendText("\nArchi: " + model.getGrafo().edgeSet().size());
    	
    	this.txtVertici.setText(model.stampaVertici());
    	this.txtArchi.setText(model.stampaArchi());


    	this.cmbRivenditore.getItems().addAll(model.getVertici());
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbAnno.getItems().add(2015);
    	this.cmbAnno.getItems().add(2016);
    	this.cmbAnno.getItems().add(2017);
    	this.cmbAnno.getItems().add(2018);
    	
    	this.cmbNazione.getItems().addAll(model.getCountry());
    }

}
