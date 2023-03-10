package com.sanvalero.AchievementTracker.controller;

import com.sanvalero.AchievementTracker.domain.Game;
import com.sanvalero.AchievementTracker.domain.dto.GameDTO;
import com.sanvalero.AchievementTracker.exception.ErrorException;
import com.sanvalero.AchievementTracker.exception.GameNotFoundException;
import com.sanvalero.AchievementTracker.service.GameService;
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
public class GameController {

    @Autowired
    private GameService gameService;

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGames(@RequestParam Map<String, String> data) throws GameNotFoundException{
        logger.info("GET Games");
        if(data.isEmpty()){
            logger.info("END GET Games");
            return ResponseEntity.ok(gameService.findAll());
        } else {
            if(data.containsKey("name")){
                List<Game> games = gameService.findByName(data.get("name"));
                return ResponseEntity.ok(games);
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/games")
    public ResponseEntity<Game> addGame(@Valid @RequestBody GameDTO gameDTO){
        logger.info("POST Game");
        Game game = gameService.addGame(gameDTO);
        logger.info("END POST Game");
        return ResponseEntity.status(HttpStatus.OK).body(game);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<ErrorException> deleteGame(@PathVariable long id) throws GameNotFoundException{
        logger.info("DELETE Game");
        boolean res = gameService.deleteGame(id);
        if(res){
            logger.info("END DELETE Game");
            return ResponseEntity.noContent().build();
        } else {
            ErrorException error = new ErrorException(403, "El borrado no se ha permitido");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Game> modifyGame(@PathVariable long id, @Valid @RequestBody GameDTO gameDTO) throws GameNotFoundException {
        logger.info("PUT Game");
        Game game = gameService.modifyGame(id, gameDTO);
        logger.info("END PUT Game");
        return  ResponseEntity.status(HttpStatus.OK).body(game);

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
