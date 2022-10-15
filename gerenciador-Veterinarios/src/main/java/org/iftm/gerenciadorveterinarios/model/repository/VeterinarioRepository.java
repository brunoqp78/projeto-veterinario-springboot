package org.iftm.gerenciadorveterinarios.model.repository;

import java.util.List;

import org.iftm.gerenciadorveterinarios.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

   public List<Veterinario> findByNomeContains(String nome);
}
