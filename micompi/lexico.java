package micompi;

import java.io.RandomAccessFile;

class lexico {
    nodo cabeza = null, p;
    int columna, valorMT, numRenglon = 1, estado = 0, caracter = 0;
    boolean errorEncontrado = false;
    String lexema = "";
    String archivo = "C:\\Proyectos\\Compi\\Ejercicio.compi";


    int matriz[][]={
        /*          0      1     2     3     4     5     6     7     8     9    10    11    12    13    14    15    16    17    18    19    20    21    22    23    24    25    26    27    28  */
        /*          l      @     _     d     +     -     *     /     ^     <     >     =     !     &     |     (     )     {     }     ,     ;     "    eb   tab    nl     .   eof    oc    rt  */
        /* 0 */{    1,     1,    1,    2,  103,  104,  105,    5,  107,    8,    9,   10,   11,   12,   13,  117,  118,  119,  120,  124,  125,   14,    0,    0,    0,  505,    0,  505,    0},
        /* 1 */{    1,     1,    1,    1,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,  100}, 
        /* 2 */{  101,   101,  101,    2,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,    3,  101,  101,  101},
        /* 3 */{  500,   500,  500,    4,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500,  500},
        /* 4 */{  102,   102,  102,    4,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,  102}, 
        /* 5 */{  106,   106,  106,  106,  106,  106,    6,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106,  106},
        /* 6 */{    6,     6,    6,    6,    6,    6,    7,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,  501,    6,    6}, 
        /* 7 */{    6,     6,    6,    6,    6,    6,    6,    0,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,  501,    6,    6},
        /* 8 */{  108,   108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  110,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,  108},
        /* 9 */{  109,   109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  111,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109,  109},
        /* 10 */{ 123,   123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  112,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123,  123},
        /* 11 */{ 116,   116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  113,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116,  116},
        /* 12 */{ 502,   502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  114,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502,  502},
        /* 13 */{ 503,   503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  115,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503,  503},
        /* 14 */{  14,    14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,  122,   14,   14,  504,   14,  504,   14,  504}
    };

    String palReservadas[][]={
        //      0        1
        /*0*/{"break", "200"},
        /*1*/{"if", "201"},
        /*2*/{"else", "202"},
        /*3*/{"main", "203"},
        /*4*/{"while", "204"},
        /*5*/{"goto", "205"},
        /*6*/{"print", "206"},
        /*7*/{"new", "207"},
        /*8*/{"float", "208"},
        /*9*/{"int", "209"},
        /*10*/{"false", "210"},
        /*11*/{"true", "211"},
        /*12*/{"string", "212"},
        /*13*/{"bool", "213"},
        /*14*/{"getvalue", "214"}
        
    };

    String errores[][]={
        //      0                                 1
        /*0*/{"Se espera un digito",            "500"},
        /*1*/{"Se espera cierre de comentario", "501"},
        /*2*/{"Se espera un &",                 "502"},
        /*3*/{"Se espera un |",                 "503"},
        /*4*/{"Se espera cierre de cadena",     "504"},
        /*5*/{"Caracter no valido",             "505"} 
    };

    RandomAccessFile file = null;

