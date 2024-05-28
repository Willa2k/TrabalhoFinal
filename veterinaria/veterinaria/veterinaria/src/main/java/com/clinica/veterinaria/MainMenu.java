package com.clinica.veterinaria;

import com.clinica.veterinaria.controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class MainMenu implements CommandLineRunner {

    @Autowired
    private DonoController donoController;

    @Autowired
    private AnimalController animalController;

    @Autowired
    private ConsultaController consultaController;

    @Autowired
    private FuncionarioController funcionarioController;

    @Autowired
    private ServicoController servicoController;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        int option;
        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Menu de Donos");
            System.out.println("2. Menu de Animais");
            System.out.println("3. Menu de Consultas");
            System.out.println("4. Menu de Funcionários");
            System.out.println("5. Menu de Serviços");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        donoController.menu();
                        break;
                    case 2:
                        animalController.menu();
                        break;
                    case 3:
                        consultaController.menu();
                        break;
                    case 4:
                        funcionarioController.menu();
                        break;
                    case 5:
                        servicoController.menu();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira apenas números.");
                scanner.nextLine();
                option = -1;
            }
        } while (option != 0);
    }
}


