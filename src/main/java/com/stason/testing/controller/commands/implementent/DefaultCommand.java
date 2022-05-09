package com.stason.testing.controller.commands.implementent;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;

import javax.servlet.http.HttpServletRequest;

/**
 * It is a default Command, which sent the user to the main page
 * @author Stanislav Hlova
 * @version 1.0
 */
public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request){
        return Path.INDEX;
    }
}
