package service;

import dao.FerramentaDAO;
import dao.LocadorDAO;
import model.Ferramenta;

import java.util.List;

public class FerramentaService {

    private final FerramentaDAO ferramentaDAO;
    private final LocadorDAO locadorDAO;

    public FerramentaService(FerramentaDAO ferramentaDAO, LocadorDAO locadorDAO) {
        this.ferramentaDAO = ferramentaDAO;
        this.locadorDAO = locadorDAO;
    }

    public void ofertarFerramenta(Ferramenta ferramenta) {
        if (ferramenta == null) {
            throw new IllegalArgumentException("Os dados da ferramenta nao podem ser nulos.");
        }

        // verifica se o locador existe
        String cpfLocador = ferramenta.getCpf_locad();
        if (!locadorDAO.isLocadorCadastrado(cpfLocador)) {
            throw new IllegalArgumentException("Locador nao encontrado com o CPF: " + cpfLocador);
        }

        // define o estado inicial como DISPONÍVEL
        ferramenta.setStatus("DISPONIVEL");

        // insere a ferramenta no banco de dados
        ferramentaDAO.inserirFerramenta(ferramenta);
    }

    public List<Ferramenta> listarFerramentasDisponiveis() {
        return ferramentaDAO.listarFerramentasDisponiveis();
    }

    public List<Ferramenta> listarFerramentasIndisponiveis() {
        return ferramentaDAO.listarFerramentasIndisponiveis();
    }
}