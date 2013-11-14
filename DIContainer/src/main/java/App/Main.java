package App;


import DI.Container;
import Parsers.ClassParser;

public class Main {

    public static void main(String[] args){
        Container cont = new Container();
        cont.ContainerStart();
    }
}
