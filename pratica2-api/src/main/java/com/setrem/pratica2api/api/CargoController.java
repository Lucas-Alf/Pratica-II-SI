package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;

import com.setrem.pratica2api.model.Cargo;
import com.setrem.pratica2api.repository.CargoRepository;;

@RestController
@RequestMapping("/api/cargo")
@CrossOrigin
public class CargoController {
    private CargoRepository CargoRepository;

    public CargoController(CargoRepository CargoRepository) {
        this.CargoRepository = CargoRepository;
    }

    @GetMapping("/all")
    public List<Cargo> all() {
        var cargos = this.CargoRepository.findAll();
        return cargos;
    }

    @GetMapping("/lista")
    public List<Cargo> Lista() {
        //var cargos = this.CargoRepository.ListarCargos();
        return this.CargoRepository.ListarCargos();
    }

    @PostMapping
    public Cargo save(@RequestBody Cargo data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.CargoRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.CargoRepository.deleteById(id);
    }

}