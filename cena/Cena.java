package cena;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import textura.Textura;

public class Cena implements GLEventListener{
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private TextRenderer textRenderer;
    private TextRenderer textRenderer1;
    public int placar = 0;
    public int vidas = 5;
    public int fase = 1;
    public float tamanho = 85;
    public float tamanhoB = 80;
    public float tamanhoR = 40;
    public float tamanhoS = 400;
    public final float tamanhoPrimarioDoObstaculo = 100;
    public float tamanhoNormalDoObstaculo = tamanhoPrimarioDoObstaculo + (20 * (fase-1));
    public float movimentoBarrinha = 0;
    public float transXBola = 0;
    public float transYBola = 0;
    private float margemXdErro;
    private float margemYdErro;
    public final float janela = 1890f;
    public float direitaXBola = tamanho, esquerdaXBola = -tamanho;
    public float superiorYBola = tamanho, inferiorYBola = -tamanho;
    public final float velocidadeX = 60f, velocidadeY = 45f;
    public float taxaAttX = 60f , taxaAttY = 45f;
    public float direitaBarrinha = 250 , esquerdaBarrinha =direitaBarrinha - 500;
    public float Ybarrinha = -900 ;
    public boolean dandoPlay = false;
    public boolean Menu = true;
    public boolean pause = false;
    public boolean fimDoJogo = false;
    public int mode;
    public final float velDoMovimentarDaBarra = 100;
    private float movimentaFaixa = 0;
    private boolean faixaDeAlcanceObstaculo = false;

    private Textura textura;

    public static final String texturaFelipe = "PONG---IntelliJ-main/textura_imagens/camisa_felipe.png";
    public static final String texturaJaorural = "PONG---IntelliJ-main/textura_imagens/camisa_joao_vitor.png";
    public static final String texturaJaourbano = "PONG---IntelliJ-main/textura_imagens/camisa_joao_otavio.png";
    public static final String texturaLuigi = "PONG---IntelliJ-main/textura_imagens/camisa_luigi.png";



    // Adicionando Variávies para o filtro da textura
    public int filtro = GL2.GL_LINEAR; ////GL_NEAREST ou GL_LINEAR
    public int wrap = GL2.GL_REPEAT;  //GL.GL_REPEAT ou GL.GL_CLAMP
    public int modo = GL2.GL_DECAL; ////GL.GL_MODULATE ou GL.GL_DECAL ou GL.GL_BLEND

