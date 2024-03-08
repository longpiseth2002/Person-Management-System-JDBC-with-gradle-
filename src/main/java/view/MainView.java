package view;

import utils.TableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainView {
   private final static List<String> mainMenu = new ArrayList<>(List.of(
            "Add New Person ",
            "Update Person ",
            "Delete Person",
            "Show Person Information",
            "Search Person Information",
            "Exit"));
   public static int renderMain(Scanner input){
       TableUtils.renderMenu(mainMenu, "Person Management System");
       System.out.print("Enter your option : ");
       return input.nextInt();
   }
}
