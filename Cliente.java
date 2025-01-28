import java.awt.Image;
import java.util.List;

/**
 * Representa os veiculos da simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public abstract class Cliente extends EntidadesCenario {
    private Localizacao localizacaoDestino;
    private Boolean naFila, atendido;
    private List<Atendente> atendentesDisponiveis;

    public Cliente(Localizacao localizacao, Image imagem, List<Atendente> atendentesDisponiveis) {
        super(localizacao, imagem);
        localizacaoDestino = null;
        naFila = false;
        atendido = false;
        this.atendentesDisponiveis = atendentesDisponiveis;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }
   
    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }
    
    public void executarAcao(){
        if(atendido){
            setLocalizacaoDestino(new Localizacao(getLocalizacaoAtual().getX(), 0));
        }else if(!naFila){
            entrarEmFila();
        }

        Localizacao destino = getLocalizacaoDestino();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(localizacaoDestino);
            setLocalizacaoAtual(proximaLocalizacao);
        }

       
    } 

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

    public List<Atendente> getAtendentesDisponiveis(){
        return atendentesDisponiveis;
    }
    public abstract void entrarEmFila();

}
