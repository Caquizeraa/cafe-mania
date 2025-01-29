import java.awt.Image;
import java.util.List;

/**
 * Representa os veiculos da simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */

 // Representa a entidade Cliente
public abstract class Cliente extends EntidadesCenario {
    private Localizacao localizacaoDestino;
    private Boolean naFila, atendido; // Váriaveis que informam o estado do cliente
    private List<Atendente> atendentesDisponiveis; // Lista de atendentes disponíveis (para cliente se locomover até)
    private List<Cliente> clientesExistentes; // Lista de clientes existentes (para evitar colisões)

    // Cliente recebe uma Localizacao, Imagem, Lista de Atendentes Disponíveis e Lista de Clientes Existentes
    public Cliente(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis, List<Cliente> clientesExistentes) {
        super(localizacao, imagem);
        // Nasce sem destino, não está na fila e não foi atendido
        localizacaoDestino = null;
        naFila = false;
        atendido = false;
        this.atendentesDisponiveis = atendentesDisponiveis;
        this.clientesExistentes = clientesExistentes;
    }   

    // Getter e Setter de LocalizacaoDestino
    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }
   
    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }
    
    // Método que executa a ação do Cliente
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

    // Método que faz o cliente entrar na fila
    public void entrarEmFila(){
        // Se não está na fila
        if(!getNaFila()){          
            // Seta o destino como o atendente mais próximo
            setLocalizacaoDestino(getAtendenteMaisProximo().getPosicaoEntradaFila()); 
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

    //Metódo que calcula o atendente disponível mais próximo
    public abstract Atendente getAtendenteMaisProximo();
}
