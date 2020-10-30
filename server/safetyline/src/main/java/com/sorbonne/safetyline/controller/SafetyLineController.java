package com.sorbonne.safetyline.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sorbonne.safetyline.dataAccess.UserDoa;
import com.sorbonne.safetyline.exception.DuplicateUsernameException;
import com.sorbonne.safetyline.model.Admin;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class SafetyLineController {
    @Autowired
    private UserService userService = new UserService();

    /**
     *
     * @return          Home of safetyline
     */
    @RequestMapping(value="/safetylineHome" , produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public JsonNode getUserId() throws JsonProcessingException {
        //name of html file inside template
        //String viewName = "safetylineHome";
        //Map<String, String> model = new HashMap<String, String>();
        //model.put("name1", "name2");
        //JSONParser parser = new JSONParser();
        //return (JSONObject) parser.parse("OK");
        String f ="fdjklsf";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree("{\"k1\":\"v1\",\"k2\":\"v2\"}");
        return actualObj;
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


        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/safetylineHome");
        return new ModelAndView(redirectView);
    }

    @RequestMapping(value= "/safetylineUserTest", method = RequestMethod.GET)
    public String testUserDB(){
        //userService.deleteUserById_user("user2");
        List<User> users = userService.getAllUsers();
        //List<User> admins = userDoa.getAllAdmins();//
        for (User user: users)
            System.out.println(user);
        //userService.deleteUserById_user("user2");
        //userService.getAllChoicesUser("mathieumemmi");
        userService.getAllSuggestions("mathieumemmi");
        return "fjsjkl";
    }
}
