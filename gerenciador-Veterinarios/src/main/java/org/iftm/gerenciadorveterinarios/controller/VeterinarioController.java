package org.iftm.gerenciadorveterinarios.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.iftm.gerenciadorveterinarios.model.Veterinario;
import org.iftm.gerenciadorveterinarios.model.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VeterinarioController {

    @Autowired
    private VeterinarioRepository vetRepository;

    // Acessa o formulario de consulta de veterinários
    @GetMapping({"/find","/find{nome}"})
    
    public String veterinariosFind(@ModelAttribute("nome") @RequestParam("nome") Optional<String> nome, Model model, Veterinario veterinario) {    	
    	if (nome.isPresent()) {
        	System.out.println(nome.get());
    		List<Veterinario> veterinarios = vetRepository.findByNomeContains(nome.get());
    		System.out.println(veterinarios.size());
    		model.addAttribute("veterinarios", veterinarios);
    		return "home";
    	}else {
            return "findVeterinarioForm";    		
    	}
    }

    // Acessa o formulario de consulta de veterinários
//    @GetMapping("/find")
  //  public String veterinariosFindForm(Veterinario veterinario) {    	
    //    return "findVeterinarioForm";
    //}
    
    
    // Acessa o formulario de cadastro de animais
    @GetMapping("/form")
    public String veterinariosForm(Veterinario veterinario) {    	
        return "addVeterinarioForm";
    }

    // Adiciona novo veterinario
    @PostMapping("/add")
    public String novo(@Valid Veterinario veterinario, BindingResult result) {

        if (result.hasFieldErrors()) {
            return "redirect:/form";
        }

        vetRepository.save(veterinario);

        return "redirect:/home";
    }

    // Acessa o formulario de edição
    @GetMapping("form/{id}")
    public String updateForm(Model model, @PathVariable(name = "id") int id) {

        //Veterinario veterinario = vetRepository.findById(id)
        //        .orElseThrow(() -> new IllegalArgumentException("Não existe usuário com esse id: " + id));
    	Veterinario veterinario = vetRepository.findById(id).get();
    	if (veterinario == null) {
    		throw new IllegalArgumentException("Não existe veterinário com esse id: " + id);
    	}
        model.addAttribute("veterinario", veterinario);
        return "atualizaVeterinarioForm";
    }

    // Atualiza veterinario
    @PostMapping("update/{id}")
    public String alterarProduto(@Valid Veterinario veterinario, BindingResult result, @PathVariable int id) {

        if (result.hasErrors()) {
            return "redirect:/form";
        }
        
        vetRepository.save(veterinario);
        return "redirect:/home";
    }

    @GetMapping("delete/{id}")
    @CacheEvict(value = "veterinarios", allEntries = true)
    public String delete(@PathVariable(name = "id") int id, Model model) {

        //Veterinario veterinario = vetRepository.findById(id)
        //        .orElseThrow(() -> new IllegalArgumentException("Não existe usuário com esse id: " + id));
    	Veterinario veterinario = vetRepository.findById(id).get();
    	if (veterinario == null) {
    		throw new IllegalArgumentException("Não existe usuário com esse id: " + id);
    	}
        
        vetRepository.delete(veterinario);
        return "redirect:/home";
    }

}
