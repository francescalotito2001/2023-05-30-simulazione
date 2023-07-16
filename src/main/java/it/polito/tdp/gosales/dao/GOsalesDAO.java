package it.polito.tdp.gosales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.gosales.model.Arco;
import it.polito.tdp.gosales.model.DailySale;
import it.polito.tdp.gosales.model.Products;
import it.polito.tdp.gosales.model.Retailers;

public class GOsalesDAO {
	
	//FATTI DA ME
	public List<Retailers> getVertici(String country){
		String sql = "SELECT  r.* "
				+ "FROM go_retailers r "
				+ "WHERE r.Country = ? "
				+ "ORDER BY r.Retailer_name";
		List<Retailers> result = new ArrayList<Retailers>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, country);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	public Arco getArco(Retailers v1, Retailers v2, int anno, int min) {
		String sql ="SELECT s1.Retailer_code, s2.Retailer_code, COUNT(DISTINCT s1.Product_number) AS peso "
				+ "FROM go_daily_sales s1, go_daily_sales s2 "
				+ "WHERE s1.Product_number = s2.Product_number AND s1.Retailer_code = ? AND s2.Retailer_code = ? "
				+ "AND year(s1.Date) = ? AND YEAR(s1.Date) = YEAR(s2.Date) "
				+ "GROUP BY s1.Retailer_code, s2.Retailer_code "
				+ "HAVING peso >= ? ";
		Arco result = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, v1.getCode());
			st.setInt(2, v2.getCode());
			st.setInt(3, anno);
			st.setInt(4, min);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result = new Arco(v1, v2, rs.getInt("peso"));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String> getCountry (){
		String query = "SELECT DISTINCT r.Country "
				+ "FROM go_retailers r "
				+ "ORDER BY r.Country asc";
		List<String> result = new ArrayList<String>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("Country"));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Metodo per leggere la lista di tutti i rivenditori dal database
	 * @return
	 */
	public List<Retailers> getAllRetailers(){
		String query = "SELECT * from go_retailers";
		List<Retailers> result = new ArrayList<Retailers>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	/**
	 * Metodo per leggere la lista di tutti i prodotti dal database
	 * @return
	 */
	public List<Products> getAllProducts(){
		String query = "SELECT * from go_products";
		List<Products> result = new ArrayList<Products>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Products(rs.getInt("Product_number"), 
						rs.getString("Product_line"), 
						rs.getString("Product_type"), 
						rs.getString("Product"), 
						rs.getString("Product_brand"), 
						rs.getString("Product_color"),
						rs.getDouble("Unit_cost"), 
						rs.getDouble("Unit_price")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	
	/**
	 * Metodo per leggere la lista di tutte le vendite nel database
	 * @return
	 */
	public List<DailySale> getAllSales(){
		String query = "SELECT * from go_daily_sales";
		List<DailySale> result = new ArrayList<DailySale>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new DailySale(rs.getInt("retailer_code"),
				rs.getInt("product_number"),
				rs.getInt("order_method_code"),
				rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
				rs.getInt("quantity"),
				rs.getDouble("unit_price"),
				rs.getDouble("unit_sale_price")  ));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
}
