package com.sorbonne.safetyline.controller;


import com.sorbonne.safetyline.exception.DuplicateUsernameException;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SafetyLineController {
    private UserService userService = new UserService();

    /**
     *
     * @return          Home of safetyline
     */
    @RequestMapping(value="/safetylineHome", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public String getUserId() {
        //name of html file inside template
        String viewName = "safetylineHome";
        Map<String, String> model = new HashMap<String, String>();
        //model.put("name1", "name2");
        return "je ne sais pas";
    }

    /**
     *  First view the user will access before user send the completed register form
     * @param user      User emtpy
     * @return          the register form view and model
     */
    @GetMapping("/safetylineRegisterForm")
    public ModelAndView registerForm(User user) {
        String viewName = "safetylineRegisterForm";
        return new ModelAndView(viewName, new HashMap<>());
    }

    /**
     *  Called after user completed the register form
     * @param user      The user containing every info sent by the user
     * @return          the view and update with form content
     */
    @PostMapping("/safetylineRegisterSubmit")
    public ModelAndView registerSubmit(@Valid User user, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            //System.out.println(bindingResult.getModel());
            return new ModelAndView("/safetylineRegisterForm");
        }

        try {
            userService.addOrUpdateUser(user);
        } catch (DuplicateUsernameException e) {
            bindingResult.rejectValue("user_id", "", "This username has already been used");
            return new ModelAndView("safetylineRegisterForm");
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/safetylineHome");
        return new ModelAndView(redirectView);
    }

}
