import java.awt.Image;

// Representa todas as entidades no cenário
public abstract class EntidadesCenario {
    private Localizacao localizacaoAtual;
    private Image imagem;

    // Entidades no cenário recebem localizacao e imagem
    public EntidadesCenario(Localizacao localizacaoAtual, Image imagem){
        this.localizacaoAtual = localizacaoAtual;
        this.imagem = imagem;
    }

    // Getter e Setter de LocalizacaoAtual
    public Localizacao getLocalizacaoAtual(){
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoNova){
        localizacaoAtual = localizacaoNova;
    }

    // Getter de Imagem
    public Image getImagem(){
        return imagem;
    }
     
}