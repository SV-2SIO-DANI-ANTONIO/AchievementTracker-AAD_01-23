package com.sanvalero.AchievementTracker.controller;

import com.sanvalero.AchievementTracker.domain.Game;
import com.sanvalero.AchievementTracker.domain.User;
import com.sanvalero.AchievementTracker.domain.dto.UserDTO;
import com.sanvalero.AchievementTracker.exception.ErrorException;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;
import com.sanvalero.AchievementTracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
            ErrorException error = new ErrorException(403, "El borrado no se ha permitido.");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable long id, @RequestBody UserDTO userDTO) throws UserNotFoundException {
        logger.info("PUT User");
        User user = userService.modifyUser(id,userDTO);
        logger.info("END PUT User");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
            
}
