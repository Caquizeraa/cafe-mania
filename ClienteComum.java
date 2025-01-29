import java.awt.Image;
import java.util.List;

// Representa um cliente comum
public class ClienteComum extends Cliente {

    // ClienteComum recebe uma Localizacao, Imagem, Lista de Atendentes Disponíveis e Lista de Clientes Existentes
    public ClienteComum(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis, List<Cliente> clientesExistentes) {
        super(localizacao, imagem, atendentesDisponiveis, clientesExistentes);
    }

    // Método que retorna o atendente disponível com a menor fila de cliente comum
    public void escolherAtendente(){
        // Pega a lista de atendentes disponíveis
        List<Atendente> atendentesDisponiveis =  getAtendentesDisponiveis();
        Atendente atendenteAtual = null;
        int tamanhoMenorFila = Integer.MAX_VALUE;
        int distanciaAtual = Integer.MAX_VALUE;
        String tipoAtual = "";

        // Para cada atendente
        for(Atendente atendente: atendentesDisponiveis){
            // Pega o tamanho da fila
            int tamanhoFila = atendente.getTamanhoFila();
            // Se o tamanho da fila for menor que a atual
            if(tamanhoMenorFila > tamanhoFila){
                // Se for comum troca
                if(atendente.getTipo().equals("Comum")){
                    // Trocar
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                }
                // Se for preferencial e estiver vazia, troca
                else if(atendente.getTipo().equals("Preferencial") && tamanhoFila == 0){
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                }
            }
            // Se o tamanho da fila for igual
            else if(tamanhoMenorFila==tamanhoFila){
                // Se a atual for preferencial troca
                if(tipoAtual.equals("Preferencial")){
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                }
                // Se a atual for Comum e a analisada também, pega o mais perto
                else if(tipoAtual.equals("Comum") && atendente.getTipo().equals("Comum")){
                    int distancia = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                    if(distanciaAtual>distancia){
                        atendenteAtual = atendente;
                        tamanhoMenorFila = tamanhoFila;
                        tipoAtual = atendente.getTipo();
                        distanciaAtual = distancia;
                    }
                }
            }
        }
        // Retorna o atendente com a menor fila após calcular todos
        super.setAtendenteEscolhido(atendenteAtual);
    }
}
