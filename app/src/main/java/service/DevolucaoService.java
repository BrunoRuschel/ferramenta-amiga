package service;

import dao.*;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DevolucaoService {

    private final DevolucaoDAO devolucaoDAO;
    private final FerramentaDAO ferramentaDAO;
    public DevolucaoService(DevolucaoDAO devolucaoDAO, FerramentaDAO ferramentaDAO) {
        this.devolucaoDAO = devolucaoDAO;
        this.ferramentaDAO = ferramentaDAO;
    }

    public void devolverFerramenta(int codFerramenta, String cpfLocatario) {
        Ferramenta ferramenta = ferramentaDAO.findById(codFerramenta);

        if (ferramenta == null) {
            throw new IllegalArgumentException("Ferramenta não encontrada.");
        }

        Locatario locatario = null;
        LocatarioDAO locatarioDAO = new LocatarioDAO();
        locatario = locatarioDAO.buscarLocatarioPorCPF(cpfLocatario);

        Devolucao devolucao = new Devolucao();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter);
        devolucao.setData(dateString);

        devolucao.setCodf(codFerramenta);
        devolucao.setCpf_locat(cpfLocatario);
        devolucao.setCodd((int)(Math.random() * 10000));

        devolucaoDAO.registrarDevolucao(devolucao);

        // Atualizar status da ferramenta
        ferramenta.setStatus("DISPONIVEL");
        ferramentaDAO.mudarStatusFerramenta(codFerramenta, ferramenta.getStatus());

    }
}
