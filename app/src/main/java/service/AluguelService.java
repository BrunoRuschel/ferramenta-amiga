package service;

import dao.AluguelDAO;
import dao.FerramentaDAO;
import dao.LocadorDAO;
import dao.LocatarioDAO;
import model.Aluguel;
import model.Ferramenta;
import model.Locador;
import model.Locatario;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AluguelService {

    private final AluguelDAO aluguelDAO;
    private final FerramentaDAO ferramentaDAO;

    public AluguelService(AluguelDAO aluguelDAO, FerramentaDAO ferramentaDAO) {
        this.aluguelDAO = aluguelDAO;
        this.ferramentaDAO = ferramentaDAO;
    }

    public void alugarFerramenta(int codFerramenta, String cpfLocatario) {
        Ferramenta ferramenta = ferramentaDAO.findById(codFerramenta);

        if (ferramenta == null) {
            throw new IllegalArgumentException("Ferramenta não encontrada.");
        }

        if (!"DISPONIVEL".equals(ferramenta.getStatus())) {
            throw new IllegalStateException("Ferramenta nao esta disponivel para aluguel.");
        }

        Locatario locatario = null;
        LocatarioDAO locatarioDAO = new LocatarioDAO();
        locatario = locatarioDAO.buscarLocatarioPorCPF(cpfLocatario);

        if(locatario.getSaldo() < ferramenta.getPreco()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        Aluguel aluguel = new Aluguel();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter);
        aluguel.setData(dateString);

        aluguel.setCodf(codFerramenta);
        aluguel.setCpf_locat(cpfLocatario);
        aluguel.setCod((int)(Math.random() * 10000));

        aluguelDAO.registrarAluguel(aluguel);

        // Atualizar status da ferramenta
        ferramenta.setStatus("ALUGADA");
        ferramentaDAO.mudarStatusFerramenta(codFerramenta, ferramenta.getStatus());
        locatarioDAO.atualizarSaldoLocatario(locatario.getCpf(), locatario.getSaldo() - ferramenta.getPreco());

        LocadorDAO locadorDAO = new LocadorDAO();
        Locador locador = locadorDAO.buscarLocadorPorCPF(ferramenta.getCpf_locad());
        locadorDAO.atualizarSaldoLocador(ferramenta.getCpf_locad(), locador.getSaldo() + ferramenta.getPreco());
    }
}
