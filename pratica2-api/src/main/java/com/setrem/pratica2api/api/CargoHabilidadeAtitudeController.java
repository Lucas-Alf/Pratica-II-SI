package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.CargoHabilidadeAtitude;
import com.setrem.pratica2api.repository.CargoHabilidadeAtitudeRepository;

@RestController
@RequestMapping("/api/cargohabilidadeatitude")
@CrossOrigin
public class CargoHabilidadeAtitudeController {
    private CargoHabilidadeAtitudeRepository CargoHabilidadeAtitudeRepository;
    
    public CargoHabilidadeAtitudeController(CargoHabilidadeAtitudeRepository CargoHabilidadeAtitudeRepository) {
        this.CargoHabilidadeAtitudeRepository = CargoHabilidadeAtitudeRepository;
    }

    @GetMapping("/all")
    public List<CargoHabilidadeAtitude> all() {
        var teste = this.CargoHabilidadeAtitudeRepository.findAll();
        return teste;
    }

    @PostMapping("/Incluir")
    public CargoHabilidadeAtitude add(@RequestBody CargoHabilidadeAtitude data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        //int id = this.CargoHabilidadeAtitudeRepository.maxIdCargoHabilidadeAtitude();
        //data.setId(id);
        data = this.CargoHabilidadeAtitudeRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.CargoHabilidadeAtitudeRepository.deleteById(id);
    }

}