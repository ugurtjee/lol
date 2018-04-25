import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {

	private static final String JDBC_URL = "jdbc:mysql://localhost/catan_dev";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "";

	private static Connection connection;
	private String username;
	private String wachtwoord;

	public Register(String username, String wachtwoord) throws SQLException {
		this.username = username;
		this.wachtwoord = wachtwoord;
		createConnection();
	}

	private void createConnection() throws SQLException {
		connection = DriverManager.getConnection(JDBC_URL + "test", JDBC_USER, JDBC_PASSWORD);
	}

	private boolean validateUser() throws SQLException {
		if (username.length() < 3 || wachtwoord.length() < 3 || !username.matches("[a-zA-Z0-9 ]*")
				|| !wachtwoord.matches("[a-zA-Z0-9 ]*")) {
			return false;
		} else {
			return !checkExistsUser();
		}
	}

	private boolean checkExistsUser() throws SQLException {
		// kijk of er een user al in de database bestaat met de username

		String sql = "SELECT * FROM account WHERE username=?";
		PreparedStatement preparedstatement = connection.prepareStatement(sql);
		preparedstatement.setString(1, username);
		ResultSet user = preparedstatement.executeQuery();

		if (user.next()) {
			// er bestaat een user
			return true;
		} else {
			// er bestaat geen user
			return false;
		}
	}

	public void insertUser() throws SQLException {
		if (validateUser()) {
			// Insert user in database
			String sql = "INSERT INTO account(username, wachtwoord) VALUES(?, ?)";
			PreparedStatement preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, username);
			preparedstatement.setString(2, wachtwoord);
			preparedstatement.executeUpdate();
		}
	}

}
