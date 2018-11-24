package com.daniel.popek.thesis.app.domain.service.data_logic.implementation;

import com.daniel.popek.thesis.app.domain.DTO.authentication.*;
import com.daniel.popek.thesis.app.persistence.entities.Designer;
import com.daniel.popek.thesis.app.persistence.repository.DesignerRepository;
import com.daniel.popek.thesis.app.domain.service.data_logic.IDesignerService;
import com.daniel.popek.thesis.app.domain.service.utils.IEmailSenderService;
import com.daniel.popek.thesis.app.domain.service.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class DesignerService implements IDesignerService {

    @Autowired
    DesignerRepository designerRepository;

    @Autowired
    IEmailSenderService emailSenderService;

    @Override
    public Pair<CustomResponseDTO, HttpStatus> login(String login, String password) {
        CustomResponseDTO response = new CustomResponseDTO();
        HttpStatus status;
        Designer userByEmail = this.designerRepository.findByEmail(login);
        if (userByEmail!=null) {
            String hash = userByEmail.getPassword();
            String salt = userByEmail.getSalt();
            System.out.println(hash);
            System.out.println(PasswordUtils.encryptPassword(password, salt));
            if (hash.equals(PasswordUtils.encryptPassword(password, salt))) {
                if (!userByEmail.getActive()) {
                    response = new UserNotActiveLoginResponseDTO();
                    status = HttpStatus.PRECONDITION_FAILED;
                } else {
                    response = new LoginSuccessfulResponseDTO(userByEmail.getApiKey());
                    status = HttpStatus.OK;
                }
            } else {
                response = new WrongLoginDataResponseDTO();
                status = HttpStatus.UNAUTHORIZED;
            }
        } else {
            response = new WrongLoginDataResponseDTO();
            status = HttpStatus.UNAUTHORIZED;
        }
        return Pair.of(response, status);
    }

    @Override
    public Pair<CustomResponseDTO, HttpStatus> register(RegisterRequestBodyDTO registerRequestBody) {
        CustomResponseDTO response = new CustomResponseDTO();
        HttpStatus status;

        Designer userByEmail = this.designerRepository.findByEmail(registerRequestBody.getEmail());
        if (userByEmail!=null) {

            response = new RegisterWrongEmailResponseDTO();
            status = HttpStatus.BAD_REQUEST;
            return Pair.of(response, status);
        }

        Pair<String, String> password = PasswordUtils.decryptPassword(registerRequestBody.getPassword());
        Designer userToSave = new Designer();
        userToSave.setEmail(registerRequestBody.getEmail());
        userToSave.setFirstName(registerRequestBody.getFirstName());
        userToSave.setLastName(registerRequestBody.getLastName());
        userToSave.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        userToSave.setActive(false);
        userToSave.setApiKey(PasswordUtils.generateAccessToker());
        userToSave.setActivationCode(PasswordUtils.generateActivationCode());
        userToSave.setPassword(password.getSecond());
        userToSave.setSalt(password.getFirst());

        Designer saved=designerRepository.save(userToSave);
        emailSenderService.sendActivationLinkByEmail(saved.getId(), registerRequestBody.getEmail(), saved.getActivationCode());
        response = new RegisterSuccessfulResponseDTO();
        status = HttpStatus.OK;

        return Pair.of(response, status);
    }

    @Override
    public Pair<CustomResponseDTO, HttpStatus> activateUser(Integer id, String activationLink) {
        Designer userById = this.designerRepository.findById(id.intValue());
        if (userById!=null && !userById.getActive()) {
            if (userById.getActivationCode().equals(activationLink)) {
                Designer user = userById;
                user.setActive(true);
                designerRepository.save(user);
                CustomResponseDTO response = new ActivationSuccessfulResponseDTO();
                HttpStatus status=HttpStatus.OK;
                return Pair.of(response, status);
            } else {
                CustomResponseDTO response = new WrongActivationLinkResponseDTO();
                HttpStatus status=HttpStatus.BAD_REQUEST;
                return Pair.of(response, status);
            }
        } else {
            CustomResponseDTO response = new NoUserToActivateResponseDTO();
            HttpStatus status=HttpStatus.PRECONDITION_FAILED;
            return Pair.of(response, status);
        }
    }
}
