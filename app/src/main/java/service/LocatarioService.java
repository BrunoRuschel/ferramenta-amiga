package service;

import dao.LocatarioDAO;
import model.Ferramenta;
import model.Locatario;

import java.util.*;


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

    public Locatario autenticarLocatario(String email, String senha) {
        Locatario locatario = locatarioDAO.buscarLocatarioPorEmail(email);

        if(locatario ==  null) {
            throw new IllegalArgumentException("Usuario nao encontrado.");
        }

        if (!locatario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return locatario;
    }

    public void adicionarCreditos(Locatario locatario, String codigoCreditos) {
        if (codigoCreditos.length() != 6) {
            throw new IllegalArgumentException("Codigo invalido.");
        } else {
            float valorAdicionado = (float)gerarValorAleatorio(10, 100);
            float novoSaldo = valorAdicionado + locatario.getSaldo();
            locatario.setSaldo(novoSaldo);
            this.locatarioDAO.atualizarSaldoLocatario(locatario.getCpf(), locatario.getSaldo());
            System.out.println("Creditos adicionados com sucesso! Valor: R$ " + valorAdicionado);
        }
    }

    private static int gerarValorAleatorio(int minimo, int maximo) {
        Random random = new Random();
        return random.nextInt(maximo - minimo + 1) + minimo;
    }

    public List<Ferramenta> buscarFerramentasAlugadas(String cpf) {
        List<Ferramenta> ferramentas = null;
        try{
           ferramentas = locatarioDAO.buscarFerramentasAlugadasPorLocatario(cpf);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao buscar ferramentas alugadas:" + e.getMessage());
        }
        return removerDuplicados(ferramentas);
    }

    public static List<Ferramenta> removerDuplicados(List<Ferramenta> ferramentas) {
        List<Ferramenta> listaUnica = new ArrayList<>();
        Set<Integer> idsVistos = new HashSet<>();

        for (Ferramenta ferramenta : ferramentas) {
            if (!idsVistos.contains(ferramenta.getCodf())) {
                listaUnica.add(ferramenta);
                idsVistos.add(ferramenta.getCodf());
            }
        }

        return listaUnica;
    }

}


