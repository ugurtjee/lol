import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

	private static final String JDBC_URL = "jdbc:mysql://localhost/catan_dev";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "";

	private static Connection connection;
	private String username;
	private String wachtwoord;

	public Login(String username, String wachtwoord) throws SQLException {
		this.username = username;
		this.wachtwoord = wachtwoord;
		createConnection();
	}

	private void createConnection() throws SQLException {
		connection = DriverManager.getConnection(JDBC_URL + "test", JDBC_USER, JDBC_PASSWORD);
	}
	 
	@SuppressWarnings("unused")
	private boolean ValidateLogin() throws SQLException {
		// Checken of logingegevens overeenkomen met de opgeslagen waarden in de database.
		String sql = "SELECT * FROM account WHERE username = ? AND wachtwoord = ?";
		PreparedStatement preparedstatement = connection.prepareStatement(sql);
		preparedstatement.setString(1, username);
		preparedstatement.setString(2, wachtwoord);
		ResultSet login = preparedstatement.executeQuery();

		if (login.next()) {
			// Username en wachtwoord correct!
			return true;
		} else {
			// Username of wachtwoord incorrect!
			return false;
		}
	}

}
