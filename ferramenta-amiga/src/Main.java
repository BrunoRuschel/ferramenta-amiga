import dao.AluguelDAO;
import dao.FerramentaDAO;
import dao.LocadorDAO;
import model.Ferramenta;
import model.Locador;
import service.AluguelService;
import service.LocadorService;

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
        try {
            aluguelService.alugarFerramenta(1, "45678901234");  // Alugando a ferramenta
            System.out.println("Aluguel realizado com sucesso!");

            // Verificando se o status da ferramenta foi atualizado para "ALUGADA"
            Ferramenta ferramentaAtualizada = ferramentaDAO.findById(1);
            System.out.println("Status da ferramenta após aluguel: " + ferramentaAtualizada.getStatus());
        } catch (Exception e) {
            System.out.println("Erro ao realizar aluguel: " + e.getMessage());
        }
    }

}