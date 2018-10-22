//To read the methods at runtime
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
public class Menu {
    private static Scanner scanner;

    //This makes menu generation easier
    public Menu() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws Exception {
        scanner = new Scanner(System.in);
        //ArrayList of all methods that are to be included in the menu
        ArrayList<Method>methods = new ArrayList<>();
        //Get all declared class methods
        for(Method m: Menu.class.getDeclaredMethods()) {
            //Check if the method should be added to the menu
            Annotation A = m.getAnnotation(MenuMethod.class);
            if(A!=null&&((MenuMethod)A).inMenu())
                methods.add(m);
        }
        //Infinite loop
        for(;;) {
            int cnt = 1, choice = 0;
            System.out.printf("Choose a program from the following menu:\n\nEnter a number from 1 - %d.\n", methods.size());
            //Dynamically generate the menu
            for(Method m:methods)
                System.out.printf("%d - %s\n", cnt++, m.getName());
            System.out.println("\nEnter 0 to exit.");
            choice = scanner.nextInt();
            if(choice==0) return;
            if(choice > methods.size()) continue;
            System.out.println();
            //Invoke the method
            methods.get(choice-1).invoke(new Menu());
            scanner.nextLine();
            System.out.println();
            System.out.println("Press enter to continue.");
            scanner.nextLine();
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface MenuMethod {
    boolean inMenu() default true;
}
