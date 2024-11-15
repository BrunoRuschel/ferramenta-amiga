package service;

import dao.LocatarioDAO;
import model.Locatario;


public class LocatarioService {

    private final LocatarioDAO locatarioDAO;

    public LocatarioService(LocatarioDAO locatarioDAO) {
        this.locatarioDAO = locatarioDAO;
    }

    public void cadastrarlocatario(Locatario locatario) {
        if (locatario == null) {
            throw new IllegalArgumentException("locatario nao pode ser nulo.");
        }
        if (!locatario.isCpfValido()) {
            throw new IllegalArgumentException("CPF invalido.");
        }
        if (!locatario.isEmailValido()) {
            throw new IllegalArgumentException("Email invaido.");
        }
        if (locatarioDAO.isEmailCadastrado(locatario.getEmail())) {
            throw new IllegalArgumentException("Email ja cadastrado.");
        }

        locatarioDAO.inserirLocatario(locatario);
    }
}
