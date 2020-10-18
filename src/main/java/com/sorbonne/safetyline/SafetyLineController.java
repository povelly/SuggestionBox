package com.sorbonne.safetyline;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SafetyLineController {
    @GetMapping("/safetylineHome")
    public ModelAndView getUserId() {
        //name of html file inside template
        String viewName = "safetylineHome";
        Map<String, String> model = new HashMap<String, String>();
        model.put("name1", "name2");
        return new ModelAndView(viewName, model);
    }
    @PostMapping("/safetylineregister")
    @ResponseBody
    public ModelAndView register(@RequestParam(name="username")String username, @RequestParam(name="password") String password) {
        String viewName = "safetylineHome";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/safetylineHome");
        return new ModelAndView(redirectView);
    }
}
