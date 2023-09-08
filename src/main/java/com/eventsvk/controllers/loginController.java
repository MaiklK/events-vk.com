package com.eventsvk.controllers;

import com.eventsvk.security.CustomAuthentication;
import com.eventsvk.services.AuthVkService;
import com.eventsvk.util.ConverterDto;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class loginController {
    private final AuthVkService authVkService;
    private final ConverterDto converterDto;

    @GetMapping("/index")
    public String getHome(@RequestParam String code, HttpSession session) throws ClientException, ApiException {
        CustomAuthentication customAuthentication = authVkService.getCustomAuthentication(code);
        if (customAuthentication != null) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(customAuthentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            if (customAuthentication.getAuthorities().contains("ADMIN")) {
                System.out.println("admin");
                return "redirect:admin";
            }
            System.out.println("index");
            return "index";
        }
        System.out.println("login");
        return "redirect:login";
    }

}
