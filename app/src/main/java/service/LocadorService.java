package service;

import dao.LocadorDAO;
import model.Ferramenta;
import model.Locador;

import java.util.List;

public class LocadorService {

    private final LocadorDAO locadorDAO;

    public LocadorService(LocadorDAO locadorDAO) {
        this.locadorDAO = locadorDAO;
    }


    public void cadastrarLocador(Locador locador) {
        if (locador == null) {
            throw new IllegalArgumentException("Locador nao pode ser nulo.");
        }
        if (!locador.isCpfValido()) {
            throw new IllegalArgumentException("CPF invalido.");
        }
        if (!locador.isEmailValido()) {
            throw new IllegalArgumentException("Email invalido.");
        }
        if (locadorDAO.isEmailCadastrado(locador.getEmail())) {
            throw new IllegalArgumentException("Email ja cadastrado.");
        }

        locadorDAO.inserirLocador(locador);
    }

    public Locador autenticarLocador(String email, String senha) {
        Locador locador = locadorDAO.buscarLocadorPorEmail(email);

        if(locador ==  null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        if (!locador.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return locador;
    }

    public Locador buscarPorCPF(String cpf) {
        Locador locador = null;
        try{
            locador = locadorDAO.buscarLocadorPorCPF(cpf);
        }catch (IllegalArgumentException e) {
            new IllegalArgumentException("Erro ao buscar locador por CPF: " + e.getMessage());
        }
        return locador;
    }

    public List<Ferramenta> buscarFerramentasPorLocador(String cpfLocador) {
        return locadorDAO.buscarFerramentasPorLocador(cpfLocador);
    }
}
