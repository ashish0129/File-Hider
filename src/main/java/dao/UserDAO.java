package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.ConnectionProvider;
import model.User;

public class UserDAO {

	public static boolean isExists(String email) throws SQLException {
		Connection connection = ConnectionProvider.getConnection();
		PreparedStatement statement = connection.prepareStatement("select email from users");
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			String e = rs.getString(1);
			if (e.equals(email))
				return true;

		}
		return false;
	}

	public static int saveUser(User user) throws SQLException {
		Connection connection = ConnectionProvider.getConnection();
		PreparedStatement statement = connection.prepareStatement("insert into users values(default,?,?)");
		statement.setString(1, user.getName());
		statement.setString(2, user.getEmail());

		return statement.executeUpdate();
	}
}
