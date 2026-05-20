package com.spring.TestingApp.service.impl;

import com.spring.TestingApp.service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
//@Profile("dev")
public class DataServiceImplDev implements DataService {

    @Override
    public String getData() {
        return "Dev Data";
    }
}
