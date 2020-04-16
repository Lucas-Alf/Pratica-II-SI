package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.websocket.server.PathParam;

import java.util.List;

import com.setrem.pratica2api.model.Cargo;
import com.setrem.pratica2api.repository.CargoRepository;

@RestController
@RequestMapping("/api/cargo")
@CrossOrigin
public class CargoController {
    private CargoRepository cargoRepository;

    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping("/all")
    public List<Cargo> all() {
        return this.cargoRepository.findAll();
    }
    
    @PostMapping("/save")
    public Cargo save(@RequestBody Cargo data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.cargoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.cargoRepository.deleteById(id);
    }

}