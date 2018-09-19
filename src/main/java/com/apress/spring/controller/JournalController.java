package com.apress.spring.controller;


import com.apress.spring.domain.JournalEntry;
import
        org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import       org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import       org.springframework.web.bind.annotation.RestController;
import       org.springframework.web.servlet.ModelAndView;
import com.apress.spring.repository.JournalRepository;

import java.util.List;

/**
 * Created by simon on 22/08/18.
 */
@RestController
public class JournalController {
    private static final String VIEW_INDEX = "index";
    @Autowired
    JournalRepository repo;
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName(VIEW_INDEX);
        modelAndView.addObject("journal", repo.findAll());
        return modelAndView;
    }

    @RequestMapping(value="/journal", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    List<JournalEntry> getJournal(){
        return repo.findAll();
    }
}
