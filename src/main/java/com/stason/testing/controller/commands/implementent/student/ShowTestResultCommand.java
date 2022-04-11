package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * It is a Command which show result of passed test for student.
 * @author Stanislav Hlova
 * @version 1.0
 */
public class ShowTestResultCommand implements com.stason.testing.controller.commands.Command {
    private static final Logger logger = Logger.getLogger(ShowTestResultCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        //Обраховуємо оцінку
        if (request.getRequestURI().contains("/student/result")) {
            if (request.getSession().getAttribute("test") != null) {
                Test test = (Test) request.getSession().getAttribute("test");
                int countOfQuestions = test.getCountOfQuestions();
                int countOfRightAnswers = 0;

                List<Question> questionList = test.getQuestions();
                for (Question question : questionList) {
                    List<Answer> answerList = question.getAnswers();
                    List<Boolean> userOptions = question.getUserOptions();
                    boolean flag = true;
                    for (int i = 0; i < answerList.size(); i++) {
                        if (!answerList.get(i).isRightAnswer() == (userOptions.get(i))) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) countOfRightAnswers++;
                }

                double mark = ((double) countOfRightAnswers / countOfQuestions) * 100;
                mark = (double) ((int) (mark * 100)) / 100;
                int userId = (int) request.getSession().getAttribute("id");
                TestService testService = new TestService();
                logger.info("The mark is " + mark);
                try {
                    testService.updatePassedTest(userId, test.getId(), mark);
                } catch (Exception ex) {
                    testService.deletePassedTestForUser(test.getId(), userId);
                    request.getSession().removeAttribute("test");
                    throw ex;
                }
                request.setAttribute("countOfRightAnswers", countOfRightAnswers);
                request.setAttribute("mark", mark);
                request.setAttribute("test", test);
                request.getSession().removeAttribute("test");
            } else {
                return Path.REDIRECT_STUDENT_TESTS;
            }
            return Path.STUDENT_RESULT;
        }
        return Path.REDIRECT_STUDENT_RESULT;

    }
}
