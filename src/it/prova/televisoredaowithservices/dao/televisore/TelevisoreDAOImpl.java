package it.prova.televisoredaowithservices.dao.televisore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.dao.AbstractMySQLDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public class TelevisoreDAOImpl extends AbstractMySQLDAO implements TelevisoreDAO {

	@Override
	public List<Televisore> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Televisore> result = new ArrayList<>();

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from televisore")) {

			while (rs.next()) {
				Televisore temp = new Televisore();
				temp.setId(rs.getLong("id"));
				temp.setMarca(rs.getString("marca"));
				temp.setModello(rs.getString("modello"));
				temp.setPollici(rs.getInt("pollici"));
				temp.setDataProduzione(rs.getDate("dataproduzione"));
				result.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Televisore get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Televisore result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from user where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Televisore();
					result.setId(rs.getLong("id"));
					result.setMarca(rs.getString("marca"));
					result.setModello(rs.getString("modello"));
					result.setPollici(rs.getInt("pollici"));
					result.setDataProduzione(rs.getDate("dataproduzione"));
				} else {
					result = null;
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return result;
		}
	}

	@Override
	public int update(Televisore input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection
				.prepareStatement("UPDATE user SET marca=?, modello=?, pollici=?, dataproduzione=? where id=?;")) {
			ps.setString(1, input.getMarca());
			ps.setString(2, input.getModello());
			ps.setInt(3, input.getPollici());
			ps.setDate(4, new java.sql.Date(input.getDataProduzione().getTime()));
			ps.setLong(5, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Televisore input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection
				.prepareStatement("INSERT INTO user (marca, modello, pollici, dataproduzione) VALUES (?, ?, ?, ?);")) {
			ps.setString(1, input.getMarca());
			ps.setString(2, input.getModello());
			ps.setInt(3, input.getPollici());
			ps.setDate(4, new java.sql.Date(input.getDataProduzione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Televisore input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Televisore> findAllTelevisionBetweenDate(Date primo, Date secondo) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (primo.equals(null) || secondo.equals(null))
			throw new Exception("Attenzione! Parametri inseriti errati");

		List<Televisore> result = new ArrayList<>();
		Televisore temp = null;
		try (PreparedStatement ps = connection
				.prepareStatement("select * from televisore where dataproduzione between ? and ?;")) {
			ps.setDate(1, new java.sql.Date(primo.getTime()));
			ps.setDate(2, new java.sql.Date(secondo.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					temp = new Televisore();
					temp.setId(rs.getLong("id"));
					temp.setMarca(rs.getString("marca"));
					temp.setModello(rs.getString("modello"));
					temp.setPollici(rs.getInt("pollici"));
					temp.setDataProduzione(rs.getDate("dataproduzione"));
					result.add(temp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Televisore findTelevisionBigger() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		Televisore result = null;
		try (Statement s = connection.createStatement();
				ResultSet rs = s.executeQuery("select max(pollici) from televisore;")) {
			if (rs.next()) {
				result = new Televisore();
				result.setId(rs.getLong("id"));
				result.setMarca(rs.getString("marca"));
				result.setModello(rs.getString("modello"));
				result.setPollici(rs.getInt("pollici"));
				result.setDataProduzione(rs.getDate("dataproduzione"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Televisore> brandsNameLastSixMonth(Date input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input.equals(null))
			throw new Exception("Attenzione! Parametro inserito errato");

		List<Televisore> result = new ArrayList<>();
		Televisore temp = null;
		try (PreparedStatement ps = connection
				.prepareStatement("select marca from televisore where dataproduzione > ?;")) {
			ps.setDate(1, new java.sql.Date(input.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					temp = new Televisore();
					temp.setId(rs.getLong("id"));
					temp.setMarca(rs.getString("marca"));
					temp.setModello(rs.getString("modello"));
					temp.setPollici(rs.getInt("pollici"));
					temp.setDataProduzione(rs.getDate("dataproduzione"));
					result.add(temp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
