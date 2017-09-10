import javax.swing.*;
import java.util.ArrayList;

public class Numeros {
    /* Considere la gramática dada para numeros octales y decimales:
    Reglas gramáticales:
        1.- num-base --> num carbase
        2.- carbase  --> o | d
        3.- num      --> num digito | digito
        4.- digito   --> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 9

    Reglas semánticas:
        1.- num-base  --> num-base.val = num.val
                          num.base = carbase.base
        2.- carbase   --> carbase.base = 8
        2.- carbase   --> carbase.base = 10
        3.- num   --> num1.val =
                            if digito.val = error or num2.val = error
                            then error
                            else num2.val * num1.base + digito.val
                        num2.base = num1.base
                        digito.base = num1.base
        3.- num   --> num.val = digito.val
                      digito.base = num.base
        4.- digito --> digito.val = '0|1|2|3|...|7'
        4.- digito --> digito.val = if digito.base = 8 then error else 8
        4.- digito --> digito.val = if digito.base = 8 then error else 9
    */
    ArrayList<Token> tokens;
    char base = 'd';
    boolean band;

    Numeros(){
        do {
            // band = para saber si la entrada es correcta
            // input = entrada del numero a trabajar
            // num[] = cadena de numeros a trabajar
            band = true;
            String input = JOptionPane.showInputDialog("Ingrese un numero con base (o | d | vacío): ");
            tokens = new ArrayList<>();

            if (input.length()==0){
                band = false;
            }
            for (int i=0; i<input.length(); i++){
                if(input.charAt(i) == 'o' || input.charAt(i) == 'd'){
                    // SI la base es el último carácter entrado:
                    if (i == input.length()-1){
                        base=input.charAt(i);
                        tokens.add(new Token("CARBASE",null,String.valueOf(base)));
                    }else{
                        band = false;
                    }
                    break;
                }
                try {
                    // Casteamos el caracter actual a un valor:
                    int valor = Integer.parseInt(String.valueOf(input.charAt(i)));
                    tokens.add(new Token("DIGITO",valor,null));
                }catch (Exception e){
                    band = false;
                    break;
                }
            }
        }while(!band);
        if (band) System.out.println("Aceptado.");
    }

    public void EvalWithBase(Treenode t){
        String kind = t.claseNodo;
        switch(kind){
            case "num-base":
                EvalWithBase(t.hijoDerecho);
                t.hijoIzquierdo.base = t.hijoDerecho.base;
                EvalWithBase(t.hijoIzquierdo);
                t.val = t.hijoIzquierdo.val;
                break;
            case "num":
                t.hijoIzquierdo.base = t.base;
                EvalWithBase(t.hijoDerecho);
                if (t.hijoDerecho != null){
                    t.hijoDerecho.base = t.base;
                    EvalWithBase(t.hijoDerecho);
                    if ((t.hijoIzquierdo.val != "error") && (t.hijoDerecho.val != "error")){
                        t.val = (t.base * t.hijoIzquierdo.val) + t.hijoDerecho.val;
                    }else{
                        t.val = "error";
                    }
                }else{
                    t.val = t.hijoIzquierdo.val;
                }
        }
    }

    /*
    * val = int
    * base = int
    * kindNode = numbase | num | carbase | digito
    * */
    public static void main(String[] args) {
        new Numeros();
    }

    class Treenode{
        String claseNodo;
        int val;
        int base;
        Treenode hijoIzquierdo;
        Treenode hijoDerecho;
    }
    class Token{
        String tipo;
        Integer valor;
        String base;

        Token(String tipo, Integer valor, String base) {
            this.tipo = tipo;
            this.valor = valor;
            this.base = base;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public Integer getValor() {
            return valor;
        }

        public void setValor(Integer valor) {
            this.valor = valor;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }
    }
}
