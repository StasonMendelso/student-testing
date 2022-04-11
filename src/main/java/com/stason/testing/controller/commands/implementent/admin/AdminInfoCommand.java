package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;

import javax.servlet.http.HttpServletRequest;
/**
 * It is a Command, which shows main information for admin
 * @author Stanislav Hlova
 * @version 1.0
 */
public class AdminInfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return Path.ADMIN_INFO;
    }
}
