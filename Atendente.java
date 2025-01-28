import java.awt.Image;

public class Atendente extends EntidadesCenario{
    private String tipoAtendente;
    private Fila fila;
    private Boolean atendendo;

    public Atendente(Localizacao localizacao, Image imagem, String tipoAtendente) {
        super(localizacao, imagem);
        this.tipoAtendente = tipoAtendente;
        atendendo = false;
        fila = new Fila(new Localizacao(getLocalizacaoAtual().getX()+1, getLocalizacaoAtual().getY()));
    }

    public String getTipo(){
        return tipoAtendente;
    }

    public int getTamanhoFila(){
        return fila.getTamanho();
    }

    public Localizacao getPosicaoEntradaFila(){
        return fila.getPosicaoEntrada();
    }

    public void atenderCliente(){
        atendendo = true;
        Cliente cliente = fila.getPrimeiro();
        if (cliente != null){
            cliente.setAtendido();
            fila.removerPrimeiro();
            atendendo = false;
        }
    }

    public void adicionarCliente(Cliente cliente){
        fila.adicionarCliente(cliente);
    }

    public void executarAcao(){
        if(!atendendo && fila.getTamanho()>0){
            atenderCliente();
        }
    }

}
