package com.stason.testing.controller.utils;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.stason.testing.controller.utils.ErrorForUser.*;

//take lang and error as attributes
public class ErrorTagLocalization extends TagSupport {
    private static final HashMap<ErrorForUser, String> keys = new HashMap<>();

    static {
        //todo доробити мапу з локалізаціює
        keys.put(INVALID_LOGIN, "error.invalid.email");
        keys.put(INVALID_PASSWORD, "error.invalid.password");
        keys.put(INVALID_CAPTCHA, "error.invalid.captcha");
        keys.put(ACCOUNT_IS_BLOCKED, "error.account.blocked");
        keys.put(ACCOUNT_IS_LOGGED, "error.account.logged");
        keys.put(ACCOUNT_NOT_FOUND, "error.account.not_found");
    }

    private ErrorForUser error;
    private ResourceBundle resourceBundle;

    public void setLang(String lang) {
        Locale locale = new Locale(lang);
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public void setError(ErrorForUser error) {
        this.error = error;
    }

    public int doStartTag() throws JspException {
        String key = keys.get(error);
        String internationalizedError = resourceBundle.getString(key);
        JspWriter out = pageContext.getOut();
        try {
            out.print(internationalizedError);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
