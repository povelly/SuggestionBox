package com.sorbonne.safetyline.utils;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MailJetUtil {
	
	private static final String SENDER = "suggestionboxsafetyline1@gmail.com";
	
	// TODO gérer mailjet api keys avec variables systèmes
	private static final String API_KEY = "b6b6b131416b89c104b6e2c2945a1d5f";
	private static final String PRIVATE_KEY = "604fe9b4c3f9a873200864183ee07c60";

	/**
	 * Envoie un mail vers un destinataire, utilise API Mailjet
	 * @param to, le destinataire
	 * @param subject, l'objet du mail
	 * @param content, le contenu du mail en HTML
	 */
	public static void send(String to, String subject, String content) throws MailjetException, MailjetSocketTimeoutException, JSONException {
		// api key, private key, version
		MailjetClient mClient = new MailjetClient(API_KEY, PRIVATE_KEY, new ClientOptions("v3.1"));
		MailjetRequest request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, new JSONArray().put(new JSONObject()
				.put(Emailv31.Message.FROM,
						new JSONObject().put("Email", SENDER))
				.put(Emailv31.Message.TO,
						new JSONArray().put(new JSONObject().put("Email", to)))
				.put(Emailv31.Message.SUBJECT, subject)
				.put(Emailv31.Message.HTMLPART, content)));
		MailjetResponse response = mClient.post(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
	}
	
	
	/**
	 * Envoie un mail vers plusieurs destinataire, utilise API Mailjet
	 * @param to, les destinataires
	 * @param subject, l'objet du mail
	 * @param content, le contenu du mail en HTML
	 */
	public static void sendMultiple(List<String> to, String subject, String content) throws MailjetException, MailjetSocketTimeoutException, JSONException {
		// api key, private key, version
		MailjetClient mClient = new MailjetClient(API_KEY, PRIVATE_KEY, new ClientOptions("v3.1"));
		
		// Build recipients
		JSONArray tos = new JSONArray();
		for (String str : to) {
			tos.put(new JSONObject().put("Email", str));
		}
		
		MailjetRequest request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, new JSONArray().put(new JSONObject()
				.put(Emailv31.Message.FROM,
						new JSONObject().put("Email", SENDER))
				.put(Emailv31.Message.TO, tos)
				.put(Emailv31.Message.SUBJECT, subject)
				.put(Emailv31.Message.HTMLPART, content)));
		MailjetResponse response = mClient.post(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
	}
	
	

	 /*public static void main(String[] args) throws MailjetException, MailjetSocketTimeoutException {
		//send("suggestionboxsafetyline1@gmail.com", "PREMIER EMAIL TEST", "<h3>ON TESTE LE PREMIER MAIL!</h3><br />LE TEST!");
		 ArrayList<String> str = new ArrayList<>(Arrays.asList("suggestionboxsafetyline1@gmail.com","mathieu.george92@gmail.com"));
		 sendMultiple(str, "MULTI EMAIL TEST", "<h3>ON TESTE LE MULTI MAIL!</h3><br />LE TEST!");
	}*/
}