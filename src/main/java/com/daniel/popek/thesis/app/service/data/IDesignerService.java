package com.daniel.popek.thesis.app.service.data;

import com.daniel.popek.thesis.app.model.DTO.authentication.CustomResponseDTO;
import com.daniel.popek.thesis.app.model.DTO.authentication.RegisterRequestBodyDTO;
import com.daniel.popek.thesis.app.model.entities.Designer;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IDesignerService {

    Pair<CustomResponseDTO,HttpStatus> login(String login, String password);

    Pair<CustomResponseDTO,HttpStatus> register(RegisterRequestBodyDTO registerRequestBody);

    Pair<CustomResponseDTO, HttpStatus> activateUser(Integer id, String activationLink);

}
