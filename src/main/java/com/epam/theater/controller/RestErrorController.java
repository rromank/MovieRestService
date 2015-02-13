//package com.epam.theater.controller;
//
//import com.epam.theater.service.message.StatusMessage;
//import org.springframework.boot.autoconfigure.web.ErrorController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class RestErrorController implements ErrorController {
//
//    @RequestMapping(value = "/error")
//    public StatusMessage error() {
//        return new StatusMessage(StatusMessage.Status.ERROR, "not supported");
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//
//}