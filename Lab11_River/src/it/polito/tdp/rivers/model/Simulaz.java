package it.polito.tdp.rivers.model;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulaz {
	
	private double Q;
	private double C;

	public Simulaz(double q,double f_medio) {
		this.Q=q;
		this.f_medio=f_medio;
		C=Q/2;
		 f_out_min=0.8*f_medio;
	}
	
	private int numGiorniOut=0;
	
	private double f_medio;
	private double f_out_min;
	private double f_out;
	
	private List<Double> occupazioniGiornaliere=new LinkedList<Double>();
	
	private PriorityQueue<Flow> coda ;

	
	public void loadMisurazioni(List<Flow> misurazioni) {
		
		this.coda = new PriorityQueue<>() ;
		
		for(Flow f : misurazioni) {
			coda.add(new Flow(f.getDay(),f.getFlow(),f.getRiver()));
		}
		
	}
	
	
	public void run() {
		
		Flow f ;
		while( (f = coda.poll())!=null ) {
		
		 if(Math.random()>0.95){
			 f_out=f_out*10;
			if((C+f.getFlow())<=Q){
				if(f.getFlow()<f_out_min || f.getFlow()>f_out_min){
				    f_out=f_out_min;
					C=C+(f.getFlow()-f_out);
				    if(C<0){
				    	numGiorniOut++;
				    C=0;
				    }
					occupazioniGiornaliere.add(C);
					
				}
				else{
					C=C;
				}
			}
			else{
				double sovra=(C+f.getFlow())-Q;
				C=Q;
				f_out=f_out_min+sovra;
				C=C-f_out;
				occupazioniGiornaliere.add(C);
			}
		 }
		 else{
			 if((C+f.getFlow())<=Q){
					if(f.getFlow()<f_out_min || f.getFlow()>f_out_min){
					    f_out=f_out_min;
						C=C+(f.getFlow()-f_out);
					    if(C<0){
					    	numGiorniOut++;
					    	C=0;
					    }
						occupazioniGiornaliere.add(C);
					}
					else{
						C=C;
					}
				}
				else{
					double sovra=(C+f.getFlow())-Q;
					C=Q;
					f_out=f_out_min+sovra;
					C=C-f_out;
					occupazioniGiornaliere.add(C);
				} 
				
			}
				
			}
		}


	public int getNumGiorniOut() {
		return numGiorniOut;
	}


	public List<Double> getOccupazioniGiornaliere() {
		return occupazioniGiornaliere;
	}
	
	
}
