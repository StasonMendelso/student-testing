package com.stason.testing.controller.commands;

import javax.servlet.http.HttpServletRequest;
/**
 * It is an interface, which has only one method, that do different things in depending on the implementation
 * @author Stanislav Hlova
 * @version 1.0
 */
public interface Command {
    String execute(HttpServletRequest request) ;
}
