import java.awt.Image;

// Classe que representa um atendente
public class Atendente extends EntidadesCenario{
    private String tipoAtendente;
    private Fila fila;
    
    // Timer de atendimento e se esta ou não atendendo
    private Boolean atendendo;
    private int timer = 0;

    // Atendente recebe uma Localizacao, Imagem e Tipo
    public Atendente(Localizacao localizacao, Image imagem, String tipoAtendente) {
        super(localizacao, imagem);
        this.tipoAtendente = tipoAtendente;
        atendendo = false;
        fila = new Fila(new Localizacao(getLocalizacaoAtual().getX()+1, getLocalizacaoAtual().getY()));
    }

    // Retorna se é preferencial ou comum
    public String getTipo(){
        return tipoAtendente;
    }

    // Retorna quantos clientes tem na sua fila
    public int getTamanhoFila(){
        return fila.getTamanho();
    }

    // Retorna a posição de entrada na fila
    public Localizacao getPosicaoEntradaFila(){
        return fila.getPosicaoEntrada();
    }

    // Atende o cliente
    public void atenderCliente(){
        // Se não está atendentendo, começa a atender
        if(!atendendo){
            atendendo = true;
        }

        // Pega o primeiro da fila
        Cliente cliente = fila.getPrimeiro();
        // Se não for nulo e o timer for maior ou igual a 10, atende o cliente
        if (cliente != null && timer >= 10){
            // Seta o atributo do cliente como atendido
            cliente.setAtendido();
            // Remove o cliente da fila
            fila.removerPrimeiro();
            // Reseta o timer e mostra que esta livre para atender outro
            atendendo = false;
            timer = 0;
        }
    }

    // Adiciona um cliente na fila
    public void adicionarCliente(Cliente cliente){
        fila.adicionarCliente(cliente);
    }

    // Executa a ação do atendente
    public void executarAcao(){
        // Se está atendendo, incrementa o timer e tenta atender o cliente
        if(atendendo){
            timer++;
            atenderCliente();
        }
        // Se não está atendendo e tem alguém na fila, atende o cliente
        if(!atendendo && fila.getTamanho()>0){
            atenderCliente();
        }
    }

}
