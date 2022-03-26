package com.stason.testing.controller.commands.implementent;

import com.stason.testing.controller.commands.Command;
import com.stason.testing.controller.utils.Path;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws UnsupportedEncodingException {
        String url = Path.INDEX;
        return url;
    }
}
