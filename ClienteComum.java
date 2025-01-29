import java.awt.Image;
import java.util.List;

// Representa um cliente comum
public class ClienteComum extends Cliente {

    // ClienteComum recebe uma Localizacao, Imagem, Lista de Atendentes Disponíveis e Lista de Clientes Existentes
    public ClienteComum(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis, List<Cliente> clientesExistentes) {
        super(localizacao, imagem, atendentesDisponiveis, clientesExistentes);
    }

    // Método que retorna o atendente disponível mais próximo de cliente comum
    public Atendente getAtendenteMaisProximo(){
        // Pega a lista de atendentes disponíveis
        List<Atendente> atendentesDisponiveis =  getAtendentesDisponiveis();
        Atendente atendenteMaisProximo = null;
        int menorDistanciaX = Integer.MAX_VALUE;

        // Para cada atendente
        for(Atendente atendente: atendentesDisponiveis){
            // Calcula a distncia até a entrada de seu fila
            int distanciaX = Math.abs(getLocalizacaoAtual().getX()-atendente.getPosicaoEntradaFila().getX());
            // Se a distancia for menor que a menor distancia salva
            if(menorDistanciaX > distanciaX){
                // Se o atendente for comum, ou for preferencial e não tiver ninguém na fila
                if((atendente.getTipo().equals("Comum")) || (atendente.getTamanhoFila()==0)){
                    // Configura este como mais proximo e atualiza a menor distancia
                    atendenteMaisProximo = atendente;
                    menorDistanciaX = distanciaX;
                }
            }
        }
        // Retorna o mais próximo após calcular todos
        return atendenteMaisProximo;
    }
}
