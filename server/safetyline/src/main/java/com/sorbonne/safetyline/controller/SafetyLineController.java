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
import com.sorbonne.safetyline.model.Suggestion;
import com.sorbonne.safetyline.model.User;
import com.sorbonne.safetyline.dto.SuggestionDTO;
import com.sorbonne.safetyline.dto.UserDTO;
import com.sorbonne.safetyline.service.StrawpollService;
import com.sorbonne.safetyline.service.SuggestionService;
import com.sorbonne.safetyline.service.UserService;
import com.sorbonne.safetyline.utils.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    		// Vérification de la session
	    	HttpSession session = request.getSession(false);

    	    if(session == null)
            {
                throw new SessionExpired();
            }
    	    
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
    	    
    		Optional<User> userFromDB = userService.getUserById(user.getUsername());
    		if(!userFromDB.isPresent())
	            throw new UtilisateurInconnuException();
    		
    	    if(user.getOldPassword() != null && user.getNewPassword() != null)
            {
    	    	// Changement de mot de passe
    	    	
    	    	// Vérification de la session
    	    	HttpSession session = request.getSession(false);

        	    if(session == null)
                {
                    throw new SessionExpired();
                }
        	    
    	    	userService.updatePassword(user.getUsername(), user.getOldPassword(),
    	    			user.getNewPassword(), userFromDB.get().getFirstName(), 
    	    			userFromDB.get().getLastName(), userFromDB.get().getAdmin(), 
    	    			userFromDB.get().getPassword());
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
    public Map<String,Object> suppressionCompte(@RequestBody UserDTO user, HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		
    		// Vérification de la session
	    	HttpSession session = request.getSession(false);

    	    if(session == null)
            {
                throw new SessionExpired();
            }
    	    
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
    		// Vérification de la session
	    	HttpSession session = request.getSession(false);

    	    if(session == null)
            {
                throw new SessionExpired();
            }
    	    
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
    	    e.printStackTrace();
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
    @GetMapping("/suggestions")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getAllSuggestions(HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	try{
    		// Vérification de la session
	    	HttpSession session = request.getSession(false);

    	    if(session == null)
            {
                throw new SessionExpired();
            }
    	    
    		List<Suggestion> listSuggestion = suggestionService.getAllSuggestions();
    		if (listSuggestion.isEmpty()) 
    		{
    			map.put("status", 500);
    			map.put("message", "no suggestions in db");
    		}
    		map.put("status", 200);
    		map.put("suggestions", listSuggestion);
    		
    	} catch(SessionExpired s) {
    	    map.put("status", 500);
    	    map.put("message", "your session expired or has never been created");
            
        } catch(Exception e) {
    	    e.printStackTrace();
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
    		// Vérification de la session
	    	HttpSession session = request.getSession(false);

    	    if(session == null)
            {
                throw new SessionExpired();
            }
    	    
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
    	    e.printStackTrace();
    	    map.put("status", 500);
    	    map.put("message", "failed create suggestion");
    	    
        } finally {
    	    return map;
        }
    }
}
