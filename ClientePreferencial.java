import java.awt.Image;
import java.util.List;

/**
 * Representa um cliente preferencial no sistema de atendimento.
 * Este cliente tem prioridade sobre clientes comuns ao escolher uma fila.
 * 
 * @author Pedro Militão e Gabriel Coelho
 * @version 1.0
 * @see Cliente
 * @see Atendente
 */
public class ClientePreferencial extends Cliente {

    /**
     * Cria um cliente preferencial com localizacao, imagem e listas de atendentes e
     * clientes existentes.
     *
     * @param localizacao           posicao inicial do cliente
     * @param imagem                representacao visual do cliente
     * @param atendentesDisponiveis lista de atendentes que podem atender este cliente
     * @param clientesExistentes    lista de outros clientes no sistema
     */
    public ClientePreferencial(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis,
            List<Cliente> clientesExistentes) {
        super(localizacao, imagem, atendentesDisponiveis, clientesExistentes);
    }

    /**
     * Determina o atendente disponivel com a menor fila para o cliente
     * preferencial.
     * Clientes preferenciais podem escolher tanto filas preferenciais quanto
     * comuns, priorizando filas menores
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

            // Se a fila for menor que a atual troca
            if (tamanhoMenorFila > tamanhoFila) {
                // Trocar
                atendenteAtual = atendente;
                tamanhoMenorFila = tamanhoFila;
                tipoAtual = atendente.getTipo();
                distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX() - atendente.getLocalizacaoAtual().getX());
            }
            // Se a fila for igual a atual
            else if (tamanhoMenorFila == tamanhoFila) {
                // Se a fila for preferencial troca
                if (atendente.getTipo().equals("Preferencial")) {
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math
                            .abs(super.getLocalizacaoAtual().getX() - atendente.getLocalizacaoAtual().getX());
                }
                // Se nao, e se a atual e a analisada forem comuns, pega o mais perto
                else if (atendente.getTipo().equals("Comum") && tipoAtual.equals("Comum")) {
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

        // Retorna o atendente com a menor fila apos calcular todos
        super.setAtendenteEscolhido(atendenteAtual);
    }
}
