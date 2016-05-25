package it.polito.tdp.rivers.db;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RiversDAO {

	public List<River> getAllRivers() {
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
			rivers.add(new River(res.getInt("id"), res.getString("name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return rivers;

	}

	public List<Flow> getAllFlows(List<River> rivers) {
		final String sql = "SELECT id, day, flow, river FROM flow";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				flows.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"),
						rivers.get(rivers.indexOf(new River(res.getInt("river"))))));
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return flows;

	}
	
	public int getNumberOfFlow(River river) {
		
		final String sql = "SELECT id FROM flow WHERE river=?";
		
		List<Integer> FlowNumber = new LinkedList<Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, river.getId());

			ResultSet res = st.executeQuery();

			while (res.next()) {
				Integer id=res.getInt("id");
		
				FlowNumber.add(id);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
			
			return FlowNumber.size();
			
			
			}
	
	
	public Statistics getStatistics (River river) {
		
		final String sql = "SELECT MIN(day),MAX(day),AVG(flow) FROM flow WHERE river=?";
		
		Statistics s=null;
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, river.getId());

			ResultSet res = st.executeQuery();
			

			while (res.next()) {
				LocalDate min=res.getDate("MIN(day)").toLocalDate();
				LocalDate max=res.getDate("MAX(day)").toLocalDate();
				Float avg=res.getFloat("AVG(flow)");
				
				s=new Statistics(min,max,avg);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
			
			return s ;
	}
	
	public List<Flow> getFlowByRiver(River river) {
		
		final String sql = "SELECT * FROM flow WHERE river=?";
		
		List<Flow> rivers = new LinkedList<Flow>();
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, river.getId());

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				LocalDate day=res.getDate("day").toLocalDate();
				double flow=res.getDouble("flow");
		
				Flow f=new Flow(day,flow,river);
				rivers.add(f);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
			
			return rivers;
			
			
			}
	
			


	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();

		List<River> rivers = dao.getAllRivers();
		System.out.println(rivers);

		List<Flow> flows = dao.getAllFlows(rivers);
		System.out.format("Loaded %d flows\n", flows.size());
		// System.out.println(flows) ;
	}

}
