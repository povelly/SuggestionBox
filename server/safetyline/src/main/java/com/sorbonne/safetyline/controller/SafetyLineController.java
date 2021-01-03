package com.sorbonne.safetyline.controller;

/**
 *
 * @author Georges Mathieu / Memmi Sacha
 *
 * Controller calling different services
 *
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.ServerPreparedQuery;
import com.sorbonne.safetyline.exception.SessionExpired;
import com.sorbonne.safetyline.exception.EmptySuggestionException;
import com.sorbonne.safetyline.exception.InvalidFormException;
import com.sorbonne.safetyline.exception.UsernameAlreadyExists;
import com.sorbonne.safetyline.exception.UtilisateurInconnuException;
import com.sorbonne.safetyline.model.Connexion;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.dto.SuggestionDTO;
import com.sorbonne.safetyline.dto.UserDTO;
import com.sorbonne.safetyline.service.StrawpollService;
import com.sorbonne.safetyline.service.SuggestionService;
import com.sorbonne.safetyline.service.UserService;
import com.sorbonne.safetyline.utils.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class SafetyLineController {

    @Autowired
    private UserService userService             = new UserService();
    @Autowired
    private StrawpollService strawpollService   = new StrawpollService();
    @Autowired
    private SuggestionService suggestionService = new SuggestionService();

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
    
    ////////////////////////////////// USER //////////////////////////////////
    
    /**
     * Search if the user is in the database or not, used for connexion
     * @return the HTTP response
     */
    @PostMapping("/safetylineConnexion")
    @ResponseBody
    public Map<String,Object> safetylineConnexion(@RequestBody UserDTO user, HttpServletResponse response, HttpServletRequest request)
    {


    	HashMap<String, Object> map = new HashMap<>();
    	List<User> list = userService.authentifyUser(user.getUsername(), PasswordUtil.sha256(user.getPassword()));
    	if (!list.isEmpty())
    	{
    		map.put("status", 200);
    		map.put("message", "user found");
    		map.put("username", user.getUsername());
    		map.put("type", list.get(0).getAdmin());
            HttpSession session = request.getSession();

            session.setAttribute("token_id", "fdsajklfj");
            session.setMaxInactiveInterval(1);
    		return map;
    	} else {
    		map.put("status", 404);
    		map.put("message", "user not found");
    		return map;
    	}
    }
    
    /**
     * Creates an account using id, first name, last name and a boolean, 
     * the boolean that indicates if the user is an admin or not
     * @return the HTTP response
     */
    @PutMapping("/account/{userId}")
    @ResponseBody
    public Map<String,Object> creationCompte(@PathVariable String userId, @RequestBody UserDTO user)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		if(user.getFirstName() == null || user.getLastName() == null) throw new InvalidFormException();
    	    if(user.isAdmin())
            {
                userService.addUser(userId, user.getFirstName(), user.getLastName(), true);
                map.put("status", 200);
                map.put("message", "admin registered");
                map.put("type", true);
            }
    	    else{
    	        userService.addUser(userId, user.getFirstName(), user.getLastName(), false);
                map.put("status", 200);
    	        map.put("message", "user registered");
    	        map.put("type", false);
            }
    	    map.put("username", userId);

        } catch (UsernameAlreadyExists e) {
            map.put("status", 500);
            map.put("message", "username already exists");
            
        } catch (InvalidFormException e) {
            map.put("status", 500);
            map.put("message", "missing content in form");

        } catch(Exception e) {
    	    e.printStackTrace();
    	    map.put("status", 500);
    	    map.put("message", "failed to register");

        } finally {
    	    return map;
        }
    }
    
    /**
     * Updates the account, can be used for passwords for now
     * @return the HTTP response
     */
    @PostMapping("/account")
    @ResponseBody
    public Map<String,Object> updateCompte(@RequestBody UserDTO user, HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    	    HttpSession session = request.getSession(false);
    	    if(session == null || !request.isRequestedSessionIdValid())
            {
                throw new SessionExpired();
            }
    		Optional<User> userFromDB = userService.getUserById(user.getUsername());
    		if(!userFromDB.isPresent())
	            throw new UtilisateurInconnuException();
    		
    	    if(user.getPassword() != null)
            {
    	    	// Changement de mot de passe
    	    	userService.updatePassword(user.getUsername(), user.getPassword(),
    	    			userFromDB.get().getFirstName(), userFromDB.get().getLastName(),
    	    			userFromDB.get().getAdmin());
                map.put("status", 200);
                map.put("message", "password has been updated");
            }
    	    else {
    	    	// Reinitialisation mot de passe
    	    	userService.forgottenPassword(user.getUsername(), userFromDB.get().getFirstName(), 
    	    			userFromDB.get().getLastName(), userFromDB.get().getAdmin());
                map.put("status", 200);
    	        map.put("message", "new password generated");
            }
    	    map.put("username", user.getUsername());

        } catch(SessionExpired s) {
    	    map.put("status", 500);
    	    map.put("message", "your session expired or has never been created");
        }
    	catch (UtilisateurInconnuException e) {
            map.put("status", 500);
            map.put("message", "unknown user");

        } catch(Exception e) {
    	    e.printStackTrace();
    	    map.put("status", 500);
    	    map.put("message", "failed to register");

        } finally {
    	    return map;
        }
    }
    
    /**
     * Deletes an account using the email
     * @return the HTTP response
     */
    @PostMapping("/accountDelete")
    @ResponseBody
    public Map<String,Object> suppressionCompte(@RequestBody UserDTO user)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		Optional<User> userFromDB = userService.getUserById(user.getUsername());
    		if(!userFromDB.isPresent())
	            throw new UtilisateurInconnuException();

    	    if(userFromDB.get().getAdmin())
            {
    	    	int nb = 0;
    	    	// on compte le nombre d'admins if(nb < 2) throw new LastAdminException()
                userService.deleteUserByIdUser(user.getUsername());
                map.put("status", 200);
                map.put("message", "admin deleted, there are " + (nb-1) + " admins left.");
            }
    	    else{
    	    	userService.deleteUserByIdUser(user.getUsername());
                map.put("status", 200);
                map.put("message", "user has been deleted");
            }
    	    map.put("username", user.getUsername());

        } catch (UtilisateurInconnuException e) {
            map.put("status", 500);
            map.put("message", "unknown username");
            // catch LastAdminException
        } catch(Exception e) {
    	    e.printStackTrace();
    	    map.put("status", 500);
    	    map.put("message", "failed to register");

        } finally {
    	    return map;
        }
    }
    
    /////////////////////////////////// SUGGESTION /////////////////////////////////
    
    /**
     * Creates a suggestion
     * @return the HTTP response
     */
    @PostMapping("/suggestion")
    @ResponseBody
    public Map<String,Object> creationSuggestion(@RequestBody SuggestionDTO sug)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		if(sug.getContent() == null) throw new EmptySuggestionException();
    		
    	    if(sug.getAuthor() != null)
            {
    	    	suggestionService.creationSuggestion(sug.getContent(), sug.getAuthor());
                map.put("status", 200);
                map.put("message", "suggestion has been created");
            }
    	    else {
    	    	// Suggestion anonyme
    	    	suggestionService.creationSuggestion(sug.getContent(), null);
                map.put("status", 200);
    	        map.put("message", "anonymous suggestion has been created");
            }

        } catch (EmptySuggestionException e) {
            map.put("status", 500);
            map.put("message", "empty suggestion");
            
        } catch(Exception e) {
    	    e.printStackTrace();
    	    map.put("status", 500);
    	    map.put("message", "failed create suggestion");
    	    
        } finally {
    	    return map;
        }
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
        if (users.isEmpty())
        {
            System.out.println("users empty");
            return "";
        }
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
