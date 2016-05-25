package it.polito.tdp.rivers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulaz;
import it.polito.tdp.rivers.model.Statistics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {
	
	RiversDAO dao=new RiversDAO();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtFMed;

    @FXML
    private Button btnSimula;

    @FXML
    private TextField txtK;
    
    @FXML
    void doScelto(ActionEvent event) {
    	
    	for(River riv:dao.getAllRivers()){
        	if(boxRiver.getValue().getId()==riv.getId()){
        		Statistics s=dao.getStatistics(riv);
        		int i=dao.getNumberOfFlow(riv);
        		txtStartDate.setText(""+s.getMin());
        		txtEndDate.setText(""+s.getMax());
        		txtFMed.setText(""+s.getAvg());
        		txtNumMeasurements.setText(""+i);
        	}
        }
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	
    	double K=Double.parseDouble(txtK.getText());
    	
    	for(River riv:dao.getAllRivers()){
        	if(boxRiver.getValue().getId()==riv.getId()){
        		Statistics s=dao.getStatistics(riv); 
        		long sec=30;
        		double Q=K*s.getAvg()*sec;
        		Simulaz sim=new Simulaz(Q,s.getAvg());
        		
        		List<Flow> rivers=dao.getFlowByRiver(riv);
        		
        		sim.loadMisurazioni(rivers);
        		
        		sim.run();
        		
        		double somma = 0;
        		for(Double d:sim.getOccupazioniGiornaliere()){
            		 somma +=d;
        		}
        		double media= (somma/(sim.getOccupazioniGiornaliere().size()));
        		txtResult.appendText(sim.getNumGiorniOut()+"\n"+media);
        		}
        	}


    }


    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";

        for(River riv:dao.getAllRivers())
        	boxRiver.getItems().add(riv);
       }
}
