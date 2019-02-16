/*
Autor: Henrique Fernandes Viana Mendes
Data:  15/02/2019 - 23/02/2019
*/
import java.util.Scanner;
import java.io.*;

public class TesteTecnico{

   public static void main(String[] args){
      try{
         encode();
         decode();
      }catch(Exception e){
         System.out.println(e);
      }
   }//end main

   public static void encode() throws Exception{
      organizadorEncode("encode-2.in","encode-2.out");
      organizadorEncode("encode-3.in","encode-3.out");
      organizadorEncode("encode-4.in","encode-4.out");
   }  
   
   public static void decode()throws Exception{
      organizadorDecode("decode-5.in", "decode-5.out");
      organizadorDecode("decode-6.in", "decode-6.out");
      organizadorDecode("decode-7.in", "decode-7.out");
      //organizadorDecode("encode-3.out", "teste-2.out");
   }


   //metodo para conseguir resolver o problema para cada arquivo decode(mandado como parametro)
   public static void organizadorDecode(String nome_leitura, String nome_escrita)throws Exception{
         String str = "";
         if(nome_leitura.equals("decode-6.in")){
            str = ler2(nome_leitura);
         }else{ str = ler(nome_leitura);}                         //ler a string de um arquivo
         int pos        = lerPos(nome_leitura);                      //ler o valor int do mesmo arquivo
         String[] strm  = new String[str.length()];                  //declarar um vetor de strings
         strm           = preencher(strm, str.length());                           //preencher a matriz com ""
         strm           = cria(str, strm, str.length());             //criar a matriz final desejada
         escreverDecode(strm, pos, nome_escrita);                    //ler a String na posicao desejada e escreve-la em um novo arquivo
   }//end organizador

   //metodo para conseguir resolver o problema para cada arquivo encode(mandado como parametro)
   public static void organizadorEncode(String nome_leitura, String nome_escrita)throws Exception{

      String   str      = lerString(nome_leitura);                     //ler a string de um arquivo
      char[][] chmatriz = new char[str.length()][str.length()];        //declarar uma matriz de caracteres 
      String[] matriz   = completaMatriz(chmatriz, str.length(), str); //inicializar a matriz e transforma-la em um vetor de Strings
      matriz            = selectionSort(matriz, str.length());         //ordenar o vetor em ordem alfabetica
      escreverEncode(matriz, str, nome_escrita);                       //escrever em um arquivo a resposta desejada

   }//end organizador

   //metodo para ler a string do arquivo decode.in
   public static String ler(String path)throws Exception{
      RandomAccessFile raf = new RandomAccessFile(path, "r");
      raf.seek(2);
      String str = "";
      int tamString = (int)raf.length()-8;
      for(byte i = 0; i < tamString; i++){
         str += (char)raf.read();
      }
      raf.close();
      return str;
   }//end ler


   //metodo para ler a string do arquivo decode.in
   public static String ler2(String path)throws Exception{
      RandomAccessFile raf = new RandomAccessFile(path, "r");
      raf.seek(2);
      String str = "";
      int tamString = (int)raf.length()-7;
      for(byte i = 0; i < tamString; i++){
         str += (char)raf.read();
      }
      raf.close();
      return str;
   }//end ler


   //metodo para ler o valor int nos arquivos decode.in
   public static int lerPos(String path) throws Exception{
      RandomAccessFile raf = new RandomAccessFile(path, "r");
      int pos = (int)raf.length()-3;
      raf.seek(pos);
      String p = "";
      p += (char)raf.read();
      int a = Integer.parseInt(p);
      raf.close();
      return a;
   }//end lerPos

   //Metodo para ler a string no arquivo
   public static String lerString(String path) throws Exception{

      BufferedReader BR = new BufferedReader(new FileReader(path));    //abrir o arquivo desejado
      String str = BR.readLine();                                      //ler a string existente no arquivo
      BR.close();                                                      //fechar o arquivo

   return str;                                                         //retorna a string lida
   }//end lerString


   //metodo para escrever no arquivo a string na posicao desejada
   public static void escreverDecode(String[] strm, int pos, String path)throws Exception{
      BufferedWriter BW = new BufferedWriter( new FileWriter(path)); //criar o arquivo
      String str        = strm[pos];                                 //pegar a string desejada
      BW.write(str);                                                 //escrever no arquivo
      BW.close();                                                    //fechar o arquivo
   }//end escreverDecode

