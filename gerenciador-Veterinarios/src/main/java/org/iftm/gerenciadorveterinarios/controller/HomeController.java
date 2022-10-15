package org.iftm.gerenciadorveterinarios.controller;


import java.util.List;

import org.iftm.gerenciadorveterinarios.model.Veterinario;
import org.iftm.gerenciadorveterinarios.model.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    private VeterinarioRepository vetRepository;
    
    

    @GetMapping("/home")
    public String home(Model model) {
    	List<Veterinario> veterinarios = vetRepository.findAll();
    	
        model.addAttribute("veterinarios", veterinarios);
        return "home";
    }
   

}
