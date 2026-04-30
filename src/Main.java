import br.com.dio.dao.UserDAO;
import br.com.dio.model.MenuOption;
import br.com.dio.model.UserModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

  private final static UserDAO dao = new UserDAO();
  private final static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {

    while (true) {
      System.out.println("\n1 - Cadastrar");
      System.out.println("2 - Atualizar");
      System.out.println("3 - Excluir");
      System.out.println("4 - Buscar");
      System.out.println("5 - Listar");
      System.out.println("6 - Sair");

      var userInput = scanner.nextInt();

      if (userInput < 1 || userInput > 6) {
        System.out.println("Opção inválida");
        continue;
      }

      var selectedOption = MenuOption.values()[userInput - 1];

      switch (selectedOption) {

        case CADASTRAR -> {
          var user = dao.save(requestUser());
          System.out.printf("Usuário cadastrado: %s%n", user);
        }

        case ATUALIZAR -> {
          var id = requestId();
          var user = requestUser();
          user.setId(id);
          dao.update(user);
          System.out.printf("Usuário atualizado: %s%n", user);
        }

        case EXCLUIR -> {
          var id = requestId();
          dao.delete(id);
          System.out.println("Usuário excluído");
        }

        case BUSCAR -> {
          var id = requestId();
          var user = dao.findById(id);
          System.out.printf("Usuário encontrado: %s%n", user);
        }

        case LISTAR -> {
          var users = dao.findAll();
          System.out.println("Usuários cadastrados:");
          users.forEach(System.out::println);
        }

        case SAIR -> {
          System.out.println("Saindo...");
          return;
        }
      }
    }
  }

  private static long requestId() {
    System.out.println("Informe o ID:");
    return scanner.nextLong();
  }

  private static UserModel requestUser() {
    System.out.println("Nome:");
    var name = scanner.next();

    System.out.println("Email:");
    var email = scanner.next();

    System.out.println("Data nascimento (dd/MM/yyyy):");
    var birthdayString = scanner.next();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    var birthday = LocalDate.parse(birthdayString, formatter);

    return new UserModel(0, name, email, birthday);
  }
}