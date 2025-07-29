package next.dao;

import core.jdbc.ConnectionManager;
import next.dao.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameter) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ){
            for(int i = 0; i < parameter.length; i++) {
                pstmt.setObject(i+1, parameter[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()){
                List<T> users = new ArrayList<>();

                while(rs.next()) {
                    users.add(rowMapper.mapRow(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Object... parameters) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i+1, parameters[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

}
