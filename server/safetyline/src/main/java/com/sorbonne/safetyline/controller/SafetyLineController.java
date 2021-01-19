package com.sorbonne.safetyline.controller;

/**
 *
 * @author Georges Mathieu / Memmi Sacha
 *
 * Controller calling different services
 *
 */

import com.sorbonne.safetyline.exception.EmptySuggestionException;
import com.sorbonne.safetyline.exception.InvalidFormException;
import com.sorbonne.safetyline.exception.LastAdminException;
import com.sorbonne.safetyline.exception.UsernameAlreadyExists;
import com.sorbonne.safetyline.exception.UtilisateurInconnuException;
import com.sorbonne.safetyline.model.Suggestion;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.dto.SuggestionDTO;
import com.sorbonne.safetyline.dto.UserDTO;
import com.sorbonne.safetyline.service.StrawpollService;
import com.sorbonne.safetyline.service.SuggestionService;
import com.sorbonne.safetyline.service.UserService;
import com.sorbonne.safetyline.utils.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
public class SafetyLineController {

    @Autowired
    private UserService userService             = new UserService();
    @Autowired
    private StrawpollService strawpollService   = new StrawpollService();
    @Autowired
    private SuggestionService suggestionService = new SuggestionService();

	private static final Logger LOGGER  = LoggerFactory.getLogger(SafetyLineController.class);
    ////////////////////////////////// USER //////////////////////////////////
    
    /**
     * Search if the user is in the database or not, used for connexion
     * @return the HTTP response
     */
    @PostMapping("/safetylineConnexion")
    @ResponseBody
    public Map<String,Object> safetylineConnexion(@RequestBody UserDTO user,  HttpServletRequest request)
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
            session.setMaxInactiveInterval(100);
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
    public Map<String,Object> creationCompte(@PathVariable String userId, @RequestBody UserDTO user, HttpServletRequest request)
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
    	    
    		Optional<User> userFromDB = userService.getUserById(user.getUsername());
    		if(!userFromDB.isPresent())
	            throw new UtilisateurInconnuException();
    		
    	    if(user.getOldPassword() != null && user.getNewPassword() != null)
            {

    	    	userService.updatePassword(user.getUsername(), user.getOldPassword(),
    	    			user.getNewPassword(), userFromDB.get());
                map.put("status", 200);
                map.put("message", "password has been updated");
            }
    	    else {
    	    	// Reinitialisation mot de passe
    	    	userService.forgottenPassword(user.getUsername(), userFromDB.get());
                map.put("status", 200);
    	        map.put("message", "new password generated");
            }
    	    map.put("username", user.getUsername());

        } catch (UtilisateurInconnuException e) {
            map.put("status", 500);
            map.put("message", "unknown user");

        } catch(Exception e) {
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
    public Map<String,Object> suppressionCompte(@RequestBody UserDTO user, HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		Optional<User> userFromDB = userService.getUserById(user.getUsername());
    		if(!userFromDB.isPresent())
	            throw new UtilisateurInconnuException();

    	    if(userFromDB.get().getAdmin())
            {
    	    	int nb = userService.getAllAdmins().size();
    	    	if (nb < 2) { 
    	    		throw new LastAdminException(); 
    	    	}
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
            
        } catch(LastAdminException e) {
    	    map.put("status", 500);
    	    map.put("message", "you're trying to delete the last admin account");
    	    
        } catch(Exception e) {
    	    map.put("status", 500);
    	    map.put("message", "failed to register");

        } finally {
    	    return map;
        }
    }
    
    /**
     * Get all users
     * @return the HTTP response
     */
    @GetMapping("/accounts")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getAllUsers(HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		List<User> listUsers = userService.getAllUsers();
    		if (listUsers.isEmpty()) 
    		{
    			map.put("status", 500);
    			map.put("message", "no users in db");
    		}
    		map.put("status", 200);
    		
    		List<UserDTO> listNoPassword = new ArrayList<>();
    		listUsers.forEach(u -> {
    			listNoPassword.add(new UserDTO(u.getUserId(), u.getFirstName(), u.getLastName(), u.getAdmin()));
    		});
    		map.put("users", listNoPassword);
    		
    	} catch(Exception e) {
    	    map.put("status", 500);
    	    map.put("message", "failed to retrieve users");
    	    
        } finally {
    	    return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    
    /////////////////////////////////// SUGGESTION /////////////////////////////////
    
    /**
     * Get all suggestions
     * @return the HTTP response
     */
    @PostMapping("/suggestions")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getAllSuggestions(@RequestBody SuggestionDTO suggestion, HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	System.out.println("date end"+suggestion.getEnd());
    	try{
    	    List<Suggestion> listSuggestion;
    	    
    	    if (suggestion.getBegin() != null && suggestion.getEnd() != null) {
    	    	 listSuggestion = suggestionService.getSuggestions(suggestion.getAuthor(),
                        suggestion.getContent(), suggestion.getBegin(),suggestion.getEnd());
    	    } else {
    	    	listSuggestion = suggestionService.getAllSuggestions();
    	    }
    	    
    		
    		if (listSuggestion.isEmpty()) 
    		{
    			map.put("status", 500);
    			map.put("message", "no suggestions in db");
    		}
    		map.put("status", 200);
    		map.put("suggestions", listSuggestion);
    		
    	} catch(Exception e) {
    	    map.put("status", 500);
    	    map.put("message", "failed to retrieve suggestions");
    	    
        } finally {
    	    return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    
    
    /**
     * Creates a suggestion
     * @return the HTTP response
     */
    @PostMapping("/suggestion")
    @ResponseBody
    public Map<String,Object> creationSuggestion(@RequestBody SuggestionDTO sug, HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		if(sug.getContent() == null) throw new EmptySuggestionException();
    		
    		List<User> admins = userService.getAllAdmins();
    		
    	    if(sug.getAuthor() != null)
            {
    	    	suggestionService.creationSuggestion(sug.getContent(), sug.getAuthor(), admins);
                map.put("status", 200);
                map.put("message", "suggestion has been created");
            }
    	    else {
    	    	// Suggestion anonyme
    	    	suggestionService.creationSuggestion(sug.getContent(), null, admins);
                map.put("status", 200);
    	        map.put("message", "anonymous suggestion has been created");
            }
    	    
    	} catch (EmptySuggestionException e) {
            map.put("status", 500);
            map.put("message", "empty suggestion");
            
        } catch(Exception e) {
    	    map.put("status", 500);
    	    map.put("message", "failed create suggestion");
    	    
        } finally {
    	    return map;
        }
    }

	/////////////////////////////////// STRAWPOLL /////////////////////////////////

	@GetMapping("/createStrawpoll")
	@ResponseBody
	public Map<String,Object> createStrawpoll(HttpServletRequest request)
	{
		HashMap<String, Object> map = new HashMap<>();
		try{
			strawpollService.createStrawpoll("mytitle", "user@mail.com",null);
			strawpollService.insertChoice(9,"my content");
			map.put("status", 200);
			map.put("message", "strawpoll has been created");

		} catch(Exception e) {
			map.put("status", 500);
			map.put("message", "failed create suggestion");

		} finally {
			return map;
		}
	}
}
