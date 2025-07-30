package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

import java.sql.ResultSet;
import java.util.List;

public class AnswerDao {
    public void insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO ANSWERS VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                answer.getWriter(),
                answer.getContents(),
                answer.getCreatedDate(),
                answer.getQuestionId()
        );
    }

    public Answer findById(long answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        List<Answer> answers = jdbcTemplate.query(
                sql,
                (ResultSet rs) -> {
                    return new Answer(
                            rs.getLong("answerId"),
                            rs.getString("writer"),
                            rs.getString("contents"),
                            rs.getTimestamp("createdDate"),
                            rs.getLong("questionId")
                    );
                },
                answerId
        );

        return answers.get(0);
    }

    public List<Answer> findByQuestionId(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId = ?";

        return jdbcTemplate.query(
                sql,
                (ResultSet rs) -> {
                    return new Answer(
                            rs.getLong("answerId"),
                            rs.getString("writer"),
                            rs.getString("contents"),
                            rs.getTimestamp("createdDate"),
                            rs.getLong("questionId")
                    );
                },
                questionId
        );
    }
}
