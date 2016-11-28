package com.epam.as.bookhotel.action;


import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

public interface Action {
    String execute(HttpRequest req, HttpResponse res);

}