    @Override
    public void init(GLAutoDrawable drawable){
        GL2 gl = drawable.getGL().getGL2();
        //SRU
        xMin = - 1920;
        xMax = 1920;
        yMin = - 1080;
        yMax = 1080;
        zMin = - 1000;
        zMax = 1000;

        reset();

        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.BOLD, 25));
        textRenderer1 = new TextRenderer(new Font("Comic Sans MS Negrito", Font.BOLD, 35));

        textura = new Textura(5);
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    public void reset(){
        placar = 0;
        vidas = 5;
        fase = 1;
        mode = GL2.GL_FILL;
    }

    public void continuarJogo(){
        placar = 0;
        vidas = 5;
        fase = 1;
    }

    public void borda(GL2 gl, GLUT glut){//Borda tela JOGO
        gl.glPushMatrix();
        gl.glColor3f(0,0,0);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(1901,820);
        gl.glVertex2f(1901,-1001);
        gl.glVertex2f(-1901,-1001);
        gl.glVertex2f(-1901,820);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void bordaInicio(GL2 gl, GLUT glut){ //Borda tela INICIO
        gl.glPushMatrix();
        gl.glColor3f(1,0,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(1550,500);
        gl.glVertex2f(1550,-150);
        gl.glVertex2f(-1550,-150);
        gl.glVertex2f(-1550,500);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void bordaPause(GL2 gl, GLUT glut){ //Borda tela PAUSE
        gl.glPushMatrix();
        gl.glColor3f(1,0.64f,0);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(720,900);
        gl.glVertex2f(720,450);
        gl.glVertex2f(-750,450);
        gl.glVertex2f(-750,900);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void sol(GL2 gl,GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(1900, 950, 0);
        gl.glColor3f(1,1,0);
        glut.glutSolidSphere(tamanhoS,500,500);
        gl.glPopMatrix();
    }
    public void estrada(GL2 gl,GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(0.5f,0.5f,0.5f);
        gl.glLineWidth(100f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-2000,-500);
        gl.glVertex2f(2000,-500);
        gl.glVertex2f(2000,-1100);
        gl.glVertex2f(-2000,-1100);
        gl.glVertex2f(-2000,-1100);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void faixaEstrada(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslatef(movimentaFaixa, 0, 10);
        gl.glColor3f(1, 1, 0);
        gl.glLineWidth(100f);
        for (int i = 0; i < 7; i++) {
            gl.glBegin(GL.GL_LINE_LOOP);
            gl.glVertex2f(-1920+(350*(i*2)), -750);
            gl.glVertex2f(-1570+(350*(i*2)), -750);
            gl.glEnd();
        }
        gl.glPopMatrix();
    }
    public void carro(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 50);
        gl.glColor3f(0, 0, 1);
        gl.glBegin(gl.GL_POLYGON);
        gl.glVertex2f(-0, -400);
        gl.glVertex2f(500, -400);
        gl.glVertex2f(500, -620);
        gl.glVertex2f(-0, -620);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void carroJanela(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 80);
        gl.glColor3f(1, 1, 1);
        gl.glBegin(gl.GL_POLYGON);
        gl.glVertex2f(420, -570);
        gl.glVertex2f(490, -570);
        gl.glVertex2f(490, -420);
        gl.glVertex2f(420, -420);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void carroRoda(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 80);
        gl.glTranslatef(100, -620, 0);
        gl.glColor3f(0,0,0);
        glut.glutSolidSphere(tamanhoR,500,500);
        gl.glPopMatrix();
    }
    public void carroRoda1(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 80);
        gl.glTranslatef(450, -620, 0);
        gl.glColor3f(0,0,0);
        glut.glutSolidSphere(tamanhoR,500,500);
        gl.glPopMatrix();
    }
    public void faixaCentro(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(1,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(10,810);
        gl.glVertex2f(10,-1000);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void faixaDireita(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(1,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(1200,500);
        gl.glVertex2f(1200,-750);
        gl.glVertex2f(1900,-750);
        gl.glVertex2f(1900,500);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void faixaEsquerda(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(1,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(-1200,500);
        gl.glVertex2f(-1200,-750);
        gl.glVertex2f(-1900,-750);
        gl.glVertex2f(-1900,500);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void campo(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(0,1,0);
        gl.glLineWidth(100f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(1900,820);
        gl.glVertex2f(1900,-1000);
        gl.glVertex2f(-1900,-1000);
        gl.glVertex2f(-1900,820);
        gl.glVertex2f(1900,820);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void barrinha(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(0,1,1);
        gl.glTranslatef(movimentoBarrinha,0,1);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-250, -850);
        gl.glVertex2f(250, -850);
        gl.glVertex2f(250, -900);
        gl.glVertex2f(-250, -900);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void bola0(GL2 gl,GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(transXBola, transYBola, 0);
        gl.glTranslatef(margemXdErro,margemYdErro,0);
        gl.glColor3f(1,0,0);
        glut.glutSolidSphere(tamanho,500,500);
        gl.glPopMatrix();
    }
    public void vidasDoJogo(GL2 gl, GLUT glut){
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glColor3f(1,0,0);
        if (vidas>=1) {
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            gl.glTranslatef(-1250, 965, 0);
            gl.glColor3f(1,0,0);
            glut.glutSolidSphere(tamanho,500,500);
            gl.glPopMatrix();
        }
        if (vidas>=2) {
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            gl.glTranslatef(-1050, 965, 0);
            gl.glColor3f(1,0,0);
            glut.glutSolidSphere(tamanho,500,500);
            gl.glPopMatrix();
        }
        if (vidas>=3) {
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            gl.glTranslatef(-850, 965, 0);
            gl.glColor3f(1,0,0);
            glut.glutSolidSphere(tamanho,500,500);
            gl.glPopMatrix();
        }
        if (vidas>=4) {
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            gl.glTranslatef(-650, 965, 0);
            gl.glColor3f(1,0,0);
            glut.glutSolidSphere(tamanho,500,500);
            gl.glPopMatrix();
        }
        if(vidas==5) {
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            gl.glTranslatef(-450, 965, 0);
            gl.glColor3f(1,0,0);
            glut.glutSolidSphere(tamanho,500,500);
            gl.glPopMatrix();
        }
    }
    public void obstaculoBolinha(GL2 gl,GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0);
        gl.glTranslatef(10, -115, 0);
        gl.glColor3f(1,1,1);
        tamanhoNormalDoObstaculo = tamanhoPrimarioDoObstaculo + (20 * (fase-1));
        glut.glutSolidSphere(tamanhoNormalDoObstaculo/2,(int)tamanhoNormalDoObstaculo,(int)tamanhoNormalDoObstaculo);
        gl.glPopMatrix();
    }
    public void vestiario(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(0.5f,0.5f,0.5f);
        gl.glLineWidth(100f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-1900,300);
        gl.glVertex2f(1900,300);
        gl.glVertex2f(1900,-1050);
        gl.glVertex2f(-1900,-1050);
        gl.glVertex2f(-1900,-1050);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glPopMatrix();
    }
    public void bordaVestiario(GL2 gl, GLUT glut){ //Borda tela INICIO
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 80);
        gl.glColor3f(0,0,0);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(1868,268);
        gl.glVertex2f(1868,-1020);
        gl.glVertex2f(-1868,-1020);
        gl.glVertex2f(-1868,268);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void faixaVestario1(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(0,0,80);
        gl.glColor3f(1,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(1000,230);
        gl.glVertex2f(1000,-980);
        gl.glVertex2f(1800,-980);
        gl.glVertex2f(1800,230);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void faixaVestario2(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(0,0,80);
        gl.glColor3f(0,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(100,230);
        gl.glVertex2f(100,-980);
        gl.glVertex2f(920,-980);
        gl.glVertex2f(920,230);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void faixaVestario3(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(0,0,80);
        gl.glColor3f(1,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(-800,230);
        gl.glVertex2f(-800,-980);
        gl.glVertex2f(20,-980);
        gl.glVertex2f(20,230);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void faixaVestario4(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(0,0,80);
        gl.glColor3f(0,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2f(-1800,230);
        gl.glVertex2f(-1800,-980);
        gl.glVertex2f(-880,-980);
        gl.glVertex2f(-880,230);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public void camiseta1(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glColor3f(0.5f, 0.5f, 0.5f);


        textura.setAutomatica(false);

        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);

        textura.gerarTextura(gl, texturaFelipe, 0);

        gl.glTranslatef(0, 0, 100);

        gl.glBegin(gl.GL_QUADS);

        gl.glTexCoord2f(0.0f, 1f);
        //A
        gl.glVertex2f(200, 100);
        gl.glTexCoord2f(1f, 1f);
        //B
        gl.glVertex2f(800, 100);
        gl.glTexCoord2f(1f, 0.0f);
        //C
        gl.glVertex2f(800, -820);
        gl.glTexCoord2f(0.0f, 0.0f);
        //D
        gl.glVertex2f(200, -820);
        gl.glEnd();

        textura.desabilitarTextura(gl,0);
        gl.glPopMatrix();
    }

    public void camiseta2(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glColor3f(0.5f, 0.5f, 0.5f);


        textura.setAutomatica(false);

        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);

        textura.gerarTextura(gl, texturaJaorural, 1);

        gl.glTranslatef(0, 0, 100);

        gl.glBegin(gl.GL_QUADS);

        gl.glTexCoord2f(0.0f, 1f);
        //A
        gl.glVertex2f(-700, 100);
        gl.glTexCoord2f(1f, 1f);
        //B
        gl.glVertex2f(-100, 100);
        gl.glTexCoord2f(1f, 0.0f);
        //C
        gl.glVertex2f(-100, -820);
        gl.glTexCoord2f(0.0f, 0.0f);
        //D
        gl.glVertex2f(-700, -820);
        gl.glEnd();

        textura.desabilitarTextura(gl,1);
        gl.glPopMatrix();
    }

    public void camiseta3(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glColor3f(0.5f, 0.5f, 0.5f);


        textura.setAutomatica(false);

        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);

        textura.gerarTextura(gl, texturaJaourbano, 2);

        gl.glTranslatef(0, 0, 100);

        gl.glBegin(gl.GL_QUADS);

        gl.glTexCoord2f(0.0f, 1f);
        //A
        gl.glVertex2f(1100, 100);
        gl.glTexCoord2f(1f, 1f);
        //B
        gl.glVertex2f(1700, 100);
        gl.glTexCoord2f(1f, 0.0f);
        //C
        gl.glVertex2f(1700, -820);
        gl.glTexCoord2f(0.0f, 0.0f);
        //D
        gl.glVertex2f(1100, -820);
        gl.glEnd();

        textura.desabilitarTextura(gl,2);
        gl.glPopMatrix();
    }

    public void camiseta4(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glColor3f(0.5f, 0.5f, 0.5f);


        textura.setAutomatica(false);

        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);

        textura.gerarTextura(gl, texturaLuigi, 3);

        gl.glTranslatef(0, 0, 100);

        gl.glBegin(gl.GL_QUADS);

        gl.glTexCoord2f(0.0f, 1f);
        //A
        gl.glVertex2f(-1650, 100);
        gl.glTexCoord2f(1f, 1f);
        //B
        gl.glVertex2f(-1050, 100);
        gl.glTexCoord2f(1f, 0.0f);
        //C
        gl.glVertex2f(-1050, -820);
        gl.glTexCoord2f(0.0f, 0.0f);
        //D
        gl.glVertex2f(-1650, -820);
        gl.glEnd();

        textura.desabilitarTextura(gl,3);
        gl.glPopMatrix();
    }

    public void iluminacaoAmbiente(GL2 gl){
        float luzAmbiente[] = {0.6f, 0.6f, 0.6f, 1.0f};
        float posicaoLuz[] = {-50.0f, 0.0f, 100.0f, 1.0f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }
    public void ligarLuz(GL2 gl) {
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glShadeModel(GL2.GL_SMOOTH);
    }
    public void restartPosicaoDaBolinha(){
        if (fase>=2){
            transYBola = tamanhoNormalDoObstaculo+ tamanho;
            superiorYBola = transYBola + tamanho;
            inferiorYBola = transYBola - tamanho;
            taxaAttY = - taxaAttY;

            transXBola = 0;
            direitaXBola = tamanho;

            margemXdErro=0;
            margemYdErro=0;

        }else{
            transYBola = 0;
            superiorYBola = tamanho;
            inferiorYBola = - tamanho;
            taxaAttY = - taxaAttY;

            transXBola = 0;
            direitaXBola = tamanho;

            margemXdErro=0;
            margemYdErro=0;
        }
    }
    public void restartDoJogo(){
        restartPosicaoDaBolinha();
        taxaAttY = velocidadeY;
        taxaAttX = velocidadeX;

        movimentoBarrinha=0;
        direitaBarrinha = 250;
        esquerdaBarrinha =direitaBarrinha - 500;

        fimDoJogo=false;
        vidas = 5;
        placar = 0;
        fase= 1;
    }
    public void colisaoDoObstaculo(){
        //criando range onde será analisado margem de erro para colisão 100% com o obstaculo
        if(direitaXBola >= -tamanhoNormalDoObstaculo*3 && esquerdaXBola  <= tamanhoNormalDoObstaculo*3
                && inferiorYBola >= -tamanhoNormalDoObstaculo*3 && superiorYBola  <= tamanhoNormalDoObstaculo*3){
            faixaDeAlcanceObstaculo= true;
        }else{faixaDeAlcanceObstaculo=false;}

        if(faixaDeAlcanceObstaculo){
            if(taxaAttX>= 0){
                float pixeisAteObstaculo = (-tamanhoNormalDoObstaculo/2) - direitaXBola;
                float restoMargemDeErro = pixeisAteObstaculo % taxaAttX;
                if(restoMargemDeErro != 0){
                    margemXdErro += 1;
                    direitaXBola += 1;
                    esquerdaXBola = direitaXBola - 170;
                }
            }else{
                float pixeisAteObstaculo = (tamanhoNormalDoObstaculo/2) - esquerdaXBola;
                float restoMargemDeErro = pixeisAteObstaculo % taxaAttX;
                if(restoMargemDeErro != 0){
                    margemXdErro -= 1;
                    direitaXBola -= 1;
                    esquerdaXBola = direitaXBola - 170;
                }
            }
            if(taxaAttY>=0){
                float pixeisAteObstaculo = (-tamanhoNormalDoObstaculo/2) - superiorYBola;
                float restoMargemDeErro = pixeisAteObstaculo % taxaAttY;
                if(restoMargemDeErro != 0){
                    margemYdErro += 1;
                    superiorYBola += 1;
                    inferiorYBola = superiorYBola - 170;
                }
            }else{
                float pixeisAteBarra = (tamanhoNormalDoObstaculo/2) - inferiorYBola;
                float restoMargemDeErro = pixeisAteBarra % taxaAttY;
                if(restoMargemDeErro != 0){
                    margemYdErro -= 1;
                    inferiorYBola -= 1;
                    superiorYBola = inferiorYBola + 170;
                }
            }
        }

        if(direitaXBola >= -tamanhoNormalDoObstaculo/2 && esquerdaXBola  <= tamanhoNormalDoObstaculo/2){
            if (inferiorYBola  <= tamanhoNormalDoObstaculo/2 && inferiorYBola  >= tamanhoNormalDoObstaculo/2 - (tamanhoNormalDoObstaculo/4) )// parte superior
            {
                //taxa crescente, eixo y
                taxaAttY = velocidadeY + (5 * (fase-1));

                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(11);// valor aleatorio de acrescimo para evitar que obstaculo entre em loop na parte superior

                if (taxaAttX<0){
                    taxaAttX = -velocidadeX - (5 * (fase-1));
                    taxaAttX -= aleatorizaAcressimoX;
                } else {
                    taxaAttX = velocidadeX + (5 * (fase - 1));
                    taxaAttX += aleatorizaAcressimoX;
                }
            } else if (superiorYBola >= - tamanhoNormalDoObstaculo/2 && superiorYBola <= - tamanhoNormalDoObstaculo/2 + (tamanhoNormalDoObstaculo/4))// parte inferior
            {
                //taxa decrescente, eixo y
                taxaAttY = -velocidadeY - (5 * (fase-1));

                if (taxaAttX<0){
                    taxaAttX = -velocidadeX - (5 * (fase-1));
                } else {
                    taxaAttX = velocidadeX + (5 * (fase - 1));
                }
            } else if (inferiorYBola <= tamanhoNormalDoObstaculo/2 && superiorYBola >= -tamanhoNormalDoObstaculo/2)
            {
                taxaAttX = - taxaAttX;
            }
        }
    }
    public void movimentarBola0(){
        if (dandoPlay && vidas!=0){
            transYBola+= taxaAttY; //inicia a movimentacao da bolinha no eixo Y
            superiorYBola =transYBola + tamanho + margemYdErro; //armazena a extremidade Y com base na translacao e tamanho do objeto
            inferiorYBola =transYBola - tamanho + margemYdErro;

            transXBola+= taxaAttX; //inicia a movimentacao da bolinha no eixo X
            direitaXBola =transXBola + tamanho + margemXdErro; //armazena a extremidade X com base na translacao e tamanho do objeto
            esquerdaXBola= transXBola - tamanho + margemXdErro;

            if(taxaAttX>= 0){
                float pixeisAteParede = janela - direitaXBola;
                float restoMargemDeErro = pixeisAteParede % taxaAttX;
                if(restoMargemDeErro != 0){
                    margemXdErro += 1;
                    direitaXBola += 1;
                    esquerdaXBola = direitaXBola - 170;
                }
            }else{
                float pixeisAteParede = - janela + esquerdaXBola;
                float restoMargemDeErro = pixeisAteParede % taxaAttX;
                if(restoMargemDeErro != 0){
                    margemXdErro -= 1;
                    direitaXBola -= 1;
                    esquerdaXBola = direitaXBola - 170;
                }
            }
            if(taxaAttY>=0){
                float pixeisAteParede = janela - superiorYBola;
                float restoMargemDeErro = pixeisAteParede % taxaAttY;
                if(restoMargemDeErro != 0){
                    margemYdErro += 1;
                    superiorYBola += 1;
                    inferiorYBola = superiorYBola - 170;
                }
            }else{
                float pixeisAteBarra = (- 800) - inferiorYBola;

                float restoMargemDeErro = pixeisAteBarra % taxaAttY;
                if(restoMargemDeErro != 0){
                    margemYdErro += 1;
                    inferiorYBola += 1;
                    superiorYBola = inferiorYBola + 170;
                }
            }
            if(direitaXBola >= janela ){
                taxaAttX = - taxaAttX;
            }else if(esquerdaXBola <= (-janela)){
                taxaAttX = - taxaAttX;
            }if(superiorYBola >= 800f){
                taxaAttY = - taxaAttY;
            }else if(inferiorYBola <= -1100f ){
                vidas-=1;
                restartPosicaoDaBolinha();
            }
        }
    }
    public void movimentacaoDaBarrinha(){
        if(direitaXBola >= esquerdaBarrinha && esquerdaXBola <= direitaBarrinha){
            if (inferiorYBola <= -850f && inferiorYBola >= 800f){

                placar+=50;
                fase = (placar/200)+1;

                taxaAttY = velocidadeY + (5 * (fase-1));

                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(6);
                // bolinha continua o curso do eixo x que estava realizando
                if (taxaAttX<0){
                    taxaAttX = -velocidadeX - (5 * (fase-1)); //aumenta a velocidade dependendo da fase
                    taxaAttX -= aleatorizaAcressimoX; // acressimo aleatorio para evitar repetição de colisão
                }else{
                    taxaAttX = velocidadeX + (5 * (fase - 1)); //aumenta a velocidade dependendo da fase
                    taxaAttX += aleatorizaAcressimoX; //acressimo aleatorio para evitar repetição de colisão
                }
            }else if (inferiorYBola <= -850f && superiorYBola >= -900f){

                placar+=50;

                fase = (placar/200)+1;

                taxaAttY = velocidadeY + (5 * (fase-1));
                //se bater na lateral a bolinha vai para o lado oposto em relação ao eixo X
                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(6);
                if (taxaAttX < 0){
                    taxaAttX = velocidadeX + (5 * (fase-1) ); //aumenta a velocidade dependendo da fase
                    taxaAttX += aleatorizaAcressimoX; // acressimo aleatorio para evitar repetição de colisão
                }else{
                    taxaAttX = -velocidadeX - (5 * (fase - 1)); //aumenta a velocidade dependendo da fase
                    taxaAttX -= aleatorizaAcressimoX; // acressimo aleatorio para evitar repetição de colisão
                }
            }
        }
    }

    @Override
    public void display(GLAutoDrawable drawable){
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT();
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);

        iluminacaoAmbiente(gl);
        ligarLuz(gl);

        if(Menu){
            bordaInicio(gl,glut);
            sol(gl,glut);
            estrada(gl, glut);
            carro(gl,glut);
            carroJanela(gl,glut);
            carroRoda(gl,glut);
            carroRoda1(gl,glut);
            faixaEstrada(gl, glut);
            movimentaFaixa -= 20;
            if (movimentaFaixa <= -650){movimentaFaixa =0;}
            desenhaTexto(gl, 700, 690, Color.BLACK, "Seja bem-vindo ao nosso jogo - PONG!");
            desenhaTexto(gl, 880, 650, Color.BLACK, "REGRAS:");
            desenhaTexto(gl, 230, 620, Color.BLACK, "Para jogar use as teclas da seta esquerda (para andar a esquerda) e a seta direita (para andar a direita) do teclado!");
            desenhaTexto(gl, 720, 590, Color.BLACK, "Para PAUSAR o jogo aperte a tecla P!");
            desenhaTexto(gl, 680, 559, Color.BLACK, "Para começar o jogo aperte a tecla ENTER!");
            desenhaTexto(gl, 720, 530, Color.BLACK, "Para sair do jogo aperte a tecla ESC!");

        }else if(dandoPlay && vidas != 0){
            bola0(gl,glut);
            vidasDoJogo(gl,glut);
            borda(gl,glut);
            faixaCentro(gl, glut);
            faixaDireita(gl,glut);
            faixaEsquerda(gl,glut);
            campo(gl,glut);
            barrinha(gl, glut);
            movimentacaoDaBarrinha();
            desenhaTexto1(gl, 30, 1000, Color.BLACK, "LEVEL " + fase);
            desenhaTexto1(gl, 1600, 1000, Color.BLACK, "SCORE " + placar);

            if (vidas!= 0) {
                bola0(gl, glut);
                movimentarBola0();

                barrinha(gl, glut);
                movimentacaoDaBarrinha();

                if (fase >= 2) {
                    obstaculoBolinha(gl, glut);
                    colisaoDoObstaculo();
                }
            }

        }else if(pause){
            bordaPause(gl,glut);
            vestiario(gl,glut);
            bordaVestiario(gl,glut);
            faixaVestario1(gl,glut);
            faixaVestario2(gl,glut);
            faixaVestario3(gl,glut);
            faixaVestario4(gl,glut);
            camiseta1(gl,glut);
            camiseta2(gl,glut);
            camiseta3(gl,glut);
            camiseta4(gl,glut);
            desenhaTexto(gl, 820, 900, Color.BLACK, "O jogo está Pausado!");
            desenhaTexto(gl, 710, 830, Color.BLACK, "Aperte a letra P para continuar o jogo!");
        } else if (vidas == 0){
            fimDoJogo = true;
            desenhaTexto(gl, 890, 650, Color.red,"GAME OVER");
            desenhaTexto(gl, 730, 600, Color.red ,"Aperte a tecla Espaço para reiniciar !!");
            desenhaTexto(gl, 810, 550, Color.red ,"A sua pontuação foi: "+placar);
        }
        gl.glFlush();
    }

    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }

    public void desenhaTexto1(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textRenderer1.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer1.setColor(cor);
        textRenderer1.draw(frase, xPosicao, yPosicao);
        textRenderer1.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //ler a matriz identidade

        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        System.out.println("Reshape: " + width + ", " + height);
    }
    @Override
    public void dispose(GLAutoDrawable drawable){}
}