package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

@Controller
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    private Scanner scanner = new Scanner(System.in);
    private SimpleDateFormat dateTimeFormat;

    public ConsultaController() {
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    public void menu() {
        int option;
        boolean returnToMainMenu = false;
        do {
            System.out.println("\n--- Menu de Consultas ---");
            System.out.println("1. Listar Consultas");
            System.out.println("2. Adicionar Nova Consulta");
            System.out.println("3. Editar Consulta");
            System.out.println("4. Excluir Consulta");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listarConsultas();
                        break;
                    case 2:
                        novaConsultaForm();
                        break;
                    case 3:
                        editarConsultaForm();
                        break;
                    case 4:
                        excluirConsulta();
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

    public void listarConsultas() {
        List<Consulta> consultas = consultaService.findAll();
        System.out.println("\n--- Lista de Consultas ---");
        for (Consulta consulta : consultas) {
            String formattedDate = dateTimeFormat.format(consulta.getData());
            System.out.println("Consulta{id=" + consulta.getId() + ", data=" + formattedDate + ", descricao='" + consulta.getDescricao() + "'}");
        }
    }

    public void novaConsultaForm() {
        Consulta consulta = new Consulta();

        System.out.print("Data e Hora (dd/MM/yyyy HH:mm): ");
        String dataStr = scanner.nextLine();
        try {
            consulta.setData(dateTimeFormat.parse(dataStr));
        } catch (ParseException e) {
            System.out.println("Data e Hora inválida!");
            return;
        }

        System.out.print("Descrição: ");
        consulta.setDescricao(scanner.nextLine());

        consultaService.save(consulta);
        System.out.println("Consulta salva com sucesso!");
    }

    public void editarConsultaForm() {
        System.out.print("ID da Consulta a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Consulta consulta = consultaService.findById(id);
        if (consulta == null) {
            System.out.println("Consulta não encontrada!");
            return;
        }

        System.out.println("Editando Consulta: " + consulta);
        System.out.print("Nova Data e Hora (dd/MM/yyyy HH:mm) (deixe vazio para não alterar): ");
        String dataStr = scanner.nextLine();
        if (!dataStr.isEmpty()) {
            try {
                consulta.setData(dateTimeFormat.parse(dataStr));
            } catch (ParseException e) {
                System.out.println("Data e Hora inválida!");
                return;
            }
        }

        System.out.print("Nova Descrição (deixe vazio para não alterar): ");
        String descricao = scanner.nextLine();
        if (!descricao.isEmpty()) {
            consulta.setDescricao(descricao);
        }

        consultaService.save(consulta);
        System.out.println("Consulta atualizada com sucesso!");
    }

    public void excluirConsulta() {
        List<Consulta> consultas = consultaService.findAll();
        if (consultas.isEmpty()) {
            System.out.println("Não há nenhuma consulta para excluir!");
            return;
        }

        System.out.print("ID da Consulta a excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Consulta consulta = consultaService.findById(id);
        if (consulta == null) {
            System.out.println("Consulta com ID " + id + " não encontrada!");
        } else {
            consultaService.deleteById(id);
            System.out.println("Consulta excluída com sucesso!");
        }
    }
}
