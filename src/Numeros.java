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
                    if ((t.hijoIzquierdo.val_name != "error") && (t.hijoDerecho.val_name != "error")){
                        t.val = (t.base * t.hijoIzquierdo.val) + t.hijoDerecho.val;
                    }else{
                        t.val_name = "error";
                    }
                }else{
                    t.val = t.hijoIzquierdo.val;
                }
                break;
            case "carbase":
                if (t.hijoDerecho.base_name.equals("o")) t.base = 8;
                else t.base = 10;
                break;
            case "digito":
                if ((t.base == 8) && (t.val == 8 || t.val == 9)) {
                    t.val_name = "error";
                }else{
                    t.val = Integer.parseInt(t.val_name); // <-- Verificar -- t.val := numval ( hijo de T )
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
        Integer val;
        String val_name;
        Integer base;
        String base_name;
        Treenode hijoIzquierdo;
        Treenode hijoDerecho;

        public Treenode(String claseNodo, String val_name, String base_name, Treenode hijoIzquierdo, Treenode hijoDerecho) {
            this.claseNodo = claseNodo;
            this.val_name = val_name;
            this.base_name = base_name;
            this.hijoIzquierdo = hijoIzquierdo;
            this.hijoDerecho = hijoDerecho;
        }
    }
}
