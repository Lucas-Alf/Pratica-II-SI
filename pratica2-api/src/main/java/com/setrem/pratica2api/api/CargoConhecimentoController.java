package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.CargoConhecimento;
import com.setrem.pratica2api.repository.CargoConhecimentoRepository;

@RestController
@RequestMapping("/api/cargoconhecimento")
@CrossOrigin
public class CargoConhecimentoController {
    private CargoConhecimentoRepository cargoConhecimentoRepository;

    public CargoConhecimentoController(CargoConhecimentoRepository cargoConhecimentoRepository) {
        this.cargoConhecimentoRepository = cargoConhecimentoRepository;
    }

    @GetMapping("/all")
    public List<CargoConhecimento> all() {
        var cargoconhecimentos = this.cargoConhecimentoRepository.findAll();
        return cargoconhecimentos;
    }

    @PostMapping
    public CargoConhecimento save(@RequestBody CargoConhecimento data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.cargoConhecimentoRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.cargoConhecimentoRepository.deleteById(id);
    }

}