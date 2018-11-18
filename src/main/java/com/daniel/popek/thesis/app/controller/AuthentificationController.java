package com.daniel.popek.thesis.app.controller;

import com.daniel.popek.thesis.app.model.DTO.authentication.CustomResponseDTO;
import com.daniel.popek.thesis.app.model.DTO.authentication.LoginRequestBodyDTO;
import com.daniel.popek.thesis.app.model.DTO.authentication.RegisterRequestBodyDTO;
import com.daniel.popek.thesis.app.service.data.IDesignerService;
import com.daniel.popek.thesis.app.service.data.implementation.DesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@Controller
@RequestMapping("/authentication")
public class AuthentificationController {

@Autowired
IDesignerService designerService;


//    @ExceptionHandler({ NoSuchUserToActivateException.class, WrongActivationLink.class })
//    public String handleException() {
//        return "SomethingWentWrong";
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<CustomResponseDTO> login(@Valid @RequestBody LoginRequestBodyDTO loginRequestBody) {
        String login=loginRequestBody.getLogin();
        String password=loginRequestBody.getPassword();
        Pair<CustomResponseDTO,HttpStatus> result=designerService.login(login,password);
        CustomResponseDTO response=result.getFirst();
        HttpStatus status=result.getSecond();
        return new ResponseEntity<CustomResponseDTO>(response, status);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<CustomResponseDTO> register(@Valid @RequestBody RegisterRequestBodyDTO registerRequestBody) {
        Pair<CustomResponseDTO, HttpStatus> result = designerService.register(registerRequestBody);
        CustomResponseDTO response = result.getFirst();
        HttpStatus status = result.getSecond();
        return new ResponseEntity<CustomResponseDTO>(response, status);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/activate/{id}", params = {"code"})
    public String activateUser(@PathVariable Integer id, @RequestParam String code) {
        Pair<CustomResponseDTO, HttpStatus> result=designerService.activateUser(id, code);
        if(result.getSecond().equals(HttpStatus.OK)) return  "ActivationConfirmation";
        else return "SomethingWentWrong";
    }


}
