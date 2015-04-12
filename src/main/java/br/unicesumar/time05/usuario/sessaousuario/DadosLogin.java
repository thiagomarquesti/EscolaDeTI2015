package br.unicesumar.time05.usuario.sessaousuario;

public class DadosLogin {
    private String login;
    private String senha;

    public DadosLogin(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
