import java.awt.Image;
import java.util.List;

public class ClienteComum extends Cliente {

    public ClienteComum(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis) {
        super(localizacao, imagem, atendentesDisponiveis);
    }

    public void entrarEmFila(){
        if(!getNaFila()){          
            setLocalizacaoDestino(getAtendenteMaisProximo().getPosicaoEntradaFila()); 
            List<Atendente> atendentesDisponiveis =  getAtendentesDisponiveis();
            for(Atendente atendente: atendentesDisponiveis){
                if(atendente.getPosicaoEntradaFila() == getLocalizacaoAtual()){
                    setNaFila(true);
                    atendente.adicionarCliente(this);
                }
            }
               
        }
    }
    
    private Atendente getAtendenteMaisProximo(){
        List<Atendente> atendentesDisponiveis =  getAtendentesDisponiveis();
        Atendente atendenteMaisProximo = null;
        int menorDistanciaX = Integer.MAX_VALUE;

        for(Atendente atendente: atendentesDisponiveis){
            int distanciaX = Math.abs(getLocalizacaoAtual().getX()-atendente.getPosicaoEntradaFila().getX());
            if(menorDistanciaX > distanciaX){
                if((atendente.getTipo().equals("Comum")) || (atendente.getTamanhoFila()==0)){
                    atendenteMaisProximo = atendente;
                    menorDistanciaX = distanciaX;
                }
            }
        }
        
        return atendenteMaisProximo;
    }
}