   //metodo para escrever no arquivo uma string contendo a ultima letra de cada string no vetor ordenado + o numero da linha contendo a palavra original
   public static void escreverEncode(String[] matriz, String str, String path)throws Exception{
      String   str2     = novaString(matriz);                        //conseguir a string com as ultimas letras
      byte     resp     = posicaoDoOriginal(matriz, str);            //conseguir o numero da linha da palavra original
      BufferedWriter BW = new BufferedWriter(new FileWriter(path));  //criar o arquivo
      BW.write("['" + str2 + "', " + resp + "]\n");                   //escrevendo a resposta no arquivo criado
      BW.close();                                                    //fechar o arquivo
   }//end escreverEncode
   
   
   //metodo para completar a matriz de char, com os caracteres desejados na ordem certa, e chamada para o metodo para transformar a matriz em um vetor
   public static String[] completaMatriz(char[][] matriz, int tam, String str){
         int temp = 0;
         for(byte i = 0; i < tam; i++){
            for(byte j = 0; j < tam; j++){               
                  matriz[i][j] = str.charAt(temp);
                  temp = (temp + 1) % tam;
            }temp = (temp + (tam-1)) % tam;
         }//end for 
         return TranformaMatriz(matriz, tam, new String[tam]);
   }//end completaMatriz
   

   //metodo para transformar a matriz de char em um vetor de String
   public static String[] TranformaMatriz(char[][] matriz, int tam, String[] strm){
      for(byte i = 0; i < tam; i++){                      //
         strm[i] = "";                                    // Inicializando o vetor vazio(impedir problemas futuros com um vetor com null)
      }//end for                                          //
      for(byte i = 0; i < matriz.length; i++){
         for(byte j = 0; j < matriz[i].length; j++){
             strm[i] += matriz[i][j]; 
         }//end for
      }//end for
      return strm;
   }//end TransformaMatriz
   

   //metodo SelectionSort para ordenar o vetor em ordem alfabética
   public static String[] selectionSort(String[] matriz, int tam) {  
      for (int i = 0; i < tam-1; i++){ 
          int actual = i; 
          for (int j = i+1; j < tam; j++)
            if ((matriz[j]).compareTo((matriz[actual])) < 0) 
              actual = j;  
              matriz = swap(actual, i, matriz);  
      }//end for 
      return matriz;
   }//end SelectionSort
   

   //metodo SWAP para fazer a troca de Strings no vetor
   public static String[] swap(int a, int b, String[] matriz){ 
      String tmp = matriz[a]; 
      matriz[a] = matriz[b]; 
      matriz[b] = tmp;
      return matriz; 
   }//end Swap 
   

   //metodo para retornar a string contendo os ultimos caracteres de cada string existente no vetor
   public static String novaString(String[] strm){
      String str = "";
      for(byte i = 0; i < strm.length; i++){                 //for percorrendo o vetor completo
            str += strm[i].charAt(strm.length-1);            //nova string adicionando os ultimos caracteres
      }//end for
      return str;                                            //retorna a nova string
   }//end novaString


   //metodo para retornar a posicao no vetor que contem a string original
   public static byte posicaoDoOriginal(String[] strm, String str){
      byte i = -1;
      for(byte a = 0; (a < strm.length || i == a) ; a++){     //for que para quando chegar no fim do vetor ou quando achar a posicao desejada
            if(strm[a].equals(str)){                          //if para comparar as strings com a string original
                  i = a;
            }//end if
      }//end for
      return i;                                               //retorna a posicao da string desejada
   }//end posicaoDoOriginal


   //metodo para preencher a matriz com "" para evitar problemas com o null
   public static String[] preencher(String[] strm, int tam){

      for(byte i = 0; i < tam; i++){
         strm[i] = "";
      }
   return strm;                                                //retorna a nova matriz
   }//end preencher


   //metodo para criar a matriz de Strings a partir da string contida nos arquivos decode.in
   public static String[] cria(String str, String[] strm, int tam){
      for(byte j = 0; j < tam; j++){
         for(byte i = 0; i < tam; i++){
            strm[i] = str.charAt(i) + strm[i];                //escrevendo os caracteres na amtriz
         }
         strm = selectionSort(strm, tam);                     //ordenando a matriz em ordem alfabetica apos a insercao de todos os elementos em uma "coluna"
      }   
   return strm;                                               //retorna a nova matriz
   }//end cria
   

}//end class