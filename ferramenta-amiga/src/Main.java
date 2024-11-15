import dao.LocadorDAO;
import model.Locador;
import service.LocadorService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Locador locador1 = new Locador("23658965321", "martin brathwaite", "martin@gmail.com", "tricolor", "humaita");
        LocadorDAO dao1 = new LocadorDAO();
        List<Locador> locadores;

        LocadorService locadorService = new LocadorService(dao1);

        locadorService.cadastrarLocador(locador1);

        locadores = dao1.buscarTodosLocadores();

        for(int i = 0; i < locadores.size(); i++) {
            System.out.println(locadores.get(i).toString());
        }
    }

}