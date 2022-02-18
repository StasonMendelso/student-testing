package com.stason.testing.controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface Command {
    String execute(HttpServletRequest request) throws UnsupportedEncodingException;


}
