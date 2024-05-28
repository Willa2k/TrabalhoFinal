package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.entity.Servico;
import com.clinica.veterinaria.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Controller
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int option;
        boolean returnToMainMenu = false;
        do {
            System.out.println("\n--- Menu de Serviços ---");
            System.out.println("1. Listar Serviços");
            System.out.println("2. Adicionar Novo Serviço");
            System.out.println("3. Editar Serviço");
            System.out.println("4. Excluir Serviço");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listarServicos();
                        break;
                    case 2:
                        novoServicoForm();
                        break;
                    case 3:
                        editarServicoForm();
                        break;
                    case 4:
                        excluirServico();
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

    public void listarServicos() {
        List<Servico> servicos = servicoService.findAll();
        System.out.println("\n--- Lista de Serviços ---");
        for (Servico servico : servicos) {
            System.out.println(servico);
        }
    }

    public void novoServicoForm() {
        Servico servico = new Servico();

        System.out.print("Nome: ");
        servico.setNome(scanner.nextLine());

        System.out.print("Preço: ");
        try {
            servico.setPreco(scanner.nextDouble());
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Preço inválido! Tente novamente.");
            scanner.nextLine();
            return;
        }

        servicoService.save(servico);
        System.out.println("Serviço salvo com sucesso!");
    }

    public void editarServicoForm() {
        System.out.print("ID do Serviço a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Servico servico = servicoService.findById(id);
        if (servico == null) {
            System.out.println("Serviço não encontrado!");
            return;
        }

        System.out.println("Editando Serviço: " + servico);
        System.out.print("Novo Nome (deixe vazio para não alterar): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            servico.setNome(nome);
        }

        System.out.print("Novo Preço (deixe vazio para não alterar): ");
        String precoStr = scanner.nextLine();
        if (!precoStr.isEmpty()) {
            try {
                double preco = Double.parseDouble(precoStr);
                servico.setPreco(preco);
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido! Tente novamente.");
                return;
            }
        }

        servicoService.save(servico);
        System.out.println("Serviço atualizado com sucesso!");
    }

    public void excluirServico() {
        List<Servico> servicos = servicoService.findAll();
        if (servicos.isEmpty()) {
            System.out.println("Não há nenhum serviço para excluir!");
            return;
        }

        System.out.print("ID do Serviço a excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Servico servico = servicoService.findById(id);
        if (servico == null) {
            System.out.println("Serviço com ID " + id + " não encontrado!");
        } else {
            servicoService.deleteById(id);
            System.out.println("Serviço excluído com sucesso!");
        }
    }
}
