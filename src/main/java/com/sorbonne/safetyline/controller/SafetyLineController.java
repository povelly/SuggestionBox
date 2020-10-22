package com.sorbonne.safetyline.controller;


import com.sorbonne.safetyline.exception.DuplicateUsernameException;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SafetyLineController {
    private UserService userService = new UserService();

    /**
     *
     * @return          Home of safetyline
     */
    @GetMapping("/safetylineHome")
    public ModelAndView getUserId() {
        //name of html file inside template
        String viewName = "safetylineHome";
        Map<String, String> model = new HashMap<String, String>();
        //model.put("name1", "name2");
        return new ModelAndView(viewName, model);
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
