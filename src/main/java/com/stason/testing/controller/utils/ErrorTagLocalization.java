package com.stason.testing.controller.utils;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.util.EnumMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.stason.testing.controller.utils.ErrorForUser.*;


/**It is class for tag, which localize errors on jsp-pages
 * @author Stanislav Hlova
 * @version 1.0
 */
public class ErrorTagLocalization extends TagSupport {
    private static final EnumMap<ErrorForUser, String> keys = new EnumMap<>(ErrorForUser.class);

    static {

        keys.put(INVALID_LOGIN, "error.invalid.email");
        keys.put(INVALID_PASSWORD, "error.invalid.password");
        keys.put(INVALID_CAPTCHA, "error.invalid.captcha");
        keys.put(ACCOUNT_IS_BLOCKED, "error.account.blocked");
        keys.put(ACCOUNT_IS_LOGGED, "error.account.logged");
        keys.put(ACCOUNT_NOT_FOUND, "error.account.not_found");

        keys.put(EMPTY_ANSWER_OPTION, "error.answer.empty_option");
        keys.put(EMPTY_RIGHT_ANSWER_OPTION, "error.answer.empty_right_option");
        keys.put(INVALID_ANSWER_NAME, "error.invalid.answer_name");
        keys.put(INVALID_DISCIPLINE_NAME, "error.invalid.discipline_name");
        keys.put(INVALID_QUESTION_NAME, "error.invalid.question_name");
        keys.put(INVALID_TEST_DIFFICULTY, "error.invalid.test_difficulty");
        keys.put(INVALID_TEST_DURATION, "error.invalid.test_duration");
        keys.put(INVALID_TEST_NAME, "error.invalid.test_name");
        keys.put(SUCH_TEST_NAME_ALREADY_EXISTS, "error.test.such_name_exists");
        keys.put(SECRET_CODE_NOT_MATCH, "error.secret_code.not.match");
        keys.put(INVALID_NAME, "error.invalid.name");
        keys.put(INVALID_SURNAME, "error.invalid.surname");
        keys.put(IDENTIFICATION_LINK_WAS_SENT, "identification_link.sent.yet");
        keys.put(PASSWORD_NOT_MATCH, "error.passwords.not.match");
        keys.put(ACCOUNT_IS_BUSY, "error.account.is.busy");
        keys.put(INVALID_ACTIVATION_CODE, "invalid.activation.code");
        keys.put(INCORRECT_ACTIVATION_CODE, "incorrect.activation.code");

    }

    private ErrorForUser error;
    private ResourceBundle resourceBundle;

    /**
     * Sets a language
     * @param lang a language, which tag received from tag's attributes as required
     */
    public void setLang(String lang) {
        resourceBundle = ResourceBundle.getBundle("messages", new Locale(lang));
    }
    /**
     * Sets an error
     * @param error an error, which tag received from tag's attributes as required
     */
    public void setError(ErrorForUser error) {
        this.error = error;
    }
    /*It is a tag handler, which take an Error from map and localize it with ResourceBundle*/
    @Override
    public int doStartTag() throws JspException {
        String key = keys.get(error);
        String internationalizedError;
        try {
            internationalizedError = resourceBundle.getString(key);
        }catch (NullPointerException ex) {
            return SKIP_BODY;
        }
        JspWriter out = pageContext.getOut();
        try {
            out.print(internationalizedError);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
