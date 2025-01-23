package ifmt.cba.util;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;

import ifmt.cba.execucao.AppGerarDadosBD;

public class ExecutaServidor {
    
    public static void main(String[] args) throws IOException {
        System.out.println("Gerando base de dados....");
        AppGerarDadosBD.gerarBaseDados();

        System.out.println("Criando ServidorHTTP....");
        HttpServer servidor = ServidorHTTP.getServerHTTP();
        System.out.println("-------------------------------------------------------------------r");
        System.out.println("Web Services Restaurante - Presseione qualquer tecla para encerrar");
        // espera uma tecla se pressionada
        System.in.read();
        servidor.shutdownNow();
    }
}
