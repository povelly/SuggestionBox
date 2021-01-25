package com.sorbonne.safetyline.controller;

/**
 *
 * @author Georges Mathieu / Memmi Sacha
 *
 * Controller calling different services
 *
 */

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.sorbonne.safetyline.dto.StrawpollDTO;
import com.sorbonne.safetyline.exception.EmptySuggestionException;
import com.sorbonne.safetyline.exception.InvalidFormException;
import com.sorbonne.safetyline.exception.LastAdminException;
import com.sorbonne.safetyline.exception.UsernameAlreadyExists;
import com.sorbonne.safetyline.exception.UtilisateurInconnuException;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<UserDTO> safetylineConnexion(@RequestBody UserDTO user,  HttpServletRequest request)
    {
		//String res=null;
    	List<User> users = userService.authentifyUser(user.getUsername(), PasswordUtil.sha256(user.getPassword()));
    	UserDTO res = new UserDTO();
    	if (!users.isEmpty())
    	{

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(100);
            LOGGER.info("Authentified user");
            User user1 = users.get(0);
			res.setAdmin(user1.getAdmin());
			res.setFirstName(user1.getFirstName());
			res.setLastName(user1.getFirstName());
			res.setUsername(user1.getUserId());
    	} else {
    		//res ="User not found";
    		LOGGER.error(res.toString());
    	}
    	return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
    /**
     * Creates an account using id, first name, last name and a boolean, 
     * the boolean that indicates if the user is an admin or not
     * @return the HTTP response
     */
    @PutMapping("/account/{userId}")
    @ResponseBody
    public ResponseEntity<String> creationCompte(@PathVariable String userId, @RequestBody UserDTO user, HttpServletRequest request)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	String res = null;
    	try{
    		if(user.getFirstName() == null || user.getLastName() == null) throw new InvalidFormException();
    		userService.addUser(userId, user.getFirstName(), user.getLastName(), true);
    	} catch (UsernameAlreadyExists e) {
            res = "Username already exists";
            LOGGER.error(res);
            
        } catch (InvalidFormException e) {
            res = "Invalid form";
            LOGGER.error(res);

        } catch(Exception e) {
    	    res = "exception occured";
    	    LOGGER.error(res);

        } finally {
    	    return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
    
    /**
     * Updates the account, can be used for passwords for now
     * @return the HTTP response
     */
    @PostMapping("/account")
    @ResponseBody
    public ResponseEntity<String> updateCompte(@RequestBody UserDTO user, HttpServletRequest request)
    {
    	String res = null;
    	try{
			Optional<User> userFromDB = userService.getUserById(user.getUsername());
			if (!userFromDB.isPresent())
				throw new UtilisateurInconnuException();

			userService.updatePassword(user.getUsername(), user.getOldPassword(), user.getNewPassword(),
					userFromDB.get());
			LOGGER.info("Password updated");

		} catch (UtilisateurInconnuException e) {
            res = "Unknown user";
            LOGGER.error(res);

        } catch(Exception e) {
    	    res = "error occured in server";
    	    LOGGER.error(res);

        } finally {
    	    return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
    
    /**
     * Updates the account, can be used for passwords for now
     * @return the HTTP response
     */
    @PostMapping("/forgetPassword")
    @ResponseBody
    public ResponseEntity<String> forgetPassword(@RequestBody UserDTO user)
    {
    	String res = null;
    	try{
    		Optional<User> userFromDB = userService.getUserById(user.getUsername());
			if (!userFromDB.isPresent())
				throw new UtilisateurInconnuException();

			// Reinitialisation mot de passe
			userService.forgottenPassword(user.getUsername(), userFromDB.get());
			LOGGER.info("Password reinitialized");

		} catch (UtilisateurInconnuException e) {
            res = "Unknown user";
            LOGGER.error(res);

        } catch(Exception e) {
    	    res = "error occured in server";
    	    LOGGER.error(res);

        } finally {
    	    return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
    
    
    
    /**
     * Deletes an account using the email
     * @return the null if everything was ok otherwise return the error (string)
     */
    @PostMapping("/accountDelete")
    @ResponseBody
    public ResponseEntity<String> suppressionCompte(@RequestBody UserDTO user, HttpServletRequest request)
    {
		String res=null;
		Optional<User> userFromDB = userService.getUserById(user.getUsername());
		try{

    		if(!userFromDB.isPresent())
	            throw new UtilisateurInconnuException();

    	    if(userFromDB.get().getAdmin())
            {
    	    	int nb = userService.getAllAdmins().size();
    	    	if (nb < 2) {
    	    		throw new LastAdminException();
    	    	}
                userService.deleteUserByIdUser(user.getUsername());
            }
    	    else{
    	    	userService.deleteUserByIdUser(user.getUsername());
			}
		}catch(UtilisateurInconnuException u){
			LOGGER.error("The user does not exist");
			res="The user does not exist";
		}catch(LastAdminException l){
			LOGGER.error("Can't delete the last admin's account");
			res="Can't delete the last admin's account";
		}catch(MailjetException | MailjetSocketTimeoutException c ){
			LOGGER.error("An error occured with mailJet");
			res="An error occured with mailJet";
		}catch(Exception e){
			LOGGER.error("An other error occured");
			res="Another error occured";
		} finally {
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
    }
    
    /**
     * Get all users
     * @return the HTTP response
     */
    @GetMapping("/accounts")
    @ResponseBody
    public ResponseEntity<List<UserDTO>> getAllUsers(HttpServletRequest request)
    {
		List<UserDTO> result = new ArrayList<>();
		try{
			result = userService.getAllUsers().stream().parallel()
					.map(u -> new UserDTO(u.getUserId(), u.getFirstName(), u.getLastName(), u.getAdmin())).collect(Collectors.toList());
		} catch(Exception e){
			LOGGER.info("error occured");
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /////////////////////////////////// SUGGESTION /////////////////////////////////
    
    /**
     * Get all suggestions
     * @return the HTTP response
     */
    @PostMapping("/suggestions")
    @ResponseBody
    public ResponseEntity<List<SuggestionDTO>> getAllSuggestions(@RequestBody SuggestionDTO suggestion, HttpServletRequest request)
    {
		List<SuggestionDTO> listSuggestions = new ArrayList<>();
    	try{
			listSuggestions = suggestionService
					.getSuggestions(suggestion.getAuthor(), suggestion.getBegin(), suggestion.getEnd())
					.stream().parallel().map(s -> new SuggestionDTO(s.getSuggestionContent(), s.getSuggestionAuthor(), s.getSuggestionCreationDate(), suggestion.getEnd()))
					.collect(Collectors.toList());

    	} catch(Exception e) {
    	    LOGGER.error("Exception");
    	    
        } finally{
			return new ResponseEntity<>(listSuggestions, HttpStatus.OK);
		}
    }
    
    
    /**
     * Creates a suggestion
     * @return the HTTP response
     */
    @PostMapping("/suggestion")
    @ResponseBody
    public ResponseEntity<String> creationSuggestion(@RequestBody SuggestionDTO sug)
    {
    	String res= null;
    	try{
    		if(sug.getContent() == null || sug.getContent().equals("")) throw new EmptySuggestionException();
    		
    		List<User> admins = userService.getAllAdmins();

    		suggestionService.creationSuggestion(sug.getContent(), sug.getAuthor(), admins);

    	    LOGGER.info("Suggestion created");
    	} catch (EmptySuggestionException e) {
            res = "Error empty suggestion";
            LOGGER.error(res);
        } catch(Exception e) {
    	    res = "Server Error";
			LOGGER.error(res);
        } finally{
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

    }

	/////////////////////////////////// STRAWPOLL /////////////////////////////////

	@PutMapping("/createStrawpoll")
	@ResponseBody
	public ResponseEntity<String> createStrawpoll(@RequestBody StrawpollDTO strawpollDTO)
	{
		String res = null;
		try{
			System.out.println(strawpollDTO.getChoices());
			strawpollService.createStrawpoll(strawpollDTO.getTitle(),
					strawpollDTO.getAuthor(),strawpollDTO.getExpirationDate(),
					strawpollDTO.getChoices());
			LOGGER.info("Strawpoll has been created");

		} catch(Exception e) {
			res="Failed to create the strawpoll";

		} finally {
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
	}


}