    public lexico(){
        try {
            file = new RandomAccessFile(archivo, "r");
            while (caracter != -1) {
                caracter = file.read();
                if (Character.isLetter(((char) caracter))) {
                    columna = 0;
                } else if (Character.isDigit(((char) caracter))) {
                    columna = 3;
                } else {
                    switch ((char) caracter) {
                        case '@':
                            columna = 1;
                            break;
                        case '_':
                            columna = 2;
                            break;
                        case '+':
                            columna = 4;
                            break;
                        case '-':
                            columna = 5;
                            break;
                        case '*':
                            columna = 6;
                            break;
                        case '/':
                            columna = 7;
                            break;
                        case '^':
                            columna = 8;
                            break;
                        case '<':
                            columna = 9;
                            break;
                        case '>':
                            columna = 10;
                            break;
                        case '=':
                            columna = 11;
                            break;
                        case '!':
                            columna = 12;
                            break;
                        case '&':
                            columna = 13;
                            break;
                        case '|':
                            columna = 14;
                            break;
                        case '(':
                            columna = 15;
                            break;
                        case ')':
                            columna = 16;
                            break;
                        case '{':
                            columna = 17;
                            break;
                        case '}':
                            columna = 18;
                            break;
                        case ',':
                            columna = 19;
                            break;
                        case ';':
                            columna = 20;
                            break;
                        case '"':
                            columna = 21;
                            break;
                        case ' ':
                            columna = 22;
                            break;
                        case 9:
                            columna = 23;
                            break;
                        case 10:
                        {
                            columna = 24;
                            numRenglon = numRenglon + 1;
                        }
                        break;
                        case 13:
                            columna = 28;
                            break;
                        case '.':
                            columna = 25;
                            break;
                        default:
                            if (caracter==-1) {
                                columna=26;
                            }else{
                              columna = 27;  
                            }
                            break;
                    }
                }
                valorMT = matriz[estado][columna];
                if (valorMT<100) {
                    estado=valorMT;
                    if (estado==0){
                        lexema="";
                    } else {
                        lexema=lexema+(char) caracter;
                    }
                } else if (valorMT>=100 && valorMT<500){
                    if  (valorMT==100) {
                        validarPalabraReservada();
                    }
                    if (valorMT==100|| valorMT==101||valorMT==102||valorMT==106||valorMT==123||valorMT==108||
                    valorMT==109||valorMT==116||valorMT>=200){
                        file.seek(file.getFilePointer()-1);
                    }else{
                        lexema=lexema+(char)caracter;                        
                    }
                    insertarNodo();
                    estado = 0;
                    lexema = "";
                } else {
                    imprimirMensajeError();
                    break;
                }
                
            }
            imprimirNodos();
            sintaxis();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                if (file !=null) {
                file.close(); 
            }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    

    private void validarPalabraReservada() {
        for(String[] palReservada : palReservadas){
            if(lexema.equals(palReservada[0])){
                valorMT = Integer.valueOf(palReservada[1]);
            }
        }
    }

    private void imprimirNodos(){
        p=cabeza;        
        while(p!=null){
            System.out.println(p.lexema + " " + p.token + " " + p.renglon);
            p = p.sig;
        }
    }

    private void imprimirMensajeError(){
        if ((caracter !=-1 && valorMT>=500)) {
            for (String[] error : errores) {
                if (valorMT == Integer.valueOf(error[1])) {
                    System.out.println("El error encontrado es:" + error[0] + ", error " + valorMT + " caracter " + caracter + " en el renglon "+numRenglon);
                }
            }
            errorEncontrado=true;
        }else if ( (caracter ==-1)&& valorMT>=500) {
            for (String[] error : errores) {
                if (valorMT == Integer.valueOf(error[1])) {
                    try {
                        file.seek(file.getFilePointer()-1);
                        caracter = file.read();
                    } catch (Exception e) {
                    }
                    System.out.println("El error encontrado es:" + error[0]+ ", error " + valorMT + " caracter "+ caracter + " en el renglon " + numRenglon);
                }
            }
            errorEncontrado=true;
        }
    }

    private void insertarNodo(){
        nodo nodo = new nodo(lexema, valorMT, numRenglon);
        if (cabeza == null) {
            cabeza = nodo;
            p = cabeza;
        } else {
            p.sig = nodo;
            p = nodo;
        }
    }

    private void sintaxis() {
        p = cabeza;
        while (p!=null) {
            if (p!= null && p.token == 203) {
                p = p.sig;
                if (p!= null && p.token == 117) {
                    p = p.sig;
                    if (p!= null && p.token == 118) {
                        p = p.sig;
                        if (p!= null && p.token == 119) {
                            p = p.sig;
                            if (p.token == 201 ||p.token == 204 || p.token == 205 || 
                                p.token == 206 || p.token == 214) 
                            {                               
                                 statements();
                            } else {
                                variables();
                                statements();
                            }
                            if (p.token != 120) {
                                System.out.println("Se espera un }");
                                System.exit(0);
                            }else{
                                p=p.sig;
                            }
                        } else {
                            System.out.println("Se espera un {");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("Se espera )");
                        System.exit(0);
                    }
                }else{
                    System.out.println("Se espera ( ");
                    System.exit(0);
                }
            } else {
                System.out.println("Se espera la palabra main");
                System.exit(0);
            }
        }
        System.out.println("Analisis Sintactico Terminado");
    }

    private void statements() {
        if (p != null) {
        switch (p.token){
            case 201:
                p=p.sig;
                if(p.token==117){
                    p=p.sig;
                    expCondicional();
                    if(p.token==118){
                        p=p.sig;
                        if(p.token==119){
                            p=p.sig;
                            statements();
                            if(p.token==120){
                                p=p.sig;
                                statements();
                                if(p.token==202){
                                    p=p.sig;
                                    if(p.token==119){
                                        p=p.sig;
                                        statements();
                                        if(p.token==120){
                                            p=p.sig;
                                            statements();
                                        }else{
                                            System.out.println("Se espera un }");
                                            System.exit(0);
                                        }
                                    }else{
                                        System.out.println("Se espera un {");
                                        System.exit(0);
                                    }
                                }
                            }else{
                                System.out.println("Se espera un }");
                                System.exit(0);
                            }
                        }else{
                            System.out.println("Se espera un {");
                            System.exit(0);
                        }
                    }else{
                        System.out.println("Se espera un )");
                        System.exit(0);
                    }
                }else{
                    System.out.println("Se espera un (");
                    System.exit(0);
                }
                break;
            case 204:
                p=p.sig;
                if(p.token==117){
                    p=p.sig;
                    expCondicional();
                    if(p.token==118){
                        p=p.sig;
                        if(p.token==119){
                            p=p.sig;
                            statements();
                            if(p.token==120){
                                p=p.sig;
                                statements();
                            }else{
                                System.out.println("Se espera un }");
                                System.exit(0);
                            }
                        }else{
                            System.out.println("Se espera un {");
                            System.exit(0);
                        }
                    }else{
                        System.out.println("Se espera un )");
                        System.exit(0);
                    }
                }else{
                    System.out.println("Se espera un (");
                    System.exit(0);
                }
                break;
            case 206:
                p=p.sig;
                if(p.token==117){
                    p=p.sig;
                    if(p.token==100 || p.token == 122){
                        p=p.sig;
                        if(p.token==124){
                            idRecursivo();
                        }else{
                            if(p.token==118){
                                p=p.sig;
                                if(p.token==125){
                                    p=p.sig;
                                    statements();
                                }else{
                                    System.out.println("Se espera un ;");
                                    System.exit(0);
                                }
                            }else{
                                System.out.println("Se espera un )");
                                System.exit(0);
                            }
                        } 
                    }else{
                        System.out.println("Se espera un id o una cadena de texto");
                        System.exit(0);
                    }
                }else{
                    System.out.println("Se espera un (");
                    System.exit(0);
                }
                break;
            case 214:
                p=p.sig;
                if(p.token==117){
                    p=p.sig;
                    if(p.token==118){
                        p=p.sig;
                        if (p.token == 125) {
                            p=p.sig;
                            statements();
                        } else {
                            System.out.println("Se espera un ;");
                            System.exit(0);
                        }
                    }else{
                        System.out.println("Se espera un )");
                        System.exit(0);
                    }
                }else{
                    System.out.println("Se espera un (");
                    System.exit(0);
                }
                
                break;
            case 100:
                p=p.sig;
                if(p.token==123){
                    p=p.sig;
                    expSimple();
                    if(p.token==125){
                        p=p.sig;
                        statements();
                    }else if(p.token==210 || p.token==211){
                        p=p.sig;
                        if (p.token != 125) {
                            System.out.println("Se espera un ;");
                            System.exit(0);
                        } else {
                            p = p.sig;
                            statements();
                        }
                    }else if(p.token==103 || p.token == 104){
                        p = p.sig;
                        expSimple();
                        if (p.token == 125) {
                            p = p.sig;
                            statements();
                        } else {
                            System.out.println("Se espera un ;");
                            System.exit(0);
                        }
                    }else{
                        System.out.println("Se espera un ;");
                        System.exit(0);
                    }
                    
                }else{
                    System.out.println("Se espera un =");
                    System.exit(0);
                }
                break;
            
            }
        } else {
            System.out.println("Se espera una }");
            System.exit(0);
        }
    }

    private void expCondicional(){
        expSimple();
        operadorRelacional();
        expSimple();
    }

    private void operadorAditivo() {
        switch (p.token) {
            case 104:
                p = p.sig;
                break;
            case 103:
                p = p.sig;
                break;
            case 115:
               p=p.sig;
               break;

            default:
                System.out.println("Se espera un operador");
                System.exit(0);
                break;
        }
    }

    private void operadorRelacional() {
        switch (p.token) {
            case 109:
                p = p.sig;
                break;
            case 108:
                p = p.sig;
                break;
            case 111:
                p = p.sig;
                break;
            case 110:
                p = p.sig;
                break;
            case 112:
                p = p.sig;
                break;
            case 113:
                p = p.sig;
                break;
            
            default:
            System.out.println("Falta un operador relacional < > <= >= == !=");
            System.exit(0);    
            break;
        }
    }

    private void operadorMult() {
        switch (p.token) {
            case 105:
                p = p.sig;
                break;
            case 106:
                p = p.sig;
                break;
            case 114:
                p = p.sig;
                break;
           
        }
    }

    private void signo() {
        switch (p.token) {
            case 104:
                p = p.sig;
                break;
        
            default:
                p = p.sig;
                break;
        }
    }

    private void idRecursivo(){
        if(p!=null && p.token==124){
            p = p.sig;
            if (p!= null && p.token == 100) {
                p = p.sig; 
                if(p!=null && p.token == 124){
                idRecursivo();
                }  
            } else {
                System.out.println("Se espera una coma"); 
                System.exit(0);
            }
        } 
    }

    private void expSimple() {
        if (p.token == 103 || p.token == 104) {
            signo();
        }
        termino();
        if (p.token == 103 || p.token == 104) {
            operadorAditivo();
            termino();
        }else if(p.token==100 || p.token==101){
            System.out.println("Se espera un Operador");
            System.exit(0);
        }
    }

    private void termino() {        
        factor();
        if (p.token == 105 || p.token == 106) {
            operadorMult();
            factor();
        }
    }   

    private void factor(){
        switch (p.token) {
            case 100:
                p = p.sig;
                break;

            case 101:
                p = p.sig;
                break;
            
            case 102:
                p = p.sig;
                break;

            case 210:
                p=p.sig;
                break;

            case 211:
                p=p.sig;
                break;
            
            case 117:
                p = p.sig;
                expSimple();
                if (p != null && p.token == 118) {
                    p = p.sig;
                } else {
                    System.out.println("Se espera cierre de parentesis");
                    System.exit(0);
                }
                break;

            case 116:
                p = p.sig;
                factor();
                break;

            default:
                System.out.println("Se espera un factor");
                System.exit(0);
                break;
        }
    }

    private void variables() {
        if (p!= null && p.token == 207) {
            p = p.sig;
            tipos();
            if (p!= null && p.token == 100) {
                p = p.sig;
                idRecursivo();
                if (p!=null && p.token == 125) {
                    p = p.sig;
                    if (p.token == 207) {
                        variables();   
                    }
                } else {
                    System.out.println("Se espera un ;");
                    System.exit(0);
                }
            } else {
                System.out.println("Se espera un identificador");
                System.exit(0);
            }
        } else {
            System.out.println("Se espera new");
            System.exit(0);
        }
    }

    private void tipos() {
        switch (p.token) {
            case 208:
                p = p.sig;
                break;
            case 209:
                p = p.sig;
                break;
            case 212:
                p = p.sig;
                break;
            case 213:
                p = p.sig;
                break;
            default:
                System.out.println("Se espera un tipo de variable");
                System.exit(0);
                break;
        }
    }
}