package com.stason.testing.controller.commands.implementent;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;

import javax.servlet.http.HttpServletRequest;


public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request){
        return Path.INDEX;
    }
}
