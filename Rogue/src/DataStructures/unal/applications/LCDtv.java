package DataStructures.unal.applications;

import java.util.*;

public class LCDtv<K extends Comparable<? super K>, E> implements Iterable<E> {
    private SortedLCD<K, E> firstNode;
    private int size;

    public LCDtv() {
        firstNode = null;
        size = 0;
    }

    public LCDtv(SortedLCD<K, E> a, int b) {
        firstNode = a;
        size = b;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E put(K miLlave, E harpia) {
        SortedLCD<K, E> jaguar = firstNode;
        SortedLCD<K, E> tePersigo = null;

        while (jaguar != null && jaguar.myKey.compareTo(miLlave) < 0) {
            tePersigo = jaguar;
            jaguar = jaguar.next;
        }
        if (jaguar != null && jaguar.myKey.equals(miLlave)) {
            E tmp = (E) jaguar.getElement();
            jaguar.setElement(harpia);
            return tmp;
        }
        if (tePersigo == null) {
            firstNode = new SortedLCD<>(miLlave, harpia, firstNode);
            size++;
            return null;
        } else {
            SortedLCD<K, E> cazadorNull = new SortedLCD<>(miLlave, harpia, jaguar);
            tePersigo.next = cazadorNull;
            size++;
            return null;
        }
    }

    public E get(K miLlave) {
        SortedLCD<K, E> fullHD = firstNode;

        while (fullHD != null && fullHD.getMyKey().compareTo(miLlave) < 0)
            fullHD = fullHD.next;

        if (fullHD != null && fullHD.getMyKey().equals(miLlave))
            return fullHD.getElement();
        return null;
    }

    public E remove(K miLlave) {
        SortedLCD<K, E> jaguar = firstNode;
        SortedLCD<K, E> tePersigo = null;

        while (jaguar != null && jaguar.getMyKey().compareTo(miLlave) < 0) {
            tePersigo = jaguar;
            jaguar = jaguar.next;
        }

        if (jaguar != null && jaguar.getMyKey().equals(miLlave)) {
            E tmp = jaguar.getElement();
            if (tePersigo == null) {
                firstNode = firstNode.next;
                size--;
            } else {
                tePersigo.next = jaguar.next;
                size--;
            }
            return tmp;
        }
        return null;

    }

    class SortedLCD<T extends Comparable<? super T>, A> {
        SortedLCD<T, A> next;
        T myKey;
        A element;

        SortedLCD() {
            next = null;
            element = null;
            myKey = null;
        }


        SortedLCD(T key, A aguas, SortedLCD<T, A> nextK) {
            myKey = key;
            element = aguas;
            next = nextK;
        }

        public A getElement() {
            return element;
        }

        public T getMyKey() {
            return myKey;
        }

        public void setElement(A myElement) {
            element = myElement;
        }
    }

    public Iterator<E> iterator() {

        return new LCDIterator();
    }

    class LCDIterator implements Iterator<E> {

        SortedLCD<K, E> ave;

        LCDIterator() {
            ave = firstNode;
        }

        public boolean hasNext() {
            return ave != null;
        }

        public E next() {
            E tmp = ave.getElement();
            ave = ave.next;
            return tmp;
        }

        public void remove() {
            throw new UnsupportedOperationException(":(");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        for (E x : this)
            builder.append(x + " ");
        builder.append("}");
        return new String(builder);
    }

    public static void main(String[] args) {
        LCDtv<Integer, Guacamaya> guacamayas = new LCDtv<>();
        LCDtv<Integer, Perro> perros = new LCDtv<>();

        while (true) {
            System.out.println("\n Amazonia and Orionoquia");
            System.out.println("Ingrese 1 para ingrsar una nueva Guacamaya.");
            System.out.println("Ingrese 2 para ingresar un nuevo perrito.");
            System.out.println("Ingrese 3 para modificar una Guacamaya.");
            System.out.println("Ingrese 4 para modificar un perrito.");
            System.out.println("Ingrese 5 para eliminar una Guacamaya.");
            System.out.println("Ingrese 6 para eliminar un perrito.");
            System.out.println("Ingrese 7 para consultar la edad promedio de las guacamayas.");
            System.out.println("Ingrese 8 para consultar la edad promedio de los perritos.");
            System.out.println("Ingrese 9 para jugar con una Guacamaya.");
            System.out.println("Ingrese 10 para jugar con un perrito.");
            System.out.println("Ingrese 11 para consultar una guacamayas.");
            System.out.println("Ingrese 12 para consultar un perrito.");
            System.out.println("Ingrese 13 para mirar todas las guacamayas.");
            System.out.println("Ingrese 14 para mirar todos los perrito.");
            System.out.println("Ingrese 0 para salir del programa.");
            try {
                int option = 0;
                int codigo = 0;
                Guacamaya embrion;
                Perro child;
                Scanner scan = new Scanner(System.in);
                option = scan.nextInt();
                switch (option) {
                    case 1:
                        embrion = new Guacamaya();
                        System.out.println("Ingrese el nombre");
                        embrion.name = scan.next();
                        System.out.println("Ingrese la edad");
                        embrion.age = scan.nextInt();
                        System.out.println("Ingrese el genero");
                        embrion.gender = scan.next();
                        System.out.println("Ingrese el color ");
                        embrion.colors = scan.next();
                        System.out.println("Ingrese el c�digo");
                        codigo = scan.nextInt();
                        guacamayas.put(codigo, embrion);
                        break;
                    case 2:
                        child = new Perro();
                        System.out.println("Ingrese el nombre");
                        child.name = scan.next();
                        System.out.println("Ingrese la edad");
                        child.age = scan.nextInt();
                        System.out.println("Ingrese el genero");
                        child.gender = scan.next();
                        System.out.println("Ingrese la raza");
                        child.raza = scan.next();
                        System.out.println("Ingrese el c�digo");
                        codigo = scan.nextInt();
                        perros.put(codigo, child);
                        break;
                    case 3:
                        System.out.println("Ingrese el c�digo de la guacamaya");
                        codigo = scan.nextInt();
                        embrion = guacamayas.get(codigo);
                        if (embrion == null)
                            System.out.println("El c�digo no es valido");
                        else {
                            System.out.println("Ingrese el nuevo nombre");
                            embrion.name = scan.next();
                            System.out.println("Ingrese la nueva edad");
                            embrion.age = scan.nextInt();
                            System.out.println("Ingrese el nuevo genero");
                            embrion.gender = scan.next();
                            System.out.println("Ingrese el nuevo color");
                            embrion.colors = scan.next();
                        }
                        break;
                    case 4:
                        System.out.println("Ingrese el c�digo del perro");
                        codigo = scan.nextInt();
                        child = perros.get(codigo);
                        if (child == null)
                            System.out.println("El c�digo no es valido");
                        else {
                            System.out.println("Ingrese el nuevo nombre");
                            child.name = scan.next();
                            System.out.println("Ingrese la nueva edad");
                            child.age = scan.nextInt();
                            System.out.println("Ingrese el nuevo genero");
                            child.gender = scan.next();
                            System.out.println("Ingrese la nueva raza");
                            child.raza = scan.next();
                        }
                        break;
                    case 5:
                        System.out.println("Ingrese el c�digo de la guacamaya");
                        codigo = scan.nextInt();
                        embrion = guacamayas.remove(codigo);
                        if (embrion != null)
                            System.out.println("La guacamaya " + embrion + " se ha muerto :(");
                        else
                            System.out.println("La guacamaya no se pudo reover porque no se encontro :)");
                        break;
                    case 6:
                        System.out.println("Ingrese el c�digo del perro");
                        codigo = scan.nextInt();
                        child = perros.remove(codigo);
                        if (child != null)
                            System.out.println("el perro " + child + " se ha muerto :(");
                        else
                            System.out.println("el perro no se pudo remover porque no se encontro :)");
                        break;
                    case 7:
                        codigo = 0;
                        for (Guacamaya x : guacamayas)
                            codigo += x.age;
                        codigo /= guacamayas.size();
                        System.out.println("La edad promedio es " + codigo + " a�os");
                        break;
                    case 8:
                        codigo = 0;
                        for (Perro x : perros)
                            codigo += x.age;
                        codigo /= perros.size();
                        System.out.println("La edad promedio es " + codigo + " a�os");
                        break;
                    case 9:
                        System.out.println("Ingrese el c�digo de la guacamaya");
                        codigo = scan.nextInt();
                        embrion = guacamayas.get(codigo);
                        if (embrion == null)
                            System.out.println("El c�digo no es valido");
                        else {
                            System.out.println("Ingrese 1 para comer.");
                            System.out.println("Ingrese 2 para dormir.");
                            System.out.println("Ingrese 3 para sobrevivir");
                            System.out.println("Ingrese 4 para volar");
                            System.out.println("Ingrese 5 para cantar.");
                            System.out.println("Ingrese 6 para coquetiar.");
                            codigo = scan.nextInt();
                            switch (codigo) {
                                case (1):
                                    embrion.eat();
                                    break;
                                case (2):
                                    embrion.sleep();
                                    break;
                                case (3):
                                    embrion.survive();
                                    break;
                                case (4):
                                    embrion.fly();
                                    break;
                                case (5):
                                    embrion.sing();
                                    break;
                                case (6):
                                    embrion.flirt();
                                    break;
                                default:
                                    System.out.println("Your option is not valid");
                                    break;
                            }
                        }
                        break;
                    case 10:
                        System.out.println("Ingrese el c�digo del perro");
                        codigo = scan.nextInt();
                        child = perros.get(codigo);
                        if (child == null)
                            System.out.println("El c�digo no es valido");
                        else {
                            System.out.println("Ingrese 1 para comer.");
                            System.out.println("Ingrese 2 para dormir.");
                            System.out.println("Ingrese 3 para sobrevivir");
                            System.out.println("Ingrese 4 para find family");
                            System.out.println("Ingrese 5 para be social.");
                            System.out.println("Ingrese 6 para be nice.");
                            codigo = scan.nextInt();
                            switch (codigo) {
                                case (1):
                                    child.eat();
                                    break;
                                case (2):
                                    child.sleep();
                                    break;
                                case (3):
                                    child.survive();
                                    break;
                                case (4):
                                    child.findFamily();
                                    break;
                                case (5):
                                    child.beSocial();
                                    break;
                                case (6):
                                    child.beNice();
                                    break;
                                default:
                                    System.out.println("Your option is not valid");
                                    break;
                            }
                        }
                        break;
                    case 11:
                        System.out.println("Ingrese el c�digo de la guacamaya");
                        codigo = scan.nextInt();
                        embrion = guacamayas.get(codigo);
                        if (embrion == null)
                            System.out.println("El c�digo no es valido");
                        else
                            System.out.println("La guacamaya es: " + embrion);
                        break;
                    case 12:
                        System.out.println("Ingrese el c�digo del perro");
                        codigo = scan.nextInt();
                        child = perros.get(codigo);
                        if (child == null)
                            System.out.println("El c�digo no es valido");
                        else
                            System.out.println("El perro es: " + child);
                        break;
                    case 13:
                        System.out.println("Guacamayas unal:");
                        System.out.println(guacamayas);
                        break;
                    case 14:
                        System.out.println("Perritos unal:");
                        System.out.println(perros);
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Your option is not valid");
                        break;
                }

            } catch (Exception e) {
                System.out.println("\n upps un error Inicie otra vez");
            }
        }
    }
}

interface Animal {
    public void eat();

    public void sleep();

    public void survive();
}

interface Ave extends Animal {
    public void fly();

    public void sing();

    public void flirt();
}

interface Mamifero extends Animal {
    public void findFamily();

    public void beSocial();
}

class Perro implements Mamifero {
    String name;
    int age;
    String gender;
    String raza;

    public Perro() {
        this(null, 0, null, null);
    }

    public Perro(String n, int a, String g, String r) {
        name = n;
        age = a;
        gender = g;
        raza = r;
    }

    public void eat() {
        System.out.println("I'm eating meat, yes!");
    }

    public void sleep() {
        System.out.println("I sleep at day and night ");
    }

    public void survive() {
        System.out.println("Wuaffff");
    }

    public void findFamily() {
        System.out.println("I want a girl that really likes what i am :)");
    }

    public void beSocial() {
        System.out.println("I want friends to play and fight for a while");
    }

    public void beNice() {
        System.out.println("I really love my human brother");
    }

    public String toString() {
        return "{" + name + ", edad: " + age + " a�os, " + gender + ", raza: " + raza + "}";
    }

}

class Guacamaya implements Ave {
    String name;
    int age;
    String gender;
    String colors;

    public Guacamaya() {
        this(null, 0, null, null);
    }

    public Guacamaya(String n, int a, String g, String c) {
        name = n;
        age = a;
        gender = g;
        colors = c;
    }

    public void eat() {
        System.out.println("I'm eating seeds, yeah!");
    }

    public void sleep() {
        System.out.println("I get sleepy very early ");
    }

    public void survive() {
        System.out.println("We don't like to be out of our habitat in jail");
    }

    public void fly() {
        System.out.println("I'm free huiii");
    }

    public void sing() {
        System.out.println("Fiigaro Fiigaro");
    }

    public void flirt() {
        System.out.println("I really like that girl");
    }

    public String toString() {
        return "{" + name + ", edad: " + age + " a�os, " + gender + ", colores: " + colors + "}";
    }
}
