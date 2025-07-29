package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException{
                pstmt.setString(4, user.getUserId());
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
            }
        };

        jdbcTemplate.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?");
    }


    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.

        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {

            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }
        };

        List users = selectJdbcTemplate.query("SELECT userId, password, name, email FROM USERS");

        return users;
    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }
        };

        List users = selectJdbcTemplate.query("SELECT userId, password, name, email FROM USERS WHERE userid=?");

        return (User) users.get(0);
    }
}
