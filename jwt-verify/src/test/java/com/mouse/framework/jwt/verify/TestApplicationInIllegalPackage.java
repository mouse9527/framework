package com.mouse.framework.jwt.verify;

import com.mouse.framework.application.QueryApplication;

@QueryApplication(requireLogged = true)
public class TestApplicationInIllegalPackage {

    public void execute() {

    }
}
