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

    /**
     * Retorna o tipo do atendente (preferencial ou comum)
     * 
     * @return tipo do atendente.
     */
    public String getTipo(){
        return tipoAtendente;
    }

    /**
     * Retorna a quantidade de clientes na fila do atendente
     * 
     * @return numero de clientes na fila.
     */
    public int getTamanhoFila(){
        return fila.getTamanho();
    }


    /**
     * Retorna a posicao de entrada da fila do atendente.
     * 
     * @return Localizacao da entrada da fila.
     */
    public Localizacao getPosicaoEntradaFila(){
        return fila.getPosicaoEntrada();
    }

    /**
     * Realiza o atendimento de um cliente na fila:
     * Define um tempo de atendimento aleatorio entre 8 e 15,
     * verifica se ha clientes na fila e, ao finalizar o tempo,
     * remove o cliente atendido.
     */
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

    /**
     * Adiciona um cliente na fila do atendente
     * 
     * @param cliente - cliente a ser adicionado na fila.
     */
    public void adicionarCliente(Cliente cliente){
        fila.adicionarCliente(cliente);
    }

    /**
     * Executa a acao do atendente, verificando se ha clientes
     * para atender e atualizando o estado do atendimento.
     */
    public void executarAcao(){
        // Se esta atendendo, incrementa o timer e tenta atender o cliente
        if(atendendo){
            timer++;
            atenderCliente();
        }
        // Se nao esta atendendo e tem alguem na fila, atende o cliente
        if(!atendendo && fila.getTamanho()>0){
            atenderCliente();
        }
    }


}
