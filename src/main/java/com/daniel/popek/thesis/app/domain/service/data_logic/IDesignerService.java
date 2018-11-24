package com.daniel.popek.thesis.app.domain.service.data_logic;

import com.daniel.popek.thesis.app.domain.DTO.authentication.CustomResponseDTO;
import com.daniel.popek.thesis.app.domain.DTO.authentication.RegisterRequestBodyDTO;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public interface IDesignerService {

    Pair<CustomResponseDTO,HttpStatus> login(String login, String password);

    Pair<CustomResponseDTO,HttpStatus> register(RegisterRequestBodyDTO registerRequestBody);

    Pair<CustomResponseDTO, HttpStatus> activateUser(Integer id, String activationLink);

}
