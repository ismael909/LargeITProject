package lsit.Controllers;

import java.nio.file.AccessDeniedException;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

@RestController
public class HomeController {
    
    @GetMapping("/")

    public ResponseEntity get(){

        return ResponseEntity.ok("Welcome to our clothing store!");

    }

   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/user")

    public String getUser(OAuth2AuthenticationToken authentication) throws Exception {

        // Extract groups and user attributes

        var groups = (List<String>) authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");

        var userAttributes = authentication.getPrincipal().getAttributes();

        // Log user details and group memberships

        logger.info("User accessed the server: Name = {}, Email = {}, Groups = {}",

            userAttributes.get("name"),

            userAttributes.get("email"),

            groups

        );


        // Return user details as response

        return "<pre> \n" +

            "Name: " + userAttributes.get("name") + "\n" +

            "Email: " + userAttributes.get("email") + "\n" +

            "Groups: " + groups + "\n" +

            "</pre>";

    }

}