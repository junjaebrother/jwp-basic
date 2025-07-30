package next.controller.qna;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QnaController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        req.setAttribute("question", questionDao.findById(questionId));
        req.setAttribute("answers", answerDao.findByQuestionId(questionId));
        return "/qna/show.jsp";
    }
}
