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
    public List query(String sql, PreparedStatementSetter preSet, RowMapper rowMapper) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ){
            preSet.values(pstmt);
            try (ResultSet rs = pstmt.executeQuery()){
                List<Object> users = new ArrayList<>();

                while(rs.next()) {
                    users.add(rowMapper.mapRow(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, PreparedStatementSetter preSet) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            preSet.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
