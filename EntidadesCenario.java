import java.awt.Image;

public abstract class EntidadesCenario {
    private Localizacao localizacaoAtual;
    private Image imagem;

    public EntidadesCenario(Localizacao localizacaoAtual, Image imagem){
        this.localizacaoAtual = localizacaoAtual;
        this.imagem = imagem;
    }

    public Localizacao getLocalizacaoAtual(){
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoNova){
        localizacaoAtual = localizacaoNova;
    }

    public Image getImagem(){
        return imagem;
    }
     
}