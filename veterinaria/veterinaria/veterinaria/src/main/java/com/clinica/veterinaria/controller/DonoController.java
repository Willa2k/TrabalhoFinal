package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.entity.Dono;
import com.clinica.veterinaria.service.DonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Controller
public class DonoController {

    @Autowired
    private DonoService donoService;

    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int option;
        boolean returnToMainMenu = false;
        do {
            System.out.println("\n--- Menu de Donos ---");
            System.out.println("1. Listar Donos");
            System.out.println("2. Adicionar Novo Dono");
            System.out.println("3. Editar Dono");
            System.out.println("4. Excluir Dono");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listarDonos();
                        break;
                    case 2:
                        novoDonoForm();
                        break;
                    case 3:
                        editarDonoForm();
                        break;
                    case 4:
                        excluirDono();
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

    public void listarDonos() {
        List<Dono> donos = donoService.findAll();
        System.out.println("\n--- Lista de Donos ---");
        for (Dono dono : donos) {
            System.out.println("ID: " + dono.getId());
            System.out.println("Nome: " + dono.getNome());
            System.out.println("Telefone: " + maskPhoneNumber(dono.getTelefone()));
            System.out.println("Email: " + maskEmail(dono.getEmail()));
            System.out.println();
        }
    }

    public void novoDonoForm() {
        Dono dono = new Dono();

        System.out.print("Nome: ");
        dono.setNome(scanner.nextLine());

        System.out.print("Telefone: ");
        dono.setTelefone(scanner.nextLine());

        System.out.print("Email: ");
        dono.setEmail(scanner.nextLine());

        donoService.save(dono);
        System.out.println("Dono salvo com sucesso!");
    }

    public void editarDonoForm() {
        System.out.print("ID do Dono a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Dono dono = donoService.findById(id);
        if (dono == null) {
            System.out.println("Dono não encontrado!");
            return;
        }

        System.out.println("Editando Dono: '" + dono.getNome() + "', '"
                + maskPhoneNumber(dono.getTelefone()) + "', '" + maskEmail(dono.getEmail()) + "'");
        System.out.print("Novo Nome (deixe vazio para não alterar): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            dono.setNome(nome);
        }

        System.out.print("Novo Telefone (deixe vazio para não alterar): ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) {
            dono.setTelefone(telefone);
        }

        System.out.print("Novo Email (deixe vazio para não alterar): ");
        String email = "";
        if (scanner.hasNextLine()) {
            email = scanner.nextLine();
        }
        if (!email.isEmpty()) {
            dono.setEmail(email);
        }

        donoService.save(dono);
        System.out.println("Dono atualizado com sucesso!");
    }

    public void excluirDono() {
        List<Dono> donos = donoService.findAll();
        if (donos.isEmpty()) {
            System.out.println("Não há nenhum dono para excluir!");
            return;
        }

        System.out.print("ID do Dono a excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Dono dono = donoService.findById(id);
        if (dono == null) {
            System.out.println("Dono com ID " + id + " não encontrado!");
        } else {
            donoService.deleteById(id);
            System.out.println("Dono excluído com sucesso!");
        }
    }

    private String maskPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }

    private String maskEmail(String email) {
        if (email.contains("@")) {
            String[] parts = email.split("@");
            String username = parts[0];
            if (username.length() > 1) {
                String maskedUsername = username.substring(0, 1) + "*****";
                return maskedUsername + "@" + parts[1];
            } else {
                return "*@" + parts[1];
            }
        } else {
            return email;
        }
    }


}



