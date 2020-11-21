package com.sorbonne.safetyline.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sorbonne.safetyline.model.Choice;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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
    
    @RequestMapping("/safetylineConnexion")
    public boolean safetylineConnexion(@RequestParam(name ="username") String username, @RequestParam(name="password") String passwordHash)
    {
        return userService.authentifyUser(username,passwordHash);
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
    public String testUserDB()  {
        //userService.deleteUserById_user("user2");
        List<User> users = userService.getAllUsers();
        //List<User> admins = userDoa.getAllAdmins();//
        for (User user: users)
            System.out.println(user);
        User user = users.get(0);
        //userService.deleteUserById_user("user2");
        //userService.getAllChoicesUser("mathieumemmi");
        //List<User> userfindall =userService.getAllAdmins();
        //System.out.println(userfindall);
        //Optional<User> findUser = userService.findByUser_id("mathieumemmi");
        //System.out.println(findUser);
        //String lowerBoundRangeDateS = "2020/10/29 16:40:22";
        //String upperBoundRangeDateS = "2020/10/31 19:00:00";
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //LocalDateTime lowerBoundRangeDate = LocalDateTime.of(2020,10,29,16,40,21);
        //LocalDateTime upperBoundRangeDate = LocalDateTime.of(2020,10,30,18,0,0);

        //System.out.println("print all suggestions between "+ lowerBoundRangeDate +" and "
        //        + upperBoundRangeDate +
         //       userService.getSuggestionByDateRange
         //               (java.sql.Date.valueOf(lowerBoundRangeDate.toLocalDate()),java.sql.Date.valueOf(upperBoundRangeDate.toLocalDate())));
        //System.out.println(user);
        System.out.println(user.getSuggestionList());
        //List<Choice> choices = userService.getAllChoices();


        //System.out.println(userService.getSuggestionByAuthor("joe"));
        return "fjsjkl";
    }
}
