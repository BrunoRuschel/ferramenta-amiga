package service;

import dao.LocadorDAO;
import model.Locador;

public class LocadorService {

    private final LocadorDAO locadorDAO;

    public LocadorService(LocadorDAO locadorDAO) {
        this.locadorDAO = locadorDAO;
    }


    public void cadastrarLocador(Locador locador) {
        if (locador == null) {
            throw new IllegalArgumentException("Locador não pode ser nulo.");
        }
        if (!locador.isCpfValido()) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (!locador.isEmailValido()) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (locadorDAO.isEmailCadastrado(locador.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
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
}
