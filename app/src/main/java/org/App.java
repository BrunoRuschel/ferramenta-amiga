package org;

public class App {
    static GUIMain FXMain = new GUIMain();

    public static void main(String[] args) {
        FXMain.javaFXMain(args);
    }
}
    /*
import dao.AluguelDAO;
import dao.FerramentaDAO;
import dao.LocadorDAO;
import dao.LocatarioDAO;
import model.Ferramenta;
import model.Locador;
import model.Locatario;
import service.AluguelService;
import service.FerramentaService;
import service.LocadorService;
import service.LocatarioService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Locador locador1 = new Locador("23658965321", "martin brathwaite", "martin@gmail.com", "tricolor", "humaita");
        //LocadorDAO dao1 = new LocadorDAO();
        //List<Locador> locadores;

        //LocadorService locadorService = new LocadorService(dao1);

        //locadorService.cadastrarLocador(locador1);

        //locadores = dao1.buscarTodosLocadores();

        //for(int i = 0; i < locadores.size(); i++) {
         //   System.out.println(locadores.get(i).toString());
        //}

        // Simulação dos repositórios
        FerramentaDAO ferramentaDAO = new FerramentaDAO();
        AluguelDAO aluguelDAO = new AluguelDAO();

        // Criando uma instância do serviço
        AluguelService aluguelService = new AluguelService(aluguelDAO, ferramentaDAO);

        // Testando o aluguel de ferramenta
        //try {
            //aluguelService.alugarFerramenta(1, "45678901234");  // Alugando a ferramenta
           // System.out.println("Aluguel realizado com sucesso!");

            // Verificando se o status da ferramenta foi atualizado para "ALUGADA"
            //Ferramenta ferramentaAtualizada = ferramentaDAO.findById(1);
            //System.out.println("Status da ferramenta após aluguel: " + ferramentaAtualizada.getStatus());
       // } catch (Exception e) {
        //    System.out.println("Erro ao realizar aluguel: " + e.getMessage());
        //}

        //Testando oferecer ferramenta
        LocadorDAO locadorDAO = new LocadorDAO();
        //Locador locador1 = new Locador("21136549654", "martin brathwaite", "martin@gmail.com", "tricolor", "humaita");

        //Ferramenta martelo = new Ferramenta(8965, "martelo", "tramontina", 5.0F, "nova", "DISPONIVEL", "21136549654");

        //FerramentaService ferramentaService = new FerramentaService(ferramentaDAO, locadorDAO);
        //try {
         //   ferramentaService.ofertarFerramenta(martelo);
         //   System.out.println("Ferramenta ofertada com sucesso!");

         //    } catch (Exception e) {
          //      System.out.println("Erro ao realizar aluguel: " + e.getMessage());
          //  }

        //Testes de autenticação
        //LocadorService locadorService = new LocadorService(locadorDAO);
        //try {
          //  Locador locador2 = locadorService.autenticarLocador("martin@gmail.com", "tricolor");
          //  System.out.println(locador2.toString());
        //}
        //catch (Exception e) {
        //    System.out.println("Erro ao autenticar locador: " + e.getMessage());
        //}


        //LocatarioDAO locatarioDAO = new LocatarioDAO();
       // LocatarioService locatarioService = new LocatarioService(locatarioDAO);
       // try {
        //    Locatario locatario = locatarioService.autenticarLocatario("ana.oliveira@email.com", "senha321");
          //  System.out.println(locatario.toString());
      //  }
       // catch (Exception e) {
        //    System.out.println("Erro ao autenticar locatario: " + e.getMessage());
        //}


        List<Ferramenta> indisponiveis = ferramentaDAO.listarFerramentasIndisponiveis();
        for (int i = 0; i < indisponiveis.size(); i++) {
            System.out.println(indisponiveis.get(i));
        }


    }

}
    */

