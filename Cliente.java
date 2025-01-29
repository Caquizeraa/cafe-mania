import java.awt.Image;
import java.util.List;

/**
* Representa um cliente no sistema de atendimento.
* Esta classe abstrata define o comportamento basico de clientes
* que precisam ser atendidos, incluindo sua movimentacao no mapa e
* interacao com filas de atendimento.
* 
* @author [Nome do Autor]
* @version 1.0
* @see EntidadesCenario
* @see Atendente
* @see Localizacao
*/
public abstract class Cliente extends EntidadesCenario {
    /** Local para onde o cliente pretende se mover */
    private Localizacao localizacaoDestino;
    
    /** Indicadores do estado atual do cliente */
    private boolean naFila, atendido;
    
    /** Lista de atendentes disponiveis para este cliente */
    private List<Atendente> atendentesDisponiveis;
    private Atendente atendenteEscolhido;
    
    /** Lista de outros clientes para verificacao de colisao */  
    private List<Cliente> clientesExistentes;
 
    /**
     * Cria um novo cliente com localizacao e imagem especificas.
     * O cliente e inicializado sem destino, fora da fila e nao atendido.
     *
     * @param localizacao posicao inicial do cliente
     * @param imagem representacao visual do cliente
     * @param atendentesDisponiveis lista de atendentes que podem atender este cliente
     * @param clientesExistentes lista de outros clientes no sistema
     */
    public Cliente(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis, List<Cliente> clientesExistentes) {
        super(localizacao, imagem);
        localizacaoDestino = null;
        naFila = false;
        atendido = false;
        atendenteEscolhido = null;
        this.atendentesDisponiveis = atendentesDisponiveis;
        this.clientesExistentes = clientesExistentes;
        escolherAtendente();
    }   
 
    /**
     * Retorna a localizacao para onde o cliente esta se dirigindo.
     *
     * @return o destino atual do cliente
     */
    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }
   
    /**
     * Define um novo destino para o cliente.
     *
     * @param localizacaoDestino nova localizacao de destino
     */
    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }
    
    /**
     * Executa a acao do cliente a cada ciclo da simulacao.
     * Se foi atendido, move-se para a saida.
     * Se nao esta na fila, procura uma fila para entrar.
     * Move-se em direcao ao seu destino se houver caminho livre.
     */
    public void executarAcao(){       
        // Se foi atendido, vai para a saída
        if(atendido){
            setLocalizacaoDestino(new Localizacao(getLocalizacaoAtual().getX(), 0));
        }
        // Se não, se não está na fila, entra na fila
        else if(!naFila){
            entrarEmFila();
        }
        
        // Movimentação do Cliente
        Localizacao destino = getLocalizacaoDestino();
        // Se tem destino
        if(destino != null){
            // Confere para onde deve ir
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(localizacaoDestino);
            // Se a próxima localização não está ocupada, atualiza a localização atual
            if (!isLocationOccupied(proximaLocalizacao)) {
                setLocalizacaoAtual(proximaLocalizacao);
            }
        }
    } 
    
    // Método que verifica se a localização está ocupada
    private boolean isLocationOccupied(Localizacao localizacao) {
        // Checa os cliente
        for (Cliente cliente : clientesExistentes) {
            // Se a localizacao do cliente é igual a localizacao passada e o cliente não é o próprio cliente
            if (cliente.getLocalizacaoAtual().equals(localizacao) && cliente != this) {
                // Retorna true (ocupada)
                return true;
            }
        }
        // Checa os atendentes
        for (Atendente atendente : atendentesDisponiveis) {
            // Se a localizacao do atendente é igual a localizacao passada
            if (atendente.getLocalizacaoAtual().equals(localizacao)) {
                // Retorna true (ocupada)
                return true;
            }
        }
        return false;
    }

    // Getter e Setter de Atendido e NaFila
    public Boolean getNaFila(){
        return naFila;
    }

    public void setAtendido(){
        atendido = true;
        setNaFila(false);
    }

    public void setNaFila(Boolean naFila){
        this.naFila = naFila;
    }

    // Get de Atendentes Disponíveis
    public List<Atendente> getAtendentesDisponiveis(){
        return atendentesDisponiveis;
    }

    public void setCientesExistentes(List<Cliente> clientesExistentes){
        this.clientesExistentes = clientesExistentes;
    }

    // Setter de Atendente Escolhido
    public void setAtendenteEscolhido(Atendente atendenteEscolhido){
        this.atendenteEscolhido = atendenteEscolhido;
    }

    // Método que faz o cliente entrar na fila
    public void entrarEmFila(){
        // Se não está na fila
        if(!getNaFila()){          
            // Seta o destino como o atendente escolhido
            setLocalizacaoDestino(atendenteEscolhido.getPosicaoEntradaFila()); 
            // Confere se entrou em alguma das filas
            List<Atendente> atendentesDisponiveis =  getAtendentesDisponiveis();
            for(Atendente atendente: atendentesDisponiveis){
                if(atendente.getPosicaoEntradaFila() == getLocalizacaoAtual()){
                    // Seta na fila, e adiciona o cliente a fila
                    setNaFila(true);
                    atendente.adicionarCliente(this);
                }
            }
               
        }
    }

    //Metódo que calcula o atendente disponível com a menor fila
    public abstract void escolherAtendente();
}
