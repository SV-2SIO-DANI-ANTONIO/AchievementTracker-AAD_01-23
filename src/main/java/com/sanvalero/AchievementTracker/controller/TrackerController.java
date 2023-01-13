package com.sanvalero.AchievementTracker.controller;

import com.sanvalero.AchievementTracker.domain.Tracker;
import com.sanvalero.AchievementTracker.domain.User;
import com.sanvalero.AchievementTracker.domain.dto.TrackerDTO;
import com.sanvalero.AchievementTracker.exception.AchievementNotFoundException;
import com.sanvalero.AchievementTracker.exception.ErrorException;
import com.sanvalero.AchievementTracker.exception.TrackerNotFoundException;
import com.sanvalero.AchievementTracker.exception.UserNotFoundException;
import com.sanvalero.AchievementTracker.service.TrackerService;
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
public class TrackerController {
    @Autowired
    TrackerService trackerService;

    private final Logger logger = LoggerFactory.getLogger(TrackerService.class);

    @GetMapping("/trackers")
    public ResponseEntity<List<Tracker>> getTrackers(@RequestParam Map<String, String> data) throws UserNotFoundException {
        logger.info("GET Tracker");
        if(data.isEmpty()){
            logger.info("END GET Tracker");
            return ResponseEntity.ok(trackerService.findAll());
        } else {
            if(data.containsKey("userID")){
                List<Tracker> trackers = trackerService.findByUser(Long.parseLong(data.get("userID")));
                return ResponseEntity.ok(trackers);
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/trackers")
    public ResponseEntity<Tracker> addTracker(@Valid @RequestBody TrackerDTO trackerDTO) throws UserNotFoundException, AchievementNotFoundException {
        logger.info("POST Trackers");
        Tracker tracker = trackerService.addTracker(trackerDTO);
        logger.info("END POST Trackers");
        return ResponseEntity.status(HttpStatus.OK).body(tracker);
    }

    @DeleteMapping("/trackers/{id}")
    public ResponseEntity<ErrorException> deleteTracker(@PathVariable long id) throws TrackerNotFoundException {
        logger.info("DELETE Trackers");
        boolean res = trackerService.deleteTracker(id);
        if(res){
            logger.info("END DELETE User");
            return ResponseEntity.noContent().build();
        } else {
            ErrorException error = new ErrorException(403, "El borrado no se ha permitido.");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/trackers/{id}")
    public ResponseEntity<Tracker> modifyTracker(@PathVariable long id,@Valid @RequestBody TrackerDTO trackerDTO) throws UserNotFoundException, TrackerNotFoundException, AchievementNotFoundException {
        logger.info("PUT Tracker");
        Tracker tracker = trackerService.modifyTracker(id,trackerDTO);
        logger.info("END PUT Tracker");
        return ResponseEntity.status(HttpStatus.OK).body(tracker);
    }

    @ExceptionHandler(TrackerNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(TrackerNotFoundException tnfe){
        logger.error("Tracker no encontrado");
        ErrorException errorException = new ErrorException(404, tnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(UserNotFoundException unfe){
        logger.error("User no encontrado");
        ErrorException errorException = new ErrorException(404, unfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AchievementNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(AchievementNotFoundException anfe){
        logger.error("Achievement no encontrado");
        ErrorException errorException = new ErrorException(404, anfe.getMessage());
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
