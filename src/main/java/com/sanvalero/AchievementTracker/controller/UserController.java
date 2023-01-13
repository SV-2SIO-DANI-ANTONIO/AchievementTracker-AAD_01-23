package com.sanvalero.AchievementTracker.controller;

import com.sanvalero.AchievementTracker.domain.Game;
import com.sanvalero.AchievementTracker.domain.User;
import com.sanvalero.AchievementTracker.domain.dto.UserDTO;
import com.sanvalero.AchievementTracker.exception.ErrorException;
import com.sanvalero.AchievementTracker.exception.TrackerNotFoundException;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;
import com.sanvalero.AchievementTracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.sanvalero.AchievementTracker.util.ErrorExceptionUtil.getErrorExceptionResponseEntity;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers (@RequestParam Map<String, String> data){
        logger.info("GET User");
        if(data.isEmpty()){
            logger.info("END GET User");
            return ResponseEntity.ok(userService.findAll());
        } else {
            if(data.containsKey("name")){
                List<User> users = userService.findByName(data.get("name"));
                return ResponseEntity.ok(users);
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO userDTO){
        logger.info("POST User");
        User user = userService.addUser(userDTO);
        logger.info("END POST User");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ErrorException> deleteUser(@PathVariable long id) throws UserNotFoundException {
        logger.info("DELETE User");
        boolean res = userService.deleteUser(id);
        if(res){
            logger.info("END DELETE User");
            return ResponseEntity.noContent().build();
        } else {
            ErrorException error = new ErrorException(418, "El borrado no se ha permitido.");
            return new ResponseEntity<>(error, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable long id, @RequestBody UserDTO userDTO) throws UserNotFoundException {
        logger.info("PUT User");
        User user = userService.modifyUser(id,userDTO);
        logger.info("END PUT User");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(UserNotFoundException unfe){
        logger.error("User no encontrado");
        ErrorException errorException = new ErrorException(404, unfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        logger.error("Restricciones violadas");
        return getErrorExceptionResponseEntity(cve);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleException(Exception e){
        logger.error("Error Interno ", e.getMessage());
        ErrorException error = new ErrorException(500, "Ha habido algun error inesperado");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorException> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){
        logger.error("Datos introducidos erroneos");
        return getErrorExceptionResponseEntity(manve);
    }
            
}
