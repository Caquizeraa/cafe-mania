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
            // Se o atendente analisado for comum
            if(atendente.getTipo().equals("Comum")){
                // Se o atual for Preferencial e o tamanho da fila do analisado for 0, preferir o analisado
                // Garantindo que entre comum e preferencial vazios, prefira o comum
                if(tipoAtual.equals("Preferencial") && tamanhoFila == 0){
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                }
                // Se o analisado nao for preferencial e a fila salva for menor que a fila analisada
                else if(tamanhoMenorFila > tamanhoFila){
                    // Trocar
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                }
                // Buscando o mais perto dos menores
                else if(tamanhoMenorFila == tamanhoFila && atendenteAtual != null){
                    int distancia = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                    if(distanciaAtual>distancia){
                        atendenteAtual = atendente;
                        tamanhoMenorFila = tamanhoFila;
                        tipoAtual = atendente.getTipo();
                        distanciaAtual = distancia;
                    }
                }
            }
            // Se o analisado for preferencial, tiver uma fila vazia e o tamanho for menor que da menor fila
            else if(atendente.getTipo().equals("Preferencial") && tamanhoFila ==0  && tamanhoMenorFila>0){
                // Trocar
                atendenteAtual = atendente;
                tamanhoMenorFila = tamanhoFila;
                tipoAtual = atendente.getTipo();
                distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
            }
        }
        // Retorna o atendente com a menor fila após calcular todos
        super.setAtendenteEscolhido(atendenteAtual);
    }
}
