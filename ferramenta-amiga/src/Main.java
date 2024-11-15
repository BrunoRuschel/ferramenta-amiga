import dao.LocadorDAO;
import model.Locador;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Locador locador1 = new Locador("21136549654", "martin brathwaite", "martin@gmail.com", "tricolor", "humaita");
        LocadorDAO dao1 = new LocadorDAO();
        List<Locador> locadores;

        locadores = dao1.buscarTodosLocadores();

        for(int i = 0; i < locadores.size(); i++) {
            System.out.println(locadores.get(i).toString());
        }
    }

}