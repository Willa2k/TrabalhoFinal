package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.entity.Funcionario;
import com.clinica.veterinaria.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int option;
        boolean returnToMainMenu = false;
        do {
            System.out.println("\n--- Menu de Funcionários ---");
            System.out.println("1. Listar Funcionários");
            System.out.println("2. Adicionar Novo Funcionário");
            System.out.println("3. Editar Funcionário");
            System.out.println("4. Excluir Funcionário");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listarFuncionarios();
                        break;
                    case 2:
                        novoFuncionarioForm();
                        break;
                    case 3:
                        editarFuncionarioForm();
                        break;
                    case 4:
                        excluirFuncionario();
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

    public void listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.findAll();
        System.out.println("\n--- Lista de Funcionários ---");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
    }

    public void novoFuncionarioForm() {
        Funcionario funcionario = new Funcionario();

        System.out.print("Nome: ");
        funcionario.setNome(scanner.nextLine());

        System.out.print("Cargo: ");
        funcionario.setCargo(scanner.nextLine());

        funcionarioService.save(funcionario);
        System.out.println("Funcionário salvo com sucesso!");
    }

    public void editarFuncionarioForm() {
        System.out.print("ID do Funcionário a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        System.out.println("Editando Funcionário: " + funcionario);
        System.out.print("Novo Nome (deixe vazio para não alterar): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            funcionario.setNome(nome);
        }

        System.out.print("Novo Cargo (deixe vazio para não alterar): ");
        String cargo = scanner.nextLine();
        if (!cargo.isEmpty()) {
            funcionario.setCargo(cargo);
        }

        funcionarioService.save(funcionario);
        System.out.println("Funcionário atualizado com sucesso!");
    }

    public void excluirFuncionario() {
        List<Funcionario> funcionarios = funcionarioService.findAll();
        if (funcionarios.isEmpty()) {
            System.out.println("Não há nenhum funcionário para excluir!");
            return;
        }

        System.out.print("ID do Funcionário a excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario == null) {
            System.out.println("Funcionário com ID " + id + " não encontrado!");
        } else {
            funcionarioService.deleteById(id);
            System.out.println("Funcionário excluído com sucesso!");
        }
    }
}
