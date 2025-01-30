import java.awt.Image;
import java.util.List;

/**
 * Representa um cliente comum no sistema de atendimento.
 * Este cliente escolhe um atendente baseado no tamanho da fila e proximidade.
 * 
 * @author Pedro Militão and Gabriel Coelho Costa
 * @version 1.0
 * @see Cliente
 * @see ClientePrefrencial
 * @see Atendente
 */
public class ClienteComum extends Cliente {

    /**
     * Cria um cliente comum com localizacao, imagem e listas de atendentes e
     * clientes.
     * 
     * @param localizacao           posicao inicial do cliente.
     * @param imagem                representacao visual do cliente.
     * @param atendentesDisponiveis lista de atendentes disponiveis.
     * @param clientesExistentes    lista de outros clientes no sistema.
     */
    public ClienteComum(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis,
            List<Cliente> clientesExistentes) {
        super(localizacao, imagem, atendentesDisponiveis, clientesExistentes);
    }

    /**
     * Escolhe o atendente disponivel com a menor fila para atendimento.
     * Se houver empate, considera a proximidade e a prioridade do atendente.
     */
    public void escolherAtendente() {
        // Pega a lista de atendentes disponiveis
        List<Atendente> atendentesDisponiveis = getAtendentesDisponiveis();
        Atendente atendenteAtual = null;
        int tamanhoMenorFila = Integer.MAX_VALUE;
        int distanciaAtual = Integer.MAX_VALUE;
        String tipoAtual = "";

        // Para cada atendente
        for (Atendente atendente : atendentesDisponiveis) {
            // Pega o tamanho da fila
            int tamanhoFila = atendente.getTamanhoFila();
            // Se o tamanho da fila for menor que a atual
            if (tamanhoMenorFila > tamanhoFila) {
                // Se for comum troca
                if (atendente.getTipo().equals("Comum")) {
                    // Trocar
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math
                            .abs(super.getLocalizacaoAtual().getX() - atendente.getLocalizacaoAtual().getX());
                }
                // Se for preferencial e estiver vazia, troca
                else if (atendente.getTipo().equals("Preferencial") && tamanhoFila == 0) {
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math
                            .abs(super.getLocalizacaoAtual().getX() - atendente.getLocalizacaoAtual().getX());
                }
            }
            // Se o tamanho da fila for igual
            else if (tamanhoMenorFila == tamanhoFila) {
                // Se a atual for preferencial troca
                if (tipoAtual.equals("Preferencial")) {
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math
                            .abs(super.getLocalizacaoAtual().getX() - atendente.getLocalizacaoAtual().getX());
                }
                // Se a atual for Comum e a analisada tambem, pega o mais perto
                else if (tipoAtual.equals("Comum") && atendente.getTipo().equals("Comum")) {
                    int distancia = Math
                            .abs(super.getLocalizacaoAtual().getX() - atendente.getLocalizacaoAtual().getX());
                    if (distanciaAtual > distancia) {
                        atendenteAtual = atendente;
                        tamanhoMenorFila = tamanhoFila;
                        tipoAtual = atendente.getTipo();
                        distanciaAtual = distancia;
                    }
                }
            }
        }
        // Define o atendente escolhido
        super.setAtendenteEscolhido(atendenteAtual);
    }
}
