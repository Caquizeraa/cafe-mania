import java.awt.Image;
import java.util.List;

/**
 * Representa um cliente no sistema de atendimento.
 * Eh uma classe abstrata que age como superclasse
 * para cliente preferencial e cliente comum.
 * 
 * @author Pedro Militão and Gabriel Coelho Costa
 * @version 1.0
 * @see EntidadesCenario
 * @see Atendente
 * @see Localizacao
 */
public abstract class Cliente extends EntidadesCenario {
    private Localizacao localizacaoDestino;
    private boolean naFila, atendido;
    private List<Atendente> atendentesDisponiveis;
    private Atendente atendenteEscolhido;

    /** Lista de outros clientes para verificacao de colisao */
    private List<Cliente> clientesExistentes;

    /**
     * Cria um novo cliente com localizacao e imagem.
     * O cliente e inicializado sem destino, fora da fila e nao atendido.
     *
     * @param localizacao           - localizacao inicial do cliente
     * @param imagem                - imagem do cliente
     * @param atendentesDisponiveis - lista de atendentes que podem atender este
     *                              cliente
     * @param clientesExistentes    - lista de outros clientes no sistema
     */
    public Cliente(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis,
            List<Cliente> clientesExistentes) {
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
     * Retorna a localizacao para onde o cliente esta se dirigindo
     *
     * @return destino atual do cliente
     */
    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    /**
     * Define um novo destino para o cliente
     *
     * @param localizacaoDestino - nova localizacao de destino
     */
    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    /**
     * Executa a acao do cliente a cada ciclo da simulacao.
     * Se foi atendido, vai para a saida;
     * Se nao esta na fila, procura uma fila para entrar.
     * Vai pro seu destino se o caminho estiver livre
     */
    public void executarAcao() {
        // Se foi atendido, vai para a saída
        if (atendido) {
            setLocalizacaoDestino(new Localizacao(getLocalizacaoAtual().getX(), 0));
        }
        // Se não, se não está na fila, entra na fila
        else if (!naFila) {
            entrarEmFila();
        }

        // Movimentação do Cliente
        Localizacao destino = getLocalizacaoDestino();
        // Se tem destino
        if (destino != null) {
            // Confere para onde deve ir
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(localizacaoDestino);
            // Se a próxima localização não está ocupada, atualiza a localização atual
            if (!localizacaoEstaOcupada(proximaLocalizacao)) {
                setLocalizacaoAtual(proximaLocalizacao);
            }
        }
    }

    /**
     * Verifica se a localizacao especificada esta ocupada por outra entidade
     * (atendente ou cliente).
     * 
     * @param localizacao Localizacao a ser verificada
     * @return true se a localizacao estiver ocupada, false caso contrario.
     */
    private boolean localizacaoEstaOcupada(Localizacao localizacao) {
        // Checa os clientes
        for (Cliente cliente : clientesExistentes) {
            // Se a localizacao do cliente é igual a localizacao passada e o cliente não é o
            // próprio cliente
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

    /**
     * Verifica se o cliente esta na fila
     * 
     * @return true se o cliente esta na fila, false caso contrario.
     */
    public Boolean getNaFila() {
        return naFila;
    }

    /**
     * Define que o cliente foi atendido e removee ele da fila.
     */
    public void setAtendido() {
        atendido = true;
        setNaFila(false);
    }

    /**
     * Define se o cliente esta na fila
     * 
     * @param naFila true para indicar que o cliente esta na fila, false caso
     *               nao esteja
     */
    public void setNaFila(Boolean naFila) {
        this.naFila = naFila;
    }

    /**
     * Retorna a lista de atendentes disponiveis para o cliente.
     * 
     * @return Lista (List<Atendente>) de atendentes disponiveis.
     */
    public List<Atendente> getAtendentesDisponiveis() {
        return atendentesDisponiveis;
    }

    /**
     * Atualiza a lista de clientes existentes no sistema
     * 
     * @param clientesExistentes Nova lista de clientes
     */
    public void setCientesExistentes(List<Cliente> clientesExistentes) {
        this.clientesExistentes = clientesExistentes;
    }

    /**
     * Define o atendente escolhido para atender o cliente
     * 
     * @param atendenteEscolhido Atendente selecionado
     */
    public void setAtendenteEscolhido(Atendente atendenteEscolhido) {
        this.atendenteEscolhido = atendenteEscolhido;
    }

    /**
     * Faz o cliente entrar em uma fila de atendimento.
     * O cliente escolhe um atendente e se posiciona na entrada da fila
     */
    public void entrarEmFila() {
        // Se nao esta na fila
        if (!getNaFila()) {
            // Seta o destino como o atendente escolhido
            setLocalizacaoDestino(atendenteEscolhido.getPosicaoEntradaFila());
            // Confere se entrou em alguma das filas
            List<Atendente> atendentesDisponiveis = getAtendentesDisponiveis();
            for (Atendente atendente : atendentesDisponiveis) {
                if (atendente.getPosicaoEntradaFila() == getLocalizacaoAtual()) {
                    // Seta na fila, e adiciona o cliente a fila
                    setNaFila(true);
                    atendente.adicionarCliente(this);
                }
            }
        }
    }

    // Metódo que calcula o atendente disponível com a menor fila
    public abstract void escolherAtendente();
}
