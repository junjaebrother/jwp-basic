package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import next.model.User;
import org.h2.result.Row;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.update(
                "INSERT INTO USERS VALUES (?, ?, ?, ?)",
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail()
        );
    }

    public void update(User user){
        // TODO 구현 필요함.
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.update(
                "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?",
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getUserId()
        );
    }


    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.

        JdbcTemplate selectJdbcTemplate = new JdbcTemplate();

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }
        };

        List<User> users = selectJdbcTemplate.query(
                "SELECT userId, password, name, email FROM USERS",
                rowMapper);

        return users;
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate selectJdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetter() {
            @Override
            public void values(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };

        List<User> users = selectJdbcTemplate.query(
                "SELECT userId, password, name, email FROM USERS WHERE userid=?",
                rowMapper,
                userId
        );

        return users.get(0);
    }
}
