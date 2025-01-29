import java.awt.Image;
import java.util.List;

// Classe que representa um cliente preferencial
public class ClientePreferencial extends Cliente{
    // ClientePrenferencial recebe uma Localizacao, Imagem, Lista de Atendentes Disponíveis e Lista de Clientes Existentes
    public ClientePreferencial(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis, List<Cliente> clientesExistentes) {
        super(localizacao, imagem, atendentesDisponiveis, clientesExistentes);
    }
    
    // Método que retorna o atendente disponível com a menor fila de cliente preferencial
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

            // Se a fila for menor que a atual troca
            if(tamanhoMenorFila > tamanhoFila){
                // Trocar
                atendenteAtual = atendente;
                tamanhoMenorFila = tamanhoFila;
                tipoAtual = atendente.getTipo();
                distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
            }
            // Se a fila for igual a atual
            else if(tamanhoMenorFila == tamanhoFila){
                // Se a fila for preferencial troca
                if(atendente.getTipo().equals("Preferencial")){
                    atendenteAtual = atendente;
                    tamanhoMenorFila = tamanhoFila;
                    tipoAtual = atendente.getTipo();
                    distanciaAtual = Math.abs(super.getLocalizacaoAtual().getX()-atendente.getLocalizacaoAtual().getX());
                }
                // Se não se a atual e a analisada forem comuns, pega o mais perto
                else if(atendente.getTipo().equals("Comum") && tipoAtual.equals("Comum")){
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
