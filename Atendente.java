import java.awt.Image;
import java.util.Random;

/**
 * Representa um atendente no cafe-mania, responsável por
 * gerenciar uma fila de clientes e realizar atendimentos.
 * 
 * O atendente pode ser de diferentes tipos (preferencial ou comum).
 * 
 * @author Isac Cunha e Otávio Sbampato
 * @version 1.0
 * @see EntidadesCenario
 * @see Cliente
 * @see Fila
 */
public class Atendente extends EntidadesCenario{
    // atributos a serem usados na classe
    private String tipoAtendente;
    private Fila fila;
    private Random r;
    private int tempoAtendimento;
    
    // Timer de atendimento e boleano que indica se o atendente está ou não atendendo
    private boolean atendendo;
    private int timer = 0;

    /**
     * Cria um atendente com uma localização, imagem e tipo
     * 
     * @param localizacao - Localização inicial do atendente.
     * @param imagem - Imagem do atendente.
     * @param tipoAtendente - Tipo do atendente (preferencial ou comum).
     */
    public Atendente(Localizacao localizacao, Image imagem, String tipoAtendente) {
        super(localizacao, imagem);
        this.tipoAtendente = tipoAtendente;
        r = new Random();
        tempoAtendimento = 0;
        atendendo = false;
        // Clientes formam filas do lado direito do atendente.
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

        // randomiza o tempo de atendimento entre 8 e 15
        tempoAtendimento = r.nextInt(8) + 8;

        // Pega o primeiro da fila
        Cliente cliente = fila.getPrimeiro();
        // Se não for nulo e o timer for maior ou igual a 10, atende o cliente
        if (cliente != null && timer >= tempoAtendimento){
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
