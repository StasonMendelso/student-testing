package com.stason.testing.controller.commands.implementent.student;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.services.TestService;
import com.stason.testing.controller.utils.Path;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
/**
 * It is a Command, which is responsible for passing the test by student
 * @author Stanislav Hlova
 * @version 1.0
 */
public class DoTestCommand implements Command{
    private static final Logger logger = Logger.getLogger(DoTestCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int testId = 0;

        //For initialization
        if (request.getParameter("id") != null) {
            testId = Integer.parseInt(request.getParameter("id"));
        }

        //If user want to continue the test
        if (request.getSession().getAttribute("test") != null && request.getParameter("id") != null) {
            logger.info("User continue to do test");
            Test currentTest = (Test) request.getSession().getAttribute("test");
            if (testId == currentTest.getId()) {
                return Path.REDIRECT_STUDENT_TEST + "?question=1";
            }
            return Path.REDIRECT_STUDENT_TESTS;

        }
        //For initialization
        if (request.getSession().getAttribute("test") == null && request.getParameter("id") != null && request.getParameter("currentClientTime") != null) {
            //start-> занести в бд що тест пройдено на 0%, занести в сесію, що тест вже пройден.
            List<Integer> idPassedTestsList = (List<Integer>) request.getSession().getAttribute("idOfPassedTests");
            for (Integer id : idPassedTestsList) {
                if (id == testId) return Path.REDIRECT_STUDENT_TESTS;
            }
            int userId = (int) request.getSession().getAttribute("id");
            TestService testService = new TestService();
            idPassedTestsList.add(testId);
            request.getSession().setAttribute("idOfPassedTests", idPassedTestsList);

            Test test = testService.findTestWithQuestionsAndAnswers(testId);
            testService.addPassedTest(userId, testId, 0);

            Date date = new Date(Long.parseLong(request.getParameter("currentClientTime")));
            long outDateMilliseconds = Long.parseLong(request.getParameter("currentClientTime")) + test.getTimeSeconds() * 1000L;
            Date outDate = new Date(outDateMilliseconds);

            logger.info("User started test [" + test.getName() + "] at " + date + " Passed Date is " + outDate);
            request.getSession().setAttribute("test", test);
            request.getSession().setAttribute("outDateMilliseconds", outDateMilliseconds);
            return Path.REDIRECT_STUDENT_TEST + "?question=1";
        }

        if (request.getSession().getAttribute("test") == null) {
            return Path.REDIRECT_STUDENT_TESTS;
        }
        //The user has finished test
        if (request.getParameter("save") != null && request.getParameter("questionNumber") != null && request.getParameter("finish") != null) {
            saveAnswers(request);
            logger.info("User has passed test");
            return new ShowTestResultCommand().execute(request);
        }
        //The user answered one question
        if (request.getParameter("nextQuestion") != null && request.getParameter("save") != null && request.getParameter("questionNumber") != null) return saveAnswers(request);
        //For initialization
        if (request.getParameter("question") != null) {
            int questionNumber = Integer.parseInt(request.getParameter("question"));
            Test test = (Test) request.getSession().getAttribute("test");
            Question question = test.getQuestion(questionNumber);
            request.setAttribute("question", question);
            return Path.STUDENT_TEST;
        }
        if (uri.contains("/student/test")) {
            return Path.STUDENT_TEST;
        }
        return Path.REDIRECT_STUDENT_TEST;


    }

    private String saveAnswers(HttpServletRequest request) {
        int questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
        String[] opt = request.getParameterValues("opt");
        if (opt != null) {
            Test test = (Test) request.getSession().getAttribute("test");
            Question question = test.getQuestion(questionNumber);
            List<Boolean> userOptions = question.getUserOptions();
            for (int i = 0; i < userOptions.size(); i++) {
                userOptions.set(i, false);
            }
            for (int i = 0; i < opt.length; i++) {
                userOptions.set(Integer.parseInt(opt[i]) - 1, true);
            }
            question.setUserOptions(userOptions);
            test.setQuestion(question, questionNumber);

            request.getSession().setAttribute("test", test);

            return Path.REDIRECT_STUDENT_TEST + "?question=" + request.getParameter("nextQuestion");
        } else {
            Test test = (Test) request.getSession().getAttribute("test");
            Question question = test.getQuestion(questionNumber);
            List<Boolean> userOptions = question.getUserOptions();
            for (int i = 0; i < userOptions.size(); i++) {
                userOptions.set(i, false);
            }

            question.setUserOptions(userOptions);
            test.setQuestion(question, questionNumber);
            request.getSession().setAttribute("test", test);
            request.getSession().setAttribute("timeLeft", request.getParameter("leftTime"));
            return Path.REDIRECT_STUDENT_TEST + "?question=" + request.getParameter("nextQuestion");
        }
    }
}
