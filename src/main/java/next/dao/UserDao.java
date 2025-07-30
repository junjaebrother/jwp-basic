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


    public List<User> findAll() {
        // TODO 구현 필요함.

        JdbcTemplate selectJdbcTemplate = new JdbcTemplate();

        List<User> users = selectJdbcTemplate.query(
                "SELECT userId, password, name, email FROM USERS",
                (ResultSet rs) -> {
                    return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                    );
                });

        return users;
    }

    public User findByUserId(String userId) {
        JdbcTemplate selectJdbcTemplate = new JdbcTemplate();

        List<User> users = selectJdbcTemplate.query(
                "SELECT userId, password, name, email FROM USERS WHERE userid=?",
                (ResultSet rs) -> {
                    return new User(
                            rs.getString("userId"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                },
                userId
        );

        return users.get(0);
    }
}
