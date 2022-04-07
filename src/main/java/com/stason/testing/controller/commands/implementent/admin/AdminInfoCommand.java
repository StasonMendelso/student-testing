package com.stason.testing.controller.commands.implementent.admin;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;

import javax.servlet.http.HttpServletRequest;

public class AdminInfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return Path.ADMIN_INFO;
    }
}
