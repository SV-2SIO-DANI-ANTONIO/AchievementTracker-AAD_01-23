package com.sanvalero.AchievementTracker.controller;

import com.sanvalero.AchievementTracker.domain.Achievement;
import com.sanvalero.AchievementTracker.domain.Game;
import com.sanvalero.AchievementTracker.domain.dto.AchievementDTO;
import com.sanvalero.AchievementTracker.domain.dto.GameDTO;
import com.sanvalero.AchievementTracker.exception.AchievementNotFoundException;
import com.sanvalero.AchievementTracker.exception.ErrorException;
import com.sanvalero.AchievementTracker.exception.GameNotFoundException;
import com.sanvalero.AchievementTracker.service.AchievementService;
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
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    private final Logger logger = LoggerFactory.getLogger(AchievementController.class);

    @GetMapping("/achievements")
    public ResponseEntity<List<Achievement>> getAchievements(@RequestParam Map<String, String> data) throws AchievementNotFoundException{
        logger.info("GET Achievements");
        if(data.isEmpty()){
            logger.info("END GET Achievements");
            return ResponseEntity.ok(achievementService.findAll());
        } else {
            if(data.containsKey("name")){
                List<Achievement> achievements = achievementService.findByName(data.get("name"));
                return ResponseEntity.ok(achievements);
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/achievements")
    public ResponseEntity<Achievement> addGame(@Valid @RequestBody AchievementDTO achievementDTO) throws GameNotFoundException {
        logger.info("POST Achievement");
        Achievement achievement = achievementService.addAchievement(achievementDTO);
        logger.info("END POST Achievement");
        return ResponseEntity.status(HttpStatus.OK).body(achievement);
    }

    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<ErrorException> deleteAchievement(@PathVariable long id) throws AchievementNotFoundException{
        logger.info("DELETE Achievement");
        boolean res = achievementService.deleteAchievement(id);

        if (res) {
            logger.info("END DELETE Achievement");
            return ResponseEntity.noContent().build();
        } else {
            ErrorException error = new ErrorException(403, "El borrado no se ha permitido");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/achievements/{id}")
    public ResponseEntity<Achievement> modifyAchievement(@PathVariable long id,@Valid @RequestBody AchievementDTO achievementDTO) throws AchievementNotFoundException, GameNotFoundException {
        logger.info("PUT Achievement");
        Achievement achievement = achievementService.updateAchievement(id, achievementDTO);
        logger.info("END PUT Achievement");
        return ResponseEntity.status(HttpStatus.OK).body(achievement);
    }

    @ExceptionHandler(AchievementNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(AchievementNotFoundException anfe){
        logger.error("Achievement no encontrado");
        ErrorException errorException = new ErrorException(404, anfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(GameNotFoundException gnfe){
        logger.error("Game no encontrado");
        ErrorException errorException = new ErrorException(404, gnfe.getMessage());
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
