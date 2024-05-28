package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.entity.Animal;
import com.clinica.veterinaria.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Controller
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int option;
        boolean returnToMainMenu = false;
        do {
            System.out.println("\n--- Menu de Animais ---");
            System.out.println("1. Listar Animais");
            System.out.println("2. Adicionar Novo Animal");
            System.out.println("3. Editar Animal");
            System.out.println("4. Excluir Animal");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listarAnimais();
                        break;
                    case 2:
                        novoAnimalForm();
                        break;
                    case 3:
                        editarAnimalForm();
                        break;
                    case 4:
                        excluirAnimal();
                        break;
                    case 0:
                        returnToMainMenu = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira apenas números.");
                scanner.nextLine();
                option = -1;
            }
        } while (!returnToMainMenu);
    }

    public void listarAnimais() {
        List<Animal> animais = animalService.findAll();
        System.out.println("\n--- Lista de Animais ---");
        for (Animal animal : animais) {
            System.out.println(animal);
        }
    }

    public void novoAnimalForm() {
        Animal animal = new Animal();

        System.out.print("Nome: ");
        animal.setNome(scanner.nextLine());

        System.out.print("Espécie: ");
        animal.setEspecie(scanner.nextLine());

        System.out.print("Raça: ");
        animal.setRaca(scanner.nextLine());

        animalService.save(animal);
        System.out.println("Animal salvo com sucesso!");
    }

    public void editarAnimalForm() {
        System.out.print("ID do Animal a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Animal animal = animalService.findById(id);
        if (animal == null) {
            System.out.println("Animal não encontrado!");
            return;
        }

        System.out.println("Editando Animal: " + animal);
        System.out.print("Novo Nome (deixe vazio para não alterar): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            animal.setNome(nome);
        }

        System.out.print("Nova Espécie (deixe vazio para não alterar): ");
        String especie = scanner.nextLine();
        if (!especie.isEmpty()) {
            animal.setEspecie(especie);
        }

        System.out.print("Nova Raça (deixe vazio para não alterar): ");
        String raca = scanner.nextLine();
        if (!raca.isEmpty()) {
            animal.setRaca(raca);
        }

        animalService.save(animal);
        System.out.println("Animal atualizado com sucesso!");
    }

    public void excluirAnimal() {
        List<Animal> animais = animalService.findAll();
        if (animais.isEmpty()) {
            System.out.println("Não há nenhum animal para excluir!");
            return;
        }

        System.out.print("ID do Animal a excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Animal animal = animalService.findById(id);
        if (animal == null) {
            System.out.println("Animal com ID " + id + " não encontrado!");
        } else {
            animalService.deleteById(id);
            System.out.println("Animal excluído com sucesso!");
        }
    }
}
