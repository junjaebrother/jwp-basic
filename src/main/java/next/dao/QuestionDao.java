package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;

import java.sql.ResultSet;
import java.util.List;

public class QuestionDao {
    public void insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getCreatedDate(),
                0);
    }

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";

        return jdbcTemplate.query(sql,
                (ResultSet rs) -> {
                    return new Question(
                            rs.getLong("questionId"),
                            rs.getString("writer"),
                            rs.getString("title"),
                            rs.getString("contents"),
                            rs.getTimestamp("createdDate"),
                            rs.getInt("countOfAnswer")
                    );}
                );

    }

    public Question findById(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";
        List<Question> list =  jdbcTemplate.query(sql,
                (ResultSet rs) -> {
                    return new Question(
                            rs.getLong("questionId"),
                            rs.getString("writer"),
                            rs.getString("title"),
                            rs.getString("contents"),
                            rs.getTimestamp("createdDate"),
                            rs.getInt("countOfAnswer")
                    );},
                questionId
        );

        return list.get(0);
    }
}
