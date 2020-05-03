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
    private Cargo cargo = new Cargo();

    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping("/all")
    public List<Cargo> all() {
        return this.cargoRepository.findAll();
    }
    
    @PostMapping("/Incluir")
    public Cargo add(@RequestBody Cargo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.cargoRepository.maxIdCargo();
        this.cargo.setId(id);
        data = this.cargoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Cargo update(@RequestBody Cargo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.cargoRepository.findById(data.getId());
        if (!jaExistente.isPresent()) {
            throw new Exception("Cargo com código " + data.getId() + " não encontrada.");
        } else {
            this.cargoRepository.save(data);
            return data;
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.cargoRepository.deleteById(id);
    }

}